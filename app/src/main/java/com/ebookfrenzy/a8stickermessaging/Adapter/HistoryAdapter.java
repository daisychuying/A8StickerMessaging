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
import com.ebookfrenzy.a8stickermessaging.Model.StickerMap;
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
        holder.bindThisData(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView sticker;
        public TextView sender;
        public TextView sentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sticker = itemView.findViewById(R.id.sticker);
            sender = itemView.findViewById(R.id.sender);
            sentTime = itemView.findViewById(R.id.time);
        }

        private void bindThisData(History historyToBind) {
            StickerMap stickerMap = new StickerMap();
            //sticker.setImageResource(stickerMap.getStickerId(historyToBind.getStickerId()));
            sticker.setImageResource(R.mipmap.ic_launcher);
            sender.setText("from " + historyToBind.getSender());
            sentTime.setText(historyToBind.getTime());


        }
    }
}
