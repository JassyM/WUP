package com.example.administrador.wup;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    private BaseDatosAlarma alarmas;
    private SQLiteDatabase bd;
    private ContentValues registro; // Guarda los datos de la alarma antes de pasarla a la BD

    EditText txtFecha;
    EditText txtHora;
    private int anoSys, mesSys, diaSys, ano2, mes2, dia2;
    private int horaSys, minutoSys, hora2, minuto2;
    static final int DATE_ID = 0;
    Calendar c;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = Calendar.getInstance();
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtHora = (EditText) findViewById(R.id.txtHora);
        txtFecha.setOnClickListener(this);
        txtHora.setOnClickListener(this);


        BaseDatosAlarma alarmas = new BaseDatosAlarma(this, "BbAlarmas", null, 1);
        bd = alarmas.getWritableDatabase();

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/



        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //update_text = (TextView) findViewById(R.id.update_text);

        final Calendar calendar = Calendar.getInstance();
        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);

        final Intent my_intent = new Intent(this.context, AlarmReceiver.class);

        btnCancelar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                alarm_manager.cancel(pending_intent);
                my_intent.putExtra("extra", "alarm off");

                //Para el sonido
                sendBroadcast(my_intent);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v){
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);
                if(hour>12){
                    hour_string = String.valueOf(hour - 12);
                }
                if (minute < 10){
                    minute_string = "0"+ String.valueOf(minute);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoAlerta dialogo = new DialogoAlerta();
                dialogo.show(fragmentManager, "tagAlerta");

                my_intent.putExtra("extra", "alarm on");

                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);
            }
        });

    }

    public void llenarBaseDatos(){
        // Abrimos la BD 'Alarma' en modo escritura
        // Aqui es donde se verifica si la BD Alumnos existe, si no, la creará.
        BaseDatosAlarma  alarmas = new BaseDatosAlarma(this, "BbAlarmas", null, 1);
        SQLiteDatabase bd = alarmas.getWritableDatabase();
        registro = new ContentValues();
            /*registro.put("mensaje", t5.getText().toString());//nombre del campo
            registro.put("fecha", t6.getText().toString());
            registro.put("hora", t7.getText().toString());*/
        bd.insert("alarma", null, registro);//nombre de la tabla
        bd.close();
            /*t3.setText("");
            t5.setText("");
            t6.setText("");
            t7.setText("");
            Toast.makeText(this, "alarma registrada", Toast.LENGTH_LONG).show();*/

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v == txtFecha){
            diaSys = c.get(Calendar.DAY_OF_MONTH);
            mesSys = c.get(Calendar.MONTH);
            anoSys = c.get(Calendar.YEAR);

            showDialog(DATE_ID);
        }
        else if (v == txtHora){
            horaSys = c.get(Calendar.HOUR_OF_DAY);
            minutoSys = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
                public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                    txtHora.setText(hourOfDay+":"+minute);
                }
            },hora2,minuto2,false);
            timePickerDialog.show();
        }
    }

    private void colocarFecha(){
        txtFecha.setText(dia2+"/"+(mes2+1)+"/"+ano2);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            dia2 = dayOfMonth;
            mes2 = monthOfYear;
            ano2 = year;
            colocarFecha();
        };
    };

    protected Dialog onCreateDialog(int id) {
        if(id == DATE_ID){
            return new DatePickerDialog(this, mDateSetListener, anoSys, mesSys, diaSys);
        }
        return null;
    }

}