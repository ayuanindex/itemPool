package com.lenovo.btopic09;

import com.lenovo.btopic09.bean.AllPeopleBean;
import com.lenovo.btopic09.bean.EnlistLogBean;
import com.lenovo.btopic09.bean.MakingsBean;
import com.lenovo.btopic09.bean.MaterialBean;
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

    /**
     * 获取所有人员信息
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/People/getAll")
    Observable<AllPeopleBean> getAllPeople();

    /**
     * 获取所有员工的招募日志
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPeopleLog/getAll")
    Observable<EnlistLogBean> getEnlistLog();

    /**
     * 获取所有原材料信息
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("Interface/index/getMaterial")
    Observable<MaterialBean> getAllMaterial();

    /**
     * 获取所有原材料日志
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPartPurchaseLog/getAll")
    Observable<MakingsBean> getAllMaking();
}
