package com.fpt.bkv2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownLoadImageAsync extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;

        InputStream inputStream = null;
        try {
            inputStream = new URL("https://www.brandsvietnam.com/upload/news/480px/2018/15699_Bia.jpg").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        return mIcon11;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    ImageView imageView;

    public DownLoadImageAsync(ImageView imageView) {
        this.imageView = imageView;
    }
}
