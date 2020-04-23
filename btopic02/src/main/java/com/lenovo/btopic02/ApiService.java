package com.lenovo.btopic02;

import com.lenovo.btopic02.bean.AddStudentStaffResult;
import com.lenovo.btopic02.bean.AllPeopleBean;
import com.lenovo.btopic02.bean.ProductionLineBean;
import com.lenovo.btopic02.bean.StudentStaffBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
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

    /**
     * 获取所有人员信息
     *
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/People/getAll")
    Observable<AllPeopleBean> getAllPeople();

    /**
     * 根据生产线位置查询生产线
     *
     * @param position 生产线为位置
     * @return 返回可操作的RxJava对象
     */
    @POST("Interface/index/getUserLineByPosition")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionLineByPosition(@Field("position") int position);

    /**
     * 添加新的学生员工
     *
     * @param hashMap 需要添加学生员工各个字段的map集合
     * @return 返回可操作的RxJava对象
     */
    @POST("dataInterface/UserPeople/create")
    @FormUrlEncoded
    Observable<AddStudentStaffResult> addStudentStaff(@FieldMap HashMap<String, Object> hashMap);
}