package kr.hs.emirim.sookhee.donerpets_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RadialGradient;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.NavigableMap;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {


    // Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    // Views
    private ListView mListView;
    private EditText mEdtMessage;
    // Values
    private ChatAdapter mAdapter;

    //Firebase2
    FirebaseDatabase Storydatabase;
    DatabaseReference myRefStory;

    //Firebase2
    FirebaseDatabase Likedatabase;
    DatabaseReference LikemyRef;

    private String shelterPosition;
    private String storyPosition;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        storyPosition = intent.getExtras().getString("storyPosition");

        Storydatabase = FirebaseDatabase.getInstance();
        myRefStory = Storydatabase.getReference("story").child(String.valueOf(storyPosition));

        myRefStory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("title").getValue(String.class);
                String img1 = dataSnapshot.child("img1").getValue(String.class);
                String img2 = dataSnapshot.child("img2").getValue(String.class);
                String img3 = dataSnapshot.child("img3").getValue(String.class);
                String img4 = dataSnapshot.child("img4").getValue(String.class);
                String line1 = dataSnapshot.child("line1").getValue(String.class);
                String line2 = dataSnapshot.child("line2").getValue(String.class);
                String line3 = dataSnapshot.child("line3").getValue(String.class);
                String line4 = dataSnapshot.child("line4").getValue(String.class);
                String date = dataSnapshot.child("date").getValue(String.class);
                String phone = dataSnapshot.child("shelterPhone").getValue(String.class);
                String shelterName = dataSnapshot.child("shelterName").getValue(String.class);
                int shelterId = dataSnapshot.child("shelterId").getValue(int.class);
                int shelterLike = dataSnapshot.child("like").getValue(int.class);
                String shelterMarks = dataSnapshot.child("shelterMark").getValue(String.class);

                shelterPosition = String.valueOf(shelterId);

                //데이터를 화면에 출력해 준다.
                TextView Title = findViewById(R.id.text_title);
                Title.setText(title);

                TextView ShelterName = findViewById(R.id.text_sheltername);
                ShelterName.setText(shelterName);

                TextView Date = findViewById(R.id.text_date);
                Date.setText(date);

                TextView Phone = findViewById(R.id.text_phone);
                Phone.setText(phone);

                ImageView ShelterMark = findViewById(R.id.text_sheltermark);
                Picasso.get().load(shelterMarks).into(ShelterMark);

                RadiusImageView  Img1 = (RadiusImageView ) findViewById(R.id.image1_activity2);
                Picasso.get().load(img1).into(Img1);
                ImageView Img2 = (ImageView) findViewById(R.id.image2_activity2);
                Picasso.get().load(img2).into(Img2);
                ImageView Img3 = (ImageView) findViewById(R.id.image3_activity2);
                Picasso.get().load(img3).into(Img3);
                ImageView Img4 = (ImageView) findViewById(R.id.image4_activity2);
                Picasso.get().load(img4).into(Img4);

                TextView Line1 = findViewById(R.id.text1_activity2);
                Line1.setText(line1);
                TextView Line2 = findViewById(R.id.text2_activity2);
                Line2.setText(line2);
                TextView Line3 = findViewById(R.id.text3_activity2);
                Line3.setText(line3);
                TextView Line4 = findViewById(R.id.text4_activity2);
                Line4.setText(line4);

                TextView ShelterLike = findViewById(R.id.text_likecount);
                ShelterLike.setText(String.valueOf(shelterLike));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DetailActivitys", "Failed to read value.", error.toException());
            }
        });

        initViews();
        initFirebaseDatabase();

        setListViewHeightBasedOnChildren(mListView);

    }

    public void onLikeClick(View v){
        Storydatabase = FirebaseDatabase.getInstance();
        myRefStory = Storydatabase.getReference("story").child(String.valueOf(storyPosition));

        myRefStory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int like = dataSnapshot.child("like").getValue(int.class);
                //like = like + 1;
                //LikemyRef.child("like").setValue(like);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void onBackClick(View v){
        super.onBackPressed();
    }


    private void initViews() {
        final DataApplication MyData = (DataApplication)getApplication();
        mListView = (ListView) findViewById(R.id.list_message);
        mAdapter = new ChatAdapter(this, 0);
        mListView.setAdapter(mAdapter);

        mEdtMessage = (EditText) findViewById(R.id.edit_message);
        findViewById(R.id.btn_send).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = mEdtMessage.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    mEdtMessage.setText("");
                    ChatData chatData = new ChatData();
                    chatData.userName = SaveSharedPreference.getUserName(DetailActivity.this);
                    chatData.message = message;
                    mDatabaseReference.push().setValue(chatData);
                }
            }
        });


    }

    private void initFirebaseDatabase() {
        DataApplication MyData = (DataApplication) getApplication();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("message").child(storyPosition);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                mAdapter.add(chatData);
                mListView.smoothScrollToPosition(mAdapter.getCount());

                setListViewHeightBasedOnChildren(mListView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public void onClickGoShelter(View v){
        Intent intent = new Intent(DetailActivity.this, ShelterActivity.class);
        intent.putExtra("storyPosition", String.valueOf(storyPosition));
        intent.putExtra("shelterPosition", shelterPosition);
        startActivity(intent);
    }
}