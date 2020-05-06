package com.nss.nss;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class GriedViewPersonalizado extends BaseAdapter {

    private Context context;
    private List<String> lista;
    private LayoutInflater layoutInflater;
    private String urlFuente = "fuentes/TitilliumWeb-Regular.ttf";

    public GriedViewPersonalizado(Context context, List<String> lista) {
        this.context = context;
        this.lista = lista;
        this.layoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View vista = inflater.inflate(R.layout.renglon_grid_view, null);
        TextView name = vista.findViewById(R.id.rowTextView);
        name.setTypeface(Typeface.createFromAsset(context.getAssets(), urlFuente));
        name.setText(lista.get(posicion));
        return vista;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}