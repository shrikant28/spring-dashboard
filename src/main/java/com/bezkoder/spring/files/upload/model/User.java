package com.bezkoder.spring.files.upload.model;

public class User {

	String id;
	String name; 
	String progress;
	String color;
	
	
	@Override
	public String toString() {
		return "MyObj [id=" + id + ", name=" + name + ", progress=" + progress + ", color=" + color + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	
}
