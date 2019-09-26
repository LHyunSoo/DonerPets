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

public class SawdFragment2 extends Fragment {
    public SawdFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sawd2, container, false);

        ImageView sawd2 = (ImageView)view.findViewById(R.id.img_sawd2);
        Picasso.get().load("https://bit.ly/2m0wzmj").into(sawd2);


        return view;
    }
}