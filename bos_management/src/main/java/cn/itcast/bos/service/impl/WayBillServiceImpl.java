package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.WayBillDao;
import cn.itcast.bos.dao.es.WayBillRepository;
import cn.itcast.bos.domain.base.take_delivery.WayBill;
import cn.itcast.bos.service.WayBillService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 犹良 on 2017/8/8 0008.
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
    //注入Dao层
    @Autowired
    private WayBillDao wayBillDao;
    //注入ElasticSearch的Dao层;
    @Autowired
    private WayBillRepository wayBillRepository;
    @Override
    public void save(WayBill t) {
       //先判断运单号是否存在;
        // 判断运单号是否存在
        WayBill persistWayBill = wayBillDao.findByWayBillNum(t.getWayBillNum());
        if (persistWayBill == null || persistWayBill.getId() == null) {
            // 运单不存在
            t.setSignStatus(1); // 待发货
            wayBillDao.save(t);
            // 保存索引
            wayBillRepository.save(t);
        } else {
            // 运单存在
            try {
                // 判断运单状态是否 为待发货
                if (persistWayBill.getSignStatus() == 1) {
                    Integer id = persistWayBill.getId();
                    BeanUtils.copyProperties(persistWayBill, t);
                    persistWayBill.setId(id);
                    persistWayBill.setSignStatus(1);// 待发货
                    // 保存索引
                    wayBillRepository.save(persistWayBill);
                } else {
                    // 运单状态 已经运输中，不能修改
                    throw new RuntimeException("运单已经发出，无法修改保存！！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public Page<WayBill> findAll(Pageable pageable,WayBill wayBill) {
        //判断查询条件是否存在;
        if (StringUtils.isBlank(wayBill.getWayBillNum())
                && StringUtils.isBlank(wayBill.getSendAddress())
                    && StringUtils.isBlank(wayBill.getRecAddress())
                    && StringUtils.isBlank(wayBill.getSendProNum())
                    && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
                //如果查询条件为空.则条件查询数据库;
            Page<WayBill> page = wayBillDao.findAll(pageable);
        } else {
            //如果有查询条件,则创建布尔查询;
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            //判断是否有条件的条件;
            if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
                //如果不为空;则取出条件进行查询;
                QueryBuilder queryBuilder = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
                //添加进入布尔查询;
                boolQueryBuilder.must(queryBuilder);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendAddress())) {
                // 发货地 模糊查询
                // 情况一： 输入"北" 是查询词条一部分， 使用模糊匹配词条查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                        "sendAddress", "*" + wayBill.getSendAddress() + "*");

                // 情况二： 输入"北京市海淀区" 是多个词条组合，进行分词后 每个词条匹配查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(
                        wayBill.getSendAddress()).field("sendAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);

                // 两种情况取or关系
                BoolQueryBuilder boolQueryBuilder2 = new BoolQueryBuilder();
                boolQueryBuilder2.should(wildcardQuery);
                boolQueryBuilder2.should(queryStringQueryBuilder);

                boolQueryBuilder.must(boolQueryBuilder);
            }
            if (StringUtils.isNoneBlank(wayBill.getRecAddress())) {
                // 收货地 模糊查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                        "recAddress", "*" + wayBill.getRecAddress() + "*");
                boolQueryBuilder.must(wildcardQuery);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
                // 速运类型 等值查询
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
                        wayBill.getSendProNum());
                boolQueryBuilder.must(termQuery);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
                // 速运类型 等值查询
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
                        wayBill.getSendProNum());
                boolQueryBuilder.must(termQuery);
            }
            if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
                // 签收状态查询
                QueryBuilder termQuery = new TermQueryBuilder("signStatus",
                        wayBill.getSignStatus());
                boolQueryBuilder.must(termQuery);
            }
            //创建SearchQuery对象;
            SearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
            searchQuery.setPageable(pageable);
            return wayBillRepository.search(searchQuery);
        }
      return wayBillDao.findAll(pageable);
    }



    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        WayBill wayBill=wayBillDao.findByWayBillNum(wayBillNum);
        return wayBill;
    }
    //编写一个方法用于同步ElasticSearch中的数据与数据库中的数据;
    public void synchro(){
        //查询出数据库中对应表中的所有数据;
        List<WayBill> all = wayBillDao.findAll();
        //将所有查询出来的数据添加进入ElasticSearch;
        wayBillRepository.save(all);

    }
}
