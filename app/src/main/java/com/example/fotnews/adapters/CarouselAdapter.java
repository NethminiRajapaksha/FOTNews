package com.example.fotnews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fotnews.CarouselItem;
import com.example.fotnews.R;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<CarouselItem> itemList;

    public CarouselAdapter(List<CarouselItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem item = itemList.get(position);
        holder.imageView.setImageResource(item.imageResId);
        holder.titleView.setText(item.title);
        holder.authorView.setText(item.author);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView, authorView;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageCarousel);
            titleView = itemView.findViewById(R.id.tvCarouselTitle);
            authorView = itemView.findViewById(R.id.tvCarouselAuthor);
        }
    }
}
