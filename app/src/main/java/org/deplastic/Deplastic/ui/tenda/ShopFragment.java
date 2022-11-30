package org.deplastic.Deplastic.ui.tenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.deplastic.Deplastic.R;
import org.deplastic.Deplastic.ui.tenda.producte.ProductFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopFragment extends Fragment implements ShopAdapter.ItemClickListener {

    ArrayList<String> ImgUrl = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager Manager;
    ShopAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenda, container, false);
        // Get markers from api rest and show them all
        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        String url = "https://deplastic.netlify.app/.netlify/functions/api/products";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject product = response.getJSONObject(i);
                            ImgUrl.add(product.getString("url"));
                            productName.add(product.getString("nom"));

                        }
                        this.recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
                        Manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(Manager);
                        adapter = new ShopAdapter(ImgUrl, productName, getContext());
                        adapter.setClickListener(this);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) { e.printStackTrace(); }
                }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Fragment fragment = new ProductFragment(position);
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, fragment);
        transaction.commit();
    }
}