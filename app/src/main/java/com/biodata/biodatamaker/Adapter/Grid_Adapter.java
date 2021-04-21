package com.biodata.biodatamaker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biodata.biodatamaker.Activity.TemplateActivity;
import com.biodata.biodatamaker.R;

public class Grid_Adapter extends BaseAdapter {
    private Context mContext;
   /* private final String[] web;*/
    private final int[] Imageid;

    public Grid_Adapter(Context c, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        /*this.web = web;*/
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Imageid.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return Imageid.length;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.listdesign_griditems, null);
            TextView textView = grid.findViewById(R.id.grid_text);
            ImageView imageView = grid.findViewById(R.id.grid_image);
            textView.setText(TemplateActivity.web[position]);
            imageView.setImageResource(Imageid[position]);
        } else {
            grid = convertView;
        }

        return grid;
    }
}