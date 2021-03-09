package com.example.demo.service;

import com.example.demo.businessDto.Student;
import com.example.demo.flow.Context;
import com.example.demo.flow.ICurrentStateDto;
import com.example.demo.flow.IRollbackTrailDto;
import com.example.demo.flow.StateFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: ConcreteContext
 * @Package com.example.demo.flow
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-02-03 11:30
 **/
@Component
public class ConcreteAnotherContext extends Context {
    private Map<String, Student> studentMap = new HashMap<>();

    public ConcreteAnotherContext() {
        Student student = new Student();
        student.setId("aa");
        student.setName("zhangsan");
        student.setStatus("11");
        studentMap.put(student.getId(), student);
        student = new Student();
        student.setId("ab");
        student.setName("lisi");
        student.setStatus("11");
        studentMap.put(student.getId(), student);
    }

    @Override
    protected String getStateFlowType() {
        return "IAnotherFlow";
    }

    @Override
    protected ICurrentStateDto getCurrentState(String id) {
        //模拟从数据库根据id取当前的状态Dto,将该对象放入当前状态dto中做适配
        Student currentState = studentMap.get(id);
        StudentAdepter studentAdepter = new StudentAdepter();
        studentAdepter.set(currentState);
        return studentAdepter;
    }

    @Override
    protected List<IRollbackTrailDto> getRollbackTrailList(String id) throws Exception {
        List<IRollbackTrailDto> trailDtoArrayList = new ArrayList<>();
        //模拟数据库查询轨迹列表
        Student trailState = studentMap.get(id);
        IRollbackTrailDto<Student> rollbackTrailDto = new IRollbackTrailDto<Student>() {
            private Student student;

            @Override
            public void set(Student student) {
                this.student = student;
            }

            @Override
            public String getState() {
                return student.getStatus();
            }
        };
        rollbackTrailDto.set(trailState);
        trailDtoArrayList.add(rollbackTrailDto);
        return trailDtoArrayList;
    }
}
