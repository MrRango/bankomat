/*
 * Interfejs ListaKorisnika, implementiran u klasi InOutListaKorisnika
 * Sadrzi metode za ucitavanje i upisivanje korisnika u fajl
 */

package interfejs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import korisnik.User;

public interface ListaKorisnika {
	
	public void ucitajListuKorisnika() throws Exception;
	
	public ArrayList<User> getListuKorisnika();
	
	public void setListuKorisnika(ArrayList<User>newListaKorisnika)throws FileNotFoundException;

}
