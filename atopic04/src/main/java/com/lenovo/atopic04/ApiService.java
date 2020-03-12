package com.lenovo.atopic04;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {

    //查询全部车辆
    @POST("dataInterface/Car/getAll")
    Observable<CarInfo> getAllCarInfo();

    //查询全部学生卖出记录
    @POST("dataInterface/UserSellOutLog/getAll")
    Observable<SaleLog> getAllUserSellOutLog();

}
