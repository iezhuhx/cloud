package com.ww.common;

/**
 * 响应对象
 *
 * @Auther: Ace Lee
 * @Date: 2019/3/5 16:35
 */
public class Result<T> {

    private boolean success;
    private String error;
    private T data;

    public Result(T data){
        this.success=true;
        this.data=data;
    }

    public static Result error(String error){
        return new Result<>(false,error,null);
    }

    public Result(boolean success, String error, T data){
        this.success=success;
        this.error=error;
        this.data=data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
