package com.lenovo.btopic05;

import com.lenovo.btopic05.bean.ProductionLineBean;
import com.lenovo.btopic05.bean.ProductionProcedureBean;
import com.lenovo.btopic05.bean.ProductionProcessesBean;
import com.lenovo.btopic05.bean.ResultStatusBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {

    /**
     * 根据位置获取生产线
     *
     * @param position 生产线位置
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionLineByPosition(@Field("position") int position);

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     * @return 返回可操作的RxJava对象
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ProductionLineBean> createProductionLine(@Field("lineId") int lineId, @Field("pos") int pos);

    /**
     * 修改生产线是否AI
     *
     * @param id   生产线ID
     * @param isAi 是否AI的标示符
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/updateIsAI")
    @FormUrlEncoded
    Observable<ResultStatusBean> changeIsAi(@Field("id") int id, @Field("isAI") int isAi);

    /**
     * 获取当前生产线所有生产环节信息
     *
     * @param userProductionLineId 生产线ID
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPlStep/search")
    @FormUrlEncoded
    Observable<ProductionProcessesBean> getAllProductionProcesses(@Field("userProductionLineId") int userProductionLineId);

    /**
     * 获取所欲生产工序
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/Stage/getAll")
    Observable<ProductionProcedureBean> getAllProductionProcedure();
}
