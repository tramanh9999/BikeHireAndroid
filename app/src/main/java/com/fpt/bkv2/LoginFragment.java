package com.fpt.bkv2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

class LoginFragment extends Fragment{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MaterialButton nextBt;
    MaterialButton cancelbt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final TextInputLayout txtname = view.findViewById(R.id.pass_text_input);
        final TextInputEditText editpass = view.findViewById(R.id.editpass);
        nextBt = view.findViewById(R.id.next_button);
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(editpass.getText())) {
                    txtname.setError(getString(R.string.error_password));
                } else {
                    txtname.setError(null);
                    AppCompatActivity activity =(AppCompatActivity)getActivity();
                    InputMethodManager inputManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);

                    // check if no view has focus:
                    View currentFocusedView = activity.getCurrentFocus();
                    if (currentFocusedView != null) {
                        inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    ((NavigationHost)getActivity()).navigateTo(new Left_NavigationFragment(),true);
                }
            }
        });



        editpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                System.out.println(keyEvent.getDisplayLabel());
                if (isPasswordValid(editpass.getText())) {
                    txtname.setError(null); //Clear the error
                }
                return false;
            }
        });
        return view;
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }



}
