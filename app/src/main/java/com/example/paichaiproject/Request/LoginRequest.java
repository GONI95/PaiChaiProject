package com.example.paichaiproject.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "https://sjs4209.cafe24.com/PaichaiLogin.php";
    //접속할 서버의 주소 내의 php 파일
    private Map<String, String> parameters;
    // Map을 구현 <key:DB상의 컬랙션 이름, value>를 묶어 하나의 entry로 저장하는 특징
    // Map<String, String> map = new HashMap<String, String>(); 사용법

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        // 요청을 공식화, 응답을 처리(해당 URL에 파라미터를 POST(숨김) 방식으로 넘김)
        parameters = new HashMap<>();   // null 허용됨
        parameters.put("userID", userID);   // 지정된 k와 v를 연관시킴
        parameters.put("userPassword", userPassword);
    }
    @Override
    public Map<String, String> getParams() {
        // 명령 행인 parameters 의 k, v를 가져옴
        return parameters;
    }
}
