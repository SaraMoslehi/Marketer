package com.example.sara.marketer.model;

/**
 * Created by Raad on 11/21/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {



        @SerializedName("city_id")
        @Expose
        private String cityId;
        @SerializedName("city_name")
        @Expose
        private String cityName;

        /**
         * No args constructor for use in serialization
         *
         */
        public City() {
        }

        /**
         *
         * @param cityId
         * @param cityName
         */
        public City(String cityId, String cityName) {
            super();
            this.cityId = cityId;
            this.cityName = cityName;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }


}
