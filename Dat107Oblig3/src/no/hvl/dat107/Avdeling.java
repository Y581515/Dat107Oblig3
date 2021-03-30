package no.hvl.dat107;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "avdeling", schema = "Oving_JPA")
public class Avdeling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int avdelingid;

    private String navn;
    
    @OneToMany(mappedBy="avdeling",fetch = FetchType.EAGER)
    private List<Ansatt> ansatte = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "sjef_id")
    private Ansatt sjef;
    
    
    public Avdeling() {}
    

    public Avdeling(String navn, Ansatt sjeff) {
		this.navn = navn;
		this.sjef = sjeff;
		
		sjef.setAvdeling(this);
	}

	public void skrivUtMedAnsatte() {
        skrivUt();
        ansatte.forEach(Ansatt::skrivUt);
    }

    public void skrivUt() {
        System.out.printf(" Avdeling %d(%s): %d ansatte, sjef = %s%n", 
        		avdelingid, navn, ansatte.size(), sjef.getFornavn());
    }

    public void leggTilAnsatt(Ansatt ansatt) {
        ansatte.add(ansatt);
        ansatt.setAvdeling(this);
    }

    public void fjernAnsatt(Ansatt ansatt) {
        ansatte.remove(ansatt);
        ansatt.setAvdeling(null);
    }

	public int getAvdelingid() {
		return avdelingid;
	}

	public String getNavn() {
		return navn;
	}

	public List<Ansatt> getAnsatte() {
		return ansatte;
	}

	public Ansatt getSjef() {
		return sjef;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public void setSjef(Ansatt sjef) {
		this.sjef = sjef;
	}


	@Override
	public String toString() {
		return "Avdeling [avdelingid=" + avdelingid + ", navn=" + navn + ", sjef=" + sjef
				+ "]";
	}

	
	
    
}
