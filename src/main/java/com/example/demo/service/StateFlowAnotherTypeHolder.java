package com.example.demo.service;

import com.example.demo.flow.Contant;
import com.example.demo.flow.StateFlow;
import com.example.demo.flow.annotition.StateService;
import org.springframework.stereotype.Service;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: StateFlowAnotherTypeHolder
 * @Package com.example.demo.service
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-02-25 17:21
 **/
// 新开立
@Service
@StateService(value = Contant.HI_HIS_MEDORDSTA.CD_11,type = "IAnotherFlow")
public class StateFlowAnotherTypeHolder extends StateFlow {
    @Override
    public void forward() throws Exception {
        System.out.println("StateFlowAnotherTypeHolder");
    }
    public boolean checkForwardAllow(){
        System.out.println("StateFlowAnotherTypeHolder");
        return true;
    }

    @Override
    public void rollback() throws Exception {

    }

    @Override
    public boolean checkRollbackAllow() {
        return false;
    }
}
