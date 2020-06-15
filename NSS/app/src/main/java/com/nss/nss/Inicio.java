package com.nss.nss;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Inicio extends AppCompatActivity {

    private String urlFuente = "fuentes/TitilliumWeb-Black.ttf";
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        unbinder = ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtRedesMoviles.setTypeface(Typeface.createFromAsset(getAssets(), urlFuente));
        txtWifi.setTypeface(Typeface.createFromAsset(getAssets(), urlFuente));

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
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @BindView(R.id.txtMovil)
    TextView txtRedesMoviles;

    @BindView(R.id.txtWifi)
    TextView txtWifi;

    @OnClick(R.id.btnMovil)
    void clickBtnMovil() {
        Intent intento = new Intent(Inicio.this, RedesMovilesActivity.class);
        startActivity(intento);
    }

    @OnClick(R.id.btnWifi)
    void clickBtnWifi() {
        Intent intento = new Intent(Inicio.this, SqlManager.class);
        startActivity(intento);
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
        return super.onOptionsItemSelected(item);
    }

}