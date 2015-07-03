/*
 * Implementira interfejs ListaKorisnika
 * Klasa za rad sa fajlom listaKorisnika.txt
 */

package ulaz_izlaz;

import interfejs.ListaKorisnika;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import korisnik.User;

public class InOutListaKorisnika implements ListaKorisnika{

	ArrayList <User> listaKorisnika = new ArrayList<>();
	int i = 0;
	
	/*
	 * (non-Javadoc)
	 * @see interfejs.ListaKorisnika#ucitajListuKorisnika()
	 * 
	 *  Ucitavanje liste korisnika iz fajla "listaKorisnika.txt"
	 */
	@Override
	public void ucitajListuKorisnika() throws Exception{
		File file = new File("listaKorisnika.txt");
		
		Scanner in = new Scanner(file);
		
		while(in.hasNext()){
			User u = new User(in.next(), in.next(), in.nextBoolean(), in.nextDouble());
			listaKorisnika.add(i, u);
			i++;
		}
		
		in.close();
		
	}
	
	/*
	 * Upisivanje liste korisnika u fajl "listaKorisnika.txt"
	 */
	private void sacuvajListuKorisnika() throws FileNotFoundException{
		// Brisanje sadrzaja fajla prije novog upisa
		PrintWriter writer = new PrintWriter("listaKorisnika.txt");
		writer.print("");
		writer.close();
		
		String username, password;
		boolean isAdmin;
		double amount;
		// Vadjenje podataka iz liste korisnika i upisivanje u fajl
		for (int i = 0; i < listaKorisnika.size(); i++){
			username = listaKorisnika.get(i).getUsername();
			password = listaKorisnika.get(i).getPassword();
			isAdmin = listaKorisnika.get(i).isAdmin();
			amount = listaKorisnika.get(i).getAmount();
		
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new FileOutputStream("listaKorisnika.txt",
						true));
				pw.append(username + " " + password + " " + isAdmin + " " + amount + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pw.close();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see interfejs.ListaKorisnika#getListuKorisnika()
	 * 
	 * Metoda za preuzimanje trenutne liste korisnika
	 */
	@Override
	public ArrayList<User> getListuKorisnika(){
		return listaKorisnika;
	}
	
	/*
	 * (non-Javadoc)
	 * @see interfejs.ListaKorisnika#setListuKorisnika(java.util.ArrayList)
	 * 
	 * Metoda za postavljanje nove liste korisnika i upisivanje liste korisnika
	 * u fajl pozivanjem metode sacuvajListuKorisnika()
	 */
	@Override
	public void setListuKorisnika(ArrayList<User>newListaKorisnika) throws FileNotFoundException{
		listaKorisnika = newListaKorisnika;
		sacuvajListuKorisnika();
	}
	
}
