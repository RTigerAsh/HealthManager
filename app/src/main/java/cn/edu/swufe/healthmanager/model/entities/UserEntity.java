package cn.edu.swufe.healthmanager.model.entities;

public class UserEntity {

    private String id;

    private String userName;

    private String roles;

    private String avatar;

    private String email;

    private String sign;

    private String state;

    private String tokenKey;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles == null ? null : roles.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public void updateTokenKey(String newTokenKey){
        if(this.getTokenKey() == null || newTokenKey == null) return;

        if(!this.getTokenKey().equals(newTokenKey)){
            this.setTokenKey(newTokenKey);
            // TODO: 修改sharedPreference， or 标记token变化，等待程序退出时再更新token
        }

    }
}
