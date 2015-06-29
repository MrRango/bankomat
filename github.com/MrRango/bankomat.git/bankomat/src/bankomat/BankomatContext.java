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

	public BankomatContext() {

	}

	@Override
	public void pokreniBankomat() throws Exception {
		while (isOn) {
			System.out.print("----------------------------\n"
					+ "Unesite vase korisnicko ime: ");
			Scanner in = new Scanner(System.in);

			String inUser = in.nextLine();

			traziKorisnika(inUser);
			if (user == null) {
				System.out
						.println("Vase korisnicko ime se ne nalazi u nasoj bazi!");
				System.out.println();
			} else {
				corentUser = user;
				Meni meni = new Meni();
				System.out.println("Unesite password: ");
				String inPassword = in.nextLine();
				if (inPassword.equals(corentUser.getPassword())) {
					if (user.isAdmin()) {
						meni.ispisiAdminMeni();

					} else {
						meni.ispisiKorisnickiMeni();
					}
				}else{
					System.out.println("Unijeli ste pogresan password");
				}

			}
		}

	}

	// trazi korisnika
	public User traziKorisnika(String inUser) throws Exception {

		ArrayList<User> listaKorisnika = new ArrayList<>();

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

	// dodaje novog korisnika
	void addUser() throws Exception {

		ArrayList<User> listaKorisnika = new ArrayList<>();
		ListaKorisnika lista = new InOutListaKorisnika();
		lista.ucitajListuKorisnika();
		listaKorisnika = lista.getListuKorisnika();

		User u = new User();

		Scanner in = new Scanner(System.in);

		System.out.println("**Unosenje novog korisnika**");
		System.out.println("Unesite username: ");
		u.setUsername(in.nextLine());
		System.out.println("Unesite password: ");
		u.setPassword(in.nextLine());
		System.out.println("Da li je korisnik administrator? ");
		u.setAdmin(in.nextBoolean());
		if (!u.isAdmin()) {
			System.out.println("Unesite kolicinu novca na racunu: ");
			u.setAmount(in.nextDouble());
			System.out.println();
		}

		listaKorisnika.add(u);
		lista.setListuKorisnika(listaKorisnika);

	}

	// uklanja korisnika
	void removeUser() throws Exception {

		System.out.println("***Brisanje korisnika***");
		System.out
				.println("Unesite username korisnika kojeg zelite da izbrisete: ");

		Scanner in = new Scanner(System.in);
		String inUser = in.nextLine();
		
		ArrayList<User> listaKorisnika = new ArrayList<>();
		ListaKorisnika lista = new InOutListaKorisnika();
		lista.ucitajListuKorisnika();
		listaKorisnika = lista.getListuKorisnika();

		for (int i = 0; i < listaKorisnika.size(); i++) {

			if (inUser.equals(listaKorisnika.get(i).getUsername())
					&& !(inUser.equals(corentUser.getUsername()))) {
				listaKorisnika.remove(i);
				break;
			}
		}
		lista.setListuKorisnika(listaKorisnika);
		System.out.println();
	}

	// mijenja broj novcanica u bankomatu
	public void postaviStanjeRacuna() {
		StanjeBankomata stanjeBankomata = new InOutStanjeBankomata();
		int[] stanje = new int[4];
		System.out.println("***Postavljanje stanja bankomata***");
		Scanner in = new Scanner(System.in);
		System.out.println("Broj novcanica od 10KM: ");
		stanje[0] = in.nextInt();
		System.out.println("Broj novcanica od 20KM");
		stanje[1] = in.nextInt();
		System.out.println("Broj novcanica od 50KM");
		stanje[2] = in.nextInt();
		System.out.println("Broj novcanica od 100KM");
		stanje[3] = in.nextInt();
		System.out.println();
		stanjeBankomata.setStanjeBankomata(stanje);
	}

	// ispisuje koliko korisnik ima novca na bankomatu
	public void provjeriStanjeRacuna() throws Exception {
		System.out.println("***Provjera stanja racuna***");
		System.out.println("Trenutno na racunu imate: "
				+ corentUser.getAmount() + "KM");
		System.out.println();
	}

	// podizanje novca sa racuna i isplata
	public void podigniSaRacuna() throws Exception {
		User user = null;
		
		ArrayList<User> listaKorisnika = new ArrayList<>();
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
		if (iznos % 10 != 0 || iznos > stanje[4]
				|| iznos > corentUser.getAmount()) {
			System.out.println("Unijeli ste iznos koji se ne moze isplatiti\n");
		} else {
			if (skiniSaRacuna(iznos)) {
				for (int i = 0; i < listaKorisnika.size(); i++) {
					user = listaKorisnika.get(i);
					if (user.getUsername().equals(corentUser.getUsername())) {
						listaKorisnika.get(i).setAmount(
								listaKorisnika.get(i).getAmount() - iznos);
						lista.setListuKorisnika(listaKorisnika);
						corentUser = user;
					}
				}
			}
		}
	}

	private boolean skiniSaRacuna(int iznos) throws Exception {
		boolean isComplete = false;
		StanjeBankomata stanjeBankomata = new InOutStanjeBankomata();
		stanjeBankomata.ucitajStanjeBankomata();
		int[] stanje = stanjeBankomata.getStanjeBankomata();
		int br10 = 0, br20 = 0, br50 = 0, br100 = 0, suma = 0;
		while (suma != iznos) {
			if (iznos - 100 >= suma && stanje[3] > 0) {
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
				System.out
						.println("Iznos od: "
								+ iznos
								+ " nije moguce isplatiti. Molimo vas da se javite na salter. Hvala!\n");
				break;
			}
		}

		if (suma == iznos) {
			stanjeBankomata.setStanjeBankomata(stanje);
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

}
