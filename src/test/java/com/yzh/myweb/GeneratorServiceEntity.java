package com.yzh.myweb;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class GeneratorServiceEntity {
    //包路径
    private static String  packageName = "com.yzh.myweb";
    //作者
    private static String authorName = "generator";
    //table名字
    private static String[] table = {"t_message", "t_user"};
    //table前缀
    private static String prefix = "t_";
    //代码输出目录
    private static String outPutDir = "D:/codeGen";
    //数据库地址
    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/test";
    //数据库用户名
    private static String dbUserName = "root";
    //数据库密码
    private static String dbPassword = "root";

    @Test
    public void generateCode() {

        boolean serviceNameStartWithI = true;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, table);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(dbUserName)
                .setPassword(dbPassword)
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(prefix)
                .setEntityLombokModel(true)
                .setLogicDeleteFieldName("is_deleted")
                .setRestControllerStyle(true)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor(authorName)
                .setOutputDir(outPutDir)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}
