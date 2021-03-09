package com.example.demo.service;

import com.example.demo.flow.Contant;
import com.example.demo.flow.StateFlow;
import com.example.demo.flow.annotition.ExpandCheckAnno;
import com.example.demo.flow.annotition.StateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: MedordState11
 * @Package com.example.demo.flow
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-01-30 13:16
 **/
// 新开立
@Service
@StateService(value = Contant.HI_HIS_MEDORDSTA.CD_11, type = "IBaseFlow")
public class StateFlow11Holder extends StateFlow{
    private List<String> forwardStateList = Arrays.asList(
            Contant.HI_HIS_MEDORDSTA.CD_12,
            Contant.HI_HIS_MEDORDSTA.CD_21,
            Contant.HI_HIS_MEDORDSTA.CD_92
        );
    private List<String> rollbackStateList = new ArrayList<>();

    @Override
    public void forward() {
        //这里变更数据库中id的状态
        super.addSuccessMsg("forward " + super.currentStateDto.getName() + " state to " + targetState);
    }

    @Override
    @ExpandCheckAnno(beforeServiceNames={"surgCheckHolder"}, afterServiceNames={"surgCheckHolder"})
    public boolean checkForwardAllow(){
        if (!forwardStateList.contains(targetState)) {
            super.addErrorMsg(super.currentStateDto.getName() + ",11状态只能变为12或者21或者92");
            return false;
        }
        return true;
    }

    //从当前状态dto和历史dto中，确定回滚的状态
    @Override
    public void rollback(){
    }

    @Override
    public boolean checkRollbackAllow() {
        super.addErrorMsg(super.currentStateDto.getName() + ",11状态就不能退回去了");
        return false;
    }
}
