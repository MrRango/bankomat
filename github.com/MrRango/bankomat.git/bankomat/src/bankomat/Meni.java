package bankomat;

import java.util.Scanner;

import interfejs.StanjeBankomata;
import ulaz_izlaz.InOutStanjeBankomata;

public class Meni {

	BankomatContext bankCon = new BankomatContext();
	StanjeBankomata stanje = new InOutStanjeBankomata();
	private boolean isOn = true;

	Meni() {

	}

	public void ispisiAdminMeni() throws Exception {
		while (isOn) {
			System.out.println("*****ADMIN MENI*****\n"
					+ "[1] Dodajte novog korisnika\n"
					+ "[2] Izbrisite korisnika\n"
					+ "[3] Provjerite kolicinu novca u bankomatu\n"
					+ "[4] Promjenite broj novcanica u bankomatu\n" 
					+ "[5] Izlogujte se\n"
					+ "********************");
			stanje.ucitajStanjeBankomata();
			int[]stanjeBankomata = stanje.getStanjeBankomata();
			if(stanjeBankomata[0] < 15 || stanjeBankomata[1] < 15 || stanjeBankomata[2] < 15 || stanjeBankomata[3] < 15){
				System.out.println("**************UPOZORENJE*************\nProvjerite broj novcanica u bankomatu\n*************************************\n");
			}
			System.out.println("Izaberite zeljenu opciju");

			Scanner in = new Scanner(System.in);
			int izbor = in.nextInt();

			switch (izbor) {
			case 1: {
				bankCon.addUser();
				break;
			}
			case 2: {
				bankCon.removeUser();
				break;
			}
			case 3: {
				stanje.ucitajStanjeBankomata();
				String a = stanje.izvijestiOStanjuBankomata();
				System.out.println(a);
				break;
			}
			case 4: {
				bankCon.postaviStanjeRacuna();
				break;
			}
			case 5: {
				isOn = false;
				break;
			}
			default:
				break;
			}
		}
	}

	public void ispisiKorisnickiMeni() throws Exception {
		while(isOn){
			System.out.println("***KORISNICKI MENI***\n"
					+ "[1] Provjerite stanje na racunu\n" 
					+ "[2] Podignite novac\n"
					+ "[3] Izlogujte se\n" + "*********************");
			System.out.println("Izaberite zeljenu opciju");
			Scanner in = new Scanner(System.in);
			int izbor = in.nextInt();

			switch (izbor) {
			case 1: {
				bankCon.provjeriStanjeRacuna();;
				break;
			}
			case 2: {
				bankCon.podigniSaRacuna();;
				break;
			}
			case 3: {
				isOn = false;
				break;
			}
			default:
				break;
			}
		}
	}
}
