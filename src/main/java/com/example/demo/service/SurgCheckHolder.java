package com.example.demo.service;

import com.example.demo.flow.StateFlow;
import com.example.demo.flow.aspect.IExpandCheck;
import org.springframework.stereotype.Service;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: SurgCheckHolder
 * @Package com.example.demo.service
 * @Description:
 * @date 2021-02-04 11:20
 **/
@Service
public class SurgCheckHolder implements IExpandCheck {
    @Override
    public void run(StateFlow stateFlow) throws Exception {
        System.out.println("SurgCheckHolder is running:" + stateFlow.toString());
    }
}
