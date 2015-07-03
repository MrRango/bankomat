/*
 * Interfejs StanjeBankomata, implementiran u klasi InOutStanjeBankomata
 * Sadrzi metode za rad sa novcem u bankomatu
 */

package interfejs;

public interface StanjeBankomata {
	
	public void ucitajStanjeBankomata()throws Exception;
	
	public String izvijestiOStanjuBankomata();
	
	public int[] getStanjeBankomata();
	
	public void setStanjeBankomata(int[]stanje);

}
