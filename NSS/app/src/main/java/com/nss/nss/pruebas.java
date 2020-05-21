package com.nss.nss;


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;

import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class pruebas extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private TelefonoMedida phoneListen;
    private TelephonyManager tm;
    private OnFragmentInteractionListener mListener;
    public static Button btnIniciarPrueba;
    private int escucharTelefono = PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE;
    private Typeface letra;
    private Unbinder unbinder;

    public pruebas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        letra = Typeface.createFromAsset(Objects.requireNonNull(getContext()).getAssets(), "fuentes/TitilliumWeb-Bold.ttf");

    }


    private void btnPrueba() {
        if (btnIniciarPrueba.getText().toString().equalsIgnoreCase("Detener"))
            btnIniciarPrueba.setText("Iniciar prueba");
        else {
            btnIniciarPrueba.setText("Detener");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        tm.listen(phoneListen, escucharTelefono);
    }


    @BindView(R.id.speedView)
    SpeedView speedometer;

    @BindView(R.id.speedDeluxe)
    DeluxeSpeedView speedDeluxe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pruebas, container, false);
        unbinder = ButterKnife.bind(this, vista);
        btnIniciarPrueba = vista.findViewById(R.id.btnIniciarPrueba);
        btnIniciarPrueba.setTypeface(letra);
        tm = (TelephonyManager) Objects.requireNonNull(getContext()).getSystemService(Context.TELEPHONY_SERVICE);
        phoneListen = new TelefonoMedida(getActivity(), speedometer, speedDeluxe);
        tm.listen(phoneListen, escucharTelefono);
        btnIniciarPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneListen.setPermitirGirar(true);
                btnPrueba();
            }
        });
        inicializarValoresSpeed();
        return vista;
    }

    private void inicializarValoresSpeed() {
        speedometer.setWithTremble(false);
        speedDeluxe.setWithTremble(false);
        speedometer.setUnitUnderSpeedText(true);
        speedDeluxe.setUnitUnderSpeedText(true);
        speedometer.setUnit("dbm");
        speedDeluxe.setUnit("asu");
        speedometer.setMinSpeed(-120);
        speedometer.setMaxSpeed(-51);
        speedDeluxe.setMinSpeed(0);
        speedDeluxe.setMaxSpeed(63);
        speedometer.setTickNumber(4);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tm.listen(phoneListen, PhoneStateListener.LISTEN_NONE);
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
