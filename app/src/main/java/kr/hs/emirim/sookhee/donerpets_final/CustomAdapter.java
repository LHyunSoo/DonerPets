package kr.hs.emirim.sookhee.donerpets_final;

import android.content.Context;
import android.content.Intent;
import android.mtp.MtpConstants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private Context mCtx;
    private HashMap<String, Story> mData;



    public CustomAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.example_item, parent, false);


        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        Story person = (Story) mData.values().toArray()[position];

        String img1 = person.getImg1();

        holder.tvTitle.setText(person.getTitle());
        holder.tvShelter.setText(person.getShelterName());
        Picasso.get().load(img1).into(holder.ivImage);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvShelter;
        ImageView ivImage;


        public CustomViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_title);
            tvShelter = itemView.findViewById(R.id.item_shelter);
            ivImage = itemView.findViewById(R.id.item_img);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){

                    Intent intent;

                    if (SaveSharedPreference.getUserName(v.getContext()).length() == 0) {
                        intent = new Intent(v.getContext(), LoginActivity.class);

                    } else {
                        intent = new Intent(v.getContext(), DetailActivity.class);
                        intent.putExtra("storyPosition", String.valueOf(getAdapterPosition()));
                    }


                    v.getContext().startActivity(intent);

                }
            });
        }

    }


    public void addDataAndUpdate(String key, Story p){
        mData.put(key, p);
        notifyDataSetChanged();
    }

    public void deleteDataAndUpdate(String key){
        mData.remove(key);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();

    }

}