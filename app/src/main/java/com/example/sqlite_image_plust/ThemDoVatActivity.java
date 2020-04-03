package com.example.sqlite_image_plust;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemDoVatActivity extends AppCompatActivity implements View.OnClickListener {

    Button btAdd,btHuy;
    EditText edtName,edtMoTa;
    ImageButton imagbtCamera,imgbtFolder;
    ImageView imgHinh;
    final int REQUEST_CODE_CAMERA = 1;
    final int REQUEST_CODE_FOLDER = 2;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_vat);
        AnhXa();
        imagbtCamera.setOnClickListener(this::onClick);
        imgbtFolder.setOnClickListener(this::onClick);
        btAdd.setOnClickListener((v) -> {
                //chuyen data image view sang mang byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinh.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinh = byteArrayOutputStream.toByteArray();
                MainActivity.database.insert_DoVat(
                        edtName.getText().toString().trim(),
                        edtMoTa.getText().toString().trim(),
                        hinh
                        );
                Toast.makeText(this,"Đã Thêm",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
        });
        btHuy.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btHuy:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.imbtCamera:
                //android cao trở lên 7.0 lên
                ActivityCompat.requestPermissions(ThemDoVatActivity.this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
                break;
            case R.id.imbtFolder:
                ActivityCompat.requestPermissions(ThemDoVatActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER);
                break;
        }
    }
    private void AnhXa(){
        btAdd = findViewById(R.id.btAdd);
        edtName = findViewById(R.id.edtName);
        edtMoTa = findViewById(R.id.edtMoTa);
        imgHinh = findViewById(R.id.imgHinh);
        btHuy = findViewById(R.id.btHuy);
        imagbtCamera = findViewById(R.id.imbtCamera);
        imgbtFolder = findViewById(R.id.imbtFolder);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE_CAMERA);
                }
                else{
                    Toast.makeText(this,"Bạn Không Cho Phép Mở Camera",Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,REQUEST_CODE_FOLDER);
                }
                else{
                    Toast.makeText(this,"Bạn Không Cho Phép Mở Thư Viện",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data!=null){
            Uri uri= data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
