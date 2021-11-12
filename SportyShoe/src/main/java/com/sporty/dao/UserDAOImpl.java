package com.sporty.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sporty.pojo.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional 
	public List<User> getUsers() {

		Session currentSession = sessionFactory.getCurrentSession();

		Query<User> theQuery = currentSession.createQuery("from User", User.class);

		List<User> users = theQuery.getResultList();
		 
		return users;
	}

	@Override
	@Transactional 
	public User getUser(int userId) {
		Session currentSession = sessionFactory.getCurrentSession();

		User user = currentSession.get(User.class, userId);

		return user;
	}

	@Override
	@Transactional 
	public void saveUser(User user) {

		Session currentSession = sessionFactory.getCurrentSession();

		currentSession.saveOrUpdate(user);

	}

	@Override
	@Transactional 
	public void deleteUser(int userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<?> theQuery = currentSession.createQuery("delete from User where id=:userId");
		theQuery.setParameter("userId", userId);		
		theQuery.executeUpdate();		
	}
}
