package com.nss.nss;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableLayoutDinamico {


    private TableLayout myTabla;
    private Context context;
    private TextView textView;
    private TableRow renglon;
    private Typeface letra;


    public TableLayoutDinamico(TableLayout tabla, Context ctx) {
        myTabla = tabla;
        context = ctx;
        letra = Typeface.createFromAsset(context.getAssets(), "fuentes/TitilliumWeb-SemiBold.ttf");
    }

    private void agregarRenglon() {
        renglon = new TableRow(context);
    }

    public void agregarCabezeras(String[] cabecera) {
        agregarRenglon();
        for (String c : cabecera)
            crearCeldaCabezera(c);
        myTabla.addView(renglon);
    }

    private void crearCelda(String texto) {
        textView = new TextView(context);
        textView.setPadding(10, 7, 10, 7);
        textView.setBackgroundResource(R.drawable.textview_border);
        textView.setTypeface(letra);
        textView.setTextColor(context.getResources().getColor(R.color.colorNns1));
        textView.setText(texto);
        textView.setGravity(Gravity.CENTER);
        renglon.addView(textView);
    }


    private void crearCeldaCabezera(String texto) {
        textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.textview_border);
        textView.setTypeface(letra);
        textView.setTextColor(context.getResources().getColor(R.color.succes));
        textView.setText(texto);
        textView.setGravity(Gravity.CENTER);
        renglon.addView(textView);
    }


    public void agregarRegistrosTable(int total, ArrayList<String> registros) {
        int contador = 0;
        for (int i = 0; i < total; i++) {
            agregarRenglon();
            for (int j = 0; j < 6; j++) {
                crearCelda(registros.get(contador));
                contador++;
            }
            myTabla.addView(renglon);
        }
    }


}
