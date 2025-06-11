package com.example.fotnews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fotnews.NewsItem;
import com.example.fotnews.R;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsItem> items;
    private OnNewsClickListener listener;

    public interface OnNewsClickListener {
        void onNewsClick(NewsItem item);
    }

    public NewsAdapter(List<NewsItem> items, OnNewsClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_tab, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.author.setText(item.getAuthor());
        holder.time.setText(item.getTime());
        holder.thumb.setImageResource(item.getImageResId());
        holder.itemView.setOnClickListener(v -> listener.onNewsClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<NewsItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView title, author, time;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.ivLike);
            title = itemView.findViewById(R.id.tvNewsTitle);
            author = itemView.findViewById(R.id.tvNewsDetailAuthor);
        }
    }
}
