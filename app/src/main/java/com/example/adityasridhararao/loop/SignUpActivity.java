package com.example.adityasridhararao.loop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

;
import com.example.adityasridhararao.loop.models.User;
import com.example.adityasridhararao.loop.utilsJava.ListnerOnTextChange;
import com.example.adityasridhararao.loop.utilsJava.RoundedImageView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "SignUpActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_COMP_ID = 2;
    //firebase code.. databased declaration
    private DatabaseReference mDatabase;
    //firebase code.. authentication declaration
    private FirebaseAuth mAuth;



    private EditText etName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhone;
    private Spinner spCompany;

    private ImageView ivURPhoto;
    private ImageView ivCompanyId;

    private TextView tvName;
    private TextView tvPassword;
    private TextView tvEmail;
    private TextView tvPhone;
    private boolean bPhotoSet;
    private boolean bCompanyIdSet;

    private String Company;

    private Button bRegister ;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public void Init() {

        bPhotoSet = false;
        bCompanyIdSet = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPasswordReg);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);


        spCompany = (Spinner)findViewById(R.id.spCompany);
        spCompany.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Companies, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCompany.setAdapter(adapter);

        bRegister =(Button)findViewById(R.id.bRegister);


        tvName = (TextView) findViewById(R.id.tvName);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPhone = (TextView) findViewById(R.id.tvPhone);

        etName.setOnFocusChangeListener(new ListnerOnTextChange(getApplicationContext(), etName, tvName));
        etPassword.setOnFocusChangeListener(new ListnerOnTextChange(getApplicationContext(), etPassword, tvPassword));
        etEmail.setOnFocusChangeListener(new ListnerOnTextChange(getApplicationContext(), etEmail, tvEmail));
        etPhone.setOnFocusChangeListener(new ListnerOnTextChange(getApplicationContext(), etPhone, tvPhone));



        ivURPhoto = (ImageView) findViewById(R.id.ivURPhoto);
        ivCompanyId = (ImageView) findViewById(R.id.ivCompanyId);
        ivURPhoto.setOnClickListener(this);
        ivCompanyId.setOnClickListener(this);
        bRegister.setOnClickListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Init();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } //end onCreate


    private void signUp() {
        Log.d(TAG, "Entered to Signup");
        if (!validateForm()) {
            return;
        }

        //method definition in baseactivity class
        showProgressDialog();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }// end of signUp Method


    private boolean validateForm() {
        boolean result = true;

        // user name required
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            result = false;
        } else {
            etEmail.setError(null);
        }


        // password required
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Required");
            result = false;
        } else {
            etPassword.setError(null);
        }

        // First Name required
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Required");
            result = false;
        } else {
            etName.setError(null);
        }




        // Phone number required
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            etPhone.setError("Required");
            result = false;
        } else {
            etPhone.setError(null);
        }

        //Photo Required

        if (bCompanyIdSet||bPhotoSet) {
            Toast.makeText(getApplicationContext(), "Please click your photo and company Id", Toast.LENGTH_LONG).show();
            result = false;
        } else {
            result = true;
        }

        //Company Required
        if (TextUtils.isEmpty(Company)) {
            Toast.makeText(getApplicationContext(), "Please select your company", Toast.LENGTH_LONG).show();
            result = false;
        } else {
            result = true;
        }

        return result;

    }// end of validate form




    private void onAuthSuccess(FirebaseUser user) {
        String Name = etName.getText().toString();

        // Write new user
        writeNewUser(user.getUid(), Name, user.getEmail());

        // Go to MainActivity
       // startActivity(new Intent(SignUpActivity.this, MapsActivity.class));
        finish();
    }


    private void writeNewUser(String userId, String name, String email) {
        String Name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String company = Company;



        User user = new User(name, email);


        mDatabase.child("users").child(userId).setValue(user);
    }

    // fetch the user name from email

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    private void signUpwithGoogle() {

    }


    //onclick of the button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                signUp();
                break;
            case R.id.ivURPhoto:
                startCamera(ivURPhoto);
                break;
        }
    }

    private void startCamera(ImageView imageview) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            if(imageview.getId()== R.id.ivURPhoto)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            if(imageview.getId()==R.id.ivCompanyId)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_COMP_ID);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(imageBitmap,100,100);
            imageBitmap = RoundedImageView.getRoundedCroppedBitmap(thumbnailBitmap,36);
            ivURPhoto.setImageBitmap(imageBitmap);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE_COMP_ID && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivCompanyId.setImageBitmap(imageBitmap);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Company = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
