package com.example.tienditapp.modelo;

import java.io.Serializable;

public class Producto implements Serializable {
    int id;
    String name;
    String thumurl;
    String price;
    String provider;
    String delivery;

    public Producto(int id, String name, String thumurl, String price, String provider, String delivery) {
        this.id = id;
        this.name = name;
        this.thumurl = thumurl;
        this.price = price;
        this.provider = provider;
        this.delivery = delivery;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumurl() {
        return thumurl;
    }

    public String getPrice() {
        return price;
    }

    public String getProvider() {
        return provider;
    }

    public String getDelivery() {
        return delivery;
    }
}
