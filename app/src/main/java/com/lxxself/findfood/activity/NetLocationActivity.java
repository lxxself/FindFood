package com.lxxself.findfood.activity;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.lxxself.findfood.util.SPUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lxxself on 2015/10/29.
 */
public class NetLocationActivity extends AppCompatActivity implements AMapLocationListener, AMapLocalWeatherListener {

    public static final String MSG = "";
    public static final String MSG1 = "";
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
    protected String mLocationPOIText;// POI名称
    protected String mLocationCityCodeText;// 城市编码
    protected String mLocationAreaCodeText;// 区域编码

    protected String mCity;
    protected String mWeather;
    protected String mWindDir;
    protected String mWindPower;
    protected String mHumidity;
    protected String mReportTime;
    protected String mTemperature;
    private LocationManagerProxy mLocationManagerProxy;
    private String TAG ="NetLocationActivity";

    //保存定位相关信息作为本地缓存
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }
    /**
     * 初始化定位
     */
    private void init() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);

        //注册天气
        mLocationManagerProxy.requestWeatherUpdates(
                LocationManagerProxy.WEATHER_TYPE_LIVE, this);

    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.UP);

        if (aMapLocation != null
                && aMapLocation.getAMapException().getErrorCode() == 0) {
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
            SPUtils.put(NetLocationActivity.this, "localtionInfo", "latitude", (float)aMapLocation.getLatitude());
            SPUtils.put(NetLocationActivity.this, "localtionInfo", "longitude", (float)aMapLocation.getLongitude());

        } else {
            Log.e("AmapErr", "Location ERR:" + aMapLocation.getAMapException().getErrorCode());
        }
        Log.d(TAG, "--------------定位------------");
        Log.d(TAG, mLocationLatlngText);
        Log.d(TAG, mLocationAccurancyText);
        Log.d(TAG, mLocationMethodText);
        Log.d(TAG, mLocationTimeText);
        Log.d(TAG, mLocationDesText);
        Log.d(TAG, mLocationCountryText);
        Log.d(TAG, mLocationProvinceText);
        Log.d(TAG, mLocationCityText);
        Log.d(TAG, mLocationCountyText);
        Log.d(TAG, mLocationRoadText);
        Log.d(TAG, mLocationPOIText);
        Log.d(TAG, mLocationCityCodeText);
        Log.d(TAG, mLocationAreaCodeText);

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 移除定位请求
        mLocationManagerProxy.removeUpdates(this);
        // 销毁定位
        mLocationManagerProxy.destroy();
    }

    @Override
    public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
        if(aMapLocalWeatherLive!=null && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0){
            mCity = aMapLocalWeatherLive.getCity();//城市
            mWeather = aMapLocalWeatherLive.getWeather();//天气情况
            mWindDir = aMapLocalWeatherLive.getWindDir();//风向
            mWindPower = aMapLocalWeatherLive.getWindPower();//风力
            mHumidity = aMapLocalWeatherLive.getHumidity();//空气湿度
            mReportTime = aMapLocalWeatherLive.getReportTime();//数据发布时间
            mTemperature = aMapLocalWeatherLive.getTemperature()+"℃";//气温
        }else{
            // 获取天气预报失败
            Toast.makeText(this, "获取天气预报失败:" + aMapLocalWeatherLive.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
        SPUtils.put(NetLocationActivity.this, "localtionInfo", "weather", mWeather);
        SPUtils.put(NetLocationActivity.this, "localtionInfo", "temperature", mTemperature);

        Log.d(TAG, "--------------天气------------");
        Log.d(TAG, mCity );
        Log.d(TAG, mWeather );
        Log.d(TAG, mTemperature );
        Log.d(TAG, mWindDir );
        Log.d(TAG, mWindPower );
        Log.d(TAG, mHumidity );
        Log.d(TAG, mReportTime );
    }

    @Override
    public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {

    }
}
