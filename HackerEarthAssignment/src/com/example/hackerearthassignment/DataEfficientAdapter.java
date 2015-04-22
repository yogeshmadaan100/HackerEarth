package com.example.hackerearthassignment;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class DataEfficientAdapter extends BaseAdapter {
	
    private LayoutInflater mInflater;
    private List<DataModel> mDataList;
    private static final int HIGHLIGHT_COLOR = 0x999be6ff;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
   
    private Context mContext;
    
    public DataEfficientAdapter(Context context,List<DataModel>mDataList) {
        mContext=context;
    	mInflater = LayoutInflater.from(context);
        this.mDataList=mDataList;
        mDrawableBuilder=TextDrawable.builder().round();
        
    }

    public int getCount() {
        return mDataList.size();
    }
    
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.enterance_list_item, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.info=(TextView)convertView.findViewById(R.id.info);
            holder.view=convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        System.out.println(mDataList.get(position).getTitle());
        holder.title.setText(mDataList.get(position).getTitle());
        holder.info.setText(mDataList.get(position).getDescription());
       
        	Log.e("temp", ""+String.valueOf(mDataList.get(position).getTitle().charAt(0)));
        TextDrawable drawable = mDrawableBuilder.build(String.valueOf(mDataList.get(position).getTitle().charAt(0)), mColorGenerator.getColor(mDataList.get(position).getTitle()));
        holder.image.setImageDrawable(drawable);
        holder.view.setBackgroundColor(Color.TRANSPARENT);
        
        holder.view.setBackgroundColor(HIGHLIGHT_COLOR);
        
      
        return convertView;
    }
    
    class ViewHolder {
        TextView title;
        TextView info;
        ImageView image;
        View view;
    }	
    
}	
