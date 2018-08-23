package com.yzh.myweb.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnnotationUtil {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> validate(Object bean) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", "验证通过");
		result.put("result", true);
		Class<?> cls = bean.getClass();

		// 检测field是否存在
		try {
			// 获取实体字段集合
			Field[] fields = cls.getDeclaredFields();
			for (Field f : fields) {
				// 通过反射获取该属性对应的值
				f.setAccessible(true);
				// 获取字段值
				Object value = f.get(bean);
				// 获取字段上的注解集合
				Annotation[] arrayAno = f.getAnnotations();
				for (Annotation annotation : arrayAno) {
					// 获取注解类型（注解类的Class）
					Class<?> clazz = annotation.annotationType();

					if (clazz.getSimpleName().equals("IsEmpty") 
							|| clazz.getSimpleName().equals("FiledName") 
							|| clazz.getSimpleName().equals("MaxSize")
							|| clazz.getSimpleName().equals("MinSize") 
							|| clazz.getSimpleName().equals("Email") 
							|| clazz.getSimpleName().equals("PersonName") 
							|| clazz.getSimpleName().equals("Mobile")) {
					

						// 获取注解类中的方法集合
						Method[] methodArray = clazz.getDeclaredMethods();
						for (Method method : methodArray) {
							// 获取方法名
							String methodName = method.getName();
							// 过滤错误提示方法的调用
							if (methodName.equals("message") || methodName.equals("name")) {
								continue;
							}
							// 初始化注解验证的方法处理类 （我的处理方法卸载本类中）
							Object obj = AnnotationUtil.class.newInstance();
							// 获取方法
							try {
								// 根据方法名获取该方法
								Method m = obj.getClass().getDeclaredMethod(methodName, Object.class, Field.class);
								// 调用该方法
								result = (Map<String, Object>) m.invoke(obj, value, f);
								/* 验证结果 有一处失败则退出 */
								if (result.get("result").equals(false)) {
									return result;
								}
							} catch (Exception e) {
								e.printStackTrace();
								log.info("找不到该方法:" + methodName);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("验证出错");
		}
		return result;
	}

	public Map<String, Object> isEmpty(Object value, Field field) {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		IsEmpty annotation = field.getAnnotation(IsEmpty.class);
		FiledName fn = field.getAnnotation(FiledName.class);
		if (value == null || value.equals("")) {
			validateResult.put("message", fn.name() + annotation.message());
			validateResult.put("result", false);
		} else {
			validateResult.put("message", "验证通过");
			validateResult.put("result", true);
		}
		return validateResult;
	}

	public Map<String, Object> min(Object value, Field field) {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		MinSize annotation = field.getAnnotation(MinSize.class);
		FiledName fn = field.getAnnotation(FiledName.class);
		if (value != null && value.toString().length() < annotation.min()) {
			validateResult.put("message", fn.name() + annotation.message() + "最小长度为：" + annotation.min() + "个字符");
			validateResult.put("result", false);
		} else {
			validateResult.put("message", "验证通过");
			validateResult.put("result", true);
		}
		return validateResult;
	}

	public Map<String, Object> max(Object value, Field field) {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		MaxSize annotation = field.getAnnotation(MaxSize.class);
		FiledName fn = field.getAnnotation(FiledName.class);
		if (value != null && value.toString().length() > annotation.max()) {
			validateResult.put("message", fn.name() + annotation.message() + "最大长度为：" + annotation.max() + "个字符");
			validateResult.put("result", false);
		} else {
			validateResult.put("message", "验证通过");
			validateResult.put("result", true);
		}
		return validateResult;
	}

	public Map<String, Object> email(Object value, Field field) {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		Email annotation = field.getAnnotation(Email.class);
		FiledName fn = field.getAnnotation(FiledName.class);
		if (!checkEmail(value.toString())) {
			validateResult.put("message", fn.name() + annotation.message());
			validateResult.put("result", false);
		} else {
			validateResult.put("message", "验证通过");
			validateResult.put("result", true);
		}
		return validateResult;
	}

	public Map<String, Object> mobile(Object value, Field field) {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		Mobile annotation = field.getAnnotation(Mobile.class);
		FiledName fn = field.getAnnotation(FiledName.class);
		if (!checkMobileNumber(value.toString())) {
			validateResult.put("message", fn.name() + annotation.message());
			validateResult.put("result", false);
		} else {
			validateResult.put("message", "验证通过");
			validateResult.put("result", true);
		}
		return validateResult;
	}
	
	public Map<String, Object> personName(Object value, Field field) {
		Map<String, Object> validateResult = new HashMap<String, Object>();
		PersonName annotation = field.getAnnotation(PersonName.class);
		FiledName fn = field.getAnnotation(FiledName.class);
		if (!checkPersonName(value.toString())) {
			validateResult.put("message", fn.name() + annotation.message());
			validateResult.put("result", false);
		} else {
			validateResult.put("message", "验证通过");
			validateResult.put("result", true);
		}
		return validateResult;
	}
	
	public boolean checkPersonName(String name) {
		boolean flag = false;
		try {
			String check = "^[a-zA-Z\u4e00-\u9fa5]+$";//^[a-zA-Z0-9\u4E00-\u9FA5]+$
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(name);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
