package app;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import core.util.HibernateUtil;
import web.member.pojo.Member;

public class TestApp {
	public static void main(String[] args) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		Member member = session.get(Member.class, 1);
//		System.out.println(member.getNickname());
//		HibernateUtil.shutdown();
		
		
//		Member member = new Member();
//		member.setUsername("使用者名稱1");
//		member.setPassword("密碼");
//		member.setNickname("暱稱");
		
		TestApp app = new TestApp();
//		app.insert(member);
//		System.out.println(member.getId());
		
//		System.out.println(app.deleteById(5));
		
//		Member member = new Member();
//		member.setId(1);
//		member.setPass(false);
//		member.setRoleId(2);
		
//		System.out.println(app.updateById(member));
		
//		System.out.println(app.selectById(2).getNickname());
		
//		System.out.println(app.selectAll());
//		app.selectAll().forEach(member -> System.out.println(member.getNickname()));
//		or
//		for(Member member : app.selectAll()) {
//			System.out.println(member.getNickname());
//		}
//		for(Member member : app.selectAll()) {
//			System.out.println(member.getNickname());
//		}
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(root.get("username"), "admin"),
				criteriaBuilder.equal(root.get("password"), "P@ssw0rd")
				));
		
		//select USERNAME, NICKNAME
		criteriaQuery.multiselect(root.get("username"),root.get("nickname"));
		
		// order by ID
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		
		Member member = session.createQuery(criteriaQuery).uniqueResult();
		System.out.println(member.getNickname());
		
	}
	
	public Integer insert(Member member) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.persist(member);
			transaction.commit();
			return member.getId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public int deleteById(Integer id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member member = session.get(Member.class, id);
			
			session.remove(member);
			transaction.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return -1;
		}
	}
	
	public int updateById(Member newmember) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member oldmember = session.get(Member.class, newmember.getId());
			
			final Boolean pass = newmember.getPass();
			if(pass != null) {
				oldmember.setPass(pass);
			}
			
			final Integer roleId = newmember.getRoleId(); 
			if (roleId != null) {
				oldmember.setRoleId(roleId);
			}
			
			
			transaction.commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return -1;
		}
	}
	
	public Member selectById(Integer id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member member = session.get(Member.class, id);
			
			transaction.commit();
			return member;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public List<Member> selectAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Query<Member> query = session.createQuery(
					"SELECT new web.member.pojo.Member(username, nickname) FROM Member", Member.class);
			List<Member> list = query.getResultList();
			transaction.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}
}
