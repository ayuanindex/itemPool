package com.lenovo.atopic08;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 网络请求接口
 */
public interface ApiService {
    //查询全部车辆
    @POST("dataInterface/Car/getAll")
    Observable<CarInfo> getAllCar();


    //查询全部订单
    @POST("dataInterface/UserAppointment/getAll")
    Observable<UserAppointment> getAllUserAppointment();


    //新增订单
    @POST("dataInterface/UserAppointment/create")
    @FormUrlEncoded
    Observable<CreateResult> createUserAppointment(@Field("userWorkId") int userWorkId,
                                                   @Field("userAppointmentName") String userAppointmentName,
                                                   @Field("content") String content,
                                                   @Field("type") int type,
                                                   @Field("carId") int carId,
                                                   @Field("time") long time,
                                                   @Field("num") int num,
                                                   @Field("gold") int gold,
                                                   @Field("engine") double engine,
                                                   @Field("speed") int speed,
                                                   @Field("wheel") int wheel,
                                                   @Field("control") int control,
                                                   @Field("hang") int hang,
                                                   @Field("brake") int brake,
                                                   @Field("color") int color
    );


}
