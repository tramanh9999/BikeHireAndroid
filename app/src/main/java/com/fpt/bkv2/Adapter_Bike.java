package com.fpt.bkv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.model.Bike;
import com.fpt.model.BikeSlot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;


public class Adapter_Bike extends RecyclerView.Adapter<Adapter_Bike.MyViewHolder> {
    List<Bike> bikeList;

    public Adapter_Bike(List<Bike> bikeList) {
        this.bikeList = bikeList;
    }


    //create new textview
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searched_bike, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Bike bk = bikeList.get(position);
        holder.location.setText(bk.getDisplayLocation());
        holder.txtname.setText(bk.getName());
//        holder.location.setText(bk.getLocation());
        Picasso.get().load(bk.getImage()).into(holder.imageView);


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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout textView;
        TextView txtname;
        TextView location;
        TextView slotlist;
        TextView updateDate;
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



