package com.lenovo.atopic10;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求接口
 */
public interface ApiService {

    //查询全部学生生产线
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<UserProductionLine> getAllUserProductionLine();

    //查询全部原材料
    @POST("dataInterface/Part/getAll")
    Observable<Part> getAllPart();


    //查询全部学生备料
    @POST("dataInterface/UserParts/getAll")
    Observable<UserParts> getAllUserParts();


    //新增学生备料
    @POST("dataInterface/UserParts/create")
    @FormUrlEncoded
    Observable<CreatePart> createUserParts(@Field("userWorkId") int userWorkId,
                                           @Field("userProductionLineId") int userProductionLineId,
                                           @Field("partId") int partId,
                                           @Field("num") int num);


}
