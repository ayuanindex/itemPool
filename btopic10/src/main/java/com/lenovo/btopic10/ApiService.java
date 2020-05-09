package com.lenovo.btopic10;

import com.lenovo.btopic10.bean.AllProductionLineBean;
import com.lenovo.btopic10.bean.AllStageBean;
import com.lenovo.btopic10.bean.CreateResultBean;
import com.lenovo.btopic10.bean.UpdateAiResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取所有生产工序的数据
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/Stage/getAll")
    Observable<AllStageBean> getAllStage();

    /**
     * 获取当前所有的生产线
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<AllProductionLineBean> getAllProductionLine();

    /**
     * 修改生产线的Ai状态
     *
     * @param id   生产线ID
     * @param isAi 是否ai的标示符---0表示非Ai，请表示Ai
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/updateIsAI")
    @FormUrlEncoded
    Observable<UpdateAiResultBean> updateAiState(@Field("id") int id, @Field("isAI") int isAi);

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型ID
     * @param pos    创建生产线的目标位置
     * @return 返回可操组的RxJava对象
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<CreateResultBean> createProductionLine(@Field("lineId") int lineId, @Field("pos") int pos);
}
