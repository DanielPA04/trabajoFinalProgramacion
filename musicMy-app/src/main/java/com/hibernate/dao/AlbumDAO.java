package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Album;
import com.hibernate.util.HibernateUtil;

public class AlbumDAO {
	public void insertAlbum(Album a) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(a);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void updateAlbum(Album a) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(a);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public void deleteAlbum(int id) {
		Transaction transaction = null;
		Album a = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			a = session.get(Album.class, id);
			session.remove(a);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	public Album selectAlbumById(int cod) {
		Transaction transaction = null;
		Album a = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			a = session.get(Album.class, cod);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return a;
	}

	public List<Album> selectAllAlbums() {
		Transaction transaction = null;
		List<Album> albums = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			albums = session.createQuery("FROM Album", Album.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return albums;
	}
	
	public List<Album> selectAllAlbumsByArtista(int cod) {
		Transaction transaction = null;
		List<Album> albums = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<Album> query = session.createQuery("FROM Album WHERE artista = :cod", Album.class);
			query.setParameter("cod", cod);
			albums = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
		return albums;
	}
}
