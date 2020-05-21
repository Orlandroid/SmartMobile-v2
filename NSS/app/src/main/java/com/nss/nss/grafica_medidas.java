package com.nss.nss;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.jjoe64.graphview.GraphView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class grafica_medidas extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

<<<<<<< HEAD

=======
>>>>>>> test
    private String mParam1;
    private String mParam2;
    private Grafica grafica;
    private Unbinder unbinder;
<<<<<<< HEAD

=======
>>>>>>> test

    private OnFragmentInteractionListener mListener;

    public grafica_medidas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

<<<<<<< HEAD
    @BindView(R.id.tablelayout)
=======
    @BindView(R.id.graph)
>>>>>>> test
    GraphView graphView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grafica_medidas, container, false);
        unbinder = ButterKnife.bind(this, view);
        grafica = new Grafica(graphView, getContext());
        grafica.inicializarGraphView();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
<<<<<<< HEAD
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
=======
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
>>>>>>> test
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
