package kr.hs.emirim.sookhee.donerpets_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView username = (TextView)findViewById(R.id.profile_name);
        username.setText(SaveSharedPreference.getUserName(ProfileActivity.this));
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    public void onSettingClick(View v){

        Intent intent = new Intent(ProfileActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
}