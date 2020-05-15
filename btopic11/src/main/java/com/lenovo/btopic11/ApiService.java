package com.lenovo.btopic11;

import com.lenovo.btopic11.bean.GoldCoinExpenditureBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 查询用户金币支出日志
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserOutPriceLog/getAll")
    Observable<GoldCoinExpenditureBean> outlayLog();

}
