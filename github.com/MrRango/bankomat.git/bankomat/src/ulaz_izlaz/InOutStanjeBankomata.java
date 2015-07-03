/*
 * Implementira interfejs StanjeBankomata
 * Klasa za rad sa fajlom stanjeBankomata.txt
 */

package ulaz_izlaz;

import interfejs.StanjeBankomata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class InOutStanjeBankomata implements StanjeBankomata {

	private static int deset, dvadeset, pedeset, sto;

	/*
	 * (non-Javadoc)
	 * @see interfejs.StanjeBankomata#ucitajStanjeBankomata()
	 * 
	 * Ucitavanje stanja bankomata iz fajla "stanjeBankomata.txt"
	 */
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

	/*
	 * Upisivanje novog stanja bankomata u fajl "stanjeBankomata.txt"
	 */
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

	/*
	 * (non-Javadoc)
	 * @see interfejs.StanjeBankomata#izvijestiOStanjuBankomata()
	 * 
	 * Metoda za ispis trenutnog o stanja bankomata
	 * Ispisuje koliko ima novcanica i kolika je vrijednost svih novcanica zajedno
	 */
	@Override
	public String izvijestiOStanjuBankomata() {
		return "U bankomatu trenutno ima:\n" + deset + " novcanica od 10KM\n"
				+ dvadeset + " novcanica od 20KM\n" + pedeset
				+ " novcanica od 50KM\n" + sto + " novcanica od 100KM\n"
				+ "Ukupna suma novca kojom bankomat raspolaze: "
				+ vratiUkupno() + "KM\n";
	}

	/*
	 * (non-Javadoc)
	 * @see interfejs.StanjeBankomata#getStanjeBankomata()
	 * 
	 * Metoda za preuzimanje trenutnog stanja bankomata
	 */
	@Override
	public int[] getStanjeBankomata() {
		int[] stanje = { deset, dvadeset, pedeset, sto, vratiUkupno()};
		return stanje;
	}
	
	/*
	 * (non-Javadoc)
	 * @see interfejs.StanjeBankomata#setStanjeBankomata(int[])
	 * 
	 * Metoda za postavnjanje novog stanja bankomata
	 */
	@Override
	public void setStanjeBankomata(int[]stanje){
		deset = stanje[0];
		dvadeset = stanje[1];
		pedeset = stanje[2];
		sto = stanje[3];
		sacuvajStanjeBankomata();
	}

	/*
	 * Metoda koja racuna koliko ukupno ima novca u bankomatu
	 */
	private int vratiUkupno() {
		return (deset * 10) + (dvadeset * 20) + (pedeset * 50) + (sto * 100);
	}

}
