package com.viddup.openglnative;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.viddup.openglnative.render.MyRender;
import com.viddup.openglnative.render.NativeRender;

import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_FORMAT_RGBA = 0x01;
    public static final int IMAGE_FORMAT_NV21 = 0x02;
    public static final int IMAGE_FORMAT_NV12 = 0x03;
    public static final int IMAGE_FORMAT_I420 = 0x04;

    GLSurfaceView surfaceView;
    NativeRender nativeRender = new NativeRender();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.gl_view);
        nativeRender.nInit();
//        nativeRender.setImageData();
        loadImage();

        surfaceView.setEGLContextClientVersion(3);
        surfaceView.setRenderer(new MyRender(nativeRender));

    }

    private void loadImage(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_0);

        int byteCount = bitmap.getByteCount();
        ByteBuffer buf = ByteBuffer.allocate(byteCount);
        bitmap.copyPixelsToBuffer(buf);
        byte[] array = buf.array();

        nativeRender.setImageData(IMAGE_FORMAT_RGBA,bitmap.getWidth(),bitmap.getHeight(),array);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nativeRender.nUnInit();
    }
}