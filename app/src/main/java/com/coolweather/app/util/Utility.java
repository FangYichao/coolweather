package com.coolweather.app.util;

import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

/**
 * Created by Administrator on 2015/12/25.
 */
public class Utility {

    /**
     * 解析从服务器返回的省数据
     */
    public  synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length>0){
                for(String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据放到数据库里
                    coolWeatherDB.savaProvince(province);

            }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析从服务器返回的市数据
     */
    public  synchronized static boolean handleCityResponse(CoolWeatherDB coolWeatherDB,String response,String provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length>0){
                for (String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.savaCity(city);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析从服务器返回的县数据
     */
    public  synchronized static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB,String response,String cityeId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length>0) {
                for (String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityeId);
                    coolWeatherDB.savaCounty(county);
                }
                return true;
            }
        }
        return false;
    }

}
