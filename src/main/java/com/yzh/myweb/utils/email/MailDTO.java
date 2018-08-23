package com.yzh.myweb.utils.email;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName:  Mail   
 * @Description: 邮件信息实体类 Mail.java 
 * @author: david.li 
 * @date:   2018年6月15日 上午9:45:44
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MailDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 邮件发送者
	 */
	private String sender;
	/**
	 * 邮件接收者
	 */
	private String[] toRecipients;
	/**
	 * 邮件抄送者
	 */
	private String[] ccRecipients;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 邮件附件
	 */
	private String[] attaches;
	/**
	 * 邮件内容
	 */
	private String content;
	
	
	@Override
	public String toString() {
		
		Map<String, String> data = Maps.newHashMap();
		
		data.put("subject", subject);
		
		data.put("toRecipients", toRecipients!=null?Arrays.toString(toRecipients):"");
		
		data.put("content", content);
	
		return data.toString();
	}

}
