package com.example.a14531.rainbowweather.base;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.a14531.rainbowweather.db.DBManager;

import org.xutils.x;

public class UniteApp extends Application {

    //定位
    public AMapLocationClient mLocationClient=null;
    //声明定位回调监听器
    public AMapLocationClientOption mLocationOption=null;

    String fcity;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
//        Toast.makeText(this,"...",Toast.LENGTH_LONG).show();
        initMap();
    }



    //    高德
    //获取位置
    private void initMap() {

        //初始化定位
        mLocationClient=new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，AMapLocationMode.Battery_Saving为低功耗模式，AMapLocationMode.Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setOnceLocation(false);//设置是否只定位一次,默认为false
        mLocationOption.setWifiActiveScan(true);//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setMockEnable(false);//设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setInterval(15000);//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(false);//可选，是否设置单次定位默认为false即持续定位
        mLocationOption.setOnceLocationLatest(false); //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mLocationOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mLocationOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    public AMapLocationListener mLocationListener=new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    // aMapLocation.getLatitude();//获取纬度
                    // aMapLocation.getLongitude();//获取经度
                    aMapLocation.getAccuracy();//获取精度信息
                    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    //  aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    //  aMapLocation.getCountry();//国家信息
                    //  aMapLocation.getProvince();//省信息
                    //  aMapLocation.getCity();//城市信息
                    //   aMapLocation.getDistrict();//城区信息
                    //    aMapLocation.getStreet();//街道信息
                    //     aMapLocation.getStreetNum();//街道门牌号信息
                    //    aMapLocation.getCityCode();//城市编码
                    //     aMapLocation.getAdCode();//地区编码
//                    System.out.println("所在城市："+aMapLocation.getCountry()+aMapLocation.getProvince()+aMapLocation.getCity());

                    fcity = aMapLocation.getDistrict().substring(0,aMapLocation.getDistrict().length()-1).replaceAll(" ","");
//                    Toast.makeText(UniteApp.this,"11111当前城市："+fcity,Toast.LENGTH_SHORT).show();
//                    String url = "https://www.tianqiapi.com/api?version=v1&appid=33432843&appsecret=RtRGB99a&city="+fcity;
//                    loadData(url);
//                    Intent intent = new Intent(UniteApp.this,MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("fcity",fcity);
                    SharedPreferences.Editor editor = getSharedPreferences("data",0).edit();
                    editor.putString("fcity",fcity);
                    editor.commit();
//                    Toast.makeText(UniteApp.this,"当前城市："+fcity,Toast.LENGTH_SHORT).show();

                    Log.e("城市：",fcity);
                    mLocationClient.stopLocation();//停止定位
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("info", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }

    };

    protected void onDestroy() {
//        super.onDestroy();
        //销毁
        if (mLocationClient !=null){
            mLocationClient.onDestroy();
        }
    }





}
