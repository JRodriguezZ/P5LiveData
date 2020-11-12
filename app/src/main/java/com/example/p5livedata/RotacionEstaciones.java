package com.example.p5livedata;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RotacionEstaciones {

    interface RotacionEstacionListener {
        void cuandoCambieElMes(String rotacion);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> rotando;


    LiveData<String> rotacionLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarRotacion(rotacion -> {

                Log.e("ABCD", "ME ha llegado el " + rotacion);
                postValue(rotacion);
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararRotacion();
        }
    };


    void iniciarRotacion(RotacionEstacionListener rotacionEstacionListener) {
        if (rotando == null || rotando.isCancelled()) {
            rotando = scheduler.scheduleAtFixedRate(new Runnable() {
                int estacion = 1;
                int mes = 0;
                @Override
                public void run() {
                    Log.e("ABCD", "Run() oh yead");

                    if(mes == 0){
                        Log.e("ABCD", "Notificando cambio de estacion: " + estacion);
                        rotacionEstacionListener.cuandoCambieElMes("ESTACION" + estacion);
                    } else {
                        Log.e("ABCD", "Notificando cambio de mes: " + mes);
                        rotacionEstacionListener.cuandoCambieElMes("MES" + mes);
                    }
                    mes++;

                    if(mes > 3){
                        estacion++;
                        mes=0;
                    }

                    if(estacion >4){
                        estacion = 1;
                    }
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararRotacion() {
        Log.e("ABCD", "cancelando rotacion");
        if (rotando != null) {
            rotando.cancel(true);
        }
    }
}