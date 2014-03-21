package com.example.achartenginedynamicchart;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
 
public class CustomListViewAdapter extends ArrayAdapter<RowItem> {
 
    Context context;
 
    public CustomListViewAdapter(Context context, int resourceId,
            List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        ImageButton imageView;
        TextView txtTitle;
        TextView txtDesc;
        Button btnViewECG;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_each_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.lblDB);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageButton) convertView.findViewById(R.id.icon);
            holder.btnViewECG = (Button) convertView.findViewById(R.id.btnViewECG);
            
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
 
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
 
        holder.imageView.setTag(holder.txtTitle);
        holder.btnViewECG.setTag(holder.txtTitle);
        
        return convertView;
    }
}

/*


imageButton = (ImageButton) findViewById(R.id.icon);
 
imageButton.setOnClickListener(new OnClickListener() {

	@Override
	public void onClick(View arg0) {

	   Toast.makeText(getApplicationContext(),
		"ImageButton is clicked!", Toast.LENGTH_SHORT).show();

	}

});*/