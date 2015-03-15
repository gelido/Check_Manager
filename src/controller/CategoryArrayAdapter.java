package controller;

import java.util.List;

import model.Category;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tinker.studio.cheque_manager.R;

public class CategoryArrayAdapter extends ArrayAdapter<Category> implements SpinnerAdapter{

	private int resourceLayoutID;
	private List<Category> objects;
	
	public CategoryArrayAdapter(Context context, int resource,
			List<Category> objects) {
		super(context, resource, objects);
		this.resourceLayoutID = resource;
		this.objects = objects;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        CategoryHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity) super.getContext()).getLayoutInflater();
            row = inflater.inflate(resourceLayoutID, parent, false);
            
            holder = new CategoryHolder();
            holder.spinner_category = (TextView) row.findViewById(R.id.spinner_category);
            holder.spinner_upperCategory = (TextView) row.findViewById(R.id.spinner_superCategory);
            
            row.setTag(holder);
        }
        else
        {
            holder = (CategoryHolder) row.getTag();
        }
        
        Category category = objects.get(position);
        holder.spinner_category.setText(category.getName());
        holder.spinner_upperCategory.setText(category.getUpperCategory()==null?"":category.getUpperCategory().getName()); //if it has an upper shows it, if not shows an empty string
        
        return row;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        CategoryHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity) super.getContext()).getLayoutInflater();
            row = inflater.inflate(resourceLayoutID, parent, false);
            
            holder = new CategoryHolder();
            holder.spinner_category = (TextView) row.findViewById(R.id.spinner_category);
            holder.spinner_upperCategory = (TextView) row.findViewById(R.id.spinner_superCategory);
            
            row.setTag(holder);
        }
        else
        {
            holder = (CategoryHolder) row.getTag();
        }
        
        Category category = objects.get(position);
        holder.spinner_category.setText(category.getName());
        holder.spinner_upperCategory.setText(category.getUpperCategory()==null?"":category.getUpperCategory().getName()); //if it has an upper shows it, if not shows an epty string
        
        return row;
	}
	
	
	
	public static class CategoryHolder {
		TextView spinner_category, spinner_upperCategory;
	}


}
