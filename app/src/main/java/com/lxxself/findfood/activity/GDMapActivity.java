package com.lxxself.findfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.lxxself.findfood.R;
import com.lxxself.findfood.util.ToastUtil;

public class GDMapActivity extends AppCompatActivity implements
        RouteSearch.OnRouteSearchListener, AMapLocationListener,
        LocationSource{

    private MapView mapView;
    private AMap aMap;
    private int busMode = RouteSearch.BusDefault;// 公交默认模式
    private int drivingMode = RouteSearch.DrivingDefault;// 驾车默认模式
    private int walkMode = RouteSearch.WalkDefault;// 步行默认模式
    private BusRouteResult busRouteResult;// 公交模式查询结果
    private DriveRouteResult driveRouteResult;// 驾车模式查询结果
    private WalkRouteResult walkRouteResult;// 步行模式查询结果
    private int routeType = 1;// 1代表公交模式，2代表驾车模式，3代表步行模式
    private RouteSearch routeSearch;
    private LatLonPoint startPoint;
    private LatLonPoint endPoint;
    private LatLonPoint currentPoint;
    private RouteSearch.FromAndTo fromAndTo;
    private AMapLocationClient mlocationClient;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocation currentMapLocation;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdmap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent getIntent = getIntent();
        if (getIntent != null) {
            float[] point = getIntent.getFloatArrayExtra("point");
            endPoint = new LatLonPoint(point[0],point[1]);
        }
//        MapsInitializer.sdcardDir = OffLineMapUtils.getSdCacheDir(this);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();

    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            registerListener();
        }
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);

    }

    private void registerListener() {
        //设置定位
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

    }

    public void StartDrive(View view) {
        ToastUtil.show(GDMapActivity.this, "开始驾驶搜索");
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, drivingMode, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
    }

    public void StartBus(View view) {
        ToastUtil.show(GDMapActivity.this, "开始公交搜索");
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, busMode, "0571", 0);
        routeSearch.calculateBusRouteAsyn(query);
    }

    public void StartWalk(View view) {
        ToastUtil.show(GDMapActivity.this, "开始行走搜索");
        RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, walkMode);
        routeSearch.calculateWalkRouteAsyn(query);
    }

    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        dissmissProgressDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);
                aMap.clear();
                BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap,
                        busPath, busRouteResult.getStartPos(),
                        busRouteResult.getTargetPos());
                routeOverlay.removeFromMap();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            } else {
                ToastUtil.show(GDMapActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(GDMapActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(GDMapActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(GDMapActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }

    /**
     * 驾车结果回调
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        dissmissProgressDialog();
        ToastUtil.show(GDMapActivity.this, "显示行车路线规划");
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        this, aMap, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                ToastUtil.show(GDMapActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(GDMapActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(GDMapActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(GDMapActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {

        ToastUtil.show(GDMapActivity.this, "显示步行路线规划");

        if (rCode == 0) {
            if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
                walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this, aMap, walkPath, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            } else {
                ToastUtil.show(GDMapActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.show(GDMapActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.show(GDMapActivity.this, R.string.error_key);
        } else {
            ToastUtil.show(GDMapActivity.this, getString(R.string.error_other)
                    + rCode);
        }
    }

    private void dissmissProgressDialog() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //显示小蓝点
                mListener.onLocationChanged(aMapLocation);
                currentMapLocation = aMapLocation;
                //获取位置信息
                double geoLat = aMapLocation.getLatitude();
                double geoLng = aMapLocation.getLongitude();
                currentPoint = new LatLonPoint(geoLat, geoLng);
                Log.d("currentPoint", currentPoint.getLatitude() + "," + currentPoint.getLongitude() + "");

//                startPoint = new LatLonPoint(30.3134798374,120.3529457707);
                startPoint = currentPoint;
//                endPoint = new LatLonPoint(30.2910537551, 120.2129113763);
                fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
//                StartWalk(null);
            }else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }

    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
