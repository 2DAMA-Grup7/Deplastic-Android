package org.deplastic.Deplastic.ui.tenda.producte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.deplastic.Deplastic.R;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductFragment extends Fragment {

    static int prodNum;
    public ProductFragment(int position) {
        prodNum = position;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        String url = "https://deplastic.netlify.app/.netlify/functions/api/products";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    TextView prodName = requireView().findViewById(R.id.NomProd);
                    TextView prodDesc = requireView().findViewById(R.id.ProdDescription);
                    TextView pointCost = requireView().findViewById(R.id.PointCost);
                    //ImageView image = requireView().findViewById(R.id.imatgeProd);
                    try {
                        JSONObject product = response.getJSONObject(prodNum);
                        prodName.setText(product.getString("nom"));
                        prodDesc.setText(product.getString("description"));
                        pointCost.setText(product.getString("price"));
                        Glide.with(getContext())
                                .load(product.getString("url"))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into((ImageView) requireView().findViewById(R.id.imatgeProd));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        Button buyButton = view.findViewById(R.id.buyButton);

        JSONObject purchase = new JSONObject();
        SharedPreferences sp = requireActivity().getSharedPreferences("email", Context.MODE_PRIVATE);
        String emailVal = sp.getString("email", "");
        int prodId = prodNum;

        try {
            purchase.put("email", emailVal);
            purchase.put("article", prodId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        buyButton.setOnClickListener(v -> {
            String newURL = "https://deplastic.netlify.app/.netlify/functions/api/receipt";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, newURL, purchase,
                    response -> {
                        try {
                            Toast.makeText(requireActivity().getApplicationContext(), response.getString("response"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        });

        return view;
    }

}