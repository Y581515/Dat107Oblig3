package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "prosjekt", schema = "Oving_JPA")
public class Prosjekt {
	static ProsjektDAO prosjektDAO = new ProsjektDAO();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projektid;

	private String navn;
	private String beskrivelse;

	@OneToMany(mappedBy = "prosjekt")
	private List<Prosjektdeltagelse> deltagelser = new ArrayList<Prosjektdeltagelse>();

	public void skrivUt(String innrykk) {
		System.out.printf("%sProsjekt nr %d: %s, Beskrivelse= %s", innrykk, projektid, navn, beskrivelse);
	}

	public void skrivUtMedAnsatte() {
		System.out.println();
		skrivUt("");
		deltagelser.forEach(a -> a.skrivUt("\n   "));
		System.out.println("\ntotal projekt Timer = " + prosjektDAO.antallTimer(projektid));

	}

	public void leggTilProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
		deltagelser.add(prosjektdeltagelse);
	}

	public void fjernProsjektdeltagelse(Prosjektdeltagelse prosjektdeltagelse) {
		deltagelser.remove(prosjektdeltagelse);
	}

	public int getProjektid() {
		return projektid;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public List<Prosjektdeltagelse> getDeltagelser() {
		return deltagelser;
	}

}
