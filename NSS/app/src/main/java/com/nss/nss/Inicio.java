package com.nss.nss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;


public class Inicio extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnMenuRedesMoviles;
    private ImageButton btnWifi;
    private TextView txtWifi;
    private TextView txtRedesMoviles;
    private String urlFuente = "fuentes/TitilliumWeb-Black.ttf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMenuRedesMoviles = findViewById(R.id.btnMovil);
        btnWifi = findViewById(R.id.btnWifi);
        txtRedesMoviles = findViewById(R.id.txtMovil);
        txtWifi = findViewById(R.id.txtWifi);
        txtRedesMoviles.setTypeface(Typeface.createFromAsset(getAssets(), urlFuente));
        txtWifi.setTypeface(Typeface.createFromAsset(getAssets(), urlFuente));

        btnMenuRedesMoviles.setOnClickListener(this);
        btnWifi.setOnClickListener(this);
        darPermisosApp();
    }

    /*este metodo sirve para dar permisos a las aplicacion ya que si no le damos los permios suficientes a la aplicacion no pedemos
     * usar algunos metodos de PhoneManeger*/
    private void darPermisosApp() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions
                    (this, new String[]
                                    {Manifest.permission.READ_PHONE_STATE,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            super.finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        if (v == btnMenuRedesMoviles) {
            Intent intento = new Intent(Inicio.this, RedesMovilesActivity.class);
            startActivity(intento);
        }
        if (v == btnWifi) {
            Intent intento = new Intent(Inicio.this, SqlManager.class);
            startActivity(intento);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_redes_moviles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuAcercaDe) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_acerca_de);
            builder.setMessage(R.string.contenido_acerca_de);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}