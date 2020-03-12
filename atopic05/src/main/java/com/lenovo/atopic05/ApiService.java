package com.lenovo.atopic05;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {

    //查询全部学生员工
    @POST("Interface/index/getAllUserPeople")
    Observable<UserPeople> getAllUserPeople();

    //查询全部学生生产线
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<UserProductionLine> getAllUserProductionLine();

    //查询全部生产线
    @POST("dataInterface/ProductionLine/getAll")
    Observable<ProductionLine> getAllProductionLine();

}
