package com.example.p5livedata;

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

            iniciarRotacion(new RotacionEstacionListener() {
                @Override
                public void cuandoCambieElMes(String rotacion) {
                    setValue(rotacion);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararEntrenamiento();
        }
    };


    void iniciarRotacion(RotacionEstacionListener rotacionEstacionListener) {
        if (rotando == null || rotando.isCancelled()) {
            rotando = scheduler.scheduleAtFixedRate(new Runnable() {
                int estacion = 1;
                int mes = 0;
                @Override
                public void run() {
                    if(mes == 0){
                        rotacionEstacionListener.cuandoCambieElMes("ESTACION " + estacion);
                    } else {
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

    void pararEntrenamiento() {
        if (rotando != null) {
            rotando.cancel(true);
        }
    }
}


/*
ESTACION1    MES0
MES1
MES2
MES3
ESTACION2   MES0
MES1
MES2
MES3
ESTACION3     MES0
MES1
MES2
MES3
ESTACION4     MES0
MES1
MES2
MES3

 */

/*
for () {
       sout("");
}

 */