package kr.hs.emirim.sookhee.donerpets_final;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FirstStartActivity extends AppCompatActivity {
    int MAX_PAGE = 9;

    Fragment currentFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);


        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(new SawdFragmentAdatper(getSupportFragmentManager()));

    }

    class SawdFragmentAdatper extends FragmentPagerAdapter {

        private SawdFragmentAdatper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 0 || MAX_PAGE <= position)
                return null;

            switch (position) {
                case 0:
                    currentFragment = new SawdFragment1();
                    break;
                case 1:
                    currentFragment = new SawdFragment2();
                    break;
                case 2:
                    currentFragment = new SawdFragment3();
                    break;
                case 3:
                    currentFragment = new SawdFragment4();
                    break;
                case 4:
                    currentFragment = new SawdFragment5();
                    break;
                case 5:
                    currentFragment = new SawdFragment6();
                    break;
                case 6:
                    currentFragment = new SawdFragment7();
                    break;
                case 7:
                    currentFragment = new SawdFragment8();
                    break;
                case 8:
                    currentFragment = new SawdFragment9();
                    break;
            }

            return currentFragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }

    public void onClickButn(View v){
        Intent intent = new Intent(FirstStartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //앱최초실행확인 (true - 최초실행)
    public boolean CheckAppFirstExecute(){
        SharedPreferences pref = getSharedPreferences("IsFirst" , Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }

        return !isFirst;
    }
}