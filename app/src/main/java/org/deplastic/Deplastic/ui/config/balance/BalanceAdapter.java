package org.deplastic.Deplastic.ui.config.balance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.deplastic.Deplastic.R;

import java.util.ArrayList;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.ViewHolder> {

    ArrayList<String> names;
    Context context;

    //constructor
    public BalanceAdapter(ArrayList<String> Names, Context context_) {
        this.names = Names;
        this.context = context_;
    }

    @NonNull
    @Override
    public BalanceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_item, parent, false);
        v.setLayoutParams(new RecyclerView.LayoutParams(800, 300));
        return new BalanceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BalanceAdapter.ViewHolder holder, int position) {
        holder.getName().setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.textpoint);
        }

        public TextView getName() {
            return this.name;
        }

    }
}
