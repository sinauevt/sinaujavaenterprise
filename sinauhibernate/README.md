# Sinau HIBERNATE #

## ORM ##

ORM merupakan kepanjangan dari Object Relational Mapping, merupakan tools yang dipakai untuk memetakan tabel-tabel database relational kedalam object. Dengan memetakan tabel kedalam object diharapkan lebih menyederhakan dan memudahkan coding.


## Hibernate ##

Hibernate merupakan salah satu framework Java yang dapat kita gunakan untuk ORM. Hibernate ini free dan opensource, bisa kita gunakan secara bebas. Hibernate dapat didownload disitus www.hibernate.org.

## Tools Yang Dibutuhkan ##

Sebelum memulai coding ada beberapa hal yang perlu kita install.
- Java SDK
- IDE Eclipse
- MySQL Database
- PHP MyAdmin / MySQL Workbench

## Memulai Coding Hibernate ##

Kita buat java project baru didalam eclipse dan kita berinama "belajarhibernate". Buat folder baru didalam project dan beri nama "lib".

Unzip file hibernate yang sudah kita download. Copy semua file .jar yang terdapat didalam folder "hibernate-release-4.2.3.Final/lib/required" kedalam folder "lib" yang kita buat didalam project sebelumnya.

Copy juga file "mysql-connector-java-5.1.31.jar" kedalam folder "lib".

Select semua file ".jar" yang terdapat didalam folder "lib" kemudian klik kanan pilih menu "Build Path >> Add to Build Path".

Buka MySQL (bisa menggunakan console, phpmyadmin atau mysql workbench). Buat database / schema baru dengan nama "cobahibernate".

Buat file baru didalam folder "src" dan beri nama "hibernate.cfg.xml". File ini adalah tempat kita definisikan semua konfigurasi database dan mapping. Isi file tersebut dengan kode dibawah ini.

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
<session-factory>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/cobahibernate</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">root</property>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="show_sql">true</property>
    <property name="format_sql">true</property>
</session-factory>
</hibernate-configuration>
```

Buat package baru didalam project dan beri nama "com.sinau.belajarhibernate". Kemudian didalam package baru tersebut buat class baru dan beri nama "HibernateUtilities". Isi file tersebut dengan kode berikut ini.

```java
package com.sinau.belajarhibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtilities {

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	static {
		try {
			Configuration configuration = new Configuration().configure();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch(HibernateException e) {
			System.out.println("Terjadi masalah saat membuat session factory!");
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
``` 

Buat class baru lagi didalam package "com.sinau.belajarhibernate" dan beri nama "MainProgram". Isi file tersebut dengan kode berikut ini.

```java
package com.sinau.belajarhibernate;

import org.hibernate.Session;

public class MainProgram {
	public static void main(String[] args) {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.close();
		System.out.println("Hello Hibernate");
	}
}
```

Saatnya kita jalankan. Program diatas belum menghasilkan apa-apa, hanya untuk melakukan pengujian apakah konfigurasi yang kita lakukan sudah benar atau belum. Jika exception tidak muncul maka semua konfigurasi sudah benar.

## Mapping ##

Mapping merupakan pemetaan tabel database kedalam object. Didalam Hibernate mapping dapat dilakukan dengan dua cara, yaitu menggunakan xml dan menggunakan annotation didalam class java.

Saat kita mapping antara table kedalam object kita harus menyamakan tipe datanya. Kita harus tau padanan tipe data antara SQL dengan Java, karena tipe data SQL tidak selalu sama dengan tipe data di Java. Berikut adalah mapping type yang bisa kita jadikan acuan.

| Mapping Type | Java Type | SQL Type |
|:------------:|:---------:|:--------:|
| integer      | int       | INTEGER  |
| long         | long      | BIGINT   |
| short        | short     | SMALLINT |
| float        | float     | FLOAT    |
| double       | double    | DOUBLE   |
| character    | String    | CHAR(1)  |
| string       | String    | VARCHAR  |
| boolean      | boolean   | BIT      |
| date         | Date      | DATE     |

Buat class baru didalam package "com.sinau.belajarhibernate" dan beri nama "User". Ini hanya class POJO biasa yang isinya merepresentasikan field-field didalam tabel database. Isi file tersebut dengan kode berikut ini.

```java
package com.sinau.belajarhibernate;

public class User {
	private int id;
	private String name;
	private int total;
	private int goal;

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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
}
```

Setelah kita membuat POJO, sekarang kita buat table didalam MySQL.

```SQL
create table user (
	id INT NOT NULL,
    name varchar(100) NOT NULL,
    total INT NOT NULL DEFAULT 0,
    goal INT NOT NULL DEFAULT 100,
    PRIMARY KEY(id)
);
```

Selanjutnya mari kita buat mapping file nya. Buat file baru didalam package "com.sinau.belajarhibernate" dan beri nama "User.hbm.xml". Isi file tersebut dengan kode berikut ini.

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.sinau.belajarhibernate.User" table="user">
        <id name="id" type="int">
            <column name="id" />
            <generator class="assigned" />
        </id>
        <property name="name" type="java.lang.String">
            <column name="name"/>
        </property>
        <property name="total" type="int">
            <column name="total"/>
        </property>
        <property name="goal" type="int">
            <column name="goal"/>
        </property>
    </class>
</hibernate-mapping>
```

Edit file hibernate.cfg.xml dan tambahkan baris berikut dibagian "session-factory".

```xml
<mapping resource="com/sinau/belajarhibernate/User.hbm.xml"/>
```

Edit class "MainProgram" menjadi seperti berikut.

```java
package com.sinau.belajarhibernate;

import org.hibernate.Session;

public class MainProgram {
	public static void main(String[] args) {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		User user = new User();
		user.setId(1);
		user.setName("Budi");
		user.setGoal(1000);
		user.setTotal(5000);
		session.save(user);

		session.getTransaction().commit();
		session.close();
		HibernateUtilities.getSessionFactory().close();
	}
}

```

Sekarang coba kita jalankan. Jika berhasil maka didalam table user di MySQL akan muncul satu record baru.

```SQL
SELECT * FROM cobahibernate.user;
```

Sekarang mari kita coba untuk mengambil data menggunakan Hibernate. Ubah class "MainProgram" menjadi seperti berikut.

```java
package com.sinau.belajarhibernate;

import org.hibernate.Session;

public class MainProgram {
	public static void main(String[] args) {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		/*session.beginTransaction();

		User user = new User();
		user.setId(1);
		user.setName("Budi");
		user.setGoal(1000);
		user.setTotal(5000);
		session.save(user);
		session.getTransaction().commit();*/

		session.beginTransaction();

		User getUser = (User) session.get(User.class, 1);
		System.out.println(getUser.getName());
		System.out.println(getUser.getGoal());
		System.out.println(getUser.getTotal());

		session.getTransaction().commit();

		session.close();
		HibernateUtilities.getSessionFactory().close();
	}
}
```

## Component ##

Didalam class Java biasanya terdapat relasi antar class yang disebut dengan "Composition", dimana suatu object berelasi dengan object lain yang menyimpan informasi nilai / value dari object tersebut. Ibarat kita memiliki sebuah class mobil dimana informasi sparepart yang digunakan disimpan didalam class yang lain.

Buat class baru didalam package "com.sinau.belajarhibernate" dan beri nama "UserData". Isi file tersebut seperti berikut.

```java
package com.sinau.belajarhibernate;

public class UserData {
	private int total;
	private int goal;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
}
```

Ubah class "User.java" menjadi seperti berikut.

```java
package com.sinau.belajarhibernate;

public class User {
	private int id;
	private String name;
	private UserData userData = new UserData();

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
}
```

Ubah class "MainProgram.java" menjadi seperti berikut.

```java
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
```

Sebelum kita jalankan kita harus perbaiki mapping terlebih dahulu. Ubah file "User.hbm.xml" menjadi seperti berikut.

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.sinau.belajarhibernate.User" table="user">
        <id name="id" type="int">
            <column name="id" />
            <generator class="assigned" />
        </id>
        <property name="name" type="java.lang.String">
            <column name="name"/>
        </property>
        <component name="userData">
	        <property name="total" type="int">
	            <column name="total"/>
	        </property>
	        <property name="goal" type="int">
	            <column name="goal"/>
	        </property>
        </component>
    </class>
</hibernate-mapping>
```

Sekarang coba kita jalankan.

## One to Many ##

Didalam database kita biasa mendengar istilah relasi antar tabel. Relasi yang paling sering kita gunakan biasanya adalah one-to-many, satu data didalam tabel memiliki relasi dengan satu atau lebih data didalam tabel yang lain.

Mari kita coba. Buat class baru didalam package "com.sinau.belajarhibernate" dan beri nama "UserHistory". Isi file tersebut dengan coding berikut.

```java
package com.sinau.belajarhibernate;

import java.util.Date;

public class UserHistory {
	private int id;
	private User user;
	private Date entryTime;
	private String entry;

	public UserHistory() {
	}
	public UserHistory(Date entryTime, String entry) {
		super();
		this.entryTime = entryTime;
		this.entry = entry;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
}
```

Ubah class "User.java" menjadi seperti berikut.

```java
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
```

Ubah class "MainProgram.java" menjadi seperti berikut.

```java
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
```

Kita perlu memapping "UserHistory" kedalam Hibernate. Kita buat file baru didalam package "com.sinau.belajarhibernate" dan beri mana "UserHistory.hbm.xml". Isi file tersebut dengan coding berikut.

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.sinau.belajarhibernate.UserHistory" table="userhistory">
        <id name="id" type="int">
            <column name="id" />
            <generator class="increment" />
        </id>
        <many-to-one name="user" class="com.sinau.belajarhibernate.User" not-null="true">
        	<column name="user_id"></column>
        </many-to-one>
        <property name="entryTime" type="java.util.Date">
            <column name="entrytime"/>
        </property>
        <property name="entry" type="java.lang.String">
            <column name="entry"/>
        </property>
    </class>
</hibernate-mapping>
```

Kita juga perlu memperbaiki mapping didalam file "User.hbm.xml". Jadi kita perlu melakukan perubahan disana, ubah menjadi seperti berikut.

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="com.sinau.belajarhibernate.User" table="user">
        <id name="id" type="int">
            <column name="id" />
            <generator class="increment" />
        </id>
        <property name="name" type="java.lang.String">
            <column name="name"/>
        </property>
        <component name="userData">
	        <property name="total" type="int">
	            <column name="total"/>
	        </property>
	        <property name="goal" type="int">
	            <column name="goal"/>
	        </property>
        </component>
        <list name="userHistory" table="userhistory" inverse="true" cascade="save-update">
        	<key column="user_id"/>
        	<list-index column="POSITION"/>
        	<one-to-many class="com.sinau.belajarhibernate.UserHistory"/>
        </list>
    </class>
</hibernate-mapping>
```

File mapping yang baru kita buat tadi "UserHistory.hbm.xml" perlu kita daftarkan kedalam file "hibernate.cfg.xml". Jadi kita ubah file tersebut menjadi seperti berikut.

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
<session-factory>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/cobahibernate</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">root</property>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="show_sql">true</property>
    <property name="format_sql">true</property>
    <property name="hibernate.hbm2ddl.auto">create</property>

    <mapping resource="com/sinau/belajarhibernate/User.hbm.xml"/>
    <mapping resource="com/sinau/belajarhibernate/UserHistory.hbm.xml"/>
</session-factory>
</hibernate-configuration>
```

Sebelum dijalankan kita hapus terlebih dahulu tabel MySQL yang tadi kita buat.

```SQL
DROP TABLE user;
```

Sekarang mari kita coba jalankan. Secara otomatis Hibernate akan membuatkan set table-table baru untuk kita.

## Many-to-Many ##

Relasi antar tabel yang sering digunakan juga adalah many-to-many, satu atau lebih data didalam tabel memiliki relasi dengan satu atau lebih data didalam tabel yang lain. Kita akan coba mengimplementasikan kedalam Hibernate.

Buat satu class baru didalam package "com.sinau.belajarhibernate" dan beri nama "Category". Isi file tersebut dengan kode dibawah ini.

```java
package com.sinau.belajarhibernate;

import java.util.HashSet;
import java.util.Set;

public class Category implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer categoryId;
	private String name;
	private String desc;
	private Set<Stock> stocks = new HashSet<Stock>(0);

	public Category() {
	}
	public Category(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	public Category(String name, String desc, Set<Stock> stocks) {
		this.name = name;
		this.desc = desc;
		this.stocks = stocks;
	}
	public Integer getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Set<Stock> getStocks() {
		return this.stocks;
	}
	public void setStocks(Set<Stock> stocks) {
		this.stocks = stocks;
	}
}
```

Kita buat file mapping didalam package "com.sinau.belajarhibernate" untuk class yang baru kita buat dan beri nama "Category.hbm.xml".

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<!-- Generated 27 April 2011 10:01:31 AM by Hibernate Tools 3.4.0.CR1 -->
<hibernate-mapping>
    <class name="com.sinau.belajarhibernate.Category" table="category">
        <id name="categoryId" type="java.lang.Integer">
            <column name="CATEGORY_ID" />
            <generator class="identity" />
        </id>
        <property name="name" type="string">
            <column name="NAME" length="10" not-null="true" />
        </property>
        <property name="desc" type="string">
            <column name="[DESC]" not-null="true" />
        </property>
        <set name="stocks" table="stock_category" inverse="true" lazy="true" fetch="select">
            <key>
                <column name="CATEGORY_ID" not-null="true" />
            </key>
            <many-to-many entity-name="com.sinau.belajarhibernate.Stock">
                <column name="STOCK_ID" not-null="true" />
            </many-to-many>
        </set>
    </class>
</hibernate-mapping>
```

Buat satu class baru lagi didalam package "com.sinau.belajarhibernate" dan beri nama "Stock". Isi file tersebut dengan kode dibawah ini.

```java
package com.sinau.belajarhibernate;

import java.util.HashSet;
import java.util.Set;

public class Stock implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer stockId;
	private String stockCode;
	private String stockName;
	private Set<Category> categories = new HashSet<Category>(0);

	public Stock() {
	}
	public Stock(String stockCode, String stockName) {
		this.stockCode = stockCode;
		this.stockName = stockName;
	}
	public Stock(String stockCode, String stockName, Set<Category> categories) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.categories = categories;
	}
	public Integer getStockId() {
		return this.stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	public String getStockCode() {
		return this.stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return this.stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public Set<Category> getCategories() {
		return this.categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
```

Kita buat file mapping didalam package "com.sinau.belajarhibernate" untuk class yang baru kita buat dan beri nama "Stock.hbm.xml".

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<!-- Generated 27 April 2011 10:01:31 AM by Hibernate Tools 3.4.0.CR1 -->
<hibernate-mapping>
    <class name="com.sinau.belajarhibernate.Stock" table="stock">
        <id name="stockId" type="java.lang.Integer">
            <column name="STOCK_ID" />
            <generator class="identity" />
        </id>
        <property name="stockCode" type="string">
            <column name="STOCK_CODE" length="10" not-null="true" unique="true" />
        </property>
        <property name="stockName" type="string">
            <column name="STOCK_NAME" length="20" not-null="true" unique="true" />
        </property>
        <set name="categories" table="stock_category" 
        	inverse="false" lazy="true" fetch="select" cascade="all" >
            <key>
                <column name="STOCK_ID" not-null="true" />
            </key>
            <many-to-many entity-name="com.sinau.belajarhibernate.Category">
                <column name="CATEGORY_ID" not-null="true" />
            </many-to-many>
        </set>
    </class>
</hibernate-mapping>
```

File mapping yang baru kita buat tadi perlu kita daftarkan kedalam file "hibernate.cfg.xml". Jadi kita ubah file tersebut menjadi seperti berikut.

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
<session-factory>
    <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/cobahibernate</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">root</property>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="show_sql">true</property>
    <property name="format_sql">true</property>
    <property name="hibernate.hbm2ddl.auto">create</property>

    <mapping resource="com/sinau/belajarhibernate/Stock.hbm.xml" />
    <mapping resource="com/sinau/belajarhibernate/Category.hbm.xml" />
</session-factory>
</hibernate-configuration>
```

Ubah file "MainProgram.java" ubah menjadi seperti berikut.

```java
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
```

Sekarang coba kita jalankan.

## Query ##

Untuk mendapatkan data dari database biasanya kita menggunakan query. Di Hibernate untuk mendapatkan data kita juga menggunakan query, namun query nya agak sedikit berbeda dengan SQL query pada umumnya. Query di Hibernate dinamakan dengan HQL, singkatan dari Hibernate Query Language.

Close project yang sebelumnya kita gunakan. Open project dari folder "Workshop JavaEnterprise/sinauhibernate/5 Query/before/belajarhibernate".

Ubah file "Program.java" menjadi seperti berikut.

```java
package com.simpleprogrammer;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class Program {

	public static void main(String[] args) {
		System.out.println("Hello world");

		PopulateSampleData();

		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("from User");
		List<User> users = query.list();
		for(User user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
		session.close();

		HibernateUtilities.getSessionFactory().close();
	}

	private static void PopulateSampleData() {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		User joe = CreateUser("Joe", 500, 50, "Good job", "You made it!");
		session.save(joe);

		User bob = CreateUser("Bob", 300, 20, "Taco time!");
		session.save(bob);

		User amy = CreateUser("Amy", 250, 200, "Yes!!!");
		session.save(amy);
		session.getTransaction().commit();
		session.close();
	}

	private static User CreateUser(String name, int goal, int total, String ... alerts){
		User user = new User();
		user.setName(name);
		user.getProteinData().setGoal(goal);
		user.addHistory(new UserHistory(new Date(), "Set goal to " + goal));
		user.getProteinData().setTotal(total);
		user.addHistory(new UserHistory(new Date(), "Set total to " + total));
		for(String alert : alerts) {
			user.getGoalAlerts().add(new GoalAlert(alert));
		}

		return user;
	}
}
```

Sekarang mari kita coba query dengan parameter. Kita ubah lagi file "Program.java" menjadi seperti berikut.

```java
package com.simpleprogrammer;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class Program {

	public static void main(String[] args) {
		System.out.println("Hello world");

		PopulateSampleData();

		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("select user from User user where user.name = :name")
				.setString("name", "Joe");
		List<User> users = query.list();
		for(User user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
		session.close();

		HibernateUtilities.getSessionFactory().close();
	}

	private static void PopulateSampleData() {
		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		User joe = CreateUser("Joe", 500, 50, "Good job", "You made it!");
		session.save(joe);

		User bob = CreateUser("Bob", 300, 20, "Taco time!");
		session.save(bob);

		User amy = CreateUser("Amy", 250, 200, "Yes!!!");
		session.save(amy);
		session.getTransaction().commit();
		session.close();
	}

	private static User CreateUser(String name, int goal, int total, String ... alerts){
		User user = new User();
		user.setName(name);
		user.getProteinData().setGoal(goal);
		user.addHistory(new UserHistory(new Date(), "Set goal to " + goal));
		user.getProteinData().setTotal(total);
		user.addHistory(new UserHistory(new Date(), "Set total to " + total));
		for(String alert : alerts) {
			user.getGoalAlerts().add(new GoalAlert(alert));
		}

		return user;
	}
}
```
































