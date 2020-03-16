package com.lenovo.atopic07;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {
    //查询全部生产环节
    @POST("dataInterface/PLStep/getAll")
    Observable<PLStep> getAllPLStep();


    //查询全部生产环节原材料消耗
    @POST("dataInterface/plStepCost/getAll")
    Observable<plStepCost> getAllPlStepCost();


    //查询全部原材料
    @POST("dataInterface/Part/getAll")
    Observable<Part> getAllPart();
}
