package com.lenovo.btopic01;

import com.lenovo.btopic01.bean.CarsBean;
import com.lenovo.btopic01.bean.LinePeopleBean;
import com.lenovo.btopic01.bean.PeopleBean;
import com.lenovo.btopic01.bean.ProductionLineBean;
import com.lenovo.btopic01.bean.StatusBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取所有生产线信息
     *
     * @param position 需要查询的位置
     * @return 返回RxJava的中的一个对象
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getAllProductionLine(@Field("position") int position);

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     * @return 返回人员数据RxJava的操作对象
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<StatusBean> createProduction(@Field("lineId") int lineId, @Field("pos") int pos);

    /**
     * 获取全部人员数据
     *
     * @return 返回人员数据RxJava的操作对象
     */
    @POST("dataInterface/People/getAll")
    Observable<PeopleBean> getAllPeople();

    /**
     * 获取当前生产线的员工
     *
     * @param userProductionLineId 生产线的ID
     * @return 返回人员数据RxJava的操作对象
     */
    @POST("dataInterface/UserPeople/search")
    @FormUrlEncoded
    Observable<LinePeopleBean> getLinePeople(@Field("userProductionLineId") int userProductionLineId);

    /**
     * 获取所有车辆成品仓库信息
     *
     * @return 返回人员数据RxJava的操作对象
     */
    @POST("Interface/index/getUserNormalCarStoreAll")
    Observable<CarsBean> getCars();
}
