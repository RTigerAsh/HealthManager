package cn.edu.swufe.healthmanager.model;


import cn.edu.swufe.healthmanager.model.entities.UserEntity;

// 单例模式设计
public class LoginUser {

    private  UserEntity userEntity;

    // 写直达，保证任何线程中的该对象都为最新
    private static volatile LoginUser instance;

    // 此类无法被实例化
    private LoginUser(){}

    public static LoginUser getInstance(){
        // 保证线程安全
        if(instance == null){
            // 设置在内部，保证效率
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
