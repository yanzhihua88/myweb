package com.yzh.myweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yzh.myweb.dto.CustomerQueryDTO;
import com.yzh.myweb.entity.Customer;

/**
 * <p>
 * 客户详情表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
public interface CustomerMapper extends BaseMapper<Customer> {
    Customer selectByRelatedSourceId(@Param("dataSource") String dataSource,
                                    @Param("relatedSourceId") String relatedSourceId);

	List<Customer> selectPageList(Pagination page, CustomerQueryDTO param);
}
