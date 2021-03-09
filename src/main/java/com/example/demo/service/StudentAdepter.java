package com.example.demo.service;

import com.example.demo.businessDto.Student;
import com.example.demo.flow.ICurrentStateDto;

/**
 * @author 孙丰佳
 * @version V1.0
 * @Title: StudentAdepter
 * @Package com.example.demo.service
 * @Description: (用一句话描述该文件做什么)
 * @date 2021-02-04 16:06
 **/
public class StudentAdepter implements ICurrentStateDto<Student> {
    private Student student;
    @Override
    public String getId() {
        return student.getId();
    }

    @Override
    public String getName() {
        return student.getName();
    }

    @Override
    public void set(Student student) {
        this.student = student;
    }

    @Override
    public String getState() {
        return student.getStatus();
    }

    @Override
    public Student get() {
        return this.student;
    }
}
