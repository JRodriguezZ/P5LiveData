package com.example.p5livedata;

import android.app.Application;

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

        idImagenLiveData = Transformations.switchMap(rotacionEstaciones.rotacionLiveData, new Function<String, LiveData<Integer>>() {
            @Override
            public LiveData<Integer> apply(String input) {
                int imagen;
                if(input.equals("ESTACION1")) {
                    imagen = R.drawable.winter;
                } else if(input.equals("ESTACION2")){
                    imagen = R.drawable.summer;
                } else {
                    return null;
                }

                return new MutableLiveData<>(imagen);
            }
        });
    }
}
