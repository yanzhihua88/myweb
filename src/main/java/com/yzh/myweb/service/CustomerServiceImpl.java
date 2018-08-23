package com.yzh.myweb.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yzh.myweb.dto.CustomerQueryDTO;
import com.yzh.myweb.entity.Customer;
import com.yzh.myweb.entity.CustomerBGY;
import com.yzh.myweb.mapper.BgyDataMapper;
import com.yzh.myweb.mapper.CustomerMapper;

/**
 * <p>
 * 客户详情表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

	@Autowired
	private BgyDataMapper bgyDataMapper;

	@Override
	public Page<CustomerBGY> selectBgyPageList(Page<CustomerBGY> page, Map<String, String> map) {
		return page.setRecords(bgyDataMapper.getCustomerList(page,map));
	}

    @Override
    public Customer selectByRelatedSourceId(String dataSource, String relatedSourceId) {
        return baseMapper.selectByRelatedSourceId(dataSource, relatedSourceId);
    }

	@Override
	public Page<Customer> selectPageList(Page<Customer> page, CustomerQueryDTO param) {
		return page.setRecords(baseMapper.selectPageList(page,param));
	}
	
}
