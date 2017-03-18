# Sinau Spring MVC #

Spring MVC merupakan sebuah Java framework yang digunakan untuk membuat aplikasi web. Ketika menggunakan Spring MVC kita harus mengikuti aturan yang telah dibuat oleh Spring yaitu harus mengikuti pattern MVC / Model-View-Controller. Yaitu memisahkan antara user-interface dan bagian backend yang mengurusi logic nya.




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














