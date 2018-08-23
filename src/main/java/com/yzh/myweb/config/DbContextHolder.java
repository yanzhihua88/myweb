package com.yzh.myweb.config;

import com.yzh.myweb.enums.DataSourceType;

public class DbContextHolder {
    private static final ThreadLocal contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源
     * @param dataSourceType
     */
    public static void setDbType(DataSourceType dataSourceType) {
        contextHolder.set(dataSourceType.getValue());
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
    }
}
