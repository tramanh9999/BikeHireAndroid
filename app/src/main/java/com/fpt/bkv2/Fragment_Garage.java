package com.fpt.bkv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.model.Account;
import com.fpt.model.Garage;
import com.fpt.retrofit.APIUtil;
import com.fpt.service.AccountService;
import com.fpt.service.GarageService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo add bike garage
class Fragment_Garage extends Fragment {


    RecyclerView hostBikereRecyclerView;
    Adapter_Bike adapterBike;
    GarageService garageService;

    AccountService accountService;
    Account account;


    EditText edtbalance;
    EditText edtdescription;
    EditText editname;
    EditText edtphone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        garageService = APIUtil.getGarageService();
        account = (Account) getActivity().getIntent().getSerializableExtra("account");

    }

    public void createGarage(View view) {
        Long balance = Long.parseLong(edtbalance.getText().toString());
        String name = editname.getText().toString();
        String phone = edtphone.getText().toString();
        String description = edtdescription.getText().toString();

        //get from map
        long lat = 0;
        long _long = 0;
        String display_location = "";
        //
        Garage garage = new Garage(0, name, Calendar.getInstance().getTime().toString(), phone, description, lat, _long, display_location, balance);

        insertGarage(account.getId(), garage);

    }

    void insertGarage(int account_id, Garage garage) {

        garageService.insertGarage(account_id, garage).enqueue(new Callback<Garage>() {
            @Override
            public void onResponse(Call<Garage> call, Response<Garage> response) {
                Toast.makeText(getContext(), R.string.create_garage_sucessful, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<Garage> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        if (account.getGarage() == null) {
            view = inflater.inflate(R.layout.fragment_add_garage, container, false);
            edtbalance = view.findViewById(R.id.edtGaragaBalance);
            editname = view.findViewById(R.id.edtGarageName);
            edtdescription = view.findViewById(R.id.edtDesc);
            edtphone = view.findViewById(R.id.edtGaragaPhone);


        } else {
            view = inflater.inflate(R.layout.fragment_host_garage, container, false);
        }


        return view;

    }


    public Fragment_Garage() {


    }
}
