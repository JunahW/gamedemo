package com.example.gamedemo.server.common.packet;

/**
 * @author wengj
 * @description：错误编码
 * @date 2019/6/11
 */
public class SM_ErrorCode {
  private int errorCode;

  public static SM_ErrorCode valueOf(int errorCode) {
    SM_ErrorCode sm_errorCode = new SM_ErrorCode();
    sm_errorCode.setErrorCode(errorCode);
    return sm_errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
