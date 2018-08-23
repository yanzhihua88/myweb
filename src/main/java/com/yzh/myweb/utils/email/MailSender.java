package com.yzh.myweb.utils.email;

import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送类 MailSender.java
 * 
 * @author mark.huang
 * @create 2017年9月4日 下午2:50:00
 */
@Slf4j
public class MailSender {

	// 发送邮件的props文件
	private final transient Properties props = System.getProperties();

	// 邮件服务器登录验证
	private transient MailAuthenticator authenticator;

	// 邮箱会话
	private transient Session session;

	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 *            SMTP邮件服务器地址
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            发送邮件的密码
	 */
	public MailSender(final String smtpHostName, final String username, final String password) {
		init(username, password, smtpHostName);
	}

	/**
	 * 初始化
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            密码
	 * @param smtpHostName
	 *            SMTP主机地址
	 */
	private void init(String username, String password, String smtpHostName) {
		// 初始化props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", "25");
		props.put("java.net.preferIPv4Stack", "true");

		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	@Deprecated
	public void send(String sender, String recipient, String subject, Object content)

			throws AddressException, MessagingException {

		final MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(sender));

		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));

		message.setSubject(subject);

		message.setContent(content.toString(), "text/html;charset=utf-8");
	
		Transport.send(message);
		
		log.debug("\n>>>>>>>>>>>>>>>>>0:邮件发送成功!");

		
	}

	/**
	 * 发送一封邮件
	 * 
	 * @param mail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(MailDTO mail) throws AddressException, MessagingException {
		String sender = mail.getSender();
		String[] recipients = mail.getToRecipients();
		String subject = mail.getSubject();
		String content = mail.getContent();
		String[] attaches = mail.getAttaches();

		final MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(sender));
	
		
		// 设置收件人们
		final int num = recipients.length;
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients[i]);

		}
		message.setRecipients(RecipientType.TO, addresses);

		message.setSubject(subject);
	
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 设置邮件附件
		// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart multiPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart bodyPart = new MimeBodyPart();
		// 设置html邮件消息内容
		bodyPart.setContent(content.toString(), "text/html; charset=utf-8");
		multiPart.addBodyPart(bodyPart);
		// 添加附件
		if (attaches != null && attaches.length != 0) {
			for (String attach : attaches) {
				bodyPart = new MimeBodyPart();
				// 得到数据源
				FileDataSource fds = new FileDataSource(attach);
				// 得到附件本身并放入BodyPart
				bodyPart.setDataHandler(new DataHandler(fds));
				// 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
				try {
					bodyPart.setFileName(MimeUtility.encodeText(fds.getName()));
				} catch (UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}
				multiPart.addBodyPart(bodyPart);
			}
		}
		// 设置邮件消息的主要内容
		message.setContent(multiPart);
		log.debug("\n邮件内容>>>>>>>>>>>>>>>>>:"+content);
		Transport.send(message);
		log.debug("\n>>>>>>>>>>>>>>>>>1:邮件发送成功!");
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	@Deprecated
	public void send(String sender, List<String> recipients, String subject, Object content)
			throws AddressException, MessagingException {

		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(sender));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));

		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
		log.debug("\n>>>>>>>>>>>>>>>>>2:邮件发送成功!");

	}
}
