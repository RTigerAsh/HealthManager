package cn.edu.swufe.healthmanager.model;


import cn.edu.swufe.healthmanager.model.entities.UserEntity;

// 单例模式设计
public class LoginUser {

    private  UserEntity userEntity;

    private static LoginUser instance;

    // 此类无法被实例化
    private LoginUser(){}

    public static LoginUser getInstance(){
        // 保证线程安全
        if(instance == null){
            synchronized (LoginUser.class){
                if(instance == null){
                    instance = new LoginUser();
                }
            }
        }
        return instance;
    }


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void updateUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
