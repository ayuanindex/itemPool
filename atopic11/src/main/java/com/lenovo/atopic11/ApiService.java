package com.lenovo.atopic11;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {

    //查询全部生产环节
    @POST("dataInterface/PLStep/getAll")
    Observable<PLStep> getAllPLStep();


}
