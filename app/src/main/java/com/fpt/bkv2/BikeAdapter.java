package com.fpt.bkv2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.model.Bike;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.MyViewHolder> {
    List<Bike> bikeList;

    public BikeAdapter(List<Bike> bikeList) {
        this.bikeList = bikeList;
    }


    //create new textview
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_item_searched, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Bike bk=bikeList.get(position);
        holder.t1.setText(bk.getName());
       holder.t2.setText(bk.getBikeId()+"");
       holder.t3.setText(bk.getNoPlate());

       new DownLoadImageAsync(holder.iv).execute("https://www.usmagazine.com/wp-content/uploads/2018/06/Smoothie-the-Cat-Instagram-zoom.jpg");

    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout textView;
        ImageView iv;
        TextView t3;
TextView t1;
TextView t2;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            t1= v.findViewById(R.id.name);
            t2= v.findViewById(R.id.brand);
            iv= v.findViewById(R.id.image);

            t3= v.findViewById(R.id.noplate);
        }
    }

    // Create new views (invoked by the layout manager)


}



