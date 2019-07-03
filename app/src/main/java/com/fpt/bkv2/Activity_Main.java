package com.fpt.bkv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

public class Activity_Main extends AppCompatActivity {

    static final int GOOGLE_SIGN_IN = 123;
    FirebaseAuth mAuth;
    Button btn_login, btn_logout;
    TextView text;
    ImageView image;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    private ImageView imageAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textLogin);
        btn_login = findViewById(R.id.btnlogin);
        progressBar = findViewById(R.id.progressBar);
        imageAccount = findViewById(R.id.imageAccount);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder().requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        btn_login.setOnClickListener(v -> signInGoogle());
//        if (savedInstanceState == null) {
////            getSupportFragmentManager().beginTransaction().add(R.id.container, new Fragment_Login()).commit();
//            startActivity(new Intent(this, Activity_Home.class));
//        }

    }

    void signInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
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

    private void fireBaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle: " + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("TAG", "Sign-in successfully");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);

            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("TAG", "Sign-in failure", task.getException());
                Toast.makeText(this, "Sign-in failed", Toast.LENGTH_LONG);
                updateUI(null);
            }


        });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent= new Intent(this, Activity_Home.class);
            Bundle bundle= new Bundle();
            bundle.putString("username", user.getDisplayName());
            bundle.putString("email", user.getEmail());
            bundle.putString("photo", String.valueOf(user.getPhotoUrl()));
            intent.putExtra("userInfo", bundle);
            startActivity(intent);
        } else {

        }
    }

}
