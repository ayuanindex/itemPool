package com.lenovo.btopic1;

import com.lenovo.btopic1.bean.ProductionLineBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取所有生产线信息
     *
     * @return 返回RxJava的中的一个对象
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<ProductionLineBean> getAllProductionLine();
}
