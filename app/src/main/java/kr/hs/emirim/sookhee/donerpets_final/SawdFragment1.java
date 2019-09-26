package kr.hs.emirim.sookhee.donerpets_final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class SawdFragment1 extends Fragment {
    public SawdFragment1() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sawd1, container, false);

        ImageView sawd1 = (ImageView)view.findViewById(R.id.img_sawd1);
        Picasso.get().load("https://bit.ly/2m38w6d").into(sawd1);


        return view;
    }
}