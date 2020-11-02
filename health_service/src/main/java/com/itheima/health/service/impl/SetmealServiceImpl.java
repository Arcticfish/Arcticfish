package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //新增套餐
        setmealDao.add(setmeal);
        //获取套餐id
        Integer setmealId=setmeal.getId ();
        //循环遍历选中的检查组id
        if(null!=checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐与检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);

            }
            }

    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage (queryPageBean.getCurrentPage (),queryPageBean.getPageSize ());
        if(!StringUtils.isEmpty (queryPageBean.getQueryString () )){
            queryPageBean.setQueryString ("%"+queryPageBean.getQueryString ()+"%");
        }
        Page<Setmeal> page=setmealDao.findByCondition(queryPageBean.getQueryString ());
        PageResult<Setmeal> pageResult=new PageResult<Setmeal> (page.getTotal (),page.getResult ());
        return pageResult;
    }

    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }


    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新套餐
        setmealDao.update(setmeal);
        //删除就关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId ());
        //添加新关系
        if(null!=checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup (setmeal.getId (),checkgroupId);
            }
        }
    }
    public void deleteById(int id) {
        // 判断是否被订单使用
        int count = setmealDao.findOrderCountBySetmealId(id);
        // 使用则报错
        if(count > 0){
            throw new MyException ("该套餐已经被订单使用了，不能删除");
        }
        // 没使用
        // 先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }

    @Override
    public List<String> findImgs() {

        return setmealDao.fingImgs();
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }
    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }
}
