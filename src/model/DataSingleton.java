package model;

import java.util.ArrayList;

import com.tinker.studio.cheque_manager.R;



public class DataSingleton {
	
	private static final DataSingleton instance = new DataSingleton();
	
	public static DataSingleton getInstance(){
		return instance;
	}

	private ArrayList<Entry> entries;
	private ArrayList<Category> categories;
	
	public DataSingleton(){
		this.entries = new ArrayList<Entry>();
		this.categories = new ArrayList<Category>();
		this.categories.add(new Category(" "));
	}
	
	public void addEntry(Entry entry){
		entries.add(entry);
	}

	public void removeEntry(Entry entry){
		entries.remove(entry);
	}
	
	public void removeEntry(int index){
		entries.remove(index);
	}
	
	public ArrayList<Entry> getEntries(){
		return entries;
	}
	
	public void addCategory(Category category){
		categories.add(category);
	}
	
	public void removeCategory(Category category){
		categories.remove(category);
	}
	
	public void removeCategory(int index){
		categories.remove(index);
	}
	
	public ArrayList<Category> getCategories(){
		return categories;
	}
}
