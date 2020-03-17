package com.lenovo.atopic09;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 网络请求接口
 */
public interface ApiService {
    //查询全部学生收入日志
    @POST("dataInterface/UserInPriceLog/getAll")
    Observable<PriceLog> getAllUserInPriceLog();

    //查询全部学生支出日志
    @POST("dataInterface/UserOutPriceLog/getAll")
    Observable<PriceLog> getAllUserOutPriceLog();

    //删除学生金币支出日志
    @POST("dataInterface/UserOutPriceLog/delete")
    @FormUrlEncoded
    Observable<PriceLog> deleteUserOutPriceLog(@Field("id") int id);

    //删除学生金币收入日志
    @POST("dataInterface/UserInPriceLog/delete")
    @FormUrlEncoded
    Observable<PriceLog> deleteUserInPriceLog(@Field("id") int id);


    //新增学生金币支出日志
    @POST("dataInterface/UserOutPriceLog/create")
    @FormUrlEncoded
    Observable<Bean> createUserOutPriceLog(
            @Field("userWorkId") int userWorkId,
            @Field("price") int price,
            @Field("endPrice") int endPrice,
            @Field("time") long time,
            @Field("type") int type
    );


    //新增学生金币收入日志
    @POST("dataInterface/UserInPriceLog/create")
    @FormUrlEncoded
    Observable<Bean> createUserInPriceLog(
            @Field("userWorkId") int userWorkId,
            @Field("price") int price,
            @Field("endPrice") int endPrice,
            @Field("time") long time,
            @Field("type") int type
    );


}
