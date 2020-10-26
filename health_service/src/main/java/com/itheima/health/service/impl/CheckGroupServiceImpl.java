package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //先添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        Integer checkGroupId=checkGroup.getId ();
        //便利选中的检查项ids
        if(null!=checkitemIds){
            //添加检查组与检查项的关系
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }

    }

    /**
     *通过id查询mmmm
     * @param id
     * @return
     */

    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Transactional//添加事务管理
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组
        checkGroupDao.update(checkGroup);
        //删除就关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId ());
        //添加新关系
        if(null !=checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem (checkGroup.getId (),checkitemId);
            }
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage (queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if(!StringUtils.isEmpty (queryPageBean.getQueryString())){
            queryPageBean.setQueryString ("%"+queryPageBean.getQueryString ()+"%");
        }
        Page<CheckGroup> page=checkGroupDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult=new PageResult<CheckGroup> (page.getTotal(),page.getResult());
        return pageResult;
    }

    @Transactional
    @Override
    public void deleteById(int id) throws MyException {
        //判断是否被套餐使用
        int count=checkGroupDao.findCountByCheckGroupId(id);
        //使用了
        if(count>0){
            throw new MyException ("该检查组已经被套餐使用了，不能删除");
        }
        //没使用，删除
        //先删除关系表
        checkGroupDao.deleteCheckGroupCheckItem (id);
        //删除检查组
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


}
