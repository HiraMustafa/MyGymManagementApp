package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.project.activities.AdminHomeActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;
    DatePickerDialog datePickerDialog;
    EditText date;
    ImageView imageView;
    public static final int PICK_IMAGE = 123;
    public static final int TAKE_PHOTO = 222;
    public static final int REQUEST_CODE_ASK_PERMISSIONS=1122;
    String currentPhotoPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_extra);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
            }
        },2000);

        /*radioGroup = findViewById(R.id.radio_group);
        radioMale = findViewById(R.id.radio_male);
        radioFemale = findViewById(R.id.radio_female);
        date = findViewById(R.id.etdate);
        imageView = findViewById(R.id.profile_image);
        imageView.setOnClickListener(this);
        date.setOnClickListener(this);
        date.setFocusable(false);
        date.setFocusableInTouchMode(false);
        requestPermission();*/
    }

        /*@Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_image:

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Choose action:");
                    builder.setMessage("Select one of these");
                    builder.setPositiveButton("TAKE PHOTO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dispatchTakePictureIntent();
                        }

                    });
                    builder.setNegativeButton("PICK FROM GALLERY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;

            case R.id.etdate:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        *//*switch (view.getId()) {
            case R.id.radio_female:
                if (checked)
                    break;
            case R.id.radio_male:
                if (checked)
                    break;

        }*//*
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*switch (requestCode) {
            case PICK_IMAGE:

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);
                }

                break;
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    galleryAddPic();
                    setPic();
                }
                break;


        }*/
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {


            }

            if (photoFile != null) {
              Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.housec.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
            }
        }
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}




