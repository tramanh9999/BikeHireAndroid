package com.fpt.bkv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.model.Bike;
import com.fpt.model.BikeSlot;

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
        holder.location.setText(R.string.location_init);
        holder.txtname.setText(bk.getName());
//        holder.location.setText(bk.getLocation());
        List<BikeSlot> list =  bk.getSlotList();
        int i = 0;
        if (list != null) {
            for (BikeSlot bs : list
            ) {

                holder.slotlist.setText(""+(i++) + R.string.dot + bs.getSlot_from() + R.string.sub + bs.getSlot_to());
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String updstr = "";
//        if (bk.getUpdateDate() != null) {
//            updstr = sdf.format(bk.getUpdateDate());
//
//        }
//        holder.updateDate.setText(updstr);

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

        public MyViewHolder(View v) {
            super(v);
            txtname = v.findViewById(R.id.name);
            location = v.findViewById(R.id.location);
            slotlist = v.findViewById(R.id.slotlist);
            updateDate = v.findViewById(R.id.updatedate);

        }
    }

    // Create new views (invoked by the layout manager)


}



