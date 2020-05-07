package com.lenovo.btopic07;

import com.lenovo.btopic07.bean.AllPeopleBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取所有人员信息
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/People/getAll")
    Observable<AllPeopleBean> getAllPeople();

}
