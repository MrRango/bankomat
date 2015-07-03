/*
 * Klasa BankomatContext, implamentira interfejs Bankomat
 * Sadrzi metode vezane za rad bankomata
 */

package bankomat;

import interfejs.Bankomat;
import interfejs.ListaKorisnika;
import interfejs.StanjeBankomata;

import java.util.ArrayList;
import java.util.Scanner;

import korisnik.User;
import ulaz_izlaz.InOutListaKorisnika;
import ulaz_izlaz.InOutStanjeBankomata;

public class BankomatContext implements Bankomat {

	private User user = null;
	static User corentUser;
	private boolean isOn = true;
	ArrayList<User> listaKorisnika = new ArrayList<>();
	
	public BankomatContext() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfejs.Bankomat#pokreniBankomat()
	 * 
	 * Metoda za pokretanje rada bankomata 
	 * Trazi od korisnika da unese username i password i provjerava ih 
	 * U zavisnosti od toga da li je korisnik admin ili obicni korisnik
	 * poziva metode za ispis odgovarajuceg menija
	 */
	@Override
	public void pokreniBankomat() throws Exception {
		while (isOn) {
			boolean notCorrect = true;
			
			System.out.print("----------------------------\n"
					+ "Unesite vase korisnicko ime: ");
			Scanner in = new Scanner(System.in);
			String inUser = in.nextLine();
			// Za uneseno korisnicko ime trazi korisnika
			traziKorisnika(inUser);
			if (user == null) {
				System.out
						.println("Vase korisnicko ime se ne nalazi u nasoj bazi!");
				System.out.println();
			} else {
			// Pronadjenog korisnika postavlja za corentUser
			// Varijabla user se mora vratiti na null da bude spremna za sljedece logovanje 
				corentUser = user;
				user = null;
				// Provjera passworda
				do{
					System.out.println("Unesite password: ");
					String inPassword = in.nextLine();
					if (inPassword.equals(corentUser.getPassword())) {
						notCorrect = false;
					} else {
						System.out.println("Unijeli ste pogresan password");
					}
				}while(notCorrect);
				// Bira se meni u zavisnosti da li je ulogovani korisnik admin ili obicni korisnik
				Meni meni = new Meni();
				if (corentUser.isAdmin()) {
					meni.ispisiAdminMeni();

				} else {
					meni.ispisiKorisnickiMeni();
				}

			}
		}

	}

	/*
	 * Metoda za trazenje korisnika u bazi(fajlu) 
	 * Ucitava vec postojece korisnike iz fajla i trazi korisnika preko korisnickog imena 
	 * Ako korisnik postoji, vraca korisnika kao objekat User
	 */
	public User traziKorisnika(String inUser) throws Exception {

		ListaKorisnika lista = new InOutListaKorisnika();
		lista.ucitajListuKorisnika();
		listaKorisnika = lista.getListuKorisnika();

		for (int i = 0; i < listaKorisnika.size(); i++) {

			if (inUser.equals(listaKorisnika.get(i).getUsername())) {
				user = listaKorisnika.get(i);
			}
		}
		return user;

	}

    /*
     * Metoda uzima podatke, pravi novi objekat User i
     * upisuje ga u listu korisnika
     */
	void addUser() throws Exception {
		
		boolean postoji = true;
		boolean notLegit = true;
		boolean isNegativ = true;
		double amount = 0;
		String username, password;

		ListaKorisnika lista = new InOutListaKorisnika();
		lista.ucitajListuKorisnika();
		listaKorisnika = lista.getListuKorisnika();

		User u = new User();

		Scanner in = new Scanner(System.in);

		System.out.println("**Unosenje novog korisnika**");
		// Trazi od korisnika da unese korisnicko ime koje se ne nalazi u bazi
		do{
			System.out.println("Unesite username: ");
			username = in.next();
			for (int i = 0; i < listaKorisnika.size(); i++) {
				if (username.equals(listaKorisnika.get(i).getUsername())) {
					System.out.println("Uneseni korisnik vec postoji");
					break;
				}
				if (i == listaKorisnika.size() - 1){
					postoji = false;
				}
			}
		}while(postoji);	
		u.setUsername(username);
		// Trazi od korisnika da unese password od 4 broja
		do{
			System.out.println("Unesite password: ");
			password = in.next();
			for(int i = 0; i < password.length(); i++){
				if(!Character.isDigit(password.charAt(i))){
					break;
				}
				if(i == password.length() - 1 && password.length() == 4){
					notLegit = false;
				}
			}		
		}while(notLegit);
		u.setPassword(password);
		System.out.println("Da li je korisnik administrator? ");
		u.setAdmin(in.nextBoolean());
		// Ukoliko je novi korisnik admin, ne unosi se stanje racuna
		if (!u.isAdmin()) {
			// Stanje na racunu ne moze biti negativno
			do{
				System.out.println("Unesite kolicinu novca na racunu: ");
				amount = in.nextDouble();
				if(amount >= 0){
					isNegativ = false;
				}
			}while(isNegativ);
			u.setAmount(amount);
			System.out.println();
		}
		if(potvrdiUnos()){
			listaKorisnika.add(u);
			lista.setListuKorisnika(listaKorisnika);
		}
		
	}

	/*
	 * Metoda uzima korisnicko ime, ukoliko se korisnik nalazi u bazi 
	 * a nije trenutno ulogovan, brise ga
	 */
	void removeUser() throws Exception {
		
		ListaKorisnika lista = new InOutListaKorisnika();
		lista.ucitajListuKorisnika();
		listaKorisnika = lista.getListuKorisnika();
		
		System.out.println("***Brisanje korisnika***");
		System.out
				.println("Unesite username korisnika kojeg zelite da izbrisete: ");

		Scanner in = new Scanner(System.in);
		String inUser = in.nextLine();
		
		if(potvrdiUnos()){
			for (int i = 0; i < listaKorisnika.size(); i++) {
				// Ukoliko se korisnik nalazi u bazi a nije trenutno ulogovan, brise se
				if (inUser.equals(listaKorisnika.get(i).getUsername())
						&& !(inUser.equals(corentUser.getUsername()))) {
					listaKorisnika.remove(i);
					System.out.println("Korisnik " + inUser
							+ " je uspjesno izbrisan.");
					break;
				}
				// Ukoliko je korisnik trenutno ulogovan
				if (inUser.equals(corentUser.getUsername())) {
					System.out
							.println("Ne mozete izbrisati trenutno ulogovanog korisnika");
					break;
				}
				// Ukoliko je citava baza pregledana, a korisnik nije pronadjen
				if (i == listaKorisnika.size() - 1) {
					System.out
							.println("Korisnicko ime koje ste unijeli se ne nalazi u nasoj bazi");
				}
			}
			lista.setListuKorisnika(listaKorisnika);
			System.out.println();
		}
	}

	/*
	 * Metoda preuzima novo stanje bankomata i cuva ga
	 * Moze se unijeti broj od 0 do 100
	 */
	public void postaviStanjeRacuna() {
		StanjeBankomata stanjeBankomata = new InOutStanjeBankomata();
		int[] stanje = new int[4];
		int inBroj = 0;
		
		System.out.println("***Postavljanje stanja bankomata***");
		
		Scanner in = new Scanner(System.in);
		do{
			System.out.println("Broj novcanica od 10KM: ");
			inBroj = in.nextInt();
			stanje[0] = inBroj;
		}while(inBroj < 0 || inBroj > 100);
		do{
			System.out.println("Broj novcanica od 20KM");
			inBroj = in.nextInt();
			stanje[1] = inBroj;
		}while(inBroj < 0 || inBroj > 100);
		do{
			System.out.println("Broj novcanica od 50KM");
			inBroj = in.nextInt();
			stanje[2] = inBroj;
		}while(inBroj < 0 || inBroj > 100);
		do{
			System.out.println("Broj novcanica od 100KM");
			inBroj = in.nextInt();
			stanje[3] = inBroj;
		}while(inBroj < 0 || inBroj > 100);
		
		if(potvrdiUnos()){
			stanjeBankomata.setStanjeBankomata(stanje);
		}
		System.out.println();
	}

	/*
	 * Metoda za trenutno ulogovanog korisnika provjerava stanje racuna
	 */
	public void provjeriStanjeRacuna() throws Exception {
		System.out.println("***Provjera stanja racuna***");
		System.out.println("Trenutno na racunu imate: "
				+ corentUser.getAmount() + "KM\n");
	}

	/*
	 * Metoda koja trenutno ulogovanom korisniku omogucava da podigne novac
	 * ukoliko su za to ispunjeni svi uslovi
	 */
	public void podigniSaRacuna() throws Exception {
		
		ListaKorisnika lista = new InOutListaKorisnika();
		lista.ucitajListuKorisnika();
		listaKorisnika = lista.getListuKorisnika();

		StanjeBankomata stanjeBankomata = new InOutStanjeBankomata();
		stanjeBankomata.ucitajStanjeBankomata();
		int[] stanje = stanjeBankomata.getStanjeBankomata();

		System.out.println("****Dizanje novca****");
		Scanner in = new Scanner(System.in);
		System.out.println("Koliko novca zelite da dignete sa racuna? ");
		int iznos = in.nextInt();
		
		// Provjerava da li je trazeni iznos moguce isplatiti
		// - da li je iznos djeljiv sa deset
		// - da li u bankomatu ima dovoljno novca
		// - da li korisnik ima dovoljno na racunu
		
		if (iznos % 10 != 0) {
			System.out.println("Unijeli ste iznos koji se ne moze isplatiti\n");			
		} else if(iznos > stanje[4]) {
			System.out.println("U bankomatu nema dovoljno novca da Vam isplati trazeni iznos\n");
		} else if(iznos > corentUser.getAmount()) {
			System.out.println("Unijeli ste iznos koji prekoracuje stanje na vasem racunu\n");
		} else {
		// Ukoliko nijedan od predhodni uslova nije ispunjen, poziva se metoda skiniSaRacuna(int iznos)
		// koja ce isplatiti novac ukoliko bankomat za to ima odgovarajuce novcanice
			if(potvrdiUnos()){
				if (skiniSaRacuna(iznos)) {
					// Ako je isplata uspjesno odradjena, korisniku se novac skida sa racuna
					for (int i = 0; i < listaKorisnika.size(); i++) {
						// Iz liste se pronalazi corentUser
						if (listaKorisnika.get(i).getUsername().equals(corentUser.getUsername())) {
							// Od prethodnog stanja racuna se oduzima isplaceni iznos
							listaKorisnika.get(i).setAmount(listaKorisnika.get(i).getAmount() - iznos);
							lista.setListuKorisnika(listaKorisnika);
							// Azuriranje objekta corentUser
							corentUser = listaKorisnika.get(i); 
						}
					}
				}
			}
			
		}
	}

	/*
	 * Metoda koja isplacuje trazeni iznos ako za to bankomat ima odgovarajuce novcanice
	 */
	private boolean skiniSaRacuna(int iznos) throws Exception {
		// Ucitavanje stanja bankomata u niz stanje
		StanjeBankomata stanjeBankomata = new InOutStanjeBankomata();
		stanjeBankomata.ucitajStanjeBankomata();
		int[] stanje = stanjeBankomata.getStanjeBankomata();
		
		// Brojaci koriscenih novcanica za isplatu trazenog iznosa
		int br10 = 0, br20 = 0, br50 = 0, br100 = 0, suma = 0;
		boolean isComplete = false;
		// Dok je isplacena suma razlicita od trazenog iznosa, trazi se novcanica
		// koja je manja ili jednaka razlici izmedju isplacene sume i trazenog iznosa.
		// Ako trazena novcanica postoji u bankomatu, njen iznos se dodaje na sumu,
		// kolicina te novcanice u bankomatu se umanjuje za 1 i brojac koriscenih
		// novcanica za tu novcanicu se povecava za 1
		while (suma != iznos) {
			// Dodadatak: Dok god moze, novcanice se uzimaju ravnomjerno
			if(iznos - 180 >= suma && stanje[3] > 0 && stanje[2] > 0 && stanje[1] > 0 && stanje[0] > 0){
				suma+=180;
				stanje[3]--;
				br100++;
				stanje[2]--;
				br50++;
				stanje[1]--;
				br20++;
				stanje[0]--;
				br10++;
			} else if (iznos - 100 >= suma && stanje[3] > 0) {
				suma += 100;
				stanje[3]--;
				br100++;
			} else if (iznos - 50 >= suma && stanje[2] > 0) {
				suma += 50;
				stanje[2]--;
				br50++;
			} else if (iznos - 20 >= suma && stanje[1] > 0) {
				suma += 20;
				stanje[1]--;
				br20++;
			} else if (iznos - 10 >= suma && stanje[0] > 0) {
				suma += 10;
				stanje[0]--;
				br10++;
			} else {
				// Ukoliko novcanice koje se nalaze u bankomatu nisu dovoljne da se isplati trazeni iznos
				System.out
						.println("Iznos od: "
								+ iznos
								+ " nije moguce isplatiti. Molimo vas da se javite na salter. Hvala!\n");
				break;
			}
		}
		// Kada je isplacena suma jednaka trazenom iznosu, potvrdjuje se isplata i snima novo stanje bankomata
		if (suma == iznos) {
			stanjeBankomata.setStanjeBankomata(stanje);
			// Cisto radi provjere
			System.out.println("Uspjesno je skinut iznos od: "
					+ (br10 * 10 + br20 * 20 + br50 * 50 + br100 * 100) + "KM");
			System.out.println("Isplaceno je:\n" + br10
					+ " novcanica od 10KM\n" + br20 + " novcanica od 20KM\n"
					+ br50 + " novcanica od 50KM\n" + br100
					+ " novcanica od 100KM\n");
			isComplete = true;
		}
		return isComplete;

	}
	
	/*
	 * Metoda koja trazi od korisnika da potvrdi unos
	 */
	boolean potvrdiUnos(){
		boolean odluka = false;
		boolean test = true;
		Scanner in = new Scanner(System.in);
		do{
			System.out.println("Izvrsiti?(DA/NE)");
			String karakter = in.next();
			if(karakter.equals("DA") || karakter.equals("da")){
				odluka = true;
				test = false;
			}
			if(karakter.equals("NE") || karakter.equals("ne")){
				test = false;
			}
			
		}while(test);
		return odluka;
	}

}
