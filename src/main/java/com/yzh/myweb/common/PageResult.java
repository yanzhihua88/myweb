package com.yzh.myweb.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    long totalRecords;

    int totalPages;

    List<T> records;

}
