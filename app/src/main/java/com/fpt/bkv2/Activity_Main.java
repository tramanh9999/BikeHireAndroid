package com.fpt.bkv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.model.Account;
import com.fpt.retrofit.APIUtil;
import com.fpt.service.AccountService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Main extends AppCompatActivity {

    static final int GOOGLE_SIGN_IN = 123;
    FirebaseAuth mAuth;
    Button btn_login, btn_logout;
    TextView text;
    ImageView image;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    private ImageView imageAccount;

    AccountService accountService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textLogin);
        btn_login = findViewById(R.id.btnlogin);
        progressBar = findViewById(R.id.progressBar);
        imageAccount = findViewById(R.id.imageAccount);
        //create account service
        accountService = APIUtil.getAccountService();
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder().requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        btn_login.setOnClickListener(v -> signInGoogle());
//        if (savedInstanceState == null) {
//            startActivity(new Intent(this, Activity_Home.class));
//        }
    }

    void signInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        //create push up box show available google accounts
        Intent intent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(intent, GOOGLE_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    fireBaseAuthWithGoogle(account);
                    // mAKE request firebase
                }
            } catch (ApiException ae) {
                ae.printStackTrace();
            }
        }
    }

    //register account in firebase
    private void fireBaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());
        //get account token id from firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("TAG", "Sign-in successfully");
                FirebaseUser user = mAuth.getCurrentUser();

//                if(accountService.getAccountByEmail(user.getEmail())== null){
//                    account.
//                };
//add new user in api db
                enterApp(user);

            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("TAG", "Sign-in failure", task.getException());
                Toast.makeText(this, "Sign-in failed", Toast.LENGTH_LONG);
                enterApp(null);
            }
        });
    }


    private void enterApp(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, Activity_Home.class);
            Bundle bundle = new Bundle();

            Account insertAccount = new Account();
            insertAccount.setEmail(user.getEmail());
            insertAccount.setUsername(user.getDisplayName());
            insertAccount.setPhone(user.getPhoneNumber());
            insertAccount.setAvatar(String.valueOf(user.getPhotoUrl()));
            insertAccount.setBalance(1000000L);
            insertAccount.setGarage(null);
            insertAccount.setId(0);
            AccountService accountService = APIUtil.getAccountService();
            Call<Account> accountCall = accountService.getAccountByEmail(insertAccount.getEmail());
            //get Account from server db
            //if not exist then insert
            // then take the new acount had been set init values into bundle
            //else take the response to bundle and start HomeActivity
            accountCall.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {


                    Account responseAccount = response.body();
                    if (responseAccount == null) {
                       Call<Account> accountCall1= accountService.insert(insertAccount);
                        accountCall1.enqueue(new Callback<Account>() {
                            @Override
                            public void onResponse(Call<Account> call, Response<Account> response) {
//                                Toast.makeText(getApplicationContext(), "Register successfully!", Toast.LENGTH_LONG);
                                if (response.code() == 201) {
                                    bundle.putSerializable("user", insertAccount);
                                    intent.putExtra("accountBundle", bundle);
                                    startActivity(intent);
                                }else{
                                    System.out.println("Register failed::"+ response.message());
                                }
                            }
                            @Override
                            public void onFailure(Call<Account> call, Throwable t) {
                                System.out.println("Register failed");
                            }
                        });
                    } else {
                        bundle.putSerializable("account", responseAccount);
                        intent.putExtra("accountBundle", bundle);
                        startActivity(intent);
                    }


                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {

                }
            });


        }
    }

}
