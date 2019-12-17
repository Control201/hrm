package com.hrm.util;

/**
 * @ClassName EmailVerifyCode
 * @Description 邮箱验证码类
 * @Version 1.0
 **/
public class EmailVerifyCode {
    private String email;

    private String code;

    private long initTime;

    public static long validTime=MyTimeUtil.VALID_TIME_5_MIN; //5min

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getInitTime() {
        return initTime;
    }

    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    public EmailVerifyCode(String email, String code, long initTime) {
        this.email = email;
        this.code = code;
        this.initTime = initTime;
    }

    public EmailVerifyCode() {
    }

    /**
     * @Description 检查当前存储的验证是否还有效
     * @Param []
     * @return boolean
     **/
    public boolean isValid(){
        return MyTimeUtil.cmpTime(initTime,validTime);
    }

    public static void main(String[] args) {
        EmailVerifyCode.validTime=10;
        System.out.println(EmailVerifyCode.validTime);
    }
}
