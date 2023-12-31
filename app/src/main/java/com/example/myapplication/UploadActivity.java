package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.DBHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.ModelClass;
import com.example.myapplication.R;

import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    EditText uploadName , uploadEmail;
    ImageView uploadImage;
    Button saveButton;
    private Uri uri;
    private Bitmap bitmapImage;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadEmail = findViewById(R.id.uploadEmail);
        uploadImage = findViewById(R.id.uploadImage);
        uploadName = findViewById(R.id.uploadName);
        saveButton = findViewById(R.id.savebutton);
        dbHelper = new DBHelper(this);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult
                (new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if(result.getResultCode() == Activity.RESULT_OK){
                                    Intent data = result.getData();
                                    assert  data!= null;
                                    uri = data.getData();
                                    try {
                                        bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    }catch (IOException e){
                                        Toast.makeText(UploadActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    uploadImage.setImageBitmap(bitmapImage);
                                }else{
                                    Toast.makeText(UploadActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                }catch(Exception e ){
                    Toast.makeText(UploadActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeImage();
            }
        });
    }
    private void storeImage() {
        String name = uploadName.getText().toString();
        String email = uploadEmail.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && isEmailValid(email) && uploadImage.getDrawable() != null && bitmapImage != null) {
            dbHelper.storeData(new ModelClass(name, email, bitmapImage));
            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Fields are mandatory or email is invalid", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}