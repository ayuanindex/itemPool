package com.lenovo.btopic09;

import com.lenovo.btopic09.bean.UserWorkBean;
import com.lenovo.btopic09.bean.UserWorkResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取当前工厂的信息
     *
     * @param id 共仓ID
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkInfo/getInfo")
    @FormUrlEncoded
    Observable<UserWorkBean> getUserWork(@Field("id") int id);

    /**
     * 修改工厂金币
     *
     * @param id    工厂ID
     * @param price 目标金额
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkInfo/updatePrice")
    @FormUrlEncoded
    Observable<UserWorkResultBean> modifyFactoryMoney(@Field("id") int id, @Field("price") int price);
}
