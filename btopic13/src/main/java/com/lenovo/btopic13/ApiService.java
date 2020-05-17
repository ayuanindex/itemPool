package com.lenovo.btopic13;

import com.lenovo.btopic13.bean.ProductionLineBean;
import com.lenovo.btopic13.bean.SwitchAiResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取所有生产线详情
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<ProductionLineBean> getAllProductionLine();

    /**
     * 修改生产线的Ai状态
     *
     * @param id   生产线的ID
     * @param isAi 0表示关闭Ai，1表示打开
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserProductionLine/updateIsAI")
    @FormUrlEncoded
    Observable<SwitchAiResultBean> changeAiStatus(@Field("id") int id, @Field("isAI") int isAi);

    /**
     * 在指定的位置创建生产线
     *
     * @param lineId 生产线类型ID
     * @param pos    生产线位置
     * @return 返回可操作的RxJava对象
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<SwitchAiResultBean> createProductionLine(@Field("lineId") int lineId, @Field("pos") int pos);
}