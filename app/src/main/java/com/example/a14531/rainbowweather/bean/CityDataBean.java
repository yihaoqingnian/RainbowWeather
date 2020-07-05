package com.example.a14531.rainbowweather.bean;

/*
 *   authr：  tangzhenhua
 *   Date：   2020.06.10
 *   Contact：1453110533@qq.com
 */

import java.util.List;

public class CityDataBean {


    private List<CityBean> city;

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * id : 101010100
         * cityEn : beijing
         * cityZh : 北京
         * provinceEn : beijing
         * provinceZh : 北京
         * leaderEn : beijing
         * leaderZh : 北京
         * lat : 39.904989
         * lon : 116.405285
         */

        private String id;
        private String cityEn;
        private String cityZh;
        private String provinceEn;
        private String provinceZh;
        private String leaderEn;
        private String leaderZh;
        private String lat;
        private String lon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityEn() {
            return cityEn;
        }

        public void setCityEn(String cityEn) {
            this.cityEn = cityEn;
        }

        public String getCityZh() {
            return cityZh;
        }

        public void setCityZh(String cityZh) {
            this.cityZh = cityZh;
        }

        public String getProvinceEn() {
            return provinceEn;
        }

        public void setProvinceEn(String provinceEn) {
            this.provinceEn = provinceEn;
        }

        public String getProvinceZh() {
            return provinceZh;
        }

        public void setProvinceZh(String provinceZh) {
            this.provinceZh = provinceZh;
        }

        public String getLeaderEn() {
            return leaderEn;
        }

        public void setLeaderEn(String leaderEn) {
            this.leaderEn = leaderEn;
        }

        public String getLeaderZh() {
            return leaderZh;
        }

        public void setLeaderZh(String leaderZh) {
            this.leaderZh = leaderZh;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }
}
