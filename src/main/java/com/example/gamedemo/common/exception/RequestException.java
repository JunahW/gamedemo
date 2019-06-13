package com.example.gamedemo.common.exception;

/**
 * @author wengj
 * @description:请求异常
 * @date 2019/6/3
 */
public class RequestException extends RuntimeException {
    /**
     * i18n错误码
     */
    private int errorCode;

    public RequestException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    /**
     * 构建异常
     *
     * @param i18nId
     */
    public static void throwException(int i18nId) {
        RequestException requestException = new RequestException(i18nId);
        throw requestException;
    }
}
