package model;

import java.io.Serializable;

public class Category implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3590794737458558387L;
	private String name;
	private Category upperCategory;
	
	public Category(String name){
		this.name = name;
		this.upperCategory = null;
		
	}
	
	public Category(String name, Category category){
		this.name = name;
		this.upperCategory = category;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getUpperCategory() {
		return upperCategory;
	}

	public void setUpperCategory(Category upperCategory) {
		this.upperCategory = upperCategory;
	}
	
	@Override
	public String toString(){
		if(upperCategory != null){
			return upperCategory + "-" + name;
		}
		return name;
	}

}
