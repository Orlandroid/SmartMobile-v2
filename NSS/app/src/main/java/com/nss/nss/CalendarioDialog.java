package com.nss.nss;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;


public class CalendarioDialog {

    private Calendar calendario;
    private DatePickerDialog seleccionarFecha;
    private Context context;
    private int dia;
    private int mes;
    private int anio;

    public CalendarioDialog(Context ctx) {
        context = ctx;
        obtenerFecha();
        seleccionarFecha = new DatePickerDialog(context, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        historicos_pruebas.txtBuscar.setText(String.format("%s/%s/%s", changeDia(day), (changeDia(month + 1)), cambiarAnio(year)));
                    }
                }, anio, mes, dia);
    }

    public Button getBtnDialog() {
        return seleccionarFecha.getButton(DialogInterface.BUTTON_POSITIVE);
    }

    public void mostar() {
        seleccionarFecha.show();
    }

    /**
     * @param yy anio en formato xxxx
     * @return String anio formato xx
     */
    private String cambiarAnio(int yy) {
        return String.valueOf(yy).substring(2, 4);
    }

    private String changeDia(int day) {
        if (day < 10)
            return "0" + day;
        return String.valueOf(day);
    }


    private void obtenerFecha() {
        calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        anio = calendario.get(Calendar.YEAR);
    }

}
