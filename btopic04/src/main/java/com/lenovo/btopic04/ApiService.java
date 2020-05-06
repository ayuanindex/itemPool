package com.lenovo.btopic04;

import com.lenovo.btopic04.bean.OrderBean;
import com.lenovo.btopic04.bean.ResultDataBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取所有订单
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserAppointment/getAll")
    Observable<OrderBean> getAllOrder();

    /**
     * 添加订单
     *
     * @param hashMap 参数集合
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserAppointment/create")
    @FormUrlEncoded
    Observable<OrderBean> addOrder(@FieldMap HashMap<String, Object> hashMap);

    /**
     * 删除一个订单
     *
     * @param id 订单的ID
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserAppointment/delete")
    @FormUrlEncoded
    Observable<ResultDataBean> removeOrder(@Field("id") int id);
}
