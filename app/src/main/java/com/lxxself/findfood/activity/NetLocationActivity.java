package com.lxxself.findfood.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.lxxself.findfood.R;
import com.lxxself.findfood.util.SPUtils;
import com.lxxself.findfood.util.ToastUtil;
import com.socks.library.KLog;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * Created by lxxself on 2015/10/29.
 */
public class NetLocationActivity extends AppCompatActivity implements AMapLocationListener, OnWeatherSearchListener {
    
    protected String mLocationLatlngText="---";// 坐标信息
    protected String mLocationAccurancyText;// 定位精确信息
    protected String mLocationMethodText;// 定位方法信息
    protected String mLocationTimeText;// 定位时间信息
    protected String mLocationDesText;// 定位描述信息
    protected String mLocationCountryText;// 所在国家
    protected String mLocationProvinceText;// 所在省
    protected String mLocationCityText;// 所在市
    protected String mLocationCountyText;// 所在区县
    protected String mLocationRoadText;// 所在街道
    protected String mLocationPOIText="";// POI名称
    protected String mLocationCityCodeText;// 城市编码
    protected String mLocationAreaCodeText;// 区域编码

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption = null;

    protected String mCity;
    protected String mWeather="";
    protected String mWindDir;
    protected String mWindPower;
    protected String mHumidity;
    protected String mReportTime;
    protected String mTemperature;
    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    private LocalWeatherForecast weatherforecast;
    private List<LocalDayWeatherForecast> forecastlist = null;
    private String cityname="杭州市";
    //保存定位相关信息作为本地缓存
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KLog.d("onCreate: " + ToastUtil.sHA1(NetLocationActivity.this));
        init();
    }

    //DELL
//8B:E1:94:CA:B0:A5:25:5E:4A:E3:0A:8B:0C:F4:1A:E3:DF:4D:78:61:
    //ASUS
    //4A:31:C2:E9:F5:A3:D2:F2:22:93:F4:EF:BA:E8:B3:4C:9C:31:73:EA
    private void searchliveweather(String cityname) {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
    private void searchforcastsweather() {
        mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_FORECAST);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult ,int rCode) {
        if (rCode == 0) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                weatherlive = weatherLiveResult.getLiveResult();
                mCity = weatherlive.getCity();//城市
                mWeather = weatherlive.getWeather();//天气情况
                mWindDir = weatherlive.getWindDirection();//风向
                mWindPower = weatherlive.getWindPower();//风力
                mHumidity = weatherlive.getHumidity();//空气湿度
                mReportTime = weatherlive.getReportTime();//数据发布时间
                mTemperature = weatherlive.getTemperature()+"℃";//气温
            }else {
                ToastUtil.show(NetLocationActivity.this, R.string.no_result);
            }
        }else {
            ToastUtil.showerror(NetLocationActivity.this, rCode);
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }

    /**
     * 初始化定位
     */
    private void init() {
        mlocationClient = new AMapLocationClient(getApplicationContext());
        mlocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(50*1000*1000);
//给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
//启动定位
        mlocationClient.startLocation();

        //注册天气
        mquery = new WeatherSearchQuery(mLocationCityText, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn();

    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            DecimalFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(2);
            format.setRoundingMode(RoundingMode.UP);

            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                // 定位成功回调信息，设置相关消息
                mLocationLatlngText = ("" + format.format(aMapLocation.getLatitude()) + " , "
                        + format.format(aMapLocation.getLongitude()));
                mLocationAccurancyText = (String.valueOf(aMapLocation
                        .getAccuracy()));
                mLocationMethodText = (aMapLocation.getProvider());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());

                mLocationTimeText = (df.format(date));
                mLocationDesText = (aMapLocation.getAddress());
                mLocationCountryText = (aMapLocation.getCountry());
                if (aMapLocation.getProvince() == null) {
                    mLocationProvinceText = ("null");
                } else {
                    mLocationProvinceText = (aMapLocation.getProvince());
                }
                mLocationCityText = (aMapLocation.getCity());
                mLocationCountyText = (aMapLocation.getDistrict());
                mLocationRoadText = (aMapLocation.getRoad());
                mLocationPOIText = (aMapLocation.getPoiName());
                mLocationCityCodeText = (aMapLocation.getCityCode());
                mLocationAreaCodeText = (aMapLocation.getAdCode());
                SPUtils.putShareData(NetLocationActivity.this, "latitude", (float) aMapLocation.getLatitude());
                SPUtils.putShareData(NetLocationActivity.this, "longitude", (float) aMapLocation.getLongitude());
                searchliveweather(aMapLocation.getCity());
                
            } else {
                KLog.e("AmapErr", "Location ERR:" + aMapLocation.getErrorCode());
            }
            KLog.d("--------------定位------------");
            KLog.d(mLocationLatlngText);
            KLog.d(mLocationAccurancyText);
            KLog.d(mLocationMethodText);
            KLog.d(mLocationTimeText);
            KLog.d(mLocationDesText);
            KLog.d(mLocationCountryText);
            KLog.d(mLocationProvinceText);
            KLog.d(mLocationCityText);
            KLog.d(mLocationCountyText);
            KLog.d(mLocationRoadText);
            KLog.d(mLocationPOIText);
            KLog.d(mLocationCityCodeText);
            KLog.d(mLocationAreaCodeText);

        }
    };




    @Override
    protected void onPause() {
        super.onPause();
        mlocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlocationClient.onDestroy();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }
}
