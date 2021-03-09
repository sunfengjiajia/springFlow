package com.example.demo.controller;

import com.example.demo.flow.Contant;
import com.example.demo.flow.Context;
import com.example.demo.flow.entity.FlowResult;
import com.example.demo.service.ConcreteAnotherContext;
import com.example.demo.service.ConcreteContext;
import com.sun.xml.internal.fastinfoset.algorithm.FloatEncodingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: MainController
 * @Package com.example.demo.controller
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-01-30 13:28
 **/
@Controller
public class MainController {
    @Autowired
    private ConcreteContext context;

    //在这里定义了另外一组流程，也就是springFlow支持项目中有多种流程
//    @Autowired
//    private ConcreteAnotherContext anotherContext;

    @RequestMapping("test")
    @ResponseBody
    public FlowResult main(String state, String flag) {
        //默认ids, 正式应用时，应该是前端传过来
        List<String> ids = Arrays.asList("aa1");
        FlowResult flowResult = context.setMedordState(ids, flag, state);
//        FlowResult flowResult1 = anotherContext.setMedordState(ids, flag, state);

        return flowResult;
    }
}
