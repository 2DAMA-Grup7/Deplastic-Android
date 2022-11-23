package org.deplastic.Deplastic.ui.config.balance;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class NewAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    public NewAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @Override public WordViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType){
        // Inflate an item view.
        View mItemView =
                mInflater.inflate(R.layout.wordlist_item,
                        parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override public void onBindViewHolder(
            WordViewHolder holder, int position) {
        // Retrieve the data for that position
        String mCurrent = mWordList.get(position);
        // Add the data to the view
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }



}
