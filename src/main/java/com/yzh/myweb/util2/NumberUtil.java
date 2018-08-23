package com.yzh.myweb.util2;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.yzh.myweb.utils.DateTimeUtil;

/**
 * @author long.luo
 * @date 2018/8/8 15:37
 */
@Component
public class NumberUtil {

    /**
     * 基础数 +
     */
    public static final int BAES_NUM = 1000;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取序列号(prefix+yyyyMMdd+随机四位)
     *
     * @return
     */
    public String getSerialNum(String prefix, String key) {
        StringBuilder serialNum = new StringBuilder();
        serialNum.append(prefix);
        serialNum.append(DateTimeUtil.formatDateTimetoString(new Date(), "yyyyMMdd"));
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        redisAtomicLong.expireAt(new DateTime().withMillisOfDay(0).plusDays(1).toDate());
        Long num = redisAtomicLong.getAndIncrement();
        serialNum.append(BAES_NUM + num);
        return serialNum.toString();
    }
}
