package com.example.demo.flow;

public interface ICurrentStateDto<T> {
    String getId();

    String getName();

    void set(T t);

    String getState();

    T get();

}
