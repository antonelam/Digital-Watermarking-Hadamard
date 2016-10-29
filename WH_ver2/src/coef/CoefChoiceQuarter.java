package coef;

import java.util.ArrayList;

public class CoefChoiceQuarter extends CoefChoice{

	@Override
	public ArrayList<int[]> fja_odabir_koef_blok(double[][] blok, int red) {
		ArrayList <int[]> piksel = new ArrayList <int[]>();
		int duzina = (int)Math.pow(2,red);
		
		for(int j = (duzina/2); j < duzina; j++){
			for(int k = (duzina/2); k < duzina; k++){
				int [] n = {j,k};
				piksel.add(n);
			}
		}
		return piksel;
	}

	@Override
	public void set_number(int N) {
		// TODO Auto-generated method stub
		
	}

}
