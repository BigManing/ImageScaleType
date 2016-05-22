package com.bigmaning.imagescaletype;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 作者:jiangShang
 * 邮箱:lingshui2008@qq.com
 * 日期:2016/5/21
 * 详情: 分四种情况 看看  scaletype的表现
 *   用户可以自己替换 这两个图片     R.mipmap.width（ 特点是   图片比较宽）      R.mipmap.height （特点是   图片比较高）
 */
public class MainActivity extends AppCompatActivity {
//  这两个大图片    资源id
    public static final int HEIGHT = R.mipmap.height1;
    public static final int WIDTH = R.mipmap.width1;
    //图片的四种的情况
    private String[] titleNames = {"大像素-宽图片", "大像素-高图片", "小像素-宽图片", "小像素-高图片"};
//    ImageView的scaleType的属性有好几种，分别是matrix（默认）、center、centerCrop、centerInside、fitCenter、fitEnd、fitStart、fitXY


    private ImageView imageView, dark;
    private static boolean isFirst = true;
    private int positon = 0;
    private String pathName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(titleNames[0]);
        initView();
        initData(0);
    }

    private void initData(int positon) {
        if (isFirst) return;
        // 原图片
        pathName = getFilesDir().getAbsolutePath() + "/" + titleNames[positon] + ".jpg";
        imageView.setImageBitmap(BitmapFactory.decodeFile(pathName));
        // sacleType类型
    }


    /**
     * 进入到列表页   查看  效果
     *
     * @param view
     */
    public void start(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.IMAGEID, pathName);
        startActivity(intent);


    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.source);
        dark = (ImageView) findViewById(R.id.dark);
        if (!isFirst) {
            return;
        }

        //  初始化  需要的四个图片
        dark.post(new Runnable() {
            @Override
            public void run() {
                int size = (int) (dark.getWidth() * 2f); //     比组件大的两个图片
                resizeWidthPic(imageView, titleNames[0] + ".jpg", size);
                resizeheightPic(imageView, titleNames[1] + ".jpg", size);

                size = (int) (dark.getWidth() *0.6f);  // 比组件小的两个图片

                resizeWidthPic(imageView, titleNames[2] + ".jpg", size);
                resizeheightPic(imageView, titleNames[3] + ".jpg", size);
                isFirst = false;
                initData(0);
            }
        });
    }

    private void resizeWidthPic(ImageView imageView, String fileName, int sampleSize) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), WIDTH, opts);
        opts.inSampleSize = opts.outWidth / sampleSize;
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), WIDTH, opts);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), fileName));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void resizeheightPic(ImageView imageView, String fileName, int sampleSize) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), HEIGHT, opts);
        opts.inSampleSize = opts.outHeight/ sampleSize;
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), HEIGHT, opts);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(getFilesDir(), fileName));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.type, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.big_width:
                setTitle(titleNames[0]);
                positon = 0;
                break;
            case R.id.big_height:
                setTitle(titleNames[1]);
                positon = 1;
                break;
            case R.id.small_width:
                setTitle(titleNames[2]);
                positon = 2;
                break;
            case R.id.small_height:
                setTitle(titleNames[3]);
                positon = 3;
                break;
        }
        initData(positon);
        return super.onOptionsItemSelected(item);
    }
}
