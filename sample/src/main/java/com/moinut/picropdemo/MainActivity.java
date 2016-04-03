package com.moinut.picropdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.moinut.picrop.OnCropListener;
import com.moinut.picrop.PiCrop;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog mDialog;
    private Button mAlbumBtn;
    private ImageView mImageView;
    private PiCrop piCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        piCrop = new PiCrop(this);

        mDialog = new AlertDialog.Builder(this).setTitle("Loading").setMessage("Please wait").create();
        mAlbumBtn = (Button) findViewById(R.id.btn_crop_from_album);
        mImageView = (ImageView) findViewById(R.id.iv_show_pic);
        mAlbumBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crop_from_album:
                piCrop.get(PiCrop.FROM_ALBUM, new OnCropListener() {
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
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        piCrop.onActivityResult(requestCode, resultCode, data);
    }
}
