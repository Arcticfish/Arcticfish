package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {
    @Reference
    private CheckGroupService checkGroupService;
    /**
     * 添加检查组信息
     * @param checkGroup ：检查组信息
     * @param checkitemIds ：选中的检查组id的集合
     * @return
     */

    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);


    }
    /**
     * 通过id查询
     *
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckGroup checkGroup=checkGroupService.findById(id);
        return new Result (true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer> checkitemIds =checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result (true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds);
    }
    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        //调用服务更新
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result (true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页条件查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用服务分页查询
        PageResult<CheckGroup> pageResult=checkGroupService.findPage(queryPageBean);
        return new Result (true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用服务删除
        checkGroupService.deleteById(id);
        return new Result (true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list=checkGroupService.findAll();
        return new Result (true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }



}
