package com.example.a14531.rainbowweather.city_manager;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a14531.rainbowweather.R;
import com.example.a14531.rainbowweather.bean.WeatherBean;
import com.example.a14531.rainbowweather.db.DatabaseBean;
import com.google.gson.Gson;

import java.util.List;

public class CityManagerAdapter extends BaseAdapter {
    Context context;
    List<DatabaseBean>mDatas;

    public CityManagerAdapter(Context context, List<DatabaseBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_city_manager,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        DatabaseBean bean = mDatas.get(position);
        holder.cityTv.setText(bean.getCity());
        WeatherBean weatherBean = new Gson().fromJson(bean.getContent(),WeatherBean.class);
        WeatherBean.DataBean dataBean = weatherBean.getData().get(0);
        holder.conTv.setText(dataBean.getWea());
        holder.cityTv.setText(weatherBean.getCity());
        holder.tempTv.setText(dataBean.getTem2()+"~"+dataBean.getTem1());
        holder.currentTv.setText(dataBean.getTem());
        return convertView;


    }

    class ViewHolder{
        TextView cityTv,conTv,tempTv,currentTv;
        public ViewHolder(View itemView){
            cityTv = itemView.findViewById(R.id.item_city_tv_city);
            conTv = itemView.findViewById(R.id.item_city_tv_condition);
            tempTv = itemView.findViewById(R.id.item_city_temprange);
            currentTv = itemView.findViewById(R.id.item_city_tv_temp);
        }
    }
}
