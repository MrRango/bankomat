package interfejs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import korisnik.User;

public interface ListaKorisnika {
	
	public void ucitajListuKorisnika() throws Exception;
	
	public ArrayList<User> getListuKorisnika();
	
	public void setListuKorisnika(ArrayList<User>newListaKorisnika)throws FileNotFoundException;

}
