package com.lenovo.btopic14;

import com.lenovo.btopic14.bean.SurroundingsBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {

    /**
     * 查询一条当前工厂的环境
     *
     * @param id 工厂ID
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<SurroundingsBean> getSurroundings(@Field("id") int id);
}