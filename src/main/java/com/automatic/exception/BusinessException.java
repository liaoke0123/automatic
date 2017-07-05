package com.automatic.exception;

import com.cloud.Code;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 业务通知
 * Created by LiaoKe on 2017/5/27.
 */
@Component
public class BusinessException extends Exception {

    private String msg;//业务msg
    private Integer code;//业务code
    private Integer notifyCode;//通知code

    public BusinessException() {

    }
    public  BusinessException(Integer notifyCode,Code codeInConfig){
        HashMap<Integer, Code.CodeInfo> codeMap = codeInConfig.getMsg();
        if(codeMap.get(notifyCode)==null){
            this.msg="Code Fail Initialization!";
            this.code=400;
            this.notifyCode=-1;
        }else{
            this.msg=codeInConfig.getMsg().get(notifyCode).getInfo();
            this.code=codeInConfig.getMsg().get(notifyCode).getHttpCode();
            this.notifyCode=notifyCode;
        }
    }

    public Integer getNotifyCode() {
        return notifyCode;
    }

    public void setNotifyCode(Integer notifyCode) {
        this.notifyCode = notifyCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
