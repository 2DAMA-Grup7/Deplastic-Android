package org.deplastic.Deplastic.ui.config.balance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.deplastic.Deplastic.R;
import org.deplastic.Deplastic.databinding.FragmentConfigBinding;

public class BalanceFragment extends Fragment {

    private BalanceViewModel mViewModel;
    private FragmentConfigBinding binding;


    public static BalanceFragment newInstance() {
        return new BalanceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return inflater.inflate(R.layout.fragment_balance, container, false);
    }
}