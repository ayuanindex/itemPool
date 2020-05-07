package com.lenovo.btopic08;

import com.lenovo.btopic08.bean.Environmental;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取天气信息
     *
     * @param id 工厂ID
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<Environmental> getEnvironmental(@Field("id") int id);
}
