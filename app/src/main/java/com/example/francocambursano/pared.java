package com.example.francocambursano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class pared extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pared);
        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_precio = (EditText) findViewById(R.id.txt_precio);
    }
    public void Registrar(View view){
        AdminSQLite admin = new AdminSQLite(this, "administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulo", null, registro);

            BaseDeDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");
        }
        else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void Buscar(View view){
        AdminSQLite admin = new AdminSQLite(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        String codigo = et_codigo.getText().toString();
        if (!codigo.isEmpty()) {
            Cursor fila = BaseDeDatabase.rawQuery("select descripcion, precio from artiuclos where codigo= " + codigo,null);
            if(fila.moveToFirst()){
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                BaseDeDatabase.close();
            }
            else {
                Toast.makeText(this,"No existe el articulo", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }
        }else{
            Toast.makeText(this, "introduce el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view){
        AdminSQLite admin = new AdminSQLite(this, "administracion", null,1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()){
            int cantidad = BaseDatabase.delete("articulos", "codigo=" + codigo, null);
            BaseDatabase.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if (cantidad==1){
                Toast.makeText(this, "Articulo eliminado", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this,"Debes de introducir el codigo del articulo",Toast.LENGTH_SHORT).show();
        }
    }
    public void Modificar(View view) {
        AdminSQLite admin = new AdminSQLite(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = BaseDatabase.update("articulos",registro,"codigo="+codigo,null);
            if (cantidad==1){
                Toast.makeText(this, "Articulo modificado", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            Toast.makeText(this,"Debes llenar campos",Toast.LENGTH_SHORT).show();
        }
    }

}
