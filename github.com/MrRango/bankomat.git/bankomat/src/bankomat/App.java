package bankomat;

import interfejs.Bankomat;


public class App {

	public static void main(String[] args) throws Exception {
		// pravljenje novog objekta bankomat i pokretanje rada
		Bankomat bankomat = new BankomatContext();
		bankomat.pokreniBankomat();
		
	}

}
