package com.nss.nss;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class historicos_pruebas extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<String> registros;
    public static EditText txtBuscar;
    private boolean buscando;
    private AdminSql adminSql;
    private CalendarioDialog calendarioFecha;
    private TableLayoutDinamico tablaDinamica;
    private Typeface letra;
    private String CBuscada = "fecha";
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private String TAG_HISTORICOS_PRUEBAS = "FRAGMENT HISTORICOS PRUEBAS ";
    private String mensajeTag;
    private PruebasLog pruebasLog;

    public historicos_pruebas() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        letra = Typeface.createFromAsset(Objects.requireNonNull(getContext()).getAssets(), "fuentes/TitilliumWeb-Black.ttf");
        adminSql = new AdminSql(getContext(), "mydb", null, 1);
        calendarioFecha = new CalendarioDialog(getContext());

    }

    @BindArray(R.array.elementosSpinner)
    String[] cabezera;

    @BindView(R.id.tablelayout)
    TableLayout table;


    @OnClick(R.id.btnExportar)
    void clickBtnExportar() {
        adminSql.exportarBase();
    }

    @BindView(R.id.btnBuscar)
    Button btnBuscar;

    @OnClick(R.id.btnBuscar)
    void clickBtnBuscar() {
        table.removeAllViews();
        buscando = true;
        agregarRegistrosAtabla();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        Log.w(TAG_HISTORICOS_PRUEBAS, "UNBINDER");
        super.onDestroyView();
    }

    @BindView(R.id.spinner)
    Spinner spinerFiltrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_historicos_pruebas, container, false);
        unbinder = ButterKnife.bind(this, vista);
        txtBuscar = vista.findViewById(R.id.txtBuscar);
        spinerFiltrar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        CBuscada = "fecha";
                        break;
                    case 1:
                        CBuscada = "dbm";
                        break;
                    case 2:
                        CBuscada = "asu";
                        break;
                    case 3:
                        CBuscada = "tipo_de_red";
                        break;
                    case 4:
                        CBuscada = "tipo_de_red_telefonica";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txtBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CBuscada.equals("fecha"))
                    calendarioFecha.mostar();
            }
        });
        btnBuscar.setTypeface(letra);
        agregarRegistrosAtabla();
        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void agregarRegistrosAtabla() {
        if (buscando) {
            registros = adminSql.regresarRegistrosConsulta(txtBuscar.getText().toString(), registros, CBuscada);
            buscando = false;
        } else
            registros = adminSql.regresarRegistros(registros);
        mensajeTag = adminSql.getTotalRegistros() + " " + registros.size();
        Log.w(TAG_HISTORICOS_PRUEBAS, mensajeTag);
        enviarLog();
        tablaDinamica = new TableLayoutDinamico(table, getContext());
        tablaDinamica.agregarCabezeras(cabezera);
        tablaDinamica.agregarRegistrosTable(adminSql.getTotalRegistros(), registros);
    }

    private void enviarLog() {
        pruebasLog = new PruebasLog(adminSql.obtenerFecha(), TAG_HISTORICOS_PRUEBAS, mensajeTag);
        adminSql.insertarLog(pruebasLog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
