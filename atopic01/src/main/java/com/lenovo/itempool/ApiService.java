package com.lenovo.itempool;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 网络请求接口sß
 */
public interface ApiService {


    @POST("Interface/index/userSellInfoTEditer")
    Observable<Bean> getUserSellInfoTEditer();

}
