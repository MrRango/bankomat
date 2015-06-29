package bankomat;

import interfejs.Bankomat;


public class App {

	public static void main(String[] args) throws Exception {
		
		Bankomat bankomat = new BankomatContext();
		bankomat.pokreniBankomat();
		
	}

}
