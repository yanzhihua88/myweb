package com.yzh.myweb.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yzh.myweb.entity.CustomerBGY;

public interface BgyDataMapper {
	List<CustomerBGY> getCustomerList(Pagination page,Map<String,String> map);
}
