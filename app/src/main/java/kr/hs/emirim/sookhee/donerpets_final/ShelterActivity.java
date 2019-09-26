package kr.hs.emirim.sookhee.donerpets_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ShelterActivity extends AppCompatActivity {

    // Firebase
    private FirebaseDatabase mFirebaseDatabaseS;
    private DatabaseReference mDatabaseReferenceS;

    //Firebase2
    FirebaseDatabase database;
    DatabaseReference myRef;

    private String shelterPosition;

    RecyclerView recyclerView;
    CustomAdapter adapter;
    FirebaseDatabase databaseStory;
    DatabaseReference mRefStory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);

        Intent intent = getIntent();
        shelterPosition = intent.getExtras().getString("shelterPosition");

        recyclerView = findViewById(R.id.shelter_recycler);
        adapter = new CustomAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        databaseStory = FirebaseDatabase.getInstance();
        mRefStory = databaseStory.getReference().child("shelter").child(shelterPosition).child("story");

        mRefStory.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                Story story = dataSnapshot.getValue(Story.class);

                adapter.addDataAndUpdate(key, story);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                Story story = dataSnapshot.getValue(Story.class);

                adapter.addDataAndUpdate(key, story);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();

                adapter.deleteDataAndUpdate(key);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shelter").child(shelterPosition);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String shelterName = dataSnapshot.child("name").getValue(String.class);
                String shelterPhone = dataSnapshot.child("phone").getValue(String.class);
                String shelterMark = dataSnapshot.child("mark").getValue(String.class);
                int shelterDonationCount = dataSnapshot.child("donationCount").getValue(int.class);
                int shelterLikeCount = dataSnapshot.child("likeCount").getValue(int.class);
                int shelterStoryCount = dataSnapshot.child("storyCount").getValue(int.class);
                String shelterIntro = dataSnapshot.child("intro").getValue(String.class);


                //데이터를 화면에 출력해 준다.
                TextView ShelterName = findViewById(R.id.tv_shelter_name);
                ShelterName.setText(shelterName);

                TextView ShelterPhone = findViewById(R.id.tv_shelter_phone);
                ShelterPhone.setText(shelterPhone);

                ImageView ShelterMark = findViewById(R.id.iv_shelter_mark);
                Picasso.get().load(shelterMark).into(ShelterMark);

                TextView ShelterDonationCount = findViewById(R.id.tv_shelter_donation_count);
                ShelterDonationCount.setText(String.valueOf(shelterDonationCount));

                TextView ShelterLikeCount = findViewById(R.id.tv_shelter_like_count);
                ShelterLikeCount.setText(String.valueOf(shelterLikeCount));

                TextView ShelterStoryCount = findViewById(R.id.tv_shelter_story_count);
                ShelterStoryCount.setText(String.valueOf(shelterStoryCount));

                TextView ShelterIntro = findViewById(R.id.tv_shelter_intro);
                ShelterIntro.setText(shelterIntro);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }

    public void onGoDonationClick(View v){
        Intent intent=new Intent(ShelterActivity.this,DonationActivity.class);
        intent.putExtra("shelterPosition", shelterPosition);
        startActivity(intent);
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }

    public void onClickShelterLike(View v){

        CheckBox checkBox = (CheckBox) findViewById(R.id.check_shelter_like) ;

        DataApplication MyData = (DataApplication)getApplication();

        mFirebaseDatabaseS = FirebaseDatabase.getInstance();
        mDatabaseReferenceS = mFirebaseDatabaseS.getReference("user").child(SaveSharedPreference.getEmail(this)).child("shelterLike");

        if (checkBox.isChecked()) {
            mDatabaseReferenceS.child(shelterPosition).setValue("1");
        } else {
            mDatabaseReferenceS.child(shelterPosition).setValue("0");
        }
    }
}