package com.example.gamedemo.server.common.packet;

/**
 * @author wengj
 * @description：错误编码
 * @date 2019/6/11
 */
public class SM_ErrorCode {
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public SM_ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static SM_ErrorCode valueOf(int errorCode) {
        return new SM_ErrorCode(errorCode);
    }
}
