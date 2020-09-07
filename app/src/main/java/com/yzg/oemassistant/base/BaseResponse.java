package com.yzg.oemassistant.base;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by dell on 2019/12/18 16:05
 * Description:
 * Emain: 1187278976@qq.com
 */
public class BaseResponse<T> implements Serializable {
    private int StatusCode;
    private T Result;

    public BaseResponse(int statusCode) {
        StatusCode = statusCode;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    @NotNull
    @Override
    public String toString() {
        return "BaseResponse{" +
                "StatusCode=" + StatusCode +
                ", Result=" + Result +
                '}';
    }
}
