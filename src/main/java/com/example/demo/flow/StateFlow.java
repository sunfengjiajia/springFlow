package com.example.demo.flow;


import java.util.List;
import java.util.Map;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: MedordState
 * @Package com.example.demo.flow
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-01-30 13:15
 **/
public abstract class StateFlow extends IStateFlow {
    //正向逻辑的状态，由前端传入，在context中赋值，作为前进的目标状态
    protected String targetState;
    //其他的参数，为兼容用
    protected Map para;

    protected ICurrentStateDto currentStateDto;

    protected List<IRollbackTrailDto> rollbackTrailDtoList;

    protected Context context;


    //下一步
    public abstract void forward() throws Exception;

    //检查是否允许走下一步
    public abstract boolean checkForwardAllow();

    //回滚
    public abstract void rollback() throws Exception;

    //检查是否允许回滚下一步
    public abstract boolean checkRollbackAllow();

    protected boolean addSuccessMsg(String msg) {
        return this.context.addSuccessMsg(msg);
    }

    protected boolean addErrorMsg(String msg) {
        return this.context.addErrorMsg(msg);
    }

    public String getTargetState() {
        return targetState;
    }

    public void setTargetState(String targetState) {
        this.targetState = targetState;
    }

    public Map getPara() {
        return para;
    }

    public void setPara(Map para) {
        this.para = para;
    }

    public ICurrentStateDto getCurrentStateDto() {
        return currentStateDto;
    }

    public void setCurrentStateDto(ICurrentStateDto currentStateDto) {
        this.currentStateDto = currentStateDto;
    }

    public List<IRollbackTrailDto> getRollbackTrailDtoList() {
        return rollbackTrailDtoList;
    }

    public void setRollbackTrailDtoList(List<IRollbackTrailDto> rollbackTrailDtoList) {
        this.rollbackTrailDtoList = rollbackTrailDtoList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
