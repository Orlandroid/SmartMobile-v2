package com.nss.nss;


import android.content.Context;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.github.anastr.speedviewlib.DeluxeSpeedView;
import com.github.anastr.speedviewlib.SpeedView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class TelefonoMedida extends PhoneStateListener {

    private String allInfo;
    private String[] partInfo;
    private int asu;
    private int dbm;
    private imformacionDispositivos info;
    private AdminSql adminSql;
    private String[] medidas = new String[2];
    private boolean permitirGirar;
    private NotificationHelpener notificacionHelpe;
    private String tituloMensajeNotificacion = "La red cambio";
    private String nombreDeFragment;
    private int lastXpoint = 1;
    private String TAG_IMFORMACION_REDES_MOVILES = "FRAGMENT REDES MOVILES";
    private String TAG_GRAFICA_MEDIDAS = "FRAGMENT GRAFICA MEDIDAS";
    private PruebasLog pruebasLog;


    /*variables usadas con instanciaos desde el fragmente de pruebas*/
    private SpeedView speedometer;
    private DeluxeSpeedView speedDeluxe;
    private ArrayAdapter adaptadorDatosRedes;
    private List<String> listDatosRm;
    private LineGraphSeries<DataPoint> series;


    public void setPermitirGirar(boolean permitirGirar) {
        this.permitirGirar = permitirGirar;
    }


    /*fragment de pruebas*/
    public TelefonoMedida(Context context, SpeedView sp, DeluxeSpeedView dx) {
        nombreDeFragment = "pruebas";
        speedDeluxe = dx;
        speedometer = sp;
        notificacionHelpe = new NotificationHelpener(context);
        info = new imformacionDispositivos(context);
        adminSql = new AdminSql(context, "mydb", null, 1);
    }

    /* fragment de imformacion*/
    public TelefonoMedida(ArrayAdapter datosRedes, Context context, List<String> datosRM) {
        nombreDeFragment = "imformacion";
        adaptadorDatosRedes = datosRedes;
        listDatosRm = datosRM;
        info = new imformacionDispositivos(context);
        adminSql = new AdminSql(context, "mydb", null, 1);
    }

    public TelefonoMedida(LineGraphSeries<DataPoint> series, Context context) {
        this.series = series;
        nombreDeFragment = "grafica";
        info = new imformacionDispositivos(context);
        adminSql = new AdminSql(context, "mydb", null, 1);
    }


    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        try {
            allInfo = signalStrength.toString();
            partInfo = allInfo.split(" ");
            switch (info.getTypeOfNetwork234()) {
                case "2G":
                    asu = signalStrength.getGsmSignalStrength();
                    dbm = esAsu(asu);
                    Log.w(TAG_IMFORMACION_REDES_MOVILES, "2G");
                    break;
                case "3G":
                    if (Build.VERSION.RELEASE.equals("7.0")) {
                        medidas = info.getSignalStrength();
                        dbm = Integer.parseInt(medidas[0]);
                        asu = Integer.parseInt(partInfo[1]);
                    } else {
                        dbm = Integer.parseInt(partInfo[14]);//14
                        asu = esDbm(Integer.parseInt(partInfo[14]));//14
                    }
                    Log.w(TAG_IMFORMACION_REDES_MOVILES, "3G");
                    break;
                case "4G":
                    dbm = Integer.parseInt(partInfo[9]);
                    asu = (Integer.parseInt(partInfo[2]));//140
                    Log.w(TAG_IMFORMACION_REDES_MOVILES, "4G");
                    break;
            }
            if (nombreDeFragment.equals("grafica")) {
                lastXpoint++;
                series.appendData(new DataPoint(lastXpoint, dbm), true, 100);
                pruebasLog = new PruebasLog(adminSql.obtenerFecha(), TAG_GRAFICA_MEDIDAS, "Actualizando grafica");
                adminSql.insertarLog(pruebasLog);
                Log.w(TAG_GRAFICA_MEDIDAS, "Actualizando grafica" + dbm + asu);
            }
            pruebasLog = new PruebasLog(adminSql.obtenerFecha(), TAG_IMFORMACION_REDES_MOVILES, "dbm " + dbm + " asu " + asu);
            adminSql.insertarLog(pruebasLog);
            Log.w(TAG_IMFORMACION_REDES_MOVILES, "dbm " + dbm + " asu " + asu);
            ponerMedidaSpeed(dbm, asu);
            if (pruebas.btnIniciarPrueba.getText().toString().equalsIgnoreCase("DETENER")) {
                adminSql.insertar(dbm, asu, info);
            }
        } catch (Exception e) {
            Log.w(TAG_IMFORMACION_REDES_MOVILES, e.getMessage());
            pruebasLog = new PruebasLog(adminSql.obtenerFecha(), TAG_IMFORMACION_REDES_MOVILES, e.getMessage());
            adminSql.insertarLog(pruebasLog);
        }
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);
        String mensajeNotificacion = "";
        if (nombreDeFragment.equals("pruebas")) {
            switch (info.getTypeOfNetwork234()) {
                case "2G":
                    mensajeNotificacion = "El tipo de red es 2G";
                    break;
                case "3G":
                    mensajeNotificacion = "El tipo de red es 3G";
                    break;
                case "4G":
                    mensajeNotificacion = "El tipo de red es 4G";
                    break;
            }
            if (!mensajeNotificacion.equals(""))
                notificacionHelpe.createNotification(mensajeNotificacion, tituloMensajeNotificacion);
        } else
            actualizarGriedView();
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
        switch (direction) {
            case TelephonyManager.DATA_CONNECTED:
            case TelephonyManager.DATA_DISCONNECTED:
            case TelephonyManager.DATA_CONNECTING:
                actualizarGriedView();
                break;
        }
    }


    private void actualizarGriedView() {
        adaptadorDatosRedes.clear();
        info.getImformationRedesMoviles(listDatosRm);
        Log.w(TAG_IMFORMACION_REDES_MOVILES, "Actualizando GridView");
        pruebasLog = new PruebasLog(adminSql.obtenerFecha(), TAG_IMFORMACION_REDES_MOVILES, "Actualizando GriedView");
    }

    /**
     * @return int
     * metodo el cual recibe como parametro un int el cual es el asu y
     * regresa un dbm
     */
    private int esAsu(int asu) {
        return ((2 * asu) - 113);
    }

    private int esDbm(int dbm) {
        return (dbm + 120);
    }

    private void ponerMedidaSpeed(int pasu, int pdbm) {
        if (permitirGirar) {
            speedometer.speedTo(pasu);
            speedDeluxe.speedTo(pdbm);
        }
    }

}
