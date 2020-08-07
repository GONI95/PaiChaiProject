package com.example.paichaiproject.Kyo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paichaiproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;


public class IlkyoGoogleMap extends AppCompatActivity implements OnMapReadyCallback {       // OnMapReadyCallback 맵로딩이 완료되면 수행할 동작을 위해 콜백 인터페이스를 상속

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private RecyclerAdapter adapter;
    private Marker placeMarker;
    private GoogleMap mGoogleMap;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilkyo_google_map);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        init();

        getData(R.id.btnA);
    }

    private void init() {       //
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);     // 뷰의 크기를 정보나 갯수와 상관없이 일정하게

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);       // 리사이클러뷰를 리뉴어방식으로 세로로 붙여서

        adapter = new RecyclerAdapter();
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {      // 리사이클러 뷰의 아이템이 클릭 되었을때 동작정의
            @Override
            public void onItemClick(View v, int pos) {
                BasePlace place = adapter.getItem(pos);     // 위치에 해당하는 값을 place에 저장

                if (placeMarker != null) {
                    placeMarker.remove();       // 마크를 지우고
                }

                MarkerOptions markerOptions = new MarkerOptions();       // 마크를 다시만든다
                markerOptions.title(place.getName());
                markerOptions.snippet(place.getDesc());
                markerOptions.position(new LatLng(place.getX(), place.getY()));
                markerOptions.visible(true);


                if (mGoogleMap != null) {       // 클릭된 아이템의 위치정보가 없거나 앱이 초기화되기전 클릭리스너가 호출되면 무시
                    placeMarker = mGoogleMap.addMarker(markerOptions);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng location1 = new LatLng(36.322529, 127.367742); //
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.title("배재대학교 정문");
        markerOptions1.snippet("인싸공간");
        markerOptions1.alpha(0.7f);
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(200f));
        markerOptions1.position(location1);
        googleMap.addMarker(markerOptions1);

        LatLng location2 = new LatLng(36.317605, 127.368058); //
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.title("배재대학교 후문");
        markerOptions2.snippet("한적한공간");
        markerOptions2.alpha(0.7f);
        markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(200f));
        markerOptions2.position(location2);
        googleMap.addMarker(markerOptions2);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 14));  // 숫자가높을수록 가까이
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
    }
    public void onClick(View v) {
        getData(v.getId());
    }
    private void getData(int viewId) {      // 버튼이 눌렸을때 뷰에 해당 값들이 출력
        ArrayList<BasePlace> placeList;

        switch (viewId) {
            case R.id.btnA: {
                Log.d("Felix.yoon", "btnA");
                FoodStore f = new FoodStore();
                f.init();
                placeList = f.getFoodStoreList();       //
                break;
            }
            case R.id.btnB: {
                Log.d("Felix.yoon", "btnB");
                PCStore p = new PCStore();
                p.init();
                placeList = p.getPCStoresList();
                break;
            }
            case R.id.btnC: {
                Log.d("Felix.yoon", "btnC");
                LIBStore u = new LIBStore();
                u.init();
                placeList = u.getLIBStoresList();
                break;
            }
            default: {
                Log.d("Felix.yoon", "default");
                placeList = new ArrayList<>();
            }
        }
        // 해당 영역 동작하는지 여부 확인할 수 있게 로그 출력 (아래 Logcat에서 tag 검색하면 볼 수 있음.)
        Log.d("Felix.yoon", "placeList.get(0): " + placeList.get(0).getName());
        adapter.setList(placeList);     // RecyclerAdapter에 해당 리스트값 대입?
        recyclerView.setAdapter(adapter);
    }

}
