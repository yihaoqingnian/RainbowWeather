package com.example.a14531.rainbowweather;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.a14531.rainbowweather.base.BaseActivity;
import com.example.a14531.rainbowweather.city_manager.CityManagerActivity;
import com.example.a14531.rainbowweather.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    ImageView addCityIv,moreIv;
    LinearLayout pointlayout;
    LinearLayout outLayout;
    ViewPager mainVp;
    //ViewPager的数据源
    List<Fragment>fragmentList;
    //要显示的城市的集合
    List<String>cityList = DBManager.queryAllCityName();
    List<String>fcityList;
    //表示ViewPager的页数
    List<ImageView>imgList;
    private CityFragmentPagerAdapter adapter;
    private SharedPreferences pref;
    private int bgNum;
    String fcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initMap();

        addCityIv = findViewById(R.id.main_iv_add);
        moreIv = findViewById(R.id.main_iv_more);
        pointlayout = findViewById(R.id.mian_layout_point);

        outLayout = findViewById(R.id.main_out_layout);

        exchangeBg();
        mainVp = findViewById(R.id.main_vp);

        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);

        fragmentList = new ArrayList<>();
//        cityList = DBManager.queryAllCityName();//获取数据库包含的城市信息列表

        imgList = new ArrayList<>();

        //添加城市界面传过来
        try {
            Intent intent = getIntent();
            String city = intent.getStringExtra("city");
            if (!cityList.contains(city)&&!TextUtils.isEmpty(city)) {
                cityList.add(city);
            }
        }catch (Exception e){
            Log.i("animee","程序出现问题了！！");
        }
        SharedPreferences sp = getSharedPreferences("data",0);
        String fcity = sp.getString("fcity",null);
        Toast.makeText(this,"当前城市："+fcity,Toast.LENGTH_SHORT).show();

        if (!cityList.contains(fcity)&&!TextUtils.isEmpty(fcity)) {
            cityList.add(fcity);
        }

        if (cityList.size()==0) {
            cityList.add("北京");

        }

        //初始化ViewPager
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainVp.setAdapter(adapter);
        //创建小圆点
        initPoint();
        //设置显示最新城市信息
        mainVp.setCurrentItem(fragmentList.size()-1);
        //设置ViewPager
        setPagerListener();

//        Toast.makeText(this,"123"+fcity+"456",Toast.LENGTH_SHORT).show();

    }

    private void setPagerListener() {
        //设置监听事件
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0 ; i< imgList.size();i++){
                    imgList.get(i).setImageResource(R.mipmap.a1);
                }
                imgList.get(position).setImageResource(R.mipmap.a3);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initPoint() {
        //创建小圆点
        for (int i = 0 ; i<fragmentList.size();i++){
            ImageView pIv = new ImageView(this);
            pIv.setImageResource(R.mipmap.a1);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIv);
            pointlayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a3);
    }

    private void initPager() {
        //创建Fragment对象，添加ViewPager数据源中
        for (int i = 0 ; i < cityList.size() ; i ++){
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_iv_add:
                intent.setClass(this,CityManagerActivity.class);
                break;
            case R.id.main_iv_more:
                intent.setClass(this,SettingActivity.class);
                break;
        }
        startActivity(intent);
    }

    //页面重写加载时会调用的函数，这个函数在页面获取焦点之前进行调用，此处完成ViewPager页数的更新
    @Override
    protected void onRestart() {
        super.onRestart();
        //获取数据库当中还剩下的城市集合
        List<String> list = DBManager.queryAllCityName();
        if (list.size()==0) {
            list.add("北京");
//            list.add(fcity);
        }
        cityList.clear();    //重写加载之前，清空原本数据源
        cityList.addAll(list);
        //剩余城市也要创建对应的fragment页面
        fragmentList.clear();
        initPager();
        adapter.notifyDataSetChanged();
        //页面数量发生改变，指示器的数量也会发生变化，重写设置添加指示器
        imgList.clear();
        pointlayout.removeAllViews();   //将布局当中所有元素全部移除
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);
    }

    //换壁纸的函数
    public void exchangeBg(){
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgNum = pref.getInt("bg", 2);
        switch (bgNum) {
            case 0:
                outLayout.setBackgroundResource(R.mipmap.bg);
                break;
            case 1:
                outLayout.setBackgroundResource(R.mipmap.bg2);
                break;
            case 2:
                outLayout.setBackgroundResource(R.mipmap.bg3);
                break;
        }
    }
}
