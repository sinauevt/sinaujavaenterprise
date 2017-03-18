package com.sinau.belajarhibernate;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;
	private String name;
	private UserData userData = new UserData();
	private List<UserHistory> userHistory = new ArrayList<UserHistory>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserData getUserData() {
		return userData;
	}
	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	public List<UserHistory> getUserHistory() {
		return userHistory;
	}
	public void setUserHistory(List<UserHistory> userHistory) {
		this.userHistory = userHistory;
	}
	public void addHistory(UserHistory historyItem) {
		historyItem.setUser(this);
		userHistory.add(historyItem);
	}
}
