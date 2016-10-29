package coef;
import java.util.ArrayList;

public class CoefChoiceOne extends CoefChoice{
	
	public ArrayList<int[]> fja_odabir_koef_blok(double[][] blok, int red){
		ArrayList <int[]> piksel = new ArrayList <int[]>();
		int [] n = new int[2];
		
		double max = Double.MIN_VALUE;
		for(int j = 0; j < (int)Math.pow(2,red); j++){
			for(int k = 0; k < (int)Math.pow(2,red); k++){
				if((max < (Math.abs(blok[j][k])))&&(j!=0)&&(k!=0)){
					max = blok[j][k];
					n[0] = j; n[1] = k;
				}
			}
		}
		//System.out.println(Integer.toString(n[0])+' '+Integer.toString(n[1])+"d");
		int[] k = new int[2];
		k[0] = n[0];
		k[1] = n[1];
		//int [] k = {1,1};
		piksel.add(k);
	
		/*for(int[] p : piksel){
			System.out.println(Integer.toString(p[0])+' '+Integer.toString(p[1]));
		}*/
		return piksel;
	}

	@Override
	public void set_number(int N) {
		// TODO Auto-generated method stub
		
	}

}
