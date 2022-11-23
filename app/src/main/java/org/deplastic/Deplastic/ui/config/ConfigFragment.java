package org.deplastic.Deplastic.ui.config;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import org.deplastic.Deplastic.Credentials.Login;
import org.deplastic.Deplastic.R;
import org.deplastic.Deplastic.databinding.FragmentConfigBinding;
import org.deplastic.Deplastic.ui.config.balance.BalanceFragment;

public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConfigViewModel configViewModel =
                new ViewModelProvider(this).get(ConfigViewModel.class);

        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button balanceButton= root.findViewById(R.id.BalanceButton);
        balanceButton.setOnClickListener(v -> {
            Fragment fragment = new BalanceFragment();

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.nav_host_fragment_content_main, fragment);
            transaction.commit();
        });

        Button logoutButton= root.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(getContext(), Login.class);
            startActivity(newIntent);
        });


        final TextView textView = binding.textConfig;
        configViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}