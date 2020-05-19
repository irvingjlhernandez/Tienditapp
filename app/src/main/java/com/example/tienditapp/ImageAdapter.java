package com.example.tienditapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tienditapp.modelo.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context contexto;
    ArrayList<Producto> productos;
    int ancho, alto;
    ImageView ivPro;

    public ImageAdapter(Context contexto, ArrayList<Producto> productos, int ancho, int alto) {
        this.contexto = contexto;
        this.productos = productos;
        this.ancho = ancho;
        this.alto = alto;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        TextView tvNom = vista.findViewById(R.id.tvNom);
        TextView tvProvi = vista.findViewById(R.id.tvProvi);
        TextView tvPrice = vista.findViewById(R.id.tvPrice);
        TextView tvDely = vista.findViewById(R.id.tvDely);
        ivPro = vista.findViewById(R.id.ivPro);

        Picasso.with(contexto)
                .load(productos.get(position).getThumurl())
                .into(ivPro);
        tvNom.setText(productos.get(position).getName());
        tvProvi.setText(contexto.getResources().getString(R.string.proveedor)+" "+productos.get(position).getProvider());
        tvPrice.setText(contexto.getResources().getString(R.string.moneda)+" "+productos.get(position).getPrice());
        if (Float.parseFloat(productos.get(position).getDelivery()) == 0.0){
            tvDely.setText(contexto.getResources().getString(R.string.envio));
        }else{
            tvDely.setText(contexto.getResources().getString(R.string.moneda)+" "+productos.get(position).getDelivery());
        }

        return vista;
    }
}
