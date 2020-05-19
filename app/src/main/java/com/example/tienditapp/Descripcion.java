package com.example.tienditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Descripcion extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String>{

    ImageView ivArticulo;
    TextView tvTitulo, tvDescription, tvCarrito;
    ProgressBar pbConexion;
    String url, error,cuenta,mon;
    RequestQueue queue;
    StringRequest request;
    ImageButton ibCarrito;
    float cant = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        final int id = bundle.getInt("ID");
        final String precio = bundle.getString("Prec");

        ivArticulo = findViewById(R.id.ivArticulo);
        pbConexion = findViewById(R.id.pbConexion);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescription = findViewById(R.id.tvDecription);
        ibCarrito = findViewById(R.id.ibCarrito);
        tvCarrito = findViewById(R.id.tvCarrito);

        error = getResources().getString(R.string.errorc);
        url = getResources().getString(R.string.url_desc)+id;
        cuenta = getResources().getString(R.string.cuenta);
        mon = getResources().getString(R.string.moneda);

        queue = Volley.newRequestQueue(this);
        ibCarrito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float pr = Float.parseFloat(precio);
                cant = cant+pr;
                tvCarrito.setText(cuenta+mon+" "+Float.toString(cant)+".");
            }
        });


        request = new StringRequest(Request.Method.POST, url,this,this){
            protected Map<String,String>getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(id));
                return params;
            }
        };

        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pbConexion.setVisibility(View.GONE);
        Toast.makeText(Descripcion.this, error + ".", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onResponse(String response) {
        pbConexion.setVisibility(View.GONE);
        try {
            byte[] u = response.toString().getBytes("ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {

            JSONObject jsonObject = new JSONObject(response);
            Picasso.with(this)
                    .load(jsonObject.getString("imag_url"))
                    .into(ivArticulo);
            tvTitulo.setText(jsonObject.getString("name"));
            String text1 = String.format(jsonObject.getString("desc"), "ISO-8859-1");
            tvDescription. setText(text1);
        }catch(JSONException e){
            Toast.makeText(Descripcion.this, error + ".", Toast.LENGTH_SHORT).show();
        }
    }
}
