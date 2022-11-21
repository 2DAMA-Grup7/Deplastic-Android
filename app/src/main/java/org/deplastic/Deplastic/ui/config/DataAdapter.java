package org.deplastic.Deplastic.ui.config;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.deplastic.Deplastic.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {

    ArrayList<String> dataList;

    public DataAdapter(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.asignData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView data;
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            data= (TextView) itemView.findViewById(R.id.dataId);
        }
        public void asignData(String datos) {
            data.setText(datos);
        }
    }
}
