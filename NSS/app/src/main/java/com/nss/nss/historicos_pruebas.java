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


public class historicos_pruebas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TableLayout table;
    private ArrayList<String> registros;
    private Button btnBuscar;
    public static EditText txtBuscar;
    private boolean buscando;
    private AdminSql adminSql;
    private CalendarioDialog calendarioFecha;
    private TableLayoutDinamico tablaDinamica;
    private String[] cabezera = {"Id", "Fecha", "Dbm", "Asu", "Codigo", "Red", "Tipo red"};
    private Typeface letra;
    private Button btnExportar;
    private Spinner spinerFiltrar;
    private String CBuscada = "fecha";
    private OnFragmentInteractionListener mListener;

    public historicos_pruebas() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment historicos_pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static historicos_pruebas newInstance(String param1, String param2) {
        historicos_pruebas fragment = new historicos_pruebas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_historicos_pruebas, container, false);
        table = vista.findViewById(R.id.tablelayout);
        btnExportar = vista.findViewById(R.id.btnExportar);
        spinerFiltrar = vista.findViewById(R.id.spinner);
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
        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminSql.exportarBase();
            }
        });
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        txtBuscar = vista.findViewById(R.id.txtBuscar);
        btnBuscar.setTypeface(letra);

        txtBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CBuscada.equals("fecha"))
                    calendarioFecha.mostar();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table.removeAllViews();
                buscando = true;
                agregarRegistrosAtabla();
            }
        });
        agregarRegistrosAtabla();
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        Log.w("Registros", adminSql.getTotalRegistros() + " " + registros.size());
        tablaDinamica = new TableLayoutDinamico(table, getContext());
        tablaDinamica.agregarCabezeras(cabezera);
        tablaDinamica.agregarRegistrosTable(adminSql.getTotalRegistros(), registros);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
