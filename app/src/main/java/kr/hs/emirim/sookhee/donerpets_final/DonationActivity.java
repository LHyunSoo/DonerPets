package kr.hs.emirim.sookhee.donerpets_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class DonationActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    FirebaseDatabase donationDatabase;
    DatabaseReference myDonation;

    TextView text_name;
    TextView text_phone;
    TextView text_account;
    EditText edit_type;
    EditText edit_count;

    ImageButton feedDown;
    ImageButton towelDown;
    ImageButton tissueDown;
    ImageButton snackDown;
    ImageButton feedUp;
    ImageButton towelUp;
    ImageButton tissueUp;
    ImageButton snackUp;
    TextView feed;
    TextView towel;
    TextView tissue;
    TextView snack;
    int countf=0;
    int countw=0;
    int countt=0;
    int counts=0;
    CheckBox agree;

    private String shelterPosition;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private EditText donation;
    private String result="";

    //글자 변경을 감지해주는 애
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        //메소드에서 앞서 설정한 포맷으로 문자를 변경
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                donation.setText(result);
                donation.setSelection(result.length());
            }
        }
        @Override
        public void afterTextChanged(Editable editable) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        Intent intent = getIntent();
        //shelterPosition = intent.getExtras().getString("shelterPosition");
        shelterPosition = "1";

//        text_name = (TextView) findViewById(R.id.text_name);
//        text_account = (TextView)findViewById(R.id.text_account);
//        text_phone = (TextView) findViewById(R.id.text_phone);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shelter").child(shelterPosition);

        // Read from the database
        // 그리고 데이터베이스에 변경사항이 있으면 실행된다.
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String name = dataSnapshot.child("name").getValue(String.class);
//                String phone = dataSnapshot.child("phone").getValue(String.class);
//                String account = dataSnapshot.child("account").getValue(String.class);
//
//                //데이터를 화면에 출력해 준다.
//                text_name.setText(name);
//                text_phone.setText(phone);
//                text_account.setText(account);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("DONATION", "Failed to read value.", error.toException());
//            }
//        });

        //금액 입력 세팅
        donation = (EditText) findViewById(R.id.donation);
        donation.addTextChangedListener(watcher);

        //기부할 물품 수량 증감
        //사료 수량
        feedDown = (ImageButton)findViewById(R.id.feedDown);
        feedUp = (ImageButton)findViewById(R.id.feedUp);
        feed = (TextView) findViewById(R.id.feed);

        feedDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countf--;
                feed.setText(""+countf);
            }
        });
        feedUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countf--;
                feed.setText(""+countf);
            }
        });

        //수건
        towelDown = (ImageButton)findViewById(R.id.towelDown);
        towelUp = (ImageButton)findViewById(R.id.towelUp);
        towel = (TextView) findViewById(R.id.towel);

        towelDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countt--;
                towel.setText(""+countw);
            }
        });
        towelUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countt++;
                towel.setText(""+countw);
            }
        });

        //휴지
        tissueDown = (ImageButton)findViewById(R.id.tissueDown);
        tissueUp = (ImageButton)findViewById(R.id.tissueUp);
        tissue = (TextView) findViewById(R.id.tissue);

        tissueDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countt--;
                tissue.setText(""+countt);
            }
        });
        tissueUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countt++;
                tissue.setText(""+countt);
            }
        });

        //간식
        snackDown = (ImageButton)findViewById(R.id.snackDown);
        snackUp = (ImageButton)findViewById(R.id.snackUp);
        snack = (TextView) findViewById(R.id.snack);

        snackDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countt--;
                snack.setText(""+counts);
            }
        });
        snackUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                countt++;
                snack.setText(""+counts);
            }
        });

    }

    //기부하기 버튼 누르면 동작할 함수
    public void onAllowDonation(View v){
        agree = (CheckBox)findViewById(R.id.agree);
        if(agree.isChecked()){
            DataApplication MyData = (DataApplication)getApplication();

            feed = (TextView) findViewById(R.id.feed);
            donationDatabase = FirebaseDatabase.getInstance();
            myDonation = donationDatabase.getReference("user").child(SaveSharedPreference.getEmail(this));

            int fcount = Integer.parseInt(feed.getText().toString());
            int wcount = Integer.parseInt(towel.getText().toString());
            int tcount = Integer.parseInt(tissue.getText().toString());
            int scount = Integer.parseInt(snack.getText().toString());

            DonationData donationData = new DonationData();
            donationData.count = fcount;
            donationData.count = wcount;
            donationData.count = tcount;
            donationData.count = scount;
            donationData.shelter = Integer.parseInt(shelterPosition);
            myDonation.child("donation").push().setValue(donationData);

            finish();
        }
    }

    //뒤로가기
    public void onBackClick(View v){
        super.onBackPressed();
    }
}
