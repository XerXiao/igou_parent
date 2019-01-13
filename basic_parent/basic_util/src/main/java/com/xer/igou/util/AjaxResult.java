package com.xer.igou.util;

/***
 * ajax请求结果返回类
 *
 * 采用链式编程
 */
public class AjaxResult {

    private boolean success = true;
    private String message = "操作成功";
    private Integer errorcode;
    private Object resultObject;

    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getErrorcode() {
        return errorcode;
    }

    public AjaxResult setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
        return this;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public AjaxResult setResultObject(Object resultObject) {
        this.resultObject = resultObject;
        return this;
    }


    public static AjaxResult me() {
        return new AjaxResult();
    }
}
