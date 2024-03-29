package com.fpt.bkv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.model.Bike;
import com.fpt.model.BikeSlot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;


public class Adapter_Bike extends RecyclerView.Adapter<Adapter_Bike.MyViewHolder> {
    List<Bike> bikeList;

    Context context;
    public Adapter_Bike(List<Bike> bikeList, Context context) {
        this.bikeList = bikeList;
this.context=context;
    }


    //create new textview
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searched_bike, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bike bk = bikeList.get(position);
        holder.location.setText(bk.getDisplayLocation());
        holder.txtname.setText(bk.getName());
//        holder.location.setText(bk.getLocation());
        if(bk.getImage().size()!=0){
            Glide.with(context).load(String.valueOf(bk.getImage().get(0).getUrl())).into(holder.imageView);
//            Picasso.get().load(String.valueOf(bk.getImage().get(0).getUrl())).into(holder.imageView);
        }
        List<BikeSlot> list =  bk.getSlotList();
        int i = 0;
        if (list != null) {
            for (BikeSlot bs : list
            ) {
                holder.slotlist.setText(""+(i++) + R.string.dot + bs.getS_from() + R.string.sub + bs.getS_to()+ R.string.sub +bs.getFee());
            }
        }


    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout textView;
        TextView txtname;
        TextView location;
        TextView slotlist;
        ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            txtname = v.findViewById(R.id.name);
            location = v.findViewById(R.id.location);
            slotlist = v.findViewById(R.id.slotlist);
            imageView= v.findViewById(R.id.image);
        }
    }

    // Create new views (invoked by the layout manager)


}



