package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * interfaceClass发布服务时使用的接口
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;


    /**
     * 查询所有的检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //页码与大小
        PageHelper.startPage (queryPageBean.getCurrentPage (),queryPageBean.getPageSize ());
        //判断是否有查询条件  如果有要实现模糊查询
        if(!StringUtils.isEmpty (queryPageBean.getQueryString () )){
            queryPageBean.setQueryString ("%"+queryPageBean.getQueryString ()+"%");
        }
        //条件查询，条件查询，查询语句 会被分页
        Page<CheckItem> page=checkItemDao.findPage(queryPageBean.getQueryString ());


        //封装到分页结果对象
        PageResult<CheckItem> pageResult=new PageResult<CheckItem> (page.getTotal (),page.getResult ());

        return pageResult;
    }

    @Override
    public void deleteById(int id) {
        //判断是否被检查组使用，
        int count=checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            // 如果被使用，则报错，
            throw new MyException ("该检查项已经被使用了，不能被删除");

        }

        // 没用，删除
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
