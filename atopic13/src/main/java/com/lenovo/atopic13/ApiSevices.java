package com.lenovo.atopic13;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiSevices {

    //查询全部人员
    @POST("dataInterface/People/getAll")
    Observable<People> getAllPeople();


    //新增学生员工
    @POST("dataInterface/UserPeople/create")
    @FormUrlEncoded
    Observable<CreatePeople> createUserPeople(@Field("userWorkId") int userWorkId,
                                              @Field("power") int power,
                                              @Field("peopleId") int peopleId,
                                              @Field("userProductionLineId") int userProductionLineId,
                                              @Field("workPostId") int workPostId
    );


    //查询全部招募日志
    @POST("dataInterface/UserPeopleLog/getAll")
    Observable<UserPeopleLog> getAllUserPeopleLog();

    //查询全部招募日志
    @POST("dataInterface/UserPeopleLog/delete")
    @FormUrlEncoded
    Observable<UserPeopleLog> deleteUserPeopleLog(@Field("id") int id);


    //新增学生招募日志
    @POST("dataInterface/UserPeopleLog/create")
    @FormUrlEncoded
    Observable<CreatePeopleLog> createUserPeopleLog(@Field("userWorkId") int userWorkId,
                                                    @Field("userPeopleId") int userPeopleId,
                                                    @Field("time") long time
    );


}
