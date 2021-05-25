package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AvdelingDAO {
	static AnsattDAO ansattdao = new AnsattDAO();

	private EntityManagerFactory emf;

	public AvdelingDAO() {
		emf = Persistence.createEntityManagerFactory("Oving_JPAPersistenceUnit");
	}

	/**
	 * 
	 * @param id
	 * @return Avdeling med gitt id
	 */
	public Avdeling finnAvdelingMedId(int id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Avdeling.class, id);
		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @return List<Avdeling>
	 */
	public List<Avdeling> finnAlleAvdelinger() {

		EntityManager em = emf.createEntityManager();

		try {
			TypedQuery<Avdeling> query = em.createQuery("SELECT a FROM Avdeling a ORDER BY a.avdelingid",
					Avdeling.class);
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	/**
	 * 
	 * @param id
	 */
	public void AvdelingsInfo(int id) {
		Avdeling avd = finnAvdelingMedId(id);
		if (avd == null) {
			System.out.println("avdeling " + id + " finnes ikke");
		} else {
			avd.skrivUtMedAnsatte();
		}

	}

	/**
	 * 
	 * @param ansatt_id
	 * @param avd_id
	 * @return
	 */
	public Avdeling oppdaterAnsattAvdeling(int ansatt_id, int avd_id) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Avdeling avd = null;
		Ansatt a = null;
		try {
			tx.begin();
			int sjeffId = ansattdao.finnAnsattMedId(ansatt_id).getAvdeling().getSjef().getAnsattid();
			if (ansatt_id == sjeffId) {

				System.out.println("!!!ansatten er sjef i avdelingen og kan ikke endres!!!");
			} else {
				avd = em.find(Avdeling.class, avd_id);

				if (avd == null) {
					System.out.println("avdeling " + avd_id + " finnes ikke");
				} else {
					a = em.find(Ansatt.class, ansatt_id);

					a.getAvdeling().fjernAnsatt(a);

					avd.leggTilAnsatt(a);

					em.merge(avd);
				}
			}
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}

		return avd;
	}

	/**
	 * 
	 * @param nyAvdelingsNavn
	 * @param ansatt
	 * @return
	 */
	public Avdeling LeggeInnEnNyAvdeling(String nyAvdelingsNavn, int ansatt) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		Avdeling avd = null;
		Ansatt a = null;
		try {
			tx.begin();
			int sjeffId = ansattdao.finnAnsattMedId(ansatt).getAvdeling().getSjef().getAnsattid();

			if (ansatt == sjeffId) {
				int avdId = ansattdao.finnAnsattMedId(ansatt).getAvdeling().getAvdelingid();
				System.out.println("!!!ansatten er allerede sjeff i avdeling " + avdId + "!!!");
			} else {

				a = em.find(Ansatt.class, ansatt);

				a.getAvdeling().fjernAnsatt(a);

				avd = new Avdeling(nyAvdelingsNavn, a);

				avd.leggTilAnsatt(a);

				em.persist(avd);

			}

			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}

		return avd;
	}

}
