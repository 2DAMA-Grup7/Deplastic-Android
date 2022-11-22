package org.deplastic.Deplastic.ui.config.balance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.deplastic.Deplastic.R;

public class BalanceFragment extends Fragment {

    private BalanceViewModel mViewModel;

    public static BalanceFragment newInstance() {
        return new BalanceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }
}