# Sinau Spring MVC #

## Spring MVC ##

Spring MVC merupakan sebuah Java framework yang digunakan untuk membuat aplikasi web. Ketika menggunakan Spring MVC kita harus mengikuti aturan yang telah dibuat oleh Spring yaitu harus mengikuti pattern MVC / Model-View-Controller. Yaitu memisahkan antara user-interface dan bagian backend yang mengurusi logic nya.

Untuk mencobanya, pertama-tama kita buat new dynamic web project dari eclipse. Copy semua file jar yang terdapat didalam folder "springmvc-library" kedalam folder "lib".

Ubah file "web.xml" menjadi seperti berikut.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 

http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"

	id="WebApp_ID" version="3.0">
  <display-name>Archetype Created Web Application</display-name>

	<servlet>
		<servlet-name>mvc-dispatcher</servlet-name>
		<servlet-class>
			org.springframework.web.servlet.DispatcherServlet
        </servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>mvc-dispatcher</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

</web-app>
```

Buat file baru didalam folder "WEB-INF" dan beri nama "mvc-dispatcher-servlet.xml". Isi file tersebut dengan kode berikut.

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
        http://www.springframework.org/schema/beans     
        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context-3.0.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd">

	<context:component-scan base-package="com.javacodegeeks.snippets.enterprise" />

	<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix">
			<value>/WEB-INF/</value>
		</property>
		<property name="suffix">
			<value>.jsp</value>
		</property>
	</bean>

</beans>
```

Buat file baru didalam folder "WEB-INF" dan beri nama "helloWorld.jsp". Isi file tersebut dengan kode berikut.

```xml
<html>
<body>
	<h1>Spring 4.0.2 MVC web service</h1>

	<h3>Your Message : ${msg}</h3>
</body>
</html>
```

Buat package baru dengan nama "com.javacodegeeks.snippets.enterprise" didalam folder "src". Buat Java class baru didalamnya dan beri nama "HelloWorldController". Isi file tersebut dengan kode berikut.

```java
package com.javacodegeeks.snippets.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/helloWorld")
public class HelloWorldController {

		@RequestMapping(value = "/hello", method = RequestMethod.GET)
		public String hello(ModelMap model) {
			model.addAttribute("msg", "JCG Hello World!");
			return "helloWorld";
		}

		@RequestMapping(value = "/displayMessage/{msg}", method = RequestMethod.GET)
		public String displayMessage(@PathVariable String msg, ModelMap model) {
			model.addAttribute("msg", msg);
			return "helloWorld";
		}
	}
```

Add new tomcat server kedalam eclipse.

Untuk menjalankannya klik kanan, run as run on server.

Buka di browser alamat berikut: http://localhost:8080/belajarspring/helloWorld/hello.
Untuk memodifikasi message buka alamata berikut: http://localhost:8080/belajarspring/helloWorld/displayMessage/haloooooooo.

## Spring-Form ##

Sekarang kita coba membuat aplikasi input data sederhana. Buka project yang berada didalam folder "Workshop JavaEnterprise/sinauspringmvc/2 mvc/before".

Buat file baru didalam folder "views" dan beri nama "UserForm.jsp". Isi file tersebut dengan kode berikut ini.

```xml
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New or Edit User</title>
</head>
<body>
	<div align="center">
		<h1>New/Edit User</h1>
		<table>
			<form:form action="save" method="post" modelAttribute="user">
			<form:hidden path="id"/>
			<tr>
				<td>Username:</td>
				<td><form:input path="username"/></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><form:input path="email"/></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><form:password path="password"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="Save">
				</td>
			</tr>
			</form:form>
		</table>
	</div>
</body>
</html>
```

Buat file baru lagi didalam folder "views" dan beri nama "UserList.jsp". Isi file tersebut dengan kode berikut ini.

```xml
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <div align="center">
	        <h1>Users List</h1>
	        <h2><a href="new">New User</a></h2>

        	<table border="1">
	        	<th>No</th>
	        	<th>Username</th>
	        	<th>Email</th>
	        	<th>Actions</th>

				<c:forEach var="user" items="${userList}" varStatus="status">
	        	<tr>
	        		<td>${status.index + 1}</td>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>
						<a href="edit?id=${user.id}">Edit</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="delete?id=${user.id}">Delete</a>
					</td>
	        	</tr>
				</c:forEach>
        	</table>
        </div>
    </body>
</html>
```

Buat package baru didalam folder "src" dan beri nama "net.codejava.spring.model". Buat class baru didalamnya dan beri nama "User". Isi file tersebut dengan kode berikut ini.

```java
package net.codejava.spring.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
```

Buat package baru lagi didalam folder "src" dan beri nama "net.codejava.spring". Buat Java class baru didalamnya dan beri nama "HomeController". Isi file tersebut dengan kode berikut.

```java
package net.codejava.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codejava.spring.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping("/")
	public ModelAndView handleRequest() throws Exception {
		List<User> listUsers = new ArrayList<User>();
		ModelAndView model = new ModelAndView("UserList");
		model.addObject("userList", listUsers);
		return model;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newUser() {
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", new User());
		return model;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = new User();
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute User user) {
		return new ModelAndView("redirect:/");
	}
}
```

Sekarang mari kita coba jalankan.


## Spring-Hibernate ##

Kita akan coba menggabungkan antara Spring MVC dan Hibernate supaya dapat berjalan bersamaan.

Buat database baru dengan nama "usersdb".

Open project "Workshop JavaEnterprise/sinauspringmvc/before/sinauspringmvc" didalam eclipse.

Ubah file "servlet-context.xml" menjadi seperti dibawah ini. Jika tidak tau dimana letaknya tekan tombol ctrl+shift+R dan cari nama filenya kemudian tekan enter.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- DispatcherServlet Context: defines this servlet's request-processing infrastructure -->

	<!-- Enables the Spring MVC @Controller programming model -->
	<mvc:annotation-driven />

	<!-- Handles HTTP GET requests for /resources/** by efficiently serving up static resources in the ${webappRoot}/resources directory -->
	<mvc:resources mapping="/resources/**" location="/resources/" />

	<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>

	<context:component-scan base-package="net.codejava.spring" />

    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/usersdb"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
    </bean>

    <bean id="sessionFactory"
        class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
		<property name="configLocation" value="classpath:hibernate.cfg.xml" />
    </bean>

    <tx:annotation-driven />
    <bean id="transactionManager"
        class="org.springframework.orm.hibernate4.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory" />
    </bean>

	<bean id="userDao" class="net.codejava.spring.dao.UserDAOImpl">
	    <constructor-arg>
	        <ref bean="sessionFactory" />
	    </constructor-arg>
	</bean>
</beans>
```

Buat file didalam folder "src" dan beri nama "hibernate.cfg.xml". Isi file tersebut dengan kode dibawah ini.

```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
  <session-factory>
    <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="show_sql">true</property>
    <property name="hbm2ddl.auto">create</property>
    <mapping resource="net/codejava/spring/model/User.hbm.xml"/>
  </session-factory>
</hibernate-configuration>
```

Buat package baru dengan nama "net.codejava.spring.model".

Buat class baru didalam package "net.codejava.spring.model" dan beri nama "User.java". Isi file tersebut dengan kode dibawah ini.

```java
package net.codejava.spring.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
```

Buat file baru didalam package "net.codejava.spring.model" dan beri nama "User.hbm.xml". Isi file tersebut dengan kode dibawah ini.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping package="net.codejava.spring.model">
	<class name="User" table="USERS">
		<id name="id" column="USER_ID">
			<generator class="native"/>
		</id>
		<property name="username" column="USERNAME" />
		<property name="password" column="PASSWORD" />
		<property name="email" column="EMAIL" />
	</class>
</hibernate-mapping>
```

Buat package baru dengan nama "net.codejava.spring.dao".

Buat class baru didalam package "net.codejava.spring.dao" dan beri nama "UserDAO". Isi file tersebut dengan kode dibawah ini.

```java
package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.User;

public interface UserDAO {
	public List<User> list();

	public User get(int id);

	public void saveOrUpdate(User user);

	public void delete(int id);
}
```

Buat class baru didalam package "net.codejava.spring.dao" dan beri nama "UserDAOImpl". Isi file tersebut dengan kode dibawah ini.

```java
package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.User;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public UserDAOImpl() {
	}

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<User> list() {
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listUser;
	}

	@Override
	@Transactional
	public void saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	@Transactional
	public void delete(int id) {
		User userToDelete = new User();
		userToDelete.setId(id);
		sessionFactory.getCurrentSession().delete(userToDelete);
	}

	@Override
	@Transactional
	public User get(int id) {
		String hql = "from User where id=" + id;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);

        @SuppressWarnings("unchecked")
		List<User> listUser = (List<User>) query.list();
		if (listUser != null && !listUser.isEmpty()) {
			return listUser.get(0);
		}
		return null;
	}
}
```

Ubah file "HomeController.java" menjadi seperti berikut.

```java
package net.codejava.spring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.codejava.spring.dao.UserDAO;
import net.codejava.spring.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@Autowired
	private UserDAO userDao;

	@RequestMapping("/")
	public ModelAndView handleRequest() throws Exception {
		List<User> listUsers = userDao.list();
		ModelAndView model = new ModelAndView("UserList");
		model.addObject("userList", listUsers);
		return model;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newUser() {
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", new User());
		return model;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userDao.get(userId);
		ModelAndView model = new ModelAndView("UserForm");
		model.addObject("user", user);
		return model;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
		userDao.delete(userId);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute User user) {
		userDao.saveOrUpdate(user);
		return new ModelAndView("redirect:/");
	}
}
```

Coba kita running menggunakan tomcat.

Selesai.

