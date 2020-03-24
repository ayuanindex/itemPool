package com.lenovo.atopic12;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    //查询全部生产环节
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<UserWorkEnvironmental> getInfoUserWorkEnvironmental(@Field("id") int id);

    //修改学生工厂环境灯光开启
    @POST("dataInterface/UserWorkEnvironmental/updateLightSwitch")
    @FormUrlEncoded
    Observable<LightSwitch> updateLightSwitch(@Field("id") int id, @Field("lightSwitch") int lightSwitch);

    //修改学生工厂环境空调开关
    @POST("dataInterface/UserWorkEnvironmental/updateAcOnOff")
    @FormUrlEncoded
    Observable<LightSwitch> updateAcOnOff(@Field("id") int id, @Field("acOnOff") int acOnOff);

    //修改学生工厂环境车间温度
    @POST("dataInterface/UserWorkEnvironmental/updateWorkshopTemp")
    @FormUrlEncoded
    Observable<LightSwitch> updateWorkshopTemp(@Field("id") int id, @Field("workshopTemp") String workshopTemp);

    //修改学生工厂环境光照
    @POST("dataInterface/UserWorkEnvironmental/updateBeam")
    @FormUrlEncoded
    Observable<LightSwitch> updateBeam(@Field("id") int id, @Field("beam") int beam);

    //修改学生工厂环境电力供应
    @POST("dataInterface/UserWorkEnvironmental/updatePower")
    @FormUrlEncoded
    Observable<LightSwitch> updatePower(@Field("id") int id, @Field("power") int power);


}
