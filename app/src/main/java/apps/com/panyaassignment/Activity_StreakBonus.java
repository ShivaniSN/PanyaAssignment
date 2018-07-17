package apps.com.panyaassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Activity_StreakBonus extends AppCompatActivity {

    ImageButton imageButtonCancel;
    TextView textViewConsecutiveRoundCount;

    String stringAccessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButtonCancel = (ImageButton)findViewById(R.id.ib_cancel);
        textViewConsecutiveRoundCount = (TextView)findViewById(R.id.tv_roundcount);

        textViewConsecutiveRoundCount.setText("You have consecutively played : " +
                getIntent().getStringExtra("consecutive_round_count")+ " rounds");
        stringAccessToken = getIntent().getStringExtra("access_token");

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_StreakBonus.this, Activity_Main.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Activity_StreakBonus.this, Activity_Main.class);
        startActivity(intent);
    }
}
