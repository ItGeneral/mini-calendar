package com.mini.calendar.controller.response;

/**
 * @author songjiuhua
 * Created by 2020/12/26 15:58
 */
public class BaseResponse<T> {

    private String code;
    private String message;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>("200", "success", data);
    }

    public static <T> BaseResponse<T> fail(String code, String message){
        return new BaseResponse<T>(code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
