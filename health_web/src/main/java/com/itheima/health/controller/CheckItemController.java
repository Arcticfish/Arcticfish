package com.itheima.health.controller;



import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/findAll")
    public Result findAll(){
        //调用服务查询
        List<CheckItem> list=checkItemService.findAll();
        //封装到result，再返回

        return new Result (true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
//    添加检查项
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //调用服务添加
        checkItemService.add(checkItem);
        return new Result (true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult=checkItemService.findPage(queryPageBean);
        return new Result (true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);

    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务删除
        try {
            checkItemService.deleteById(id);
        } catch (MyException e) {
            e.printStackTrace ( );
        }
        catch (Exception e) {
            e.printStackTrace ( );
        }
        return new Result (true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    /**
     * 通过id查询
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem=checkItemService.findById(id);
        return new Result (true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    //    编辑检查项
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //调用服务添加
        checkItemService.update(checkItem);
        return new Result (true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }




}
