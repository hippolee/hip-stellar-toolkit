package org.hippo.toolkit.some;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by litengfei on 2017/8/15.
 */
public class EMailDemo {

    private static final Logger logger = LoggerFactory.getLogger(EMailDemo.class);

    private static final String MAIL_SERVER = "mail.yonyou.com";

    private static final String MAIL_FROM = "upservice@yonyou.com";

    private static final String MAIL_USERNAME = "upservice";

    private static final String MAIL_PASSWORD = "yonyou2014%";

    public static void main(String[] args) {
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", MAIL_SERVER);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        // 设置环境信息
        Session session = Session.getInstance(props);

        try {
            // 创建邮件对象
            Message msg = new MimeMessage(session);
            msg.setSubject("JavaMail测试");
            // 设置邮件内容
            msg.setText("这是一封由JavaMail发送的邮件！");
            // 设置发件人
            msg.setFrom(new InternetAddress(MAIL_FROM));

            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(MAIL_USERNAME, MAIL_PASSWORD);
            // 发送邮件
            transport.sendMessage(msg, new Address[]{new InternetAddress("litfb@yonyou.com")});
            // 关闭连接
            transport.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
