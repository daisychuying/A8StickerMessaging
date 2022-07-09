package com.ebookfrenzy.a8stickermessaging.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebookfrenzy.a8stickermessaging.Model.History;
import com.ebookfrenzy.a8stickermessaging.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<History> historyList;

    public HistoryAdapter(Context mContext, List<History> historyList) {
        this.mContext = mContext;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = historyList.get(position);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView sticker;
        public TextView sentName;
        public TextView sentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sticker = itemView.findViewById(R.id.sticker);
            sentName = itemView.findViewById(R.id.sentName);
            sentTime = itemView.findViewById(R.id.time);



        }
    }
}
