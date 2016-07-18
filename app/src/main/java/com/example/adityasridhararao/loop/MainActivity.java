package com.example.adityasridhararao.loop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.adityasridhararao.loop.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button bLoginGoogle;
    Button bLogin;
    EditText etuserName;
    EditText etpassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = "MainActivity";

    TextView tvRegister;
    TextView tvForgotPasswrod;

    private void Init()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        bLoginGoogle = (Button)findViewById(R.id.bSigninGoogle);
        bLogin = (Button)findViewById(R.id.bLogin);


        etuserName = (EditText)findViewById(R.id.etEmail);
        etpassword = (EditText)findViewById(R.id.etPassword);

        tvRegister = (TextView)findViewById(R.id.tvRegister);
        tvForgotPasswrod = (TextView)findViewById(R.id.tvResetPwd);

        tvRegister.setOnClickListener(this);
        tvForgotPasswrod.setOnClickListener(this);
        bLoginGoogle.setOnClickListener(this);
        bLogin.setOnClickListener(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }


    // sign in using email id and password
    public void signIn(){
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = etuserName.getText().toString();
        String password = etpassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(MainActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        // startActivity(new Intent(MainActivity.this, MapsActivity.class));
        finish();
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;

        // user name required
        if (TextUtils.isEmpty(etuserName.getText().toString())) {
            etuserName.setError("Required");
            result = false;
        } else {
            etuserName.setError(null);
        }
        // password required
        if (TextUtils.isEmpty(etpassword.getText().toString())) {
            etpassword.setError("Required");
            result = false;
        } else {
            etpassword.setError(null);
        }

        return result;
    }

    @Override
    public void onClick(View v) {




        if(v.getId()==R.id.bLogin)
            signIn();

        if(v.getId()==R.id.tvRegister)
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
}
