package com.lenovo.atopic03;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {

    //查询全部原材料
    @POST("dataInterface/Part/getAll")
    Observable<Part> getAllPart();

    //查询全部供货商
    @POST("dataInterface/Suppier/getAll")
    Observable<Suppier> getAllSuppier();

    //查询全部供货列表
    @POST("dataInterface/SuppierList/getAll")
    Observable<SuppierList> getAllSuppierList();
}
