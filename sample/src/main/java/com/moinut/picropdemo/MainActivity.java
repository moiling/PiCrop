package com.moinut.picropdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.moinut.picrop.PiCrop;
import com.moinut.picrop.callback.OnCropListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog mDialog;
    private Button mAlbumBtn;
    private Button mCameraBtn;
    private ImageView mImageView;
    private PiCrop piCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StoragePermissions.verifyStoragePermissions(this);
        }

        piCrop = new PiCrop(this);

        mDialog = new AlertDialog.Builder(this).setTitle("Loading").setMessage("Please wait").create();
        mAlbumBtn = (Button) findViewById(R.id.btn_crop_from_album);
        mCameraBtn = (Button) findViewById(R.id.btn_crop_from_camera);
        mImageView = (ImageView) findViewById(R.id.iv_show_pic);
        mAlbumBtn.setOnClickListener(this);
        mCameraBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int type = PiCrop.FROM_ALBUM;
        switch (v.getId()) {
            case R.id.btn_crop_from_album:
                type = PiCrop.FROM_ALBUM;
                break;
            case R.id.btn_crop_from_camera:
                type = PiCrop.FROM_CAMERA;
                break;
        }

        piCrop.get(type, new OnCropListener() {
            @Override
            public void onStart() {
                mDialog.show();
            }

            @Override
            public void onSuccess(Uri picUri) {
                mImageView.setImageDrawable(null);
                mDialog.dismiss();
                mImageView.setImageURI(picUri);
            }

            @Override
            public void onError(String errorMsg) {
                mDialog.dismiss();
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        piCrop.onActivityResult(requestCode, resultCode, data);
    }

}
