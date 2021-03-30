package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
	static List<Ansatt> alleAnsatter = null;
	static List<Avdeling> alleAvdelinger = null;
	static AnsattDAO ansattdao = new AnsattDAO();
	static AvdelingDAO avdelingdAO = new AvdelingDAO();

	public static void main(String[] args) {

		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("finn ansatt med PK=3");
		Ansatt ansatt3 = ansattdao.finnAnsattMedId(3);
		System.out.println("   "+ansatt3);
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		System.out.println("skrive ut alle ansatter by Order");
		skrivUtAlleAnsatter();

		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("legger til en ny ansatt (Birk)");
		Ansatt birk = new Ansatt("Birk", "Birk", "Johanessen", LocalDate.of(2024, 05, 05), "Dataingenior",
				new BigDecimal(750000), ansatt3.getAvdeling());
		
		ansattdao.lagreNyAnsatt(birk);
		System.out.println();
		skrivUtAlleAnsatter();

		System.out.println("sletter ansatten (Birk)");
		ansattdao.slettAnsattMedPk(birk.getAnsattid());
		System.out.println();
		skrivUtAlleAnsatter();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		
		System.out.println("finn ett projekt med PK=3");
		Avdeling avdiling3 = avdelingdAO.finnAvdelingMedId(3);
		avdiling3.skrivUtMedAnsatte();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		
		
		System.out.println("Opdatter ansatt med PK=2  Stiling");
		// Opdatter ansatt Stilingg
		ansattdao.oppdaterAnsattStilling(2, "fotballer");
		System.out.println();
		
		System.out.println("Opdatter ansatt med PK=2  lonn");
		ansattdao.oppdaterAnsattLonn(2, new BigDecimal(1111111));
		skrivUtAlleAnsatter();
		
		System.out.println("Opdatter tilbake");
		ansattdao.oppdaterAnsattStilling(2, "Dataingenior");
		ansattdao.oppdaterAnsattLonn(2, new BigDecimal(750000));
		skrivUtAlleAnsatter();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		
		System.out.println("Utlisting av alle ansatte på en avdeling inkl. utheving av hvem som er sjef");
		avdelingdAO.AvdelingsInfo(2);
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("endrer ansatt avdeling ved bruk av ansatt_Id og avdeling_Id hvis ansatten er ikke sjeff i sin avdeling");
		skrivUtAlleAnsatter();
		int ans_id=9;
		int avd_id=2;
		System.out.println(ansattdao.finnAnsattMedId(ans_id));
		avdelingdAO.oppdaterAnsattAvdeling(ans_id,avd_id);
		System.out.println(ansattdao.finnAnsattMedId(ans_id));
		
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("lager en ny avdeling");
		skrivUtAlleAvdelinger();
		int sjeffId=8;
		String nyAvdelingNavn="avd4";
		avdelingdAO.LeggeInnEnNyAvdeling(nyAvdelingNavn, sjeffId);
		skrivUtAlleAvdelinger();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		
		System.out.println("\nFerdig");
	}

	
	
	/**
	 * 
	 */
	
	public static void skrivUtAlleAnsatter() {
		alleAnsatter = ansattdao.finnAlleAnsatter();
		alleAnsatter.forEach(t -> System.out.println("   " + t));
		System.out.println();
	}
	
	public static void skrivUtAlleAvdelinger() {
		alleAvdelinger = avdelingdAO.finnAlleAvdelinger();
		alleAvdelinger.forEach(t -> System.out.println("   " + t));
		System.out.println();
	}
}
