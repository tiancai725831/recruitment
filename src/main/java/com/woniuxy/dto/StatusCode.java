package com.woniuxy.dto;

public class StatusCode {
        public static final int OK = 20000;//成功
        public static final int ERROR = 20001;//失败
        public static final int LOGINERROR = 20002;//用户名或密码错误
        public static final int ACCESSERROR = 20003;//权限不足
        public static final int ACCOUNTEXIST = 20004;//账号已存在
        public static final int INCORRECTCREDENTIALS = 20005;//密码错误
        public static final int UNKNOWNACCOUNT = 20006;//账号错误
        public static final int NULLPOINTER=00000;//空指向异常
        public static final int EXECUTO=20007;//执行异常
    }
