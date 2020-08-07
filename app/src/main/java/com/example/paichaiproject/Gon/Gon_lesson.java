package com.example.paichaiproject.Gon;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.paichaiproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Gon_lesson.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gon_lesson#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gon_lesson extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Gon_lesson() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gon_lesson.
     */
    // TODO: Rename and change types and number of parameters
    public static Gon_lesson newInstance(String param1, String param2) {
        Gon_lesson fragment = new Gon_lesson();
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

    private ListView lessonListView;
    private LessonListAdapter adapter;
    private ArrayList<Lesson> lessonList;


    public void onActivityCreated(Bundle b) {
        // 프래그먼트와 연결된 액티비티가 onCreate() 메소드의 작업을 완료하면 호출 됨
        super.onActivityCreated(b);

        lessonListView = (ListView) getView().findViewById(R.id.ListView);
        // fragment_gon_lesson의 ListView를 객체로 생성
        lessonList = new ArrayList<Lesson>();
        // lesson의 정보를 담은 ArrayList 객체를 생성
        // 배열과 리스트 특징을 가진 자료구조로 반복문을 통해 주어지는 순서로 출력시킬 수 있도록 합니다.

        adapter = new LessonListAdapter(getActivity().getApplicationContext(), lessonList);
        // 단일 전역 어플리케이션 개체의 컨텍스트와 ArrayList<Lesson>의 객체를 LessonListAdapter에 추가하여 객체로 생성
        lessonListView.setAdapter(adapter);
        // setAdapter() 메소드로 ListView에 어답터를 추가

        new BackgroundTask().execute();
        // BackgrounTask를 실행합니다.
        // 실행이 되면 작업은 4단계를 거침

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        //쓰레드 및 핸들러를 조작하지 않고 백그라운드 작업을 수행할 수 있게 하기위하여 AsyncTask를 상속받음
        String target;
        // 해당 php파일의 주소를 저장하기 위한 String 변수
        /*@Override
        protected void onPreExecute() {
            // 1단계: doInBackground() 메소드 실행 전 UI 스레드에서 실행 execute() 메소드에 의해 직접호출됨
            // 진행률을 표시줄을 표시하여 작업을 설정하는데 사용됨, 기본 버전은 딱히 하는것이 없음
        }

         */

        @Override
        protected String doInBackground(Void... voids) {
            // 2단계: onPreExecute() 메소드가 실행이 완료된 직후 백그라운드 스레드에서 호출됨
            // AsyncTask의 매개변수가 이 곳으로 전달됨
            target = "https://sjs4209.cafe24.com/LessonList.php";
            try{
                // 패턴: URL설정 -> openConnection() -> getInputStream() -> disconnect()
                URL url = new URL(target);
                // url 객체를 생성해 해당 주소를 저장, try/catch
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // HttpURLConnection 객체를 생성하여 openConnection() 메소드로 새 항목을 얻음 - 요청보냄
                InputStream inputStream = httpURLConnection.getInputStream();
                // 위 요청에 의한 응답 데이터를 받기 InputStream 객체를 생성
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                Log.i("readLine = ", String.valueOf(bufferedReader));
                //InputStream은 바이트단위로 읽어들인 것을 InputStreamReader를 사용하여 문자단위로 InputStream을 읽오게 합니다.
                // ● BufferedReader에 InputStreamReader를 래핑한 이유는 BufferedReader를 사용하여 버퍼링 기능을 추가해 데이터를 매번 읽는게 아니라
                // 일정량 사이즈로 한번에 읽어온 후 버퍼에 보관하여 버퍼에서 읽어오게 끔하여 성능을 높이고 readLine()으로 한줄씩 읽어오게 합니다.
                // 그렇다면 BufferedReader만 쓰지 왜 래핑하냐? BufferedReader는 인코딩, 디코딩 기능이 없어 어느하나가 빠질수가 없는 것.
                // ●next(), read() 안쓰고 readLine() 쓴 이유는 String값은 물론이고 개행문자(엔터값)를 포함해 한줄을 전부 읽어오게 하기위해서

                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                // StringBuilder를 객체로 생성(내부 버퍼 조작가능)
                // 단일 스레드 환경이라면 스트링빌더를 사용하는 것이 좋음, 스트링버퍼의 동기화관련 처리로 인해 스트링빌더 보다 성능이 떨어짐

                while((temp = bufferedReader.readLine()) != null) {
                    // bufferedReader.readLine()로 읽어온 값을 저장한 String 변수 temp가 null 일 때까지 반복
                    // String값은 물론이고 개행문자(엔터값)를 포함해 한줄을 전부 읽어오게 하기위해서
                    stringBuilder.append(temp + "\n");
                    Log.i("stringbuilder = ", String.valueOf(stringBuilder));
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                // 응답 본문을 다 읽었으니 모두 종료
                return stringBuilder.toString().trim();
                // 이 반환값이 onPostExecute(매개변수로 전달)
                // toString(): 객체의 정보를 문자열로 만듬, trim() 객체의 정보의 공백을 없애버림
            }catch (Exception e) {
                e.printStackTrace();
            }

            return null;    // 반환 안하면 오류남

        }
/*
        @Override
        protected void onProgressUpdate(Void... values) {
            // 3단계: doInBackground()가 진행되는 동안 3단계 부분을 처리하는데 ->
            // 매개변수(진행, 값) 진행률에 대한 값을 표시하는 부분, 기본 버전은 딱히 하는게 없음
            super.onProgressUpdate();
        }

 */

        @Override
        protected void onPostExecute(String result) {
            // 4단계: doInBackground()가 완료되면 실행됨(result: php에서 보내준 name:response/value: db table정보)
            Log.i("result = ", result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                // 지정된 JSONObject 문자열에서 name/ value 매핑을 사용하여 새로 만듬() -> encode
                Log.i("jsonObject = ", String.valueOf(jsonObject));

                JSONArray jsonArray = jsonObject.getJSONArray("response");
                // jsonObject 객체에 담긴 value를 JSONArray 객체를 생성해 새 값을 저장
                Log.i("jsonArray = ", String.valueOf(jsonArray));

                int count = 0;
                String Name, Exam, Project, Tip, Level;

                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    // jsonArray 객체의 위치값 마다 정보를 잘라 JSONObject 객체에 저장

                    Name = object.getString("lessonName");
                    Exam = object.getString("lessonExam");
                    Project = object.getString("lessonProject");
                    Tip = object.getString("lessonTip");
                    Level = object.getString("lessonLevel");
                    // php 파일에서 보낸 name값과 같아야함

                    Lesson lesson = new Lesson(Name, Exam, Project, Tip, Level);
                    // Lesson 객체를 생성해 정보들을 매개변수로 전달
                    lessonList.add(lesson);
                    // Lesson 객체를 ArrayList<lesson> 객체에 계속해서 추가해줌 (lessonList: lesson 객체의 정보,lesson: 반복문의 count값에 따른 lesson 객체)
                    adapter.notifyDataSetChanged();
                    // LessonListAdapter 객체의 데이터 갱신
                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gon_lesson, container, false);
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
