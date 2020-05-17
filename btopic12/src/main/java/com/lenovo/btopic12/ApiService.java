package com.lenovo.btopic12;

import com.lenovo.btopic12.bean.AllProductionProcessesBean;
import com.lenovo.btopic12.bean.AllStageBean;
import com.lenovo.btopic12.bean.AllStepInfoBean;
import com.lenovo.btopic12.bean.ProductionLineBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 根据位置获取生产线信息
     *
     * @param position 生产线的位置
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionByPosition(@Field("position") int position);

    /**
     * 在指定位置创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     * @return 返回可操作的RxJava对象
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ProductionLineBean> createProductionLine(@Field("lineId") int lineId, @Field("pos") int pos);

    /**
     * 获取全部用户生生产环节数据
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPlStep/getAll")
    Observable<AllProductionProcessesBean> getAllProductionProcesses();

    /**
     * 获取全部永固生产环节详细数据
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPlStepInfo/getAll")
    Observable<AllStepInfoBean> getAllStepInfo();

    /**
     * 查询全部生产工序数据
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/Stage/getAll")
    Observable<AllStageBean> getAllStage();
}
