package com.lenovo.atopic14;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiSevices {


    //查询正常车辆成品仓库
    @POST("dataInterface/UserNormalCarStore/getAll")
    Observable<CarStore> getAllUserNormalCarStore();

    //查询维修车辆成品仓库
    @POST("dataInterface/UserRepairCarStore/getAll")
    Observable<CarStore> getAllUserQuestion();


    //查询一条学生工厂信息数据
    @POST("dataInterface/UserWorkInfo/getInfo")
    @FormUrlEncoded
    Observable<UserWorkInfo> getInfoUserWorkInfo(@Field("id") int id);

    //查询全部车辆
    @POST("dataInterface/Car/getAll")
    Observable<Car> getAllCar();

    //查询全部车辆信息
    @POST("dataInterface/CarInfo/getAll")
    Observable<CarInfo> getAllCarInfo();


    //删除维修车辆成品仓库
    @FormUrlEncoded
    @POST("dataInterface/UserRepairCarStore/delete")
    Observable<CarStore> deleteUserRepairCarStore(@Field("id") int id);

    //删除正常车辆成品仓库
    @FormUrlEncoded
    @POST("dataInterface/UserNormalCarStore/delete")
    Observable<CarStore> deleteUserNormalCarStore(@Field("id") int id);


    //修改学生工厂信息金币
    @FormUrlEncoded
    @POST("dataInterface/UserWorkInfo/updatePrice")
    Observable<UpdatePrice> updatePriceUserWorkInfo(@Field("id") int id, @Field("price") int price);


    //新增学生卖出日志
    @FormUrlEncoded
    @POST("dataInterface/UserSellOutLog/create")
    Observable<CarStore> createUserSellOutLog(
            @Field("userWorkId") int userWorkId,
            @Field("carId") int carId,
            @Field("gold") int gold,
            @Field("time") long time,
            @Field("num") int num
    );

    //查询全部卖出记录
    @POST("dataInterface/UserSellOutLog/getAll")
    Observable<UserSellOutLog> getAllUserSellOutLog();


    //删除学生卖出日志
    @FormUrlEncoded
    @POST("dataInterface/UserSellOutLog/delete")
    Observable<UserSellOutLog> deleteUserSellOutLog(@Field("id") int userWorkId);

}
