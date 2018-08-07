package com.wipro.proficiency.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wipro.proficiency.R;
import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;
import com.wipro.proficiency.mvp.model.response.Rows;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>{

    private NewsFeedResponse newsFeedResponse;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView captionText;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleTxt);
            captionText = itemView.findViewById(R.id.captionTxt);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public NewsFeedAdapter(Context context, NewsFeedResponse newsFeedResponse){
        this.context = context;
        this.newsFeedResponse = newsFeedResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rows[] rows = newsFeedResponse.getRows();
        if(rows[position].getTitle() != null){
            holder.titleText.setText(rows[position].getTitle());
        } else {
            holder.titleText.setText("No Data");
        }

        if(rows[position].getDescription() != null){
            holder.captionText.setText(rows[position].getDescription());
        } else {
            holder.captionText.setText("No Description ...");
        }


        Glide.with(context)
                .load(rows[position].getImageHref())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.no_image)
                        .fitCenter()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(newsFeedResponse.getRows() != null){
            return (newsFeedResponse.getRows()).length;
        }else {
            return 0;
        }

    }

    public void updateAdapter(NewsFeedResponse newsFeedResponse){
        this.newsFeedResponse = newsFeedResponse;
        notifyDataSetChanged();
    }
}
