package com.yzh.myweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yzh.myweb.common.PageResult;
import com.yzh.myweb.dto.CustomerQueryDTO;
import com.yzh.myweb.dto.CustomerRequestDTO;
import com.yzh.myweb.dto.CustomerResponseDTO;
import com.yzh.myweb.dto.LevelRequestDTO;
import com.yzh.myweb.entity.Customer;
import com.yzh.myweb.entity.User;
import com.yzh.myweb.enums.CustomerLevel;
import com.yzh.myweb.enums.DataSourceType;
import com.yzh.myweb.enums.UserLevel;
import com.yzh.myweb.exception.NotLoginErrorException;
import com.yzh.myweb.exception.ServerErrorException;
import com.yzh.myweb.service.ICustomerService;
import com.yzh.myweb.service.IUserService;
import com.yzh.myweb.utils.PageDataUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 客户详情表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2018-07-18
 */
@RestController
@Slf4j
public class CustomerController extends BaseController{

	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IUserService userService;
	
	
	@RequestMapping(value = "/saveCustomerInfo", method = RequestMethod.POST)
	@ApiOperation(value = "保存客户信息", notes = "保存客户信息")
	@Transactional
	public Map<String,Integer> saveCustomerInfo(@RequestBody @Validated CustomerRequestDTO dto) {
		Map<String,Integer>  retMap = new HashMap<>();
		Integer customerId = dto.getCustomerId();
		Integer userId = getCurrentUserId();
		try {
			User user = userService.selectById(userId);
			if(user == null){
				throw new ServerErrorException("用户未初始化");
			}
			EntityWrapper<Customer> ew = new EntityWrapper<>();
			ew.where("customer_name={0}", dto.getCustomerName());
			Customer customerDB = customerService.selectOne(ew);
			Customer customer = new Customer();
			BeanUtils.copyProperties(dto, customer);
			
			//ID不为0是更新
			if(null!= dto.getCustomerId() &&dto.getCustomerId()!=0){
				if(null!=customerDB  && !customerDB.getCustomerId().equals(dto.getCustomerId())){
					throw new ServerErrorException("企业名称已经存在！");
				}
				customerService.updateById(customer);
			}else{
				
				if(null!=customerDB){
					throw new ServerErrorException("企业名称已经存在！");
				}
				customer.setDataSource(DataSourceType.A.getValue());
				if(StringUtils.isEmpty(customer.getCustomerLevel())){
					customer.setCustomerLevel(CustomerLevel.COMMON.getCode());
				}
				customerService.insert(customer);
				customerId = customer.getCustomerId();

			}
			retMap.put("customerId", customerId);
		} catch (NotLoginErrorException e) {
			log.error("error: ",e);
			throw new ServerErrorException(e.getMessage());
		} catch (ServerErrorException e) {
			log.error("error: ",e);
			throw e;
		} catch (Exception e) {
			log.error("error: ",e);
			throw new ServerErrorException("系统异常");
		}
		return retMap;
	}
	
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	@ApiOperation(value = "客户分页查询", notes = "客户分页查询")
	public PageResult<CustomerResponseDTO> customerPageList(@RequestBody @Validated CustomerQueryDTO param) {
		PageResult<CustomerResponseDTO> data = new PageResult<CustomerResponseDTO>();
		try {

			int userId = getCurrentUserId();
			User user = userService.selectById(userId);
			if(null==user){
				throw new ServerErrorException("用户未初始化");
			}

			param.setUserId(String.valueOf(userId));
			//如果是主办，能看到负责行业下所有的客户信息
			if(UserLevel.LEADER.getCode().equals(String.valueOf(user.getUserLevel())) || UserLevel.BOSS.getCode().equals(String.valueOf(user.getUserLevel()))){
				param.setIndustryCate(user.getResponsiblePlate());
			}
			//分页查询
			Page<Customer>  pages = customerService.selectPageList(new Page<>(param.getPage(), param.getSize()), param);
			PageDataUtil.transform(pages, data, CustomerResponseDTO.class);
			return data;
		} catch (NotLoginErrorException e) {
			log.error("error: ",e);
			throw new ServerErrorException(e.getMessage());
		} catch (ServerErrorException e) {
			log.error("error: ",e);
			throw e;
		} catch (Exception e) {
			log.error("error: ",e);
			throw new ServerErrorException("系统异常");
		}

	}
	
	@RequestMapping(value = "/customerInfo", method = RequestMethod.POST)
	@ApiOperation(value = "客户详情查询", notes = "客户详情查询")
	public CustomerResponseDTO customerInfo(@RequestBody @Validated CustomerQueryDTO param) {
		CustomerResponseDTO data = new CustomerResponseDTO();
		try {
			if(StringUtils.isEmpty(param.getCustomerId())){
				throw new ServerErrorException("参数customerId不能为空");
			}
			Customer customer = customerService.selectById(param.getCustomerId());
			if(null!=customer){
				BeanUtils.copyProperties(customer, data);
			}else{
				throw new ServerErrorException("没有数据");
			}
		} catch (NotLoginErrorException e) {
			log.error("error: ",e);
			throw new ServerErrorException(e.getMessage());
		} catch (ServerErrorException e) {
			log.error("error: ",e);
			throw e;
		} catch (Exception e) {
			log.error("error: ",e);
			throw new ServerErrorException("系统异常");
		}

		return data;
	}
	
	@RequestMapping(value = "/setCustomerLevel", method = RequestMethod.POST)
	@ApiOperation(value = "设置客户级别", notes = "设置客户级别")
	public void setCustomerLevel(@RequestBody @Validated LevelRequestDTO param) {
		try {
			int userId = getCurrentUserId();
//			int userId = 12;
			User user = userService.selectById(userId);
			if(null==user){
				throw new ServerErrorException("用户未初始化");
			}
			List<Integer> customerIds = param.getCustomerIds();
			Customer customer = null;
			for(Integer custId : customerIds){
				
				Customer customer_db = customerService.selectById(custId);
				
				customer = new Customer();
				customer.setCustomerId(custId);
				customer.setCustomerLevel(param.getLevel());
				if(CustomerLevel.FLOWAWAY.getCode().equals(param.getLevel())){
					customer.setRemark(param.getRemark());
				}else{
					customer.setRemark("--");
				}
				customerService.updateById(customer);
				
				String messageContent = user.getRealName()+"把客户："+customer_db.getCustomerName() + " 的等级由："+CustomerLevel.getValueByCode(customer_db.getCustomerLevel())+" 设置为："+CustomerLevel.getValueByCode(param.getLevel());
				
			}
			
			
		} catch (NotLoginErrorException e) {
			log.error("error: ",e);
			throw new ServerErrorException(e.getMessage());
		} catch (ServerErrorException e) {
			log.error("error: ",e);
			throw e;
		} catch (Exception e) {
			log.error("error: ",e);
			throw new ServerErrorException("系统异常");
		}
	}
	
}

