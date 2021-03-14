package cn.edu.swufe.healthmanager.model;

public class ServerResult<T>{
    private boolean isSuccess = true;
    private int code;
    private String token;
    private String msg;

    private T data;

    public static ServerResult createLinkFail(){
        ServerResult failure = new ServerResult();
        failure.setCode(3);
        failure.setSuccess(false);
        failure.setMsg("连接出错");

        return failure;
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
