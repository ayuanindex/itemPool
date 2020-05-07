package com.lenovo.btopic08;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.lenovo.basic.utils.Network;
import com.lenovo.btopic08.bean.Environmental;

import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayuan
 */
public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private ApiService remote;
    private Disposable subscribe;
    private Disposable subscribe1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        remote = Network.remote(ApiService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getEnvironmental();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 获取工厂的当前环境
     */
    private void getEnvironmental() {
        subscribe = Observable.interval(3, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        subscribe1 = remote.getEnvironmental(1)
                                .subscribeOn(Schedulers.io())
                                .map(Environmental::getData)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<Environmental.DataBean>>() {
                                    @Override
                                    public void accept(List<Environmental.DataBean> dataBeans) throws Exception {
                                        Log.d(TAG, "accept: " + dataBeans.toString());
                                        sendNotification(dataBeans.get(0));
                                    }
                                }, (Throwable throwable) -> Log.d(TAG, "accept: 请求工厂环境数据出现问题----" + throwable.getMessage()));
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                    }
                }, (Throwable throwable) -> Log.d(TAG, "accept: 循环腿就出现问题----" + throwable.getMessage()));
    }

    /**
     * 发送通知
     *
     * @param dataBean
     */
    @SuppressLint("NewApi")
    private void sendNotification(Environmental.DataBean dataBean) {
        String message = "温度：" + dataBean.getWorkshopTemp() + "         电力消耗" + dataBean.getPowerConsume() + "kw/h";


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "渠道ID";
            String channelName = "渠道A";
            NotificationChannel channel = new NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_NONE);
            notificationManager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(this, id)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pic_icon))
                    .setSmallIcon(R.drawable.pic_icon)
                    .setContentTitle("智慧制造工厂环境监控")
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .build();
        }
        notificationManager.notify(123, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscribe.dispose();
    }
}
