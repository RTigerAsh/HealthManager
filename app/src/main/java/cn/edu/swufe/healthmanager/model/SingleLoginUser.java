package cn.edu.swufe.healthmanager.model;


import cn.edu.swufe.healthmanager.model.entities.UserEntity;

// 单例模式设计
public class SingleLoginUser {

    private  UserEntity userEntity;

    // 写直达，保证任何线程中的该对象都为最新
    private static volatile SingleLoginUser instance;

    // 此类无法被实例化
    private SingleLoginUser(){}

    public static SingleLoginUser getInstance(){
        // 保证线程安全
        if(instance == null){
            // 设置在内部，保证效率
            synchronized (SingleLoginUser.class){
                if(instance == null){
                    instance = new SingleLoginUser();
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
