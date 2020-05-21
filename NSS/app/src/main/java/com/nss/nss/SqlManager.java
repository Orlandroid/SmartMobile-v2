package com.nss.nss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SqlManager extends AppCompatActivity {

    private AdminSql adminSql;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_manager);
        unbinder = ButterKnife.bind(this);
<<<<<<< HEAD
=======
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
>>>>>>> test
    }

    @BindView(R.id.txtConsulta)
    EditText txtConsulta;
<<<<<<< HEAD
=======


    @OnClick(R.id.btnEjecutar)
    void clickBtnEjecutarConsulta() {
        adminSql = new AdminSql(getApplicationContext(), "mydb", null, 1);
        adminSql.ejecutarConsulta(txtConsulta.getText().toString(), getApplicationContext());
>>>>>>> test

    @OnClick(R.id.btnEjecutar)
    void ejecuatarConsulta() {
        adminSql.ejecutarConsulta(txtConsulta.getText().toString(), getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
