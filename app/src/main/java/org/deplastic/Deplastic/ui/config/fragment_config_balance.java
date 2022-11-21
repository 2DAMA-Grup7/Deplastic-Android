package org.deplastic.Deplastic.ui.config;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.deplastic.Deplastic.R;

import java.util.ArrayList;

public class fragment_config_balance extends Fragment {

    private FragmentConfigBalanceViewModel mViewModel;

    public static fragment_config_balance newInstance() {
        return new fragment_config_balance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_config_balance, container, false);
        Context app_context = view.getContext();
        RecyclerView recycler = getView().findViewById(R.id.RecyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(app_context, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            listData.add("Data #" + "i" + "");
        }

        DataAdapter adapter = new DataAdapter(listData);
        recycler.setAdapter(adapter);

        return inflater.inflate(R.layout.fragment_config_balance, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentConfigBalanceViewModel.class);
        // TODO: Use the ViewModel
    }

}