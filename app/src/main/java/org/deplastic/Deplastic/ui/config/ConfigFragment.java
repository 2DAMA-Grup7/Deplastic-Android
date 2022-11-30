package org.deplastic.Deplastic.ui.config;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = requireContext().getSharedPreferences("login", MODE_PRIVATE);


        TextView username = root.findViewById(R.id.text_Config);
        username.setText(sharedPreferences.getString("username","ERROR"));


        Button balanceButton = root.findViewById(R.id.BalanceButton);
        balanceButton.setOnClickListener(v -> {
            Fragment fragment = new BalanceFragment();
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.nav_host_fragment_content_main, fragment);
            transaction.commit();
        });
        Button logoutButton= root.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            sharedPreferences.edit().remove("token").apply();
            Intent newIntent = new Intent(getContext(), Login.class);
            startActivity(newIntent);
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}