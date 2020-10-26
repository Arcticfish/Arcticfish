package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.Utils.QiNiuUtils;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.handler.MessageContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static final Logger log= LoggerFactory.getLogger (SetmealController.class);
    @Reference
    private SetmealService setmealService;

    /**
     *
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //获取原文件名才可以获取他的后缀名
        String originalFilename = imgFile.getOriginalFilename ( );
        //获取文件扩展名
        String extension = originalFilename.substring (originalFilename.lastIndexOf ("."));
        //产生唯一标志，拼接后缀名，唯一的文件名（七牛的仓库）
        String uniqueName=UUID.randomUUID ().toString ()+ extension;
        //调用七牛utils上传文件
        try {
            QiNiuUtils.uploadViaByte (imgFile.getBytes (),uniqueName);
            //上传成功
            Map<String,String> resultMap=new HashMap<String,String> ();
            resultMap.put("domain",QiNiuUtils.DOMAIN);
            resultMap.put("imgName",uniqueName);
            return new Result (true,MessageConstant.PIC_UPLOAD_SUCCESS,resultMap);
        } catch (IOException e) {
            e.printStackTrace ( );
            log.error("上传文件失败",e);
            return new Result (false, MessageConstant.PIC_UPLOAD_FAIL);
        }





    }
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        //调用服务
        setmealService.add(setmeal,checkgroupIds);
        return new Result (true,MessageConstant.ADD_SETMEAL_SUCCESS);

    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult=setmealService.findPage(queryPageBean);
        return new Result (true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal=setmealService.findById(id);
        Map<String,Object> resultMap=new HashMap<String, Object> ( 2 );
        resultMap.put ("setmeal",setmeal);
        resultMap.put ("domain",QiNiuUtils.DOMAIN);
        return new Result (true,MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);

    }
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){
        // 后端list => 前端 [], javaBean 或 map => json{}
        // 查询属于这个套餐的选中的检查组id
        List<Integer> list = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        //调用服务更新
        setmealService.update(setmeal,checkgroupIds);
        return new Result (true,"更新套餐成功");
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        // 调用服务删除
        setmealService.deleteById(id);
        return new Result(true, "删除套餐成功！");
    }
}
