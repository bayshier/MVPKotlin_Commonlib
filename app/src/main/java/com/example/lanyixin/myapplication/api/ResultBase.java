package com.example.lanyixin.myapplication.api;

import java.io.Serializable;

/**
 * @author easin on 2018/6/22 18:25
 */

public class ResultBase<T> implements Serializable {
  /**
   * status : true
   * code : 0
   * action : add
   * msg : 关注成功
   * data :
   */

  private String status;
  private String code;
  private String action;
  private String msg;
  private T data;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }


}
