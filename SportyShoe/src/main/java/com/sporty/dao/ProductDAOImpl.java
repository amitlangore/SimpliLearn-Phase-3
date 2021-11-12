package com.sporty.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sporty.pojo.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Product> getProducts() {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Product> theQuery = currentSession.createQuery("from Product", Product.class);

		List<Product> products = theQuery.getResultList();

		return products;
	}

	@Override
	@Transactional
	public Product getProduct(int productId) {
		Session currentSession = sessionFactory.getCurrentSession();

		Product prod = currentSession.get(Product.class, productId);

		return prod;
	}

	@Override
	@Transactional
	public void saveProduct(Product product) {
		Session currentSession = sessionFactory.getCurrentSession();

		currentSession.saveOrUpdate(product);

	}

	@Override
	@Transactional
	public void deleteProduct(int productId) {
				Session currentSession = sessionFactory.getCurrentSession();

				Query<?> theQuery = currentSession.createQuery("delete from Product where id=:productId");
				theQuery.setParameter("productId", productId);

				theQuery.executeUpdate();

	}

	@Override
	@Transactional
	public List<Product> searchProducts(String key) {
				Session currentSession = sessionFactory.getCurrentSession();

				Query<Product> theQuery = currentSession.createQuery("from Product where name like: key", Product.class);
				theQuery.setParameter("key", "%" + key + "%");
				List<Product> products = theQuery.getResultList();
				
				System.out.println();
				return products;
	}

}
