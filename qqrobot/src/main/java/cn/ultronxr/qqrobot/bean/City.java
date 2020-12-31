package cn.ultronxr.qqrobot.bean;

import java.util.Arrays;

public enum City {
    ;

    private String cityId;
    private String cityName;

    private City(String cityId, String cityName){
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public static City getCityId(String cityName){
        return Arrays.stream(City.values())
                    .filter(city -> city.getCityName().equals(cityName))
                    .findFirst().get();
    }

    public static String getCityName(String cityId){
        for(City c : City.values()){
            if(c.getCityId().contains(cityId)){
                return c.getCityName();
            }
        }
        return "";
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
