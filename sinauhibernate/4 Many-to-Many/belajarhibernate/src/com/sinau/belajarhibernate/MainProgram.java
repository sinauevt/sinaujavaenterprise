package com.sinau.belajarhibernate;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

public class MainProgram {
	public static void main(String[] args) {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();
		
		Stock stock = new Stock();
        stock.setStockCode("7052");
        stock.setStockName("PADINI");
 
        Category category1 = new Category("CONSUMER", "CONSUMER COMPANY");
        Category category2 = new Category("INVESTMENT", "INVESTMENT COMPANY");
    
        Set<Category> categories = new HashSet<Category>();
        categories.add(category1);
        categories.add(category2);
        
        stock.setCategories(categories);
        
        session.save(stock);
		
		session.getTransaction().commit();
		
		session.beginTransaction();
		
		Stock getStock = (Stock) session.get(Stock.class, 1);
		System.out.println(getStock.getStockId());
		System.out.println(getStock.getStockCode());
		System.out.println(getStock.getStockName());
		for(Category category : getStock.getCategories()) {
			System.out.println(category.getName() + " " + category.getDesc());
		}
		
		session.getTransaction().commit();
		
		session.close();
		HibernateUtilities.getSessionFactory().close();
	}
}
