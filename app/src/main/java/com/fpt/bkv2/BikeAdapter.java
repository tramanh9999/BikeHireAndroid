package com.fpt.bkv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fpt.model.Bike;

import java.util.List;


public class BikeAdapter extends RecyclerView.Adapter<BikeAdapter.ViewHolder> {

    private List<Bike> mItems;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTv;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(android.R.id.text1);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bike item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getBikeId());
            notifyDataSetChanged();
        }
    }

    public BikeAdapter(Context context, List<Bike> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public BikeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BikeAdapter.ViewHolder holder, int position) {

        Bike item = mItems.get(position);
        TextView textView = holder.titleTv;
        textView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateBikes(List<Bike> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Bike getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}



