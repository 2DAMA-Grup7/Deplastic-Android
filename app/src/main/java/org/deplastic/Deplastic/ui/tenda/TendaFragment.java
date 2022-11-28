package org.deplastic.Deplastic.ui.tenda;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.deplastic.Deplastic.MyRecyclerViewAdapter;
import org.deplastic.Deplastic.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TendaFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    private TendaViewModel mViewModel;

    MyRecyclerViewAdapter adapter;


    public static TendaFragment newInstance() {
        return new TendaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenda, container, false);

        // data to populate the RecyclerView with

        ArrayList<String> productNames = new ArrayList<>();

        // Get markers from api rest and show them all
        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        String url = "https://deplastic.netlify.app/.netlify/functions/api/products";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject product = response.getJSONObject(i);
                            productNames.add(product.getString("nom"));
                        }
                        // set up the RecyclerView
                        RecyclerView recyclerView = view.findViewById(R.id.Products);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new MyRecyclerViewAdapter(getContext(), productNames);
                        adapter.setClickListener(this);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) { e.printStackTrace(); }
                }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TendaViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}