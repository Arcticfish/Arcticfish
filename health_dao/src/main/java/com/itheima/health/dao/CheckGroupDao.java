package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param ("checkGroupId") Integer checkGroupId, @Param ("checkitemId") Integer checkitemId);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    void deleteCheckGroupCheckItem(Integer id);

    void update(CheckGroup checkGroup);

    /**
     * 条线查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

    int findCountByCheckGroupId(int id);

    void deleteById(int id);
}
