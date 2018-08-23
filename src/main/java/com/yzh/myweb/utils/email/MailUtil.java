package com.yzh.myweb.utils.email;


/**
 * 邮件测试发送类 MailUtil.java
 * 
 * @author mark.huang
 * @create 2017年9月4日 下午3:09:19
 */
public class MailUtil {
	/**
	 * 发送邮件
	 */
	public static void sendMail() {
//		EmailConfiguration configuration=new EmailConfiguration();
//		MailSender mailSender = new MailSender(configuration.getHost(), configuration.getUsername(), configuration.getPassword());
//		MailDTO mail = new MailDTO();
//		String[] toRecipients = { "lucky.ou@shengyecapital.com" };
//		mail.setSender(configuration.getAddr());
//		mail.setToRecipients(toRecipients);
//		mail.setSubject("邮件测试");
//		mail.setContent("fdsfasdf");
//
//		try {
//			mailSender.send(mail.getSender(), mail.getToRecipients()[0], mail.getSubject(), mail.getContent());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}
	 public static void main(String[] args) {
	 MailUtil.sendMail();
	 }
}
