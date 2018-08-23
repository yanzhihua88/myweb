package com.yzh.myweb.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yzh.myweb.dto.CustomerQueryDTO;
import com.yzh.myweb.entity.Customer;
import com.yzh.myweb.entity.CustomerBGY;

/**
 * <p>
 * 客户详情表 服务类
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
public interface ICustomerService extends IService<Customer> {

    Page<CustomerBGY> selectBgyPageList(Page<CustomerBGY> page, Map<String,String> map);

    /**
     * 按第三方数据ID查找
     * @param dataSource
     * @param relatedSourceId
     * @return
     */
    Customer selectByRelatedSourceId(String dataSource, String relatedSourceId);

	Page<Customer> selectPageList(Page<Customer> page, CustomerQueryDTO param);

}
