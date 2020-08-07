package com.example.paichaiproject.Gon;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paichaiproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Gon_weather.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gon_weather#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gon_weather extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Gon_weather() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gon_weather.
     */
    // TODO: Rename and change types and number of parameters
    public static Gon_weather newInstance(String param1, String param2) {
        Gon_weather fragment = new Gon_weather();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView result;
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        result = getActivity().findViewById(R.id.result);
        // 날씨정보를 출력시킬 TextView
        String content;
        // openweathermap.org에 응답 데이터를 저장할 String 변수
        Weather weather = new Weather();
        // Weather 객체를 생성

        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q=daejeon&appid=b6907d289e10d714a6e88b30761fae22").get();
            // execute() 메소드로 Weather 클래스 실행하고 get() 메소드로 결과를 검색한 데이터의 정보가 content에 저장됨
            JSONObject jsonObject = new JSONObject(content);
            // 지정된 JSONObject 문자열에서 name/ value 매핑을 사용하여 새로 만듬() -> encode
            String weatherData = jsonObject.getString("weather");
            // JSONObject 객체의 데이터 중 weather의 이름을 가진 부분
            String mainData = jsonObject.getString("main");
            // JSONObject 객체의 데이터 중 main의 이름을 가진 부분
            String windData = jsonObject.getString("wind");
            // JSONObject 객체의 데이터 중 wind의 이름을 가진 부분

            double visibility;
            // 가시거리를 나타내기 위해 생성

            Log.i("weatherData = ", weatherData);
            JSONArray array = new JSONArray(weatherData);
            // jsonObject 객체에 담긴 value를 JSONArray 객체를 생성해 새 값을 저장(mainweather, description 정보)

            String mainweather = "";
            String description = "";
            String temperature = "";
            String windSpeed = "";

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                mainweather = weatherPart.getString("main");
                // 데이터값에서 weather-main 부분(날씨)
                description = weatherPart.getString("description");
                // 데이터값에서 weather-description 부분(상세날씨)
            }

            JSONObject mainPart = new JSONObject(mainData);
            temperature = mainPart.getString("temp");
            // 데이터값에서 base:stations, main-temp 부분(온도)

            JSONObject windPart = new JSONObject(windData);
            windSpeed = windPart.getString("speed");

            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            //기본적으로 가시성은 미터입니다 그래서 아래에 킬로미터 단위로 바꾸는 부분
            int visibilityInKilometer = (int) visibility / 1000;

            Log.i("Mainweather = ", mainweather);
            Log.i("Description = ", description);
            Log.i("Temperature = ", temperature);

            //Now we will show this result on screen
            String resultText = "날씨 : " + mainweather
                    + "\n세부정보 : " + description
                    + "\n온도 : " + temperature + "℃"
                    + "\n가시거리 : " + visibilityInKilometer + "Km"
                    + "\n풍속 : " + windSpeed + "m/s\n";

            result.setText(resultText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class Weather extends AsyncTask<String, Void, String> {
        //쓰레드 및 핸들러를 조작하지 않고 백그라운드 작업을 수행할 수 있게 하기위하여 AsyncTask를 상속받음
        @Override
        protected String doInBackground(String... address) {
            // AsyncTask의 매개변수가 전달됨
            try {
                URL url = new URL(address[0]);
                // url 객체를 생성해 해당 주소를 저장, execute() 메소드의 주소
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // HttpURLConnection 객체를 생성하여 openConnection() 메소드로 새 항목을 얻음 - 요청보냄
                InputStream is = connection.getInputStream();
                // 위 요청에 의한 응답 데이터를 받기 InputStream 객체를 생성
                // 응답 데이터에서 읽은 입력 데이터을 반환(바이트 단위로 읽음)
                InputStreamReader isr = new InputStreamReader(is);
                // 바이트 스트림으로 입력받은 바이트들을 문자로 자동변환해줘서 다시 입력받도록 InputStreamReader(보조스트림)를 사용
                int data = isr.read();
                // read() 메소드로 읽어서 저장 - 아스키코드 값으로 받아옴
                String content = "";
                char ch;
                // int값으로 변환한 정보를 저장하기 위해서 사용
                while (data != -1){
                    // 제대로 된 아스키코드 값이 맞으면, -1은 없음
                    ch = (char) data;
                    // char형으로 변환, String 형으로 바로 변환을 못함
                    content = content + ch;
                    data = isr.read();
                    // 다음 정보를 계속 읽어오도록 해줌
                }
                Log.i("Content = ",content);
                return content;
                // 변환한 정보를 반환

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gon_weather, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
