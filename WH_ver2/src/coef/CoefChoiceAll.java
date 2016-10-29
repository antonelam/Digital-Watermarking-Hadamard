package coef;
import java.util.ArrayList;

public class CoefChoiceAll extends CoefChoice {
	
	public ArrayList<int[]> fja_odabir_koef_blok(double[][] blok, int red){
		ArrayList <int[]> piksel = new ArrayList <int[]>();
		
		for(int j = 0; j < (int)Math.pow(2,red); j++){
			for(int k = 0; k < (int)Math.pow(2,red); k++){
				if((j!=0)||(k!=0)){
					int [] n = {j,k};
					piksel.add(n);
				}
			}
		}
		return piksel;
	}

	@Override
	public void set_number(int N) {
		// TODO Auto-generated method stub
		
	}

}
