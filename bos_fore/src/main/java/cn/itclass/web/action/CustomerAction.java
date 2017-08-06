package cn.itclass.web.action;

import cn.itcast.crm.domain.Constans;
import cn.itcast.crm.domain.Customer;

import cn.itclass.utiles.MailUtils;
import cn.itclass.web.action.cn.itclass.web.action.base.BaseAction;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by 犹良 on 2017/8/1 0001.
 */
@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/customer")
public class CustomerAction extends BaseAction<Customer> {
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;
    @Action(value = "sendSms")
    public String sendSms() {
        String randomNumeric = RandomStringUtils.randomNumeric(4);
        final String count = "欢迎使用速运快递!!您的验证码是" + randomNumeric + "[速运公司:联系电话6666666]";
        //将发送短信验证码的业务分类出去执行;
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone",t.getTelephone());
                mapMessage.setString("count",count);
                return mapMessage;
            }
        });
        /*System.out.print(SmsUtils.sendSmsByHTTP(t.getTelephone(),count));*/
       /* System.out.println(randomNumeric);
        ServletActionContext.getRequest().getSession().setAttribute("telephone", randomNumeric);*/
        return NONE;
    }

    private String yanzhengma;

    public void setYanzhengma(String yanzhengma) {
        this.yanzhengma = yanzhengma;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Action(value = "saveCustomer", results = {
            @Result(name = "success", location = "/signup-success.html", type = "redirect"),
            @Result(name = "error", location = "/signup.html", type = "redirect")})
    public String saveCustomer() {
        String telephone = (String) ServletActionContext.getRequest().getSession().getAttribute("telephone");
        if (yanzhengma.equals(telephone)) {
            String activecode = RandomStringUtils.randomNumeric(32);
            redisTemplate.opsForValue().set(t.getTelephone(), activecode, 24, TimeUnit.DAYS);
            String content = "尊敬的客户您好，请于24小时内，进行邮箱账户的绑定，点击下面地址完成绑定:<br/><a href='"
                    + MailUtils.activeUrl + "?telephone=" + t.getTelephone()
                    + "&activecode=" + activecode + "'>速运快递邮箱绑定地址</a>";
            MailUtils.sendMail("激活账户", content, t.getEmail());
            ServletActionContext.getRequest().getSession().invalidate();
            WebClient.create("http://localhost:9999/crm_management/services/customer//saveCustomer").type(MediaType.APPLICATION_JSON_TYPE).post(t);

            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    @Action(value = "activeMail")
    public String activeMail() {
        ServletActionContext.getResponse().setContentType(
                "text/html;charset=utf-8");
        System.out.println("运行到啦!!");
        Customer customer = WebClient.create("http://localhost:9999/crm_management/services/customer/findCustomer/" + t.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (customer != null && customer.getType() != null && customer.getType() != 1) {
            try {
                WebClient.create("http://localhost:9999/crm_management/services/customer/activeMail/" + t.getTelephone()).accept(MediaType.APPLICATION_JSON_TYPE).post(null);
                ServletActionContext.getResponse().getWriter().print("注册成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ServletActionContext.getResponse().getWriter().print("请勿重复点击注册!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return NONE;
    }

    // /customer/login           .
    // /customer/index
    // /index
    @Action(value = "login",results = {
            @Result(name = "login", location = "/login.html", type = "redirect"),
            @Result(name = "success", location = "/index.html#/myhome", type = "redirect") })
    public String login(){
        //发送WEB_SERVICE查询CRM服务器
        Customer customer = WebClient.create(Constans.CRM_MANAGEMENT_URL + "/services/customer/login?telephone=" + t.getTelephone() + "&password=" + t.getPassword()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (customer==null){
            return LOGIN;
        }else{
            ServletActionContext.getRequest().getSession().setAttribute("Customer",customer);
            return SUCCESS;
        }

    }

}
