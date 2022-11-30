package org.deplastic.Deplastic.ui.tenda.producte;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.deplastic.Deplastic.Adapter;
import org.deplastic.Deplastic.R;
import org.deplastic.Deplastic.databinding.FragmentConfigBinding;
import org.deplastic.Deplastic.ui.config.balance.BalanceFragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ProductFragment extends Fragment {
    static int prodnum;
    private ProductViewModel mViewModel;

    public ProductFragment(int position) {
        prodnum = position;
    }

    public static ProductFragment newInstance() {
        return new ProductFragment(prodnum);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ProductViewModel productViewModel =
                new ViewModelProvider(this).get(ProductViewModel.class);

        //    TextView prodName  = getView().findViewById(R.id.NomProd);
        //  TextView prodDesc  = getView().findViewById(R.id.ProdDescription);
        //  TextView pointCost  = getView().findViewById(R.id.PointCost);
        //  ImageView imatge = getView().findViewById(R.id.imatgeProd);

        RequestQueue queue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        String url = "https://deplastic.netlify.app/.netlify/functions/api/products";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {

                    TextView prodName = getView().findViewById(R.id.NomProd);
                    TextView prodDesc = getView().findViewById(R.id.ProdDescription);
                    TextView pointCost = getView().findViewById(R.id.PointCost);
                    ImageView imatge = getView().findViewById(R.id.imatgeProd);

                    try {
                        JSONObject product = response.getJSONObject(prodnum);
                        prodName.setText(product.getString("nom"));
                        prodDesc.setText(product.getString("description"));
                        pointCost.setText(product.getString("price"));
                        Glide.with(getContext())
                                .load(product.getString("url"))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into((ImageView) getView().findViewById(R.id.imatgeProd));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(requireActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

      Button buyButton = getView().findViewById(R.id.buyButton);

        JSONObject compra = new JSONObject();
        SharedPreferences sp = getActivity().getSharedPreferences("email",Context.MODE_PRIVATE);
        String emailVal = sp.getString("email","");
        int prodId = prodnum;

        try {
            compra.put("email", emailVal);
            compra.put("article", prodId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        buyButton.setOnClickListener(v -> {
            String newurl = "https://deplastic.netlify.app/.netlify/functions/api/receipt";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, newurl, compra,
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


        return inflater.inflate(R.layout.fragment_product, container, false);
    }

}