package com.example.paichaiproject;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.paichaiproject.Kyo.PCStore;
import com.example.paichaiproject.Request.RegisterRequest;
import com.example.paichaiproject.Request.ValidateRequest;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    // values 디렉토리의 arrays.xml 정보를 ArrayAdapter 로 받음
    private Spinner spinner;
    // 학과를 선택하기 위한 Spinner
    private String userGender;
    // 선택된 라디오 버튼의 text값을 저장하기 위한 String 변수
    private AlertDialog dialog;
    // 발생한 이벤트의 내용을 확인 할 수 있게 하기위한 대화상자
    private boolean validate = false;
    // 중복확인을 위해 생선한 boolean 형 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 액티비티 실행 시 화면을 세로로 고정
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.majorSpinner);
        // 스피너 객체를 spinner로 생성
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        // adapter ->  생성한 리소스 파일의 학과정보를 리소스에서 얻어 사용할수 있도록 하는 부분(simple_spinner_dropdown_item: 폼의 종류)
        // createFromResource() -> 외부 리소스에서 새 ArrayAdapter를 만듬
        spinner.setAdapter(adapter);
        //스피너에 위에 있는 어뎁터를 추가해주면 정상적으로 등록됩니다

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText birthdatText = (EditText) findViewById(R.id.birthdayText);
        final EditText classText = (EditText) findViewById(R.id.classText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText phoneText = (EditText) findViewById(R.id.phoneText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        // 라디오 그룹의 객체를 genderGroup으로 생성
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        // genderGroupID -> 현재 선택된 라디오 버튼의 id값
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();
        // userGender -> 라디오 버튼에서 위에서 선택된 라디오 버튼의 id값을 매칭시켜 그 값의 text를 저장
        // 아래 내용은 체크상태가 변경되어야 호출되기 때문에 기본값으로 적용할시 성별값이 나오지않는 것을 방지하기 위해 위의 내용을 추가

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //  setOnCheckedChangeListener() -> 뷰에 리스너를 연결할때 사용되는 메소드
            //  OnCheckedChangeListener() -> 복합버튼의 체크상태가 변경되면 호출되는 인터페이스
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                //  복합버튼의 체크상태가 변경되면 호출되는 메소드(group: 상태가 변경된 버튼, i: 버튼의 상태)
                RadioButton genderButton = (RadioButton) findViewById(i);
                //  라디오버튼 객체 생성(상태가 변경된 버튼)
                userGender = genderButton.getText().toString();
                // Stinrg 변수에 변경된 버튼의 text가 저장
            }
        });

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        // 중복확인을 위한 버튼객체 생성(final 초기값 변경에 제약을 주지않으면 오류남 표시:!!)
        validateButton.setOnClickListener(new View.OnClickListener() {
            // setOnClickListener() -> 뷰에 리스너를 연결할때 사용되는 메소드
            // View.OnClickListener() -> 뷰 클릭 시 호출되는 인터페이스
            @Override
            public void onClick(View v) {   // 뷰 클릭 시 호출되는 메소드
                String userID = idText.getText().toString();  // idText에 입력된 정보를 얻어와 userID에 저장
                if(validate){   // validate(boolean) 값이 참이라면
                    return; // 종료
                }
                if(userID.equals(""))    //아이디 빈칸일때
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    // 기본 대화상자 테마를 사용하는 대화 상자의 빌더를 생성
                    dialog = builder.setMessage(R.string.IDBlank)   // 출력 할 메시지를 설정하는 메소드
                            .setPositiveButton(R.string.Confirm,null)
                            // 대화상자의 버튼클릭시 호출하는 리스너(null: 별도의 이벤트 x)
                            .create();
                    dialog.show();  //show() 메소드로 객체를 호출하는 것(기능적으로 create() 메소드와 같이 씀)
                    return; // 종료
                }
                // 정상적으로 ID값을 입력했을때 중복확인 절차를 이어나감
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    // 리스너객체 생성(volley 사용을 위한 ResponseListener 구현 부분)
                    @Override
                    public void onResponse(String response) {      // Do something with the response
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            // 지정된 JSONObject 문자열에서 이름/ 값 매핑을 사용하여 새로 만듬
                            boolean success = jsonResponse.getBoolean("success");
                            // success -> 정상적으로 수행이 되었는지의 response의 값을 의미
                            if(success){    // 서버에서 보내준 값이 true라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage(R.string.Available_ID)
                                        .setPositiveButton(R.string.Confirm,null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);   // 수정하지 못하게 TextView를  잠금
                                validate = true;    // validate 변수에 체크가 완료 되었다는 것을 의미
                                idText.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));    //표시:!!
                            }else {        // 서버에서 보내준 값이 false라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage(R.string.Not_available_ID)
                                        .setNegativeButton(R.string.Confirm,null)
                                        .create();
                                dialog.show();

                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                // 생성자를 통하여 ValidateRequest 객체를 만듬(ID값, 성공 실패의 유무 값??)
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                // 요청을 실질적으로 보낼수 있도록 queue를 만듬
                // Volley.newRequestQueue() -> Volley에서 제공하는 기본값을 사용해 RequestQueue를 설정해주는 메서드
                queue.add(validateRequest);
                // 요청이 파이프라인을 통해 이동하고 처리되며 원시 응답이 파싱되어 전달됩니다.
            }
        });

        Button register = (Button) findViewById(R.id.registerButton);
        // 회원가입을 위한 버튼객체 생성
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userBirthday = birthdatText.getText().toString();
                String userClass = classText.getText().toString();
                String userName = nameText.getText().toString();
                String userPhone = phoneText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();
                // 각각의 String 변수에 사용자가 입력한 값을 저장

                if (!validate){    //validate -> false 즉 중복체크가 안되어있을때
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage(R.string.Please_double_check)
                            .setPositiveButton(R.string.Confirm, null)
                            .create();
                    dialog.show();
                    return;
                }
                // 사용자의 정보에 빈 곳이 있을때
                if (userID.equals("") || userPassword.equals("") || userBirthday.equals("") || userClass.equals("") || userName.equals("")
                        || userPhone.equals("") || userGender.equals("") || userMajor.equals("") || userEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage(R.string.Blank)
                            .setPositiveButton(R.string.Confirm, null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    // 리스너객체 생성(volley 사용을 위한 ResponseListener 구현 부분)
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            // 지정된 JSONObject 문자열에서 이름/ 값 매핑을 사용하여 새로 만듬
                            boolean success = jsonResponse.getBoolean("success");
                            // success -> 정상적으로 수행이 되었는지의 response의 값을 의미
                            if(success){     // 서버에서 보내준 값이 true라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage(R.string.Member_registration_success)
                                        .setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();   // 해당 엑티비티를 종료
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }else {      // 서버에서 보내준 값이 false라면
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage(R.string.Member_registration_failed)
                                        .setNegativeButton(R.string.Confirm, new DialogInterface.OnClickListener() {
                                            //
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                idText.setText("");
                                                passwordText.setText("");
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userBirthday, userClass,
                        userName, userPhone, userGender, userMajor, userEmail, responseListener);
                // 생성자를 통하여 RegisterRequest 객체를 만듬
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                // 요청을 실질적으로 보낼수 있도록 queue를 만듬
                // Volley.newRequestQueue() -> Volley에서 제공하는 기본값을 사용해 RequestQueue를 설정해주는 메서드
                queue.add(registerRequest);
                // 요청이 파이프라인을 통해 이동하고 처리되며 원시 응답이 파싱되어 전달됩니다.
            }
        });
    }
}
