package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ProsjektDAO {

	private EntityManagerFactory emf;

	public ProsjektDAO() {
		emf = Persistence.createEntityManagerFactory("Oving_JPAPersistenceUnit");
	}

	public Prosjekt finnProsjektMedId(int id) {

		EntityManager em = emf.createEntityManager();

		Prosjekt prosjekt = null;
		try {
			prosjekt = em.find(Prosjekt.class, id);
		} finally {
			em.close();
		}
		return prosjekt;
	}



	public Integer antallTimer(Integer projektid) {

		EntityManager em = emf.createEntityManager();

		List<Prosjektdeltagelse> listen;
		int tT = 0;

		try {
			TypedQuery<Prosjektdeltagelse> query = em.createQuery(
					"SELECT p FROM Prosjektdeltagelse p WHERE p.prosjekt.projektid =:projektid",
					Prosjektdeltagelse.class);

			query.setParameter("projektid", projektid);

			listen = query.getResultList();

			for (int i = 0; i < listen.size(); i++) {
				tT += listen.get(i).getTimer();
			}

		} finally {
			em.close();
		}
		return tT;
	}

}
