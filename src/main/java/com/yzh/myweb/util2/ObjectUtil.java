package com.yzh.myweb.util2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 */
@Slf4j
public class ObjectUtil {

    public static boolean allFieldIsNULL(Object o) {
        try {
            // 循环该类，取出类中的每个属性
            for (Field field : o.getClass().getDeclaredFields()) {
                //把该类中的所有属性设置成 public
                field.setAccessible(true);
                // object 是对象中的属性
                Object object = field.get(o);
                // 判断对象中的属性的类型，是否都是CharSequence的子类
                if (object instanceof CharSequence) {
                    // 如果是他的子类，那么就可以用ObjectUtils.isEmpty进行比较
                    if (!ObjectUtils.isEmpty(object)) {
                        return false;
                    }
                } else {
                    // 如果不是那就直接用null判断
                    if (null != object) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            log.info("对象判断为空异常", e);
//            throw new ServerErrorException("对象判断为空异常");
        }
        return true;
    }
}
