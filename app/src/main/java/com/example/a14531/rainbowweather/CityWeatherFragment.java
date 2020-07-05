package com.example.a14531.rainbowweather;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.a14531.rainbowweather.base.BaseFragment;
import com.example.a14531.rainbowweather.bean.WeatherBean;
import com.example.a14531.rainbowweather.db.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

public class CityWeatherFragment extends BaseFragment implements View.OnClickListener {

    TextView cityTv,dateTv,tempTv,conditionTv,windTv,clothIndexTv,carIndexTv,sportIndexTv,raysIndexTv,coldIndexTv,xueIndexTv;
    LinearLayout futureLayout;

    String url1 = "https://www.tianqiapi.com/api?version=v1&appid=33432843&appsecret=RtRGB99a&city=";//https://www.tianqiapi.com/api?version=v1&appid=33432843&appsecret=RtRGB99a&city=%E5%8C%97%E4%BA%AC
//    String url2 = "&output=json&ak=endDQHXWM8mW7bWkZIofQA2OXgQyVQ09";//https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=e9a00261ac4c2de642b997bdec58feb0
    private List<WeatherBean.DataBean.IndexBean> indexList;
    String city;
    ScrollView fragview;
    private SharedPreferences pref;
    private int bgNum;

    public CityWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather,container,false);
        initView(view);
        exchangeBg();

        Bundle bundle = getArguments();
        city = bundle.getString("city");

        String url = url1+city;

        loadData(url);
        return view;
    }

    @Override
    public void onSuccess(String result) {
        //解析展示数据
        parseShowData(result);
        //更新数据
        int i = DBManager.updateInfoByCity(city,result);
        if (i<=0) {
            //更新失败
            DBManager.addCityInfo(city,result);
        }

    }


    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //数据库查询上一次信息加载
        String s = DBManager.queryInfoByCity(city);
        if (!TextUtils.isEmpty(s)) {
            parseShowData(s);
        }

    }

    private void parseShowData(String result) {
        //解析数据
        WeatherBean resultsBean = new Gson().fromJson(result,WeatherBean.class);
//        WeatherBean.DataBean resultsBean = weatherBean.get;

        //获取指数信息
        indexList = resultsBean.getData().get(0).getIndex();
        //设置textView
        dateTv.setText(resultsBean.getUpdate_time().substring(0,10));
        cityTv.setText(resultsBean.getCity());
        //获取今日天气
        WeatherBean.DataBean todayDataBean = resultsBean.getData().get(0);
        windTv.setText(todayDataBean.getWin().get(0)+" "+todayDataBean.getWin_speed());
        tempTv.setText(todayDataBean.getTem());//实时温度
        conditionTv.setText(todayDataBean.getWea());

        //获取未来3天
        List<WeatherBean.DataBean> futureList = resultsBean.getData();
        futureList.remove(0);
        for (int i = 0;i < futureList.size() ; i++){//futureList.size()
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_layout,null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iTv = itemView.findViewById(R.id.item_center_iv);
            //获取天气情况
            WeatherBean.DataBean dataBean = futureList.get(i);
            idateTv.setText(dataBean.getDay());
            iconTv.setText(dataBean.getWea());
            itemprangeTv.setText(dataBean.getTem2()+"~"+dataBean.getTem1());
            Picasso.with(getActivity()).load(dataBean.getWea_img()).into(iTv);
        }

    }

    private void initView(View view) {
        //初始化控件
        cityTv = view.findViewById(R.id.frag_tv_city);
        dateTv = view.findViewById(R.id.frag_tv_date);
        tempTv = view.findViewById(R.id.frag_tv_currenttemp);
        conditionTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        clothIndexTv = view.findViewById(R.id.frag_index_tv_dress);
        carIndexTv = view.findViewById(R.id.frag_index_tv_washcar);
        sportIndexTv = view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv = view.findViewById(R.id.frag_index_tv_rays);
        coldIndexTv = view.findViewById(R.id.frag_index_tv_cold);
        xueIndexTv = view.findViewById(R.id.frag_index_tv_xue);
        futureLayout = view.findViewById(R.id.frag_center_layout);
        fragview = view.findViewById(R.id.fragview);

        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        xueIndexTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (v.getId()) {
            case R.id.frag_index_tv_dress:
                builder.setTitle("穿衣指数");
                WeatherBean.DataBean.IndexBean indexBean = indexList.get(3);
                String msg = indexBean.getLevel()+"\n"+indexBean.getDesc();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_washcar:
                builder.setTitle("洗车指数");
                indexBean = indexList.get(4);
                msg = indexBean.getLevel()+"\n"+indexBean.getDesc();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_sport:
                builder.setTitle("运动指数");
                indexBean = indexList.get(1);
                msg = indexBean.getDesc();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_rays:
                builder.setTitle("紫外线指数");
                indexBean = indexList.get(0);
                msg = indexBean.getLevel()+"\n"+indexBean.getDesc();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_cold:
                builder.setTitle("空气污染指数");
                indexBean = indexList.get(5);
                msg = indexBean.getLevel()+"\n"+indexBean.getDesc();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_xue:
                builder.setTitle("健臻·血糖指数");
                indexBean = indexList.get(2);
                msg = indexBean.getLevel()+"\n"+indexBean.getDesc();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
        }
        builder.create().show();

    }

    //换壁纸的函数
    public void exchangeBg(){
        pref = getActivity().getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgNum = pref.getInt("bg", 2);
        switch (bgNum) {
            case 0:
                fragview.setBackgroundResource(R.mipmap.bg);
                break;
            case 1:
                fragview.setBackgroundResource(R.mipmap.bg2);
                break;
            case 2:
                fragview.setBackgroundResource(R.mipmap.bg3);
                break;
        }

    }


}
