package com.lenovo.btopic03;

import com.lenovo.btopic03.bean.EnvironmentBean;
import com.lenovo.btopic03.bean.ResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取一条工厂的环境信息
     *
     * @param id 工厂ID
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<EnvironmentBean> getEnvironment(@Field("id") int id);

    /**
     * 切换灯光的状态
     *
     * @param id          工厂ID
     * @param lightSwitch 目标状态
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkEnvironmental/updateLightSwitch")
    @FormUrlEncoded
    Observable<ResultBean> changeLightStatus(@Field("id") int id, @Field("lightSwitch") int lightSwitch);

    /**
     * 修改空调的状态
     *
     * @param id      工厂ID
     * @param acOnOff 目标状态
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserWorkEnvironmental/updateAcOnOff")
    @FormUrlEncoded
    Observable<ResultBean> changeAirConditioningStatus(@Field("id") int id, @Field("acOnOff") int acOnOff);
}
