package com.example.a14531.rainbowweather.base;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import android.content.Context;
import android.content.res.AssetManager;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CityRead {

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }}
        catch(IOException e){
                e.printStackTrace();
            }
        return stringBuilder.toString();
    }


}