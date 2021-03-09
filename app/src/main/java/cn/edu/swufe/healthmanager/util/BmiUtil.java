package cn.edu.swufe.healthmanager.util;



public class BmiUtil {

    //体质指数(BMI)=体重(kg)÷身高^2(m)
    public static float getBmi(float kg,float m){
        float bmi=kg/(m*m);
        return bmi;
    }

    //BMI的正常值在18.5-23.9之间
    //得到正常体重
    public static float getMinWeight(float m,String gender){
        float MinWeight=0;
        if (gender.equals("0")){
            MinWeight=m*m*0.9f*22;
        }else if (gender.equals("1")){
            MinWeight=m*m*0.9f*20;
        }

        return MinWeight;
    }

    public static float getMaxWeight(float m,String gender){
        float MaxWeight=0;
        if (gender.equals("0")){
            MaxWeight=m*m*1.1f*22;
        }else if (gender.equals("1")){
            MaxWeight=m*m*1.1f*20;
        }
        return MaxWeight;
    }

    public static float getBMR(float kg,float m,String gender,int year){
        float bmr=0;
        if (gender.equals("0")){
            bmr=655+(9.6f*kg)+(1.8f*m)-(4.7f*year);
        }else if (gender.equals("1")){
            bmr=66+(13.7f*kg)+(5*m)-(6.8f*year);
        }
        return bmr;
    }

}
