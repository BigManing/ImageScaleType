package com.bigmaning.imagescaletype;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *  列表界面
 */
public class ResultActivity extends AppCompatActivity {

    public static final String IMAGEID = "imageId";
    private ImageView.ScaleType[] scaleType;
    private ArrayList<String> typeNames;
    private GridView gv;
    private String pathName;//  文件路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        gv = (GridView) findViewById(R.id.lv);
        pathName=getIntent().getStringExtra(IMAGEID);


        scaleType = ImageView.ScaleType.values();
        typeNames = new ArrayList<>();
        for (ImageView.ScaleType value : scaleType) {
            typeNames.add(value + "");
        }
        gv.setNumColumns(2);
        gv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return typeNames.size();
            }

            @Override
            public Object getItem(int position) {
                return typeNames.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v;
                if (convertView == null) {
                    v = View.inflate(getBaseContext(), R.layout.item, null);
                } else {
                    v = convertView;
                }
//                    View view = super.getView(position, convertView, parent);
                ImageView iv = (ImageView) v.findViewById(R.id.iv);
                iv.setImageBitmap(BitmapFactory.decodeFile(pathName));
//                    iv.setImageResource(((int) data.get(0).get("id")));
                iv.setScaleType(scaleType[position]);

                TextView tv = (TextView) v.findViewById(R.id.tv);

                tv.setText(typeNames.get(position).toLowerCase());
                return v;
            }
        });
    }
}
