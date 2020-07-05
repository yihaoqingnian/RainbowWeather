package com.example.a14531.rainbowweather.base;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.support.v4.app.Fragment;

import org.xutils.http.RequestParams;
import org.xutils.x;

import org.xutils.common.Callback;

public class BaseFragment extends Fragment implements Callback.CommonCallback<String> {

    public void loadData(String path){
        RequestParams params = new RequestParams(path);
        x.http().get(params,this);


    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    //取消请求时
    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
