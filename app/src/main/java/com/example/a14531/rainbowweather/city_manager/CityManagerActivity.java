package com.example.a14531.rainbowweather.city_manager;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a14531.rainbowweather.R;
import com.example.a14531.rainbowweather.db.DBManager;
import com.example.a14531.rainbowweather.db.DatabaseBean;

import java.util.ArrayList;
import java.util.List;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView addIv,backIv,deleteIv;
    ListView cityLv;
    List<DatabaseBean>mDatas;  //显示列表数据源
    private CityManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);

        addIv = findViewById(R.id.city_iv_add);
        backIv = findViewById(R.id.city_iv_back);
        deleteIv = findViewById(R.id.city_iv_delete);
        cityLv = findViewById(R.id.city_lv);
        mDatas = new ArrayList<>();

        //添加点击事件
        addIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
//        cityLv.setOnItemLongClickListener(this);

        //设置适配器
        adapter = new CityManagerAdapter(this, mDatas);
        cityLv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<DatabaseBean> list = DBManager.queryAllInfo();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city_iv_add:
                int cityCount = DBManager.getCityCount();
                if (cityCount<5){
                    Intent intent = new Intent(this,SearchCityActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"数量已达上限，请删除后再操作",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.city_iv_back:
                finish();
                break;
            case R.id.city_iv_delete:
                Intent intent1 = new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;
        }
    }

//    cityLv.


//    //长按删除
//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        TextView cv = cityLv.getChildAt(position).findViewById(R.id.item_city_tv_city);
//        String city = cv.getText().toString();
//        tipDialog(city);
//        return true;
//    }



//    public void tipDialog(final String city) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("删除：");
//        builder.setMessage("确认删除此城市？");
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失
//
//        //设置正面按钮
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(CityManagerActivity.this, city, Toast.LENGTH_SHORT).show();
//                mDatas.remove(city);
//
//
//            }
//        });
//        //设置反面按钮
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(CityManagerActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//
//        AlertDialog dialog = builder.create();      //创建AlertDialog对象
//        //对话框显示的监听事件
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
////                Log.e(TAG, "对话框显示了");
//            }
//        });
//        //对话框消失的监听事件
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
////                Log.e(TAG, "对话框消失了");
//            }
//        });
//        dialog.show();                              //显示对话框
//    }

}
