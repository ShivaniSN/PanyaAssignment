package apps.com.panyaassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Activity_StreakBonus extends AppCompatActivity {

    ImageButton imageButtonCancel;
    TextView textViewConsecutiveRoundCount,textViewMessage;
    RecyclerView recyclerViewStreakBonus;

    String stringAccessToken = "",stringConsecutiveRoundCount = "";
    List<String> arrayListStreakBonus = new ArrayList<String>();
    List<Integer> arrayListStreakBonusValues = new ArrayList<Integer>();
    Adapter_Recycler_StreakBonus sbadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streakbonus);

        imageButtonCancel = (ImageButton)findViewById(R.id.ib_cancel);
        textViewConsecutiveRoundCount = (TextView)findViewById(R.id.tv_roundcount);
        textViewMessage = (TextView)findViewById(R.id.tv_msg);
        recyclerViewStreakBonus = (RecyclerView)findViewById(R.id.recycler_view);
        sbadapter = new Adapter_Recycler_StreakBonus(Activity_StreakBonus.this, arrayListStreakBonusValues);

        stringAccessToken = getIntent().getStringExtra("access_token");
        stringConsecutiveRoundCount = "<font color='#FFFF00'>" +
                getIntent().getStringExtra("consecutive_round_count") + "</font>";
        textViewConsecutiveRoundCount.setText(Html.fromHtml("You have consecutively played : " +
                stringConsecutiveRoundCount + " rounds"));
        textViewMessage.setText(Html.fromHtml("Receive extra lives ! Join the game for <br>" +
        "<font color='#FFFF00'> more than 5 consecutive rounds. </font> <br>" +
        "The more rounds joined, the more hearts given <br>" +
        "<font color='#FFFF00'> (join the game before the first question appears) </font> <br>" +
        "Miss a single round and start over !"));

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_StreakBonus.this, Activity_Main.class);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(this);
        recyclerViewStreakBonus.setLayoutManager(recylerViewLayoutManager);
        recyclerViewStreakBonus.setAdapter(sbadapter);

        getStreakBonusVolleyRequest();
    }

    public void getStreakBonusVolleyRequest(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.STREAK_BONUS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);

                            if (json.has("data")) {
                                JSONObject data = new JSONObject(json.getString("data"));
                                JSONArray streakBonusArray = new JSONArray(data.getString("streak_bonus"));

                                for (int i = 0;i < streakBonusArray.length();i++){
                                    arrayListStreakBonus.add(String.valueOf(streakBonusArray.getInt(i)));
                                }
                                Collections.sort(arrayListStreakBonus);
                                Set<String> hs = new HashSet<>();
                                hs.addAll(arrayListStreakBonus);
                                arrayListStreakBonus.clear();
                                arrayListStreakBonus.addAll(hs);

                                for (int j=0;j<arrayListStreakBonus.size();j++){
                                    arrayListStreakBonusValues.add(Integer.parseInt(arrayListStreakBonus.get(j)));
                                }
                                Collections.sort(arrayListStreakBonusValues);
                                sbadapter.notifyDataSetChanged();
                            }else {
                                JSONObject error = new JSONObject(json.getString("error"));
                                Toast.makeText(Activity_StreakBonus.this,error.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_StreakBonus.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("access-token",stringAccessToken);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity_StreakBonus.this, Activity_Main.class);
        startActivity(intent);
    }
}
