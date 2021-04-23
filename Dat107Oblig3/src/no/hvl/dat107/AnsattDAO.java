package no.hvl.dat107;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AnsattDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Oving_JPAPersistenceUnit");

	/**
	 * @param pk
	 * @return
	 */
	public Ansatt finnAnsattMedId(int pk) {

		EntityManager em = emf.createEntityManager();

		try {
			return em.find(Ansatt.class, pk);

		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<Ansatt> finnAlleAnsatter() {

		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a ORDER BY a.ansattid", Ansatt.class);//Select * 
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param brukernavn
	 * @return
	 */
	public Ansatt finnAnsattMedbrukernavn(String brukernavn) {
		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn",
					Ansatt.class);
			query.setParameter("brukernavn", brukernavn);
			return query.getSingleResult(); // NB! Unntak hvis 0 eller flere.

		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param nyAnsatt
	 * @return avdelingen nyAnsatten er på
	 */
	public Avdeling lagreNyAnsatt(Ansatt nyAnsatt) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.persist(nyAnsatt);

			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
		return nyAnsatt.getAvdeling();
	}

	/**
	 * @param pk
	 */
	public void slettAnsattMedPk(int pk) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Ansatt ansatt = em.find(Ansatt.class, pk);
			em.remove(ansatt);

			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param ansatt
	 */
	public void oppdaterAnsatt(Ansatt ansatt) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.merge(ansatt);

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param pk
	 * @param nyStilling
	 */
	public void oppdaterAnsattStilling(int pk, String nyStilling) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Ansatt ansatt = em.find(Ansatt.class, pk);
			ansatt.setStilling(nyStilling);
			em.merge(ansatt);

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param pk
	 * @param nyLonn
	 */
	public void oppdaterAnsattLonn(int pk, BigDecimal nyLonn) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Ansatt ansatt = em.find(Ansatt.class, pk);
			ansatt.setMaanadslon(nyLonn);
			em.merge(ansatt);

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}
	}

}
