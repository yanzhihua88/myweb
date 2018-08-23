package com.yzh.myweb.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.yzh.myweb.common.PageResult;

/**
 * 
 * @author pero.yan
 *
 */
public class PageDataUtil {
	public static <T,E> void transform(Page<T> source,PageResult<E> target,Class<E> targetCls) throws InstantiationException, IllegalAccessException{
		List<T> list = source.getRecords();
		List<E> dtos = new ArrayList<>();
		if(list.size()>0){
			for(T c:list){
				E dto = targetCls.newInstance();
				BeanUtils.copyProperties(c, dto);
				dtos.add(dto);
			}
		}
		target.setTotalRecords(source.getTotal());
		target.setTotalPages(source.getPages());
		target.setRecords(dtos);
	}
}
