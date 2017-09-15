package net.itw.wcms.x27.entity;

import java.sql.SQLException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.itw.wcms.toolkit.security.PasswordUtils;

public class X27Test {

	private ApplicationContext ctx = null;
	private EntityManager entityManager = null;
	private EntityManagerFactory entityManagerFactory = null;

	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		entityManagerFactory = ctx.getBean(EntityManagerFactory.class);
		entityManager = entityManagerFactory.createEntityManager();
	}

	/**
	 * 测试DataSource是否配置成功
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
	//Git commit test...
	/**
	 * 测试Jpa
	 */
	@Test
	public void testJPA() {
		Organization org = new Organization();
		org.setOrgName("运维部");
		org.setOrgCode("007");
		org.setOrgShortName("运维部");
		org.setCreateDate(new Date());

		User user = new User();
		user.setUserName("admin");
		user.setPassword(PasswordUtils.hash("q1w2e3r4"));
		user.setEmail("admin@gbicc.net");
		user.setCreateDate(new Date());
		user.setRealName("三傻");

		user.getOrgs().add(org);
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(org);
		entityManager.persist(user);
		transaction.commit();
	}

}
