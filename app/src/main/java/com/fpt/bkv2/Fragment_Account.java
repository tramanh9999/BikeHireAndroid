package com.fpt.bkv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.model.Account;
import com.fpt.retrofit.APIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Fragment_Account extends Fragment {

    //todo change fragment of homeactity
    public Fragment_Account() {
    }


    String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getBundleExtra("userInfo");
        email = bundle.getString("email");

        Call<Account> account = APIUtil.getAccountService().getAccountByEmail(email);

        account.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
//                response.body()
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

}
