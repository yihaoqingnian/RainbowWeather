package com.example.a14531.rainbowweather.city_manager;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a14531.rainbowweather.MainActivity;
import com.example.a14531.rainbowweather.R;
import com.example.a14531.rainbowweather.base.BaseActivity;
import com.example.a14531.rainbowweather.base.CityRead;
import com.example.a14531.rainbowweather.bean.CityDataBean;
import com.google.gson.Gson;



public class SearchCityActivity extends BaseActivity implements View.OnClickListener {


    EditText searchEt;
    ImageView submintIv;
    GridView searchGv;//https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=e9a00261ac4c2de642b997bdec58feb0
    String[]hotCitys = {"北京","上海","广州","深圳","珠海","佛山","南京","苏州","厦门","南宁","成都","长沙","福州","杭州","武汉","青岛","西安","太原","石家庄","沈阳","重庆","天津"};
    private ArrayAdapter<String> adapter;
    private Context context;
    String url1 = "https://www.tianqiapi.com/api?version=v1&appid=33432843&appsecret=RtRGB99a&city=";
    String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        searchEt = findViewById(R.id.search_et);
        submintIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);

        submintIv.setOnClickListener(this);

        //设置适配器
        adapter = new ArrayAdapter<>(this, R.layout.item_hotcity, hotCitys);
        searchGv.setAdapter(adapter);

        setListener();
    }

    //监听事件
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCitys[position];
                String url = url1+city;
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_submit:
                city = searchEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
                    String JsonData = new CityRead().getJson(this,"city.json");
                    CityDataBean resultsBean = new Gson().fromJson(JsonData,CityDataBean.class);
                    for (int i = 0;i<resultsBean.getCity().size();i++){
                        String c = resultsBean.getCity().get(i).getCityZh();
                        if (c.equals(city)){//c.equals(city)
                            onSuccess(city);
                            break;//Toast.makeText(this,"该城市未收录...",Toast.LENGTH_SHORT).show();
                        }else{
                            if (i==resultsBean.getCity().size()-1){
                                Toast.makeText(this,"该城市未收录...",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                }else {
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    private void success(String city) {
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("city",city);
//        startActivity(intent);
//    }

    @Override
    public void onSuccess(String result) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("city",city);
        startActivity(intent);
    }
}
