package com.nss.nss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SqlManager extends AppCompatActivity {

    private AdminSql adminSql;
    private EditText txtConsulta;
    private Button btnEjecutarConsulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_manager);
        iniciarlizar();
    }

    private void iniciarlizar() {
        txtConsulta = findViewById(R.id.txtConsulta);
        btnEjecutarConsulta = findViewById(R.id.btnEjecutar);

        adminSql = new AdminSql(getApplicationContext(), "mydb", null, 1);

        btnEjecutarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminSql.ejecutarConsulta(txtConsulta.getText().toString(), getApplicationContext());
            }
        });

    }

}
