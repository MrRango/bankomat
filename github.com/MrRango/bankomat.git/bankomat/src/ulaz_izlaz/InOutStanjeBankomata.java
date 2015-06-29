package ulaz_izlaz;

import interfejs.StanjeBankomata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class InOutStanjeBankomata implements StanjeBankomata {

	private static int deset, dvadeset, pedeset, sto;

	// ucitavanje stanja bankomata iz fajla "stanjeBankomata.txt"
	@Override
	public void ucitajStanjeBankomata() throws Exception {
		File file = new File("stanjeBankomata.txt");

		Scanner in = new Scanner(file);

		while (in.hasNext()) {
			deset = in.nextInt();
			dvadeset = in.nextInt();
			pedeset = in.nextInt();
			sto = in.nextInt();
		}
		in.close();
	}

	// upisivanje novog stanja bankomata u fajl "stanjeBankomata.txt"
	private void sacuvajStanjeBankomata() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream("stanjeBankomata.txt",
					false));
			pw.append(deset + " " + dvadeset + " " + pedeset + " " + sto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	// izvjestaj o stanju bankomata
	@Override
	public String izvijestiOStanjuBankomata() {
		return "U bankomatu trenutno ima:\n" + deset + " novcanica od 10KM\n"
				+ dvadeset + " novcanica od 20KM\n" + pedeset
				+ " novcanica od 50KM\n" + sto + " novcanica od 100KM\n"
				+ "Ukupna suma novca kojom bankomat raspolaze: "
				+ vratiUkupno() + "KM\n";
	}

	// preuzimanje stanja bankomata
	@Override
	public int[] getStanjeBankomata() {
		int[] stanje = { deset, dvadeset, pedeset, sto, vratiUkupno()};
		return stanje;
	}
	
	// postavnjanje novog stanja bankomata
	@Override
	public void setStanjeBankomata(int[]stanje){
		deset = stanje[0];
		dvadeset = stanje[1];
		pedeset = stanje[2];
		sto = stanje[3];
		sacuvajStanjeBankomata();
	}

	// metoda koja racuna koliko ukupno ima novca u bankomatu
	private int vratiUkupno() {
		return (deset * 10) + (dvadeset * 20) + (pedeset * 50) + (sto * 100);
	}

}
