package org.deplastic.Deplastic.ui.config.balance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.deplastic.Deplastic.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BalanceFragment extends Fragment {


    ArrayList<String> productName= new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager Manager;
    BalanceAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        productName.add("KG" + "                " + "Points");

        //TODO
        // Get points from api rest and show them all
        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        String url = "https://deplastic.netlify.app/.netlify/functions/api/transaction";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject balance = response.getJSONObject(i);
                            productName.add(balance.getString("kg")+"                "+balance.getString("points"));
                        }
                        this.recyclerView = (RecyclerView)view.findViewById(R.id.balanceRecyclerView);
                        Manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(Manager);
                        adapter = new BalanceAdapter(productName, getContext());
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) { e.printStackTrace(); }
                }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        return view;
    }
}