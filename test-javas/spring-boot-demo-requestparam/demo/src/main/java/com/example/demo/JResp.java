package com.example.demo;

import java.util.Objects;

public class JResp {
    private String msg;
    private Object data;

    public JResp() {
    }

    public JResp(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JResp jResp = (JResp) o;
        return Objects.equals(msg, jResp.msg) &&
                Objects.equals(data, jResp.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, data);
    }

    @Override
    public String toString() {
        return "JResp{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
