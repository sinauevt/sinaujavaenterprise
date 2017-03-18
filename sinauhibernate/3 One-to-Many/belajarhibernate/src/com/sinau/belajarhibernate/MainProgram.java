package com.sinau.belajarhibernate;

import java.util.Date;

import org.hibernate.Session;

public class MainProgram {
	public static void main(String[] args) {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();
		
		User user = new User();
		user.setName("Donna");
		user.addHistory(new UserHistory(new Date(), "Set Nama Donna"));
		user.getUserData().setGoal(1000);
		user.addHistory(new UserHistory(new Date(), "Set Goal 1000"));
		user.getUserData().setTotal(500);
		user.addHistory(new UserHistory(new Date(), "Set Total 500"));
		session.save(user);
		session.getTransaction().commit();
		
		session.beginTransaction();
		
		User getUser = (User) session.get(User.class, 1);
		System.out.println(getUser.getName());
		System.out.println(getUser.getUserData().getGoal());
		System.out.println(getUser.getUserData().getTotal());
		for(UserHistory history : getUser.getUserHistory()) {
			System.out.println(history.getEntryTime().toString() + " " + history.getEntry());
		}
		
		session.getTransaction().commit();
		
		session.close();
		HibernateUtilities.getSessionFactory().close();
	}
}
