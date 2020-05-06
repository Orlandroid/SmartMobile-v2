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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pruebas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pruebas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TelefonoMedida phoneListen;
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    private TelephonyManager tm;
    private OnFragmentInteractionListener mListener;
    public static Button btnIniciarPrueba;
    private int escucharTelefono = PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE;
    private Typeface letra;

    public pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static pruebas newInstance(String param1, String param2) {
        pruebas fragment = new pruebas();
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
        letra=Typeface.createFromAsset(Objects.requireNonNull(getContext()).getAssets(),"fuentes/TitilliumWeb-Bold.ttf");

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_pruebas, container, false);
        btnIniciarPrueba = vista.findViewById(R.id.btnIniciarPrueba);
        btnIniciarPrueba.setTypeface(letra);
        tm = (TelephonyManager) Objects.requireNonNull(getContext()).getSystemService(Context.TELEPHONY_SERVICE);
        speedometer = vista.findViewById(R.id.speedView);
        speedDeluxe = vista.findViewById(R.id.speedDeluxe);
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

    // TODO: Rename method, update argument and hook method into UI event
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
