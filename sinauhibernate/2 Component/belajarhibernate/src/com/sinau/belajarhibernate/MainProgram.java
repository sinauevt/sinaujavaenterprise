package com.sinau.belajarhibernate;

import org.hibernate.Session;

public class MainProgram {
	public static void main(String[] args) {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();
		
		User user = new User();
		user.setId(2);
		user.setName("Joni");
		user.getUserData().setGoal(1500);
		user.getUserData().setTotal(2000);
		session.save(user);		
		session.getTransaction().commit();
		
		session.beginTransaction();
		
		User getUser = (User) session.get(User.class, 1);
		System.out.println(getUser.getName());
		System.out.println(getUser.getUserData().getGoal());
		System.out.println(getUser.getUserData().getTotal());
		
		session.getTransaction().commit();
		
		session.close();
		HibernateUtilities.getSessionFactory().close();
	}
}
