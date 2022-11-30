package org.deplastic.Deplastic.ui.tenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.deplastic.Deplastic.R;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    ArrayList<String> urls;
    ArrayList<String> names;
    Context context;
    private static ItemClickListener mClickListener;

    //constructor
    public ShopAdapter(ArrayList<String> ImgUrl, ArrayList<String> Names, Context context_) {
        this.names = Names;
        this.urls = ImgUrl;
        this.context = context_;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_items, parent, false);
        v.setLayoutParams(new RecyclerView.LayoutParams(800, 300));
        return new ViewHolder(v);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(this.context)
                .load(urls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());

        holder.getName().setText(names.get(position));

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView image;
        private final TextView name;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.img);
            name = (TextView) v.findViewById(R.id.ProductName);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        public ImageView getImage() {
            return this.image;
        }

        public TextView getName() {
            return this.name;
        }

    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}