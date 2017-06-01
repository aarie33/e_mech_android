package com.example.arieahmad.e_mech.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arieahmad.e_mech.OrderGetSet;
import com.example.arieahmad.e_mech.R;

import java.util.ArrayList;

/**
 * Created by Arie Ahmad on 5/12/2017.
 */

public class OrderAdapter extends BaseAdapter{
    Activity activity;
    ArrayList listItem;

    public OrderAdapter(Activity activity, ArrayList listItem) {
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_list_proses_item, null);

            holder.imgServis = (ImageView)view.findViewById(R.id.imgServis);
            holder.txtKeahlian = (TextView)view.findViewById(R.id.txtKeahlian);
            holder.txtDeskripsi = (TextView)view.findViewById(R.id.txtDeskripsi);
            holder.txtTempat = (TextView)view.findViewById(R.id.txtTempat);
            holder.txtTanggal = (TextView)view.findViewById(R.id.txtTanggal);
            holder.txtJam = (TextView)view.findViewById(R.id.txtJam);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        OrderGetSet data = (OrderGetSet) getItem(i);
        holder.txtKeahlian.setText(data.getKeahlian());
        holder.txtDeskripsi.setText(data.getDeskripsi());
        holder.txtTempat.setText(data.getTempat());
        holder.txtTanggal.setText(data.getTgl_pemesanan());
        holder.txtJam.setText(data.getJam_pemesanan());

        String keahlian = data.getKeahlian().toString();
        if (keahlian.equals("Servis Laptop")){
            holder.imgServis.setImageResource(R.drawable.laptop_icon);
        }else {
            holder.imgServis.setImageResource(R.drawable.pc_icon);
        }

        return view;
    }

    static class ViewHolder{
        ImageView imgServis;
        TextView txtKeahlian, txtDeskripsi, txtTempat, txtTanggal, txtJam;
    }
}
