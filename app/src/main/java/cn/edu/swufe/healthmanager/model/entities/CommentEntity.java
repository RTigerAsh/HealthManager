package cn.edu.swufe.healthmanager.model.entities;

import java.util.Date;

public class CommentEntity {
    private String id;

    // question id，根据需要，可扩充为父内容id
    private String uccId;

    private String userId;

    private String userAvatar;

    private String userName;

    private String state;

    private String content;

    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUccId() {
        return uccId;
    }

    public void setUccId(String uccId) {
        this.uccId = uccId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 设置进行评论的用户信息
     * @param loginUser
     * @return
     */
    public  boolean  setUserInfo(UserEntity loginUser){
        if(loginUser == null){
            return false;
        }

        this.setUserId(loginUser.getId());
        this.setUserName(loginUser.getUserName());
        this.setUserAvatar(loginUser.getAvatar());

        return true;
    }
}
