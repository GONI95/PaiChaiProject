package com.example.paichaiproject.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
//정보를 보내는 것임
public class RegisterRequest extends StringRequest {
    final static private String URL = "https://sjs4209.cafe24.com/PaichaiRegister.php";
    //접속할 서버의 주소 내의 php 파일
    private Map<String, String> parameters;
    //<key:DB상의 컬랙션 이름, value>를 묶어 하나의 entry로 저장하는 특징
    // Map<String, String> map = new HashMap<String, String>(); 사용법

    public RegisterRequest(String userID, String userPassword, String userBirthday,
                           String userClass, String userName, String userPhone,
                           String userGender, String userMajor, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        // 해당 URL에 파라미터를 POST(숨김) 방식으로 넘김
        parameters = new HashMap<>();
        parameters.put("userID", userID);   //put() key, value를 매핑하여 hashmap에 저장 v를 리턴
        parameters.put("userPassword", userPassword);
        parameters.put("userBirthday", userBirthday);
        parameters.put("userClass", userClass);
        parameters.put("userName", userName);
        parameters.put("userPhone", userPhone);
        parameters.put("userGender", userGender);
        parameters.put("userMajor", userMajor);
        parameters.put("userEmail", userEmail);
    }   // 생성자
    @Override
    public Map<String, String> getParams() {
        return parameters;  // 객체를 리턴
    }
}