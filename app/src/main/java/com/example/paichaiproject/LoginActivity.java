package com.example.paichaiproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.paichaiproject.Request.LoginRequest;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private AlertDialog dialog;
    // 발생한 이벤트의 내용을 확인 할 수 있게 하기위한 대화상자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 액티비티 실행 시 화면을 세로로 고정
        setContentView(R.layout.activity_login);

        final TextView website = (TextView) findViewById(R.id.website);
        // 학교 웹사이트를 연결해주는 버튼 역할을 하는 TextView 객체
        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        // 회원가입 화면으로 전환을 하기위한 버튼 역할을 하는 TextView 객체
        registerButton.setOnClickListener(new View.OnClickListener() {
            // setOnClickListener() -> 뷰에 리스너를 연결할때 사용되는 메소드
            // View.OnClickListener() -> 뷰 클릭 시 호출되는 인터페이스
            @Override
            public void onClick(View v) {  // 뷰 클릭 시 호출되는 메소드
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
                // 활동은 맨 위의 활동 스택에 배치
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        // 로그인을 시도하는 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                // 입력한 사용자의 정보를 String 변수에 저장

                Response.Listener<String> reponseLister = new Response.Listener<String>() {
                    // 리스너객체 생성(volley 사용을 위한 ResponseListener 구현 부분)
                    @Override
                    public void onResponse(String response) {      // Do something with the response
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            // 지정된 JSONObject 문자열에서 이름/ 값 매핑을 사용하여 새로 만듬(response 해당 결과를 받아옴 - name:success/value:true)
                            boolean success = jsonResponse.getBoolean("success");
                            // success -> 정상적으로 수행이 되었는지의 response의 값을 의미(매핑 된 값을 리턴)
                            if (!success) {   // 서버에서 보내준 값이 false라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                // 기본 대화상자 테마를 사용하는 대화 상자의 빌더를 생성
                                dialog = builder.setMessage(R.string.Account_verification)  // 출력 할 메시지를 설정하는 메소드
                                        .setNegativeButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                                            // 대화상자의 버튼클릭시 호출하는 리스너(null: 별도의 이벤트 x)
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                idText.setText("");
                                                passwordText.setText("");
                                            }
                                        })
                                        .create();  //show() 메소드로 객체를 호출하는 것(기능적으로 create() 메소드와 같이 씀)
                                dialog.show();  // 종료
                            }
                            else {     // 서버에서 보내준 값이 true라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage(R.string.Login_succeed)
                                        .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                // 로그인 성공 시 -> Intent를 통하여 메인화면으로 전환
                                                LoginActivity.this.startActivity(intent);
                                                finish();   // 해당 엑티비티 종료                                                                                                        //해당 엑티비티를 닫기위해 사용
                                            }
                                        })
                                        .create();
                                dialog.show();                                                                                                                   // 다이어로그 실행  // dialog 빌더에서 setMessage로 로그인에 성공했다고 출력
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, reponseLister);
                //실제로 id와 password를 받고 reponselister를 보낼수 있도록 LoginRequest를 만든다
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                // 요청을 실질적으로 보낼수 있도록 queue를 만듬
                // Volley.newRequestQueue() -> Volley에서 제공하는 기본값을 사용해 RequestQueue를 설정해주는 메서드
                queue.add(loginRequest);
                // 정상적으로 리퀘스트가 보내지고 그 결과로 나온 레스폰스가 위의 제이슨 레스폰스를 통해서 다루어지게 됩니다.
            }
        });
        // 학교 웹사이트를 연결
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pcu.ac.kr/www/index/index.html"));
                startActivity(intent);
            }
        });
    }
}
