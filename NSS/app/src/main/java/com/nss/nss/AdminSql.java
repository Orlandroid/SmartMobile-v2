package com.nss.nss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AdminSql extends SQLiteOpenHelper {

    private final String TABLE_NAME = "historicosRedesMoviles";
    private String crearTabla = "CREATE TABLE " + TABLE_NAME + "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fecha TEXT," +
            "dbm INTEGER," +
            "asu INTEGER," +
            "pais TEXT," +
            "tipo_de_red TEXT," +
            "tipo_de_red_telefonica TEXT" +
            ")";
    private SQLiteDatabase db;
    private int totalRegistros;
    private Context ctx;

    public AdminSql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = this.getWritableDatabase();
        ctx = context;
    }

    public void exportarBase() {
        SQLiteToExcel sqLiteToExcel = new SQLiteToExcel(ctx, "mydb");
        sqLiteToExcel.exportSingleTable(TABLE_NAME, "historicos.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                Toast.makeText(ctx, "Exportando base de datos", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(ctx, filePath, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ctx, "Error no se pudo exportar la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void insertar(int iDbm, int iAsu, imformacionDispositivos info) {
        try {
            db = AdminSql.this.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("fecha", obtenerFecha());
            registro.put("dbm", iDbm);
            registro.put("asu", iAsu);
            registro.put("pais", info.getCodigoPais());
            registro.put("tipo_de_red", info.getTypeOfNetwork234());
            registro.put("tipo_de_red_telefonica", info.getTypeOfNetwork());
            db.insert(TABLE_NAME, null, registro);
            db.close();
        } catch (Exception e) {
            Log.w("Error", e.getMessage());
        }
    }


    private void setTotalRegistros(int total) {
        totalRegistros = total;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }


    /*** metodo el cual regresa un arrayLIst con todos los elementos de la base de datos
     * @param registros del tipo ArrayList<String>
     * @return ArrayList<String> con los registros
     */
    public ArrayList<String> regresarRegistros(ArrayList<String> registros) {
        try {
            registros = new ArrayList<>();
            db = this.getWritableDatabase();
            Cursor fila = db.rawQuery("select * from " + TABLE_NAME + " limit 100", null);
            setTotalRegistros(fila.getCount());
            if (fila.moveToFirst()) {
                do {
                    registros.add(fila.getString(0));
                    registros.add(fila.getString(1));
                    registros.add(fila.getString(2));
                    registros.add(fila.getString(3));
                    registros.add(fila.getString(4));
                    registros.add(fila.getString(5));
                    registros.add(fila.getString(6));
                } while (fila.moveToNext());
                Log.w("MENSAJE", registros.toString());
                fila.close();
                db.close();
            }
        } catch (Exception e) {
            Log.w("ERROR", e.getMessage());
        }
        return registros;
    }

    public ArrayList<String> regresarRegistrosConsulta(String pbuscador, ArrayList<String> registros, String cBuscado) {
        try {
            Toast.makeText(ctx, cBuscado, Toast.LENGTH_SHORT).show();
            String consulta;
            if (pbuscador.equals("")) {
                consulta = "select * from " + TABLE_NAME + " limit 100";
            } else
                consulta = "select * from historicosRedesMoviles where " + cBuscado + "='" + pbuscador + "'";
            registros = new ArrayList<>();
            db = this.getWritableDatabase();
            Cursor fila = db.rawQuery(consulta, null);
            setTotalRegistros(fila.getCount());
            if (fila.moveToFirst()) {
                do {
                    registros.add(fila.getString(0));
                    registros.add(fila.getString(1));
                    registros.add(fila.getString(2));
                    registros.add(fila.getString(3));
                    registros.add(fila.getString(4));
                    registros.add(fila.getString(5));
                    registros.add(fila.getString(6));
                } while (fila.moveToNext());
                Log.w("MENSAJE", registros.toString());
                fila.close();
                db.close();
            }
        } catch (Exception e) {
            Log.w("ERROR", e.getMessage());
        }
        return registros;
    }

    /**
     * @return String
     * regresa la fecha actual en formato dd/mm/yy
     */

    private String obtenerFecha() {
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(fecha);
    }

    public void ejecutarConsulta(String id, Context ctx) {
        String consultaEliminar = "delete from " + TABLE_NAME + " where id in(" + id + ")";
        String mensaje;
        db = this.getWritableDatabase();
        db.execSQL(consultaEliminar);
        db.close();
        mensaje = "Se ha ejecutado correctamente la consulta";
        Toast.makeText(ctx, mensaje, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(crearTabla);

    }


}
