package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateFormat;

public class Entry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7022873875502565754L;
	
	private Date dueDate;
	private float value;
	private Category category;
	private boolean isDone;
	private String description;
	
	public Entry(Date dueDate, float value, Category category, boolean done, String description){
		this.dueDate= dueDate;
		this.value = value;
		this.category = category;
		this.isDone = done;
		this.setDescription(description);
	}
	
	public Entry(Date dueDate, float value, Category category){
		this.dueDate= dueDate;
		this.value = value;
		this.category = category;
		this.isDone = false;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getDateString(Context context){
		java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
		return dateFormat.format(dueDate);
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
