package com.example.demo.flow;

import com.example.demo.flow.annotition.StateService;
import com.example.demo.flow.entity.FlowResult;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: Context
 * @Package com.example.demo.flow
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-01-30 13:16
 **/
public abstract class Context {
    public static final String FORWARD_FLAG = "1";
    public static final String ROLLBACK_FLAG = "-1";

    private FlowResult result;

    @Autowired
    private List<StateFlow> stateFlowList;

    protected Map<String, StateFlow> stateCache = new HashMap<>();



    //定义一个当前状态
    StateFlow stateFlow;

    //遇到复杂逻辑时，需要保留的参数
    Map para;

    public void setPara(Map para) {
        this.para = para;
    }

    //显式的让子类继承，返回stateFlow实现的标识名@StateService的type属性
    //原因：如果一个项目有多个流程，需要设计一个标识，作为区分不同流程的标识
    //这样的话，可以根据标识名称，分组构建stateFlow流的stateCache
    //配合@StateService的value构建stateCache
    protected abstract String getStateFlowType();

    private void setStateFlowCache() {
        //获取子类的@StateService的type，遍历注入的stateFlowList，
        //看每个stateFlow的type，证明是该流程中的stateFlow，那么拼入该context实例的stateCache
        String stateFlowType = getStateFlowType();
        for (StateFlow flow : this.stateFlowList) {
            Class<? extends StateFlow> flowClass = flow.getClass();
            if (AopUtils.isAopProxy(flow)) {
                flowClass = (Class<? extends StateFlow>) AopProxyUtils.getSingletonTarget(flow).getClass();
            }
            StateService annotation = flowClass.getAnnotation(StateService.class);
            String key = annotation.value();
            String type = annotation.type();
            if (stateFlowType.equals(type)) {
                this.stateCache.put(key, flow);
            }
        }
    }

    /** @Title: setMedordState
     * @Description: 环境类主要逻辑
     * @param ids
     * @param flag
     * @param state    设定文件
     * @return void    返回类型
     * @author 孙丰佳
     * @time 2021-02-02 11:32
     */
    public FlowResult setMedordState(List<String> ids, String flag, String state) {
        this.result = new FlowResult();
        try {
            doSetMedordState(ids, flag, state);
        } catch (Exception e) {
            e.printStackTrace();
            this.addErrorMsg(e.getMessage());
        }
        return this.result;
    }

    private void doSetMedordState(List<String> ids, String flag, String state) throws Exception {
        try {
            this.setStateFlowCache();
        } catch (Exception e) {
            throw new RuntimeException("服务器内部错误");
        }
        for (String id : ids) {
            //这里从数据库根据id取当前的状态state
            ICurrentStateDto currentStateDto = this.getCurrentState(id);
            if (ObjectUtils.isEmpty(currentStateDto) || ObjectUtils.isEmpty(currentStateDto.get())) {
                throw new RuntimeException("没有找到这个对象:" + id);
            }
            StateFlow stateFlow = stateCache.get(currentStateDto.getState());
            if (null == stateFlow) {
                throw new NoSuchElementException("容器中没有这个处理器");
            }
            this.stateFlow = stateFlow;
            this.stateFlow.setPara(para);
            this.stateFlow.setContext(this);
            this.stateFlow.setCurrentStateDto(currentStateDto);

            if (null == flag || FORWARD_FLAG.equals(flag)) {
                /** 正向逻辑 开始 **/
                this.forward(state);
            } else if (ROLLBACK_FLAG.equals(flag)) {

                /** 回滚逻辑 开始 **/
                //先根据医嘱id，获取明细list，找到最新的状态dt赋值到flow中
                List<IRollbackTrailDto> rollbackTrailList = getRollbackTrailList(id);
                if (rollbackTrailList.size()==0) {
                    throw new RuntimeException("未找到历史轨迹");
                }
                //痕迹list，有可能用到
                this.stateFlow.setRollbackTrailDtoList(rollbackTrailList);

                this.rollback(rollbackTrailList.get(0).getState());
            } else {
                throw new RuntimeException("参数不正常");
            }
        }
    }
    //根据id获取当前状态
    protected abstract ICurrentStateDto getCurrentState(String id);

    //根据id获取rollback轨迹
    protected abstract List<IRollbackTrailDto> getRollbackTrailList(String id) throws Exception;

    //检查，并调用flow实例去处理相应的rollback逻辑
    private void rollback(String state) throws Exception {

        //默认设置回滚的状态为痕迹list的最后一个状态
        this.stateFlow.setTargetState(state);
        if (this.stateFlow.checkRollbackAllow()){
            this.stateFlow.rollback();
        }
    }

    //检查，并调用flow实例去处理相应的forward逻辑
    private void forward(String state) throws Exception {

        //设置前进的目标状态
        this.stateFlow.setTargetState(state);
        if (this.stateFlow.checkForwardAllow()){
            this.stateFlow.forward();
        }
    }

    boolean addSuccessMsg(String msg) {
        return this.result.getSuccessList().add(msg);
    }

    boolean addErrorMsg(String msg) {
        return this.result.getErrorList().add(msg);
    }
}
