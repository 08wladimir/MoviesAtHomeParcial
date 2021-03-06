package com.example.wladimir.moviesathome;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Wladimir on 26/09/2016.
 */
public class VerActivity extends AppCompatActivity {

    private ImageView txtImagen;
    private TextView txtNombre, txtEstreno, txtTipo, txtDuracion, txtCalificacion, txtsinopsis;
    String Stringimagen, Stringnombre, Stringestreno, Stringtipo, Stringduracion, Stringcalificacion, Stringsinopsis;
    String nombrePeli, nombreUsuario,reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        //Recibo las variables del intent anterior para usarlas
        nombrePeli = getIntent().getStringExtra("nombrePeli");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        SQLiteMovieAtHome admin = new SQLiteMovieAtHome(this, "MovieAtHome", null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM movie WHERE nombre='"+nombrePeli+"' ", null);

        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Stringimagen = cursor.getString(8);
                Stringnombre = cursor.getString(1);
                Stringestreno = cursor.getString(4);
                Stringtipo = cursor.getString(6);
                Stringduracion = cursor.getString(3);
                Stringcalificacion = cursor.getString(5);
                Stringsinopsis = cursor.getString(2);
            } while(cursor.moveToNext());
        }

        // traigo todos los elements por id para asignarles la variable por defecto con el dato que ya traje
        txtImagen = (ImageView) findViewById(R.id.imagen);
        txtNombre = (TextView)findViewById(R.id.nombre);
        txtEstreno = (TextView)findViewById(R.id.estreno);
        txtTipo = (TextView)findViewById(R.id.tipo);
        txtDuracion = (TextView)findViewById(R.id.duracion);
        txtCalificacion = (TextView)findViewById(R.id.calificacion);
        txtsinopsis = (TextView)findViewById(R.id.sinopsis);

        txtImagen.setImageDrawable(getResources().getDrawable(R.drawable.superman));
        txtNombre.setText(Stringnombre);
        txtEstreno.setText(Stringestreno);
        txtTipo.setText(Stringtipo);
        txtDuracion.setText(Stringduracion);
        txtCalificacion.setText(Stringcalificacion);
        txtsinopsis.setText(Stringsinopsis);

    }

    public void reserva(View v){
        EditText r = (EditText) findViewById(R.id.editDiasReserva);
        reserva = r.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Usted ha comenzado hacer una reserva")
                .setTitle("Reservacion");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), ReservaActivity.class);
                intent.putExtra("reserva", reserva);
                intent.putExtra("nombrePeli", nombrePeli);
                intent.putExtra("nombreUsuario", nombreUsuario);
                startActivity(intent);
            }
        });

        builder.create().show();
    }


}
