package com.lenovo.atopic06;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {

    //查询全部生产工序
    @POST("dataInterface/Stage/getAll")
    Observable<Stage> getAllStage();

    //查询全部学生生产线
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<UserProductionLine> getAllUserProductionLine();

    //查询全部生产环节
    @POST("dataInterface/PLStep/getAll")
    Observable<PLStep> getAllPLStep();


}
