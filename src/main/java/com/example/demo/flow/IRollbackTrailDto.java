package com.example.demo.flow;

public interface IRollbackTrailDto<T> {
    void set(T t);
    String getState();
}
