//package com.fpt.bkv2;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.media.MediaScannerConnection;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Locale;
//
//public class AddBikeActivity extends AppCompatActivity {
//
//    private ImageView imgBike;
//    private static final String IMAGE_DIRECTORY = "/demonuts";
//    private int GALLERY = 1, CAMERA = 2;
//    private Button btnBikeImage;
//    private Button btnGetBikeLocation;
//    private EditText edtBikeLocation;
//    LocationManager locationManager;
//    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
//    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.);
//
//        requestMultiplePermissions();
//        edtBikeLocation = findViewById(R.id.edtBikeLocation);
//        btnGetBikeLocation = (Button) findViewById(R.id.btnGetBikeLocation);
//        btnBikeImage = (Button) findViewById(R.id.btnBikeImg);
//        imgBike = (ImageView) findViewById(R.id.imgBike);
//
//        btnBikeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPictureDialog();
//            }
//        });
//        btnGetBikeLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
//                } else {
//                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    try {
//                        String city = hereLocation(location.getLatitude(), location.getLongitude());
//                        edtBikeLocation.setText(city);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                        Toast.makeText(AddBikeActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] premissions, @NonNull int[] grantResults){
//        super.onRequestPermissionsResult(requestCode, premissions, grantResults);
//        switch (requestCode){
//            case 1000:{
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    try {
//                        String city = hereLocation(location.getLatitude(), location.getLongitude());
//                        edtBikeLocation.setText(city);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                        Toast.makeText(AddBikeActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(this, "Premission not granted!", Toast.LENGTH_LONG).show();
//                }
//                break;
//            }
//        }
//    }
//
//    private String hereLocation(double lat, double lon){
//        String cityName = "";
//
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses;
//        try {
//            addresses = geocoder.getFromLocation(lat, lon, 10);
//            if(addresses.size() > 0){
//                for (Address adr: addresses){
//                    if(adr.getLocality() != null && adr.getLocality() .length() > 0){
//                        cityName = adr.getLocality() + ", " + adr.getSubAdminArea() + ", " + adr.getAdminArea() + " / " +  adr.getLatitude() + " - " + adr.getLongitude();
//                        break;
//                    }
//                }
//            }
//        } catch (IOException ex){
//            ex.printStackTrace();
//        }
//        return cityName;
//    }
//
//
//
//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Select photo from gallery",
//                "Capture photo from camera"};
//        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0:
//                        choosePhotoFromGallary();
//                        break;
//                    case 1:
//                        takePhotoFromCamera();
//                        break;
//                }
//            }
//        });
//        pictureDialog.show();
//    }
//
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, GALLERY);
//    }
//
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == this.RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(AddBikeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    imgBike.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(AddBikeActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            imgBike.setImageBitmap(thumbnail);
//            saveImage(thumbnail);
//            Toast.makeText(AddBikeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public String saveImage(Bitmap myBitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance()
//                    .getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }
//
//    private void requestMultiplePermissions() {
//        Dexter.withActivity(this)
//                .withPermissions(
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).withErrorListener(new PermissionRequestErrorListener() {
//            @Override
//            public void onError(DexterError error) {
//                Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
//            }
//        }).onSameThread()
//                .check();
//
//    }
//}
