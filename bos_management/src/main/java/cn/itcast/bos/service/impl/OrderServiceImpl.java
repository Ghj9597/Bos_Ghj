package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaDao;
import cn.itcast.bos.dao.FixedAreaDao;
import cn.itcast.bos.dao.OrderDao;
import cn.itcast.bos.dao.WorkBillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Constans;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.take_delivery.Order;
import cn.itcast.bos.domain.base.take_delivery.WorkBill;
import cn.itcast.bos.service.OrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 犹良 on 2017/8/7 0007.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    //注入
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;
    //注入Workbuild;
    @Autowired
    private WorkBillDao workBillDao;
    //注入fixedAreaDao
    @Autowired
    private FixedAreaDao fixedAreaDao;
    //注入OrderDao;
    @Autowired
    private OrderDao orderDao;
    //注入AreaDao;
    @Autowired
    private AreaDao areaDao;

    //抽取出来封装方法保存订单;
    private void saveOrder(Order order, Courier courier) {
        //得到快递员
        order.setCourier(courier);
        //并且设置类型为自动分单;
        order.setOrderType("1");
        //保存订单;
        orderDao.save(order);
    }

    //抽取出来生成工单的方法;发送短信
    private void generateWorkBill(final Order order) {

        // 生成工单
        WorkBill workBill = new WorkBill();
        workBill.setType("新");
        workBill.setPickstate("新单");
        workBill.setBuildtime(new Date());
        workBill.setRemark(order.getRemark());
        final String smsNumber = RandomStringUtils.randomNumeric(4);
        workBill.setSmsNumber(smsNumber); // 短信序号
        workBill.setOrder(order);
        workBill.setCourier(order.getCourier());
        workBillDao.save(workBill);
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone", order.getCourier().getTelephone());
                mapMessage.setString(
                        "msg",
                        "短信序号：" + smsNumber + ",取件地址：" + order.getSendAddress()
                                + ",联系人:" + order.getSendName() + ",手机:"
                                + order.getSendMobile() + "，快递员捎话："
                                + order.getSendMobileMsg());
                return mapMessage;
            }
        });
        // 修改工单状态
        workBill.setPickstate("已通知");
    }



    @Override
    public void add(Order order) {
        System.out.println(order.getSendArea().getProvince());
        System.out.println(order.getSendArea().getCity());
        System.out.println(order.getSendArea().getDistrict());
        // 寄件人 省市区
        Area persistArea = areaDao.findByProvinceAndCityAndDistrict(order.getSendArea().getProvince(), order.getSendArea().getCity(), order.getSendArea().getDistrict());
        // 收件人 省市区
        Area persistRecArea = areaDao
                .findByProvinceAndCityAndDistrict(order.getRecArea().getProvince(),
                        order.getRecArea().getCity(), order.getRecArea().getDistrict());
        //查询出来后替换掉Order中那些没得id的Area
        order.setSendArea(persistArea);
        order.setRecArea(persistRecArea);

        //订单服务器代码实现;
        //一既然订单过来了那就先给个id呗;
        String uuid = UUID.randomUUID().toString();
        order.setOrderNum(uuid);
        //既然id都给了那就再给个下单时间呗;
        order.setOrderTime(new Date());
        //既然都生成订单了那就再给个订单状态
        order.setStatus("1");//待取件;
        //基于address输入的详细路径匹配crm所存储的定区进行匹配;
        String fixedAreaId = WebClient.create(Constans.CRM_MANAGEMENT_URL + "/services/customer/findFixedAreaIdByAddress?address=" + order.getSendAddress()).accept(MediaType.APPLICATION_JSON).get(String.class);
        //判断是否为空;
        if (fixedAreaId != null) {
            //不是空的话通过定区查询快递员
            Iterator<Courier> iterator = fixedAreaDao.findOne(fixedAreaId).getCouriers().iterator();
            if(iterator.hasNext()){
                Courier courier = iterator.next();
                //将查询到的快递员对象保存进订单,生成工单,发送短信;
                saveOrder(order, courier);
                //发送短信
                generateWorkBill(order);
                return;
            }

        }
        //如果没有分单成功;
        //得到收件人地址;
        Area area = order.getSendArea();
        //根据省市区查询数据库中所有的分区!!重点!得到的是一个持久态对象,直接调用依赖查询即可
        Area area1= areaDao.findByProvinceAndCityAndDistrict(area.getProvince(), area.getCity(), area.getDistrict());
        Set<SubArea> subAreas = area1.getSubareas();
        for (SubArea subArea : subAreas) {
            //判断用户输入的地址中是否包含关键字与指定关键字;
            if (order.getSendAddress().contains(subArea.getKeyWords()) || order.getSendAddress().contains(subArea.getAssistKeyWords())) {
                //包含的话根据order查询定区;
                Iterator<Courier> iterator = subArea.getFixedArea().getCouriers().iterator();
                if(iterator.hasNext()){
                    Courier courier=iterator.next();
                    if(courier!=null){
                        //得到快递员
                        saveOrder(order, courier);
                        //生成工单//发送短信
                        generateWorkBill(order);
                        return;
                    }
                }

            }
        }
        //都没分单成功,则将进入人工分单;
        order.setOrderType("2");
        orderDao.save(order);
    }

    @Override
    public Order findByOrderNum(String orderNum) {
        Order order=orderDao.findByOrderNum(orderNum);
        return order;
    }
}
