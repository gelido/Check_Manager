package controller;

import java.util.List;

import com.tinker.studio.cheque_manager.R;


import model.Entry;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class EntryArrayAdapter extends ArrayAdapter<Entry> {

	private int resourceLayoutID;
	private List<Entry> objects;
	
	public EntryArrayAdapter(Context context, int resource, List<Entry> objects) {
		super(context, resource, objects);
		this.resourceLayoutID = resource;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        EntryHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity) super.getContext()).getLayoutInflater();
            row = inflater.inflate(resourceLayoutID, parent, false);
            
            holder = new EntryHolder();
            holder.tv_category = (TextView) row.findViewById(R.id.tv_category_entry);
            holder.tv_date = (TextView) row.findViewById(R.id.tv_date_entry);
            holder.tv_value = (TextView) row.findViewById(R.id.tv_value_entry);
            holder.cb_isDone = (CheckBox) row.findViewById(R.id.cb_isDone);
            
            row.setTag(holder);
        }
        else
        {
            holder = (EntryHolder) row.getTag();
        }
        
        Entry entry = objects.get(position);
        holder.tv_date.setText(entry.getDateString(getContext()));
        holder.tv_category.setText(entry.getCategory().toString());
        holder.tv_value.setText(String.valueOf(entry.getValue()));
        holder.cb_isDone.setChecked(entry.isDone());
        
        return row;
	}
	
	public static class EntryHolder {
		TextView tv_date, tv_category, tv_value;
		CheckBox cb_isDone;
	}

}
