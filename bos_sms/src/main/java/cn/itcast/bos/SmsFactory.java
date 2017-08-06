package cn.itcast.bos;


import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by 犹良 on 2017/8/3 0003.
 */

@Service("smsFactory")
public class SmsFactory implements MessageListener{

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage=(MapMessage)message;
        try {
            String telephone=mapMessage.getString("telephone");
            String count=mapMessage.getString("count");
            System.out.println("发送短信!用户手机号为==>>>"+telephone+"激活码为====>>>"+count);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
