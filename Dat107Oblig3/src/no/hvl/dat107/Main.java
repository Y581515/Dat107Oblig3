package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
	static List<Ansatt> alleAnsatter = null;
	static List<Avdeling> alleAvdelinger = null;
	static AnsattDAO ansattdao = new AnsattDAO();
	static AvdelingDAO avdelingdAO = new AvdelingDAO();
	static ProsjektDAO prosjektDAO = new ProsjektDAO();

	public static void main(String[] args) {

		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("finn ansatt med PK=3");
		Ansatt ansatt3 = ansattdao.finnAnsattMedId(3);
		System.out.println("   " + ansatt3);
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println("skrive ut alle ansatter by Order");
		skrivUtAlleAnsatter();

		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
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
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println("finn ett projekt med PK=3");
		Avdeling avdiling3 = avdelingdAO.finnAvdelingMedId(3);
		avdiling3.skrivUtMedAnsatte();
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

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
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println("Utlisting av alle ansatte på en avdeling inkl. utheving av hvem som er sjef");
		avdelingdAO.AvdelingsInfo(2);
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		int ans_id = 2;
		int avd_id =3;
		System.out.println("ansatt med anstatt id = "+ans_id+" endre til avdeling"+avd_id+" hvis ansatten er ikke sjeff i sin avdeling");
		
		System.out.println(
				"Alle ansatter: ");
		skrivUtAlleAnsatter();
		System.out.println("\nalle avdelinger");
		skrivUtAlleAvdelinger();
		
//		Ansatt sjef = avdelingdAO.finnAvdelingMedId(avd_id).getSjef();
		
		System.out.println(ansattdao.finnAnsattMedId(ans_id));
		System.out.println("opdatter...");
		avdelingdAO.oppdaterAnsattAvdeling(ans_id, avd_id);
		System.out.println(ansattdao.finnAnsattMedId(ans_id));

		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("lager en ny avdeling, hvis ansatten med sjeffId ikke er sjeff fra før");
		skrivUtAlleAvdelinger();
		int sjeffId = 8;
		String nyAvdelingNavn = "avd4";
		avdelingdAO.LeggeInnEnNyAvdeling(nyAvdelingNavn, sjeffId);
		skrivUtAlleAvdelinger();
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println(
				"Utskrift av info om prosjekt, inkl. liste av deltagere med rolle og timer, og totalt timetall for prosjektet");
		Prosjekt p1 = prosjektDAO.finnProsjektMedId(2);
		p1.skrivUtMedAnsatte();

		
		
		
		
		System.out.println("\n\n\n   Ferdig!");
	}

	/**
	 * 
	 */

	// hjelpMetode for å skriveut alle ansatter
	public static void skrivUtAlleAnsatter() {
		alleAnsatter = ansattdao.finnAlleAnsatter();
		alleAnsatter.forEach(t -> System.out.println("   " + t));
		System.out.println();
	}

	// hjelpMetode for å skriveut alle avdelinger
	public static void skrivUtAlleAvdelinger() {
		alleAvdelinger = avdelingdAO.finnAlleAvdelinger();
		alleAvdelinger.forEach(t -> System.out.println("   " + t));
		System.out.println();
	}
}
