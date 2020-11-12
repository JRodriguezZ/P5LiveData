package com.example.p5livedata;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class EstacionesViewModel extends AndroidViewModel {
    RotacionEstaciones rotacionEstaciones;

    LiveData<Integer> idImagenLiveData;

    public EstacionesViewModel(@NonNull Application application) {
        super(application);

        rotacionEstaciones = new RotacionEstaciones();

        idImagenLiveData = Transformations.switchMap(rotacionEstaciones.rotacionLiveData, input -> {

            Log.e("ABCD", "RECIBIDO " + input + "... conviernd en imagen");
            int imagen;
            switch (input) {
                case "ESTACION1":
                    imagen = R.drawable.winter;
                    break;
                case "ESTACION2":
                    imagen = R.drawable.spring;
                    break;
                case "ESTACION3":
                    imagen = R.drawable.summer;
                    break;
                case "ESTACION4":
                    imagen = R.drawable.autumn;
                    break;
                default:
                    return null;
            }
            Log.e("ABCD", "La imagen es " + imagen);
            return new MutableLiveData<>(imagen);
        });
    }
    LiveData<Integer> obtenerEstacion(){
        return idImagenLiveData;
    }
}
