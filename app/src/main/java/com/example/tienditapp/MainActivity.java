package com.example.tienditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tienditapp.modelo.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONArray> {

    ListView lv;
    ProgressBar pbConexion;

    int ancho, alto, id;
    float cantidad;

    String url,name,thumbnail_url,price,provider,delivery,envio;
    RequestQueue queue;
    JsonArrayRequest request;
    ArrayList<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ancho = size.x;
        alto = size.y;

        lv = findViewById(R.id.lv);
        envio = getResources().getString(R.string.envio);

        productos = new ArrayList<Producto>();
        queue = Volley.newRequestQueue(this);
        url = getResources().getString(R.string.url_base);
        request = new JsonArrayRequest(Request.Method.GET,url,null,this,this);
        queue.add(request);

        lv.setOnItemClickListener(onClickListView);
        pbConexion = findViewById(R.id.progressBar);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pbConexion.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, error + ".", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        pbConexion.setVisibility(View.GONE);
        JSONObject jsonObject;

        try{
            for(int i = 0;i<response.length();i++){
                jsonObject = response.getJSONObject(i);
                id = jsonObject.getInt("id");
                name = jsonObject.getString("name");
                thumbnail_url = jsonObject.getString("thumbnail_url");
                price = jsonObject.getString("price");
                provider = jsonObject.getString("provider");
                delivery = jsonObject.getString("delivery");

                Producto producto = new Producto(id, name, thumbnail_url, price, provider, delivery);
                productos.add(producto);
            }

            ImageAdapter adaptador = new ImageAdapter(this, productos,ancho,alto);
            lv.setAdapter(adaptador);

        }catch (JSONException e){

        }
    }

    private ListView.OnItemClickListener onClickListView = new  ListView.OnItemClickListener(){
        @Override
        public void onItemClick(final AdapterView<?> adapter, View v, int position, long arg){
            int ide = productos.get(position).getId();
            String precio = productos.get(position).getPrice();
            Bundle bundle = new Bundle();
            bundle.putInt("ID",ide);
            bundle.putString("Prec",precio);
            Intent intent = new Intent(MainActivity.this, Descripcion.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

}
