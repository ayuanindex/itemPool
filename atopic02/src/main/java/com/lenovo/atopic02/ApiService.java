package com.lenovo.atopic02;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {

    @POST("dataInterface/PassRate/getAll")
    Observable<Bean> getAll();


}
