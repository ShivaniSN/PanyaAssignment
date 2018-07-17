package apps.com.panyaassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Main extends AppCompatActivity {

    EditText editTextEmail,editTextPassword;
    Button buttonRegister;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
    String stringName = "",stringEmail = "",
            stringConsecutiveRoundCount = "",stringAccessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText)findViewById(R.id.et_email);
        editTextPassword = (EditText)findViewById(R.id.et_pwd);
        buttonRegister = (Button)findViewById(R.id.btn_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt  = 0;

                if (editTextEmail.getText().length() < 0){
                    Toast.makeText(Activity_Main.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    cnt ++ ;
                }
                if (editTextPassword.getText().length() < 0){
                    Toast.makeText(Activity_Main.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    cnt ++ ;
                }
                if (!isValidEmail(editTextEmail.getText().toString())){
                    Toast.makeText(Activity_Main.this,"Invalid Email",Toast.LENGTH_SHORT).show();
                    cnt ++ ;
                }

                if (cnt == 0){
                    loginVolleyRequest();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void loginVolleyRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);

                            if (json.has("data")) {
                                JSONObject data = new JSONObject(json.getString("data"));
                                stringName = data.getString("name");
                                stringAccessToken = data.getString("access_token");
                                stringConsecutiveRoundCount = data.getString("consecutive_round_count");
                                stringEmail = data.getString("email");

                                Intent intent = new Intent(Activity_Main.this, Activity_StreakBonus.class);
                                intent.putExtra("access_token",stringAccessToken);
                                intent.putExtra("consecutive_round_count",stringConsecutiveRoundCount);
                                startActivity(intent);
                            }else {
                                JSONObject error = new JSONObject(json.getString("error"));
                                Toast.makeText(Activity_Main.this,error.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_Main.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",editTextEmail.getText().toString());
                params.put("password",editTextPassword.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
