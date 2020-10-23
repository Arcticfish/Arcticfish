package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
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


}
