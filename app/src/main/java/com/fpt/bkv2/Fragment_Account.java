package com.fpt.bkv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.model.Account;
import com.fpt.sqllite.dao.AccountDAO;
import com.fpt.sqllite.database.AppDatabase;

class Fragment_Account extends Fragment {

    //todo change fragment of homeactity
    public Fragment_Account() {
    }


    Button testDb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        testDb = view.findViewById(R.id.txtmail);
        appDatabase= AppDatabase.getInMemoryDatabase(this.getContext());
        AccountDAO accountDAO = appDatabase.accountDAO();
        Account x = accountDAO.findByEmail(getActivity().getIntent().getBundleExtra("userInfo").getString("email"));

        /*testDb.setText(x.getEmail());*/

        return view;
    }

}
