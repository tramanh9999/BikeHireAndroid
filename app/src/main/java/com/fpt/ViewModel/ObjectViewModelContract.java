package com.fpt.ViewModel;

import android.content.Context;

import com.fpt.model.Bike;

import java.util.List;

public interface ObjectViewModelContract {


    interface mainView{
        void loadBike(List< Bike > bikeList);
        Context getContext();
    }

        interface  ViewModel {
            void destroy();
        }
}
