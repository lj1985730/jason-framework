package com.yoogun.utils.infrastructure;

import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.mail.*;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 发送电子邮件
 */
public class MailUtils {

	private static String address;	//发送邮箱地址
	private static String account;	//发送邮箱账户
	private static String password;		//发送邮箱密码
	private static String name;	//发送人名称

	private static String smtpServer;	//smtp服务器
	private static Integer smtpPort;	//smtp端口

	private static String pop3Server;	//pop3服务器
	private static Integer pop3Port;	//pop3端口

	private static final String charset = "UTF-8";	//邮件编码

	/**
	 * 发送普通邮件
	 * @param toMailAddr 收信人地址
	 * @param subject email主题
	 * @param message 发送email信息
	 */
	public static void send(String toMailAddr, String subject, String message) {
		HtmlEmail email = new HtmlEmail();
		try {
			processMail(email);

			email.addTo(toMailAddr);
			email.setSubject(subject);
			email.setMsg(message);

			email.send();
		} catch (Exception e) {
			throw new BusinessException("发送邮件失败！", e);
		}
	}

	/**
	 * 发送普通邮件
	 * @param toMailAddr 收信人地址
	 * @param subject email主题
	 * @param message 发送email信息
	 */
	public static void sendWithAttachment(String toMailAddr, String subject, String message, String attachmentUrl) throws MalformedURLException {

		EmailAttachment attachment = new EmailAttachment();
		attachment.setURL(new URL(attachmentUrl));

		MultiPartEmail email = new MultiPartEmail();
		try {
			processMail(email);

			email.addTo(toMailAddr);
			email.setSubject(subject);
			email.setMsg(message);

			email.attach(attachment);		//add attachment

			email.send();
		} catch (Exception e) {
			throw new BusinessException("发送邮件失败！", e);
		}
	}

	/**
	 * 预处理邮件
	 * @param email 邮件对象
	 * @throws EmailException 异常
	 */
	private static void processMail(Email email) throws EmailException {
		email.setHostName(smtpServer);
		email.setSmtpPort(smtpPort);
		email.setCharset(charset);
		email.setFrom(address, name);
		email.setAuthentication(account, password);
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.ADDRESS' ] }")
	public void setAddress(String address) {
		MailUtils.address = address;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.ACCOUNT' ] }")
	public static void setAccount(String account) {
		MailUtils.account = account;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.PASSWORD' ] }")
	public static void setPassword(String password) {
		MailUtils.password = password;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.NAME' ] }")
	public static void setName(String name) {
		MailUtils.name = name;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.SMTP.SERVER' ] }")
	public static void setSmtpServer(String smtpServer) {
		MailUtils.smtpServer = smtpServer;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.SMTP.PORT' ] }")
	public static void setSmtpPort(Integer smtpPort) {
		MailUtils.smtpPort = smtpPort;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.POP3.SERVER' ] }")
	public static void setPop3Server(String pop3Server) {
		MailUtils.pop3Server = pop3Server;
	}

	@Value("#{ utilInitializeProperties[ 'MAIL.POP3.PORT' ] }")
	public static void setPop3Port(Integer pop3Port) {
		MailUtils.pop3Port = pop3Port;
	}
}