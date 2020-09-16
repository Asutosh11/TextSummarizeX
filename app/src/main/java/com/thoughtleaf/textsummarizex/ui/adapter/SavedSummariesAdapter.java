package com.thoughtleaf.textsummarizex.ui.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtleaf.textsummarizex.R;
import com.thoughtleaf.textsummarizex.data.dao.MySummariesDAO;
import com.thoughtleaf.textsummarizex.ui.model.SavedSummary;

import java.util.ArrayList;

public class SavedSummariesAdapter extends RecyclerView.Adapter<SavedSummariesAdapter.Viewholder> {

    private ArrayList<SavedSummary> data;
    private Context context;
    private CardClickListener listener;

    public SavedSummariesAdapter(ArrayList<SavedSummary> data, Context context, CardClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    static class Viewholder extends RecyclerView.ViewHolder {

        // 1. Declare your Views here

        TextView summary_title_tv;
        TextView summary_source_tv;
        ImageView summary_status_imv;
        CardView card_view;


        Viewholder(View itemView) {
            super(itemView);

            // 2. Define your Views here

            summary_title_tv = (TextView)itemView.findViewById(R.id.summary_title_tv);
            summary_source_tv = (TextView)itemView.findViewById(R.id.summary_source_tv);
            summary_status_imv = (ImageView)itemView.findViewById(R.id.summary_status_imv);
            card_view = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_summary, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        holder.summary_title_tv.setText(data.get(position).getSummary());
        holder.summary_source_tv.setText(data.get(position).getSummarizedFrom());
        switch (data.get(position).getSummarizedFrom()){
            case "txt": {
                holder.summary_source_tv.setBackgroundColor(context.getResources().getColor(R.color.color_for_txt_source));
                break;
            }
            case "pdf": {
                holder.summary_source_tv.setBackgroundColor(context.getResources().getColor(R.color.colour_for_file_source));
                break;
            }
            case "web": {
                holder.summary_source_tv.setBackgroundColor(context.getResources().getColor(R.color.colour_for_web_source));
                break;
            }
        }

        if(data.get(position).isSummaryRead()){
            holder.summary_status_imv.setVisibility(View.GONE);
        }
        else{
            holder.summary_status_imv.setVisibility(View.VISIBLE);
            Drawable greenCircle = context.getResources().getDrawable(R.drawable.drawable_green_circle);
            holder.summary_status_imv.setImageDrawable(greenCircle);
        }

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!data.get(position).isSummaryRead()){
                    MySummariesDAO.Companion.markArticleAsRead(data.get(position).getSummaryId());
                    holder.summary_status_imv.setVisibility(View.GONE);
                }

                listener.onCardCLicked(data.get(position).getSummary());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface CardClickListener{
        void onCardCLicked(String summary);
    }

}

