package com.example.demo.flow.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: Result
 * @Package com.example.demo.flow.entity
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-02-07 9:33
 **/
public class FlowResult {
    private List<String> successList = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    public List<String> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
