package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    //查询所有的检查项
    List<CheckItem> findAll();
//添加检查项
    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteById(int id);

    /**
     * 通过id、查询
     * @param id
     * @return
     */
    CheckItem findById(int id);

    void update(CheckItem checkItem);
}
