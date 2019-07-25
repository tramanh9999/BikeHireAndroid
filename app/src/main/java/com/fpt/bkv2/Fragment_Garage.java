package com.fpt.bkv2;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.model.Account;
import com.fpt.model.Garage;
import com.fpt.retrofit.APIUtil;
import com.fpt.service.AccountService;
import com.fpt.service.BikeService;
import com.fpt.service.GarageService;
import com.fpt.sqllite.dao.AccountDAO;
import com.fpt.sqllite.database.AppDatabase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo add bike garage
class Fragment_Garage extends Fragment {


    RecyclerView hostBikereRecyclerView;
    Adapter_Bike adapterBike;
    GarageService garageService;

    AccountService accountService;
    Account account;


    EditText edtbalance;
    EditText edtdescription;
    EditText editname;
    EditText edtphone;
    AppDatabase appDatabase;

    AccountDAO accountDAO;
    private Button btnImage;
    ImageView imageview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        garageService = APIUtil.getGarageService();

        account = ((Activity_Home) getActivity()).account;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        if (account.getGarage() == null) {
            view = inflater.inflate(R.layout.fragment_add_garage, container, false);
            edtbalance = view.findViewById(R.id.edtGaragaBalance);
            editname = view.findViewById(R.id.edtGarageName);
            edtdescription = view.findViewById(R.id.edtDesc);
            edtphone = view.findViewById(R.id.edtGaragaPhone);
            chooseLocationbt = view.findViewById(R.id.chooseLocbt);
            addGarage = view.findViewById(R.id.btnAddGarage);

//            chooseLocationbt.setOnClickListener(v -> chooseLocation());

            addGarage.setOnClickListener(v -> createGarage());

            btnImage = view.findViewById(R.id.btnGarageImg);
            imageview = view.findViewById(R.id.imgView);
            btnImage.setOnClickListener(v -> showPictureDialog());
        } else {
            view = inflater.inflate(R.layout.fragment_host_garage, container, false);

            btCreateBike = view.findViewById(R.id.btCreateBike);

            btCreateBike.setOnClickListener(v -> insertBike());

        }
        return view;

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    Button btCreateBike;
    BikeService bikeService = APIUtil.getBikeService();

    void insertBike() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_home, new Fragment_AddBike());
    }


    void insertGarage(int account_id, Garage garage) {

        garageService.insertGarage(account_id, garage).enqueue(new Callback<Garage>() {
            @Override
            public void onResponse(Call<Garage> call, Response<Garage> response) {
                Toast.makeText(getContext(), R.string.create_garage_sucessful, Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_home, new Fragment_Garage());


            }

            @Override
            public void onFailure(Call<Garage> call, Throwable t) {

            }
        });
    }


    Button chooseLocationbt;
    Button addGarage;


    public void createGarage() {
        Long balance = Long.parseLong(edtbalance.getText().toString());
        String name = editname.getText().toString();
        String phone = edtphone.getText().toString();
        String description = edtdescription.getText().toString();
        //get from map
        long lat = 0;
        long _long = 0;
        String display_location = "";


        //upload to file image server . return short link
//        insert link to imagetable  database api


        garage = new Garage(0, name, Calendar.getInstance().getTime().toString(), phone, description, lat, _long, display_location, balance);



        insertGarage(account.getId(), garage);
    }

    Garage garage;

//    void chooseLocation() {
//        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
//        try {
//            startActivityForResult(intentBuilder.build(getActivity()), PLACE_PICKER_REQUEST);
//        } catch (GooglePlayServicesRepairableException e) {
//            e.printStackTrace();
//        } catch (GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
//
//    }

    int RESULT_OK = 2;
    int PLACE_PICKER_REQUEST = 4;

    private int GALLERY = 1, CAMERA = 2;

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    TextView txtdisplayloc;
    int RESULT_CANCELED = 0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, getContext());
//                String toastMsg = String.format("Place: %s", place.getName());
//
//                txtdisplayloc.setText("lat:" + place.getLatLng().latitude + "\nlong:" + place.getLatLng().longitude + "\n" + place.getAddress());
//                garage.setLatitude(place.getLatLng().latitude);
//                garage.setLongitude(place.getLatLng().longitude);
//                Toast.makeText(this.getContext(), toastMsg, Toast.LENGTH_LONG).show();
//            }
//        }

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();


//                    imageview.setImageBitmap(bitmap);
                    base64image=encodeBitMap(bitmap);
                    Glide.with(getContext()).load(contentURI).into(imageview);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
            base64image=encodeBitMap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    String base64image;

    // Encode bitmap to String
    public String encodeBitMap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    String IMAGE_DIRECTORY = "bikehireImage/";

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public Fragment_Garage() {


    }
}
