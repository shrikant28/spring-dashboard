package com.bezkoder.spring.files.upload.model;

import java.util.List;

public class FinalResponse {

	private List<User> user;

	public List<User> getMyObjList() {
		return user;
	}

	public void setMyObjList(List<User> myObjList) {
		this.user = myObjList;
	}

	@Override
	public String toString() {
		return "FinalResponse [myObjList=" + user + "]";
	}
	
	
}
