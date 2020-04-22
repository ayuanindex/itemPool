package com.lenovo.btopic02;

import com.lenovo.btopic02.bean.StudentStaffBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author ayuan
 */
public interface ApiService {
    /**
     * 获取全部用户工厂员工数据
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPeople/getAll")
    Observable<StudentStaffBean> getLineStudentStaff();
}
