package coef;
import java.util.ArrayList;

public class CoefChoiceMore extends CoefChoice{
	private int N;
	
	public CoefChoiceMore(){
		this.N = 16;
	}
	
	@Override
	public ArrayList<int[]> fja_odabir_koef_blok(double[][] blok, int red) {
		ArrayList <int[]> piksel = new ArrayList <int[]>();

		int mat_size = (int) Math.pow(2, red) * (int) Math.pow(2, red);
		int n1 = (int) Math.pow(2, red) - 1;
		int n2 = (int) Math.pow(2, red) - 1;
		boolean trigger = false;
		int i = 0;

		if( this.N >= mat_size )
			this.N = mat_size - 1;//we don't want to include DC
		
		while(piksel.size() < this.N){
			
			if(n1 == (int) Math.pow(2, red) - 1 && n2 == 0){
				trigger = true;
				i -= 2;
			}
			if( i == 0 ){
				int [] n = {n1,n2};
				piksel.add(n);
				
				}
			else if( i % 2 == 0 ){
				if(!trigger)
					n2 -= 1;
				else
					n1 -= 1;

				int [] n = {n1,n2};
				piksel.add(n);
				
				for( int j = i; j > 0; j--){
					n1 -= 1;
					n2 += 1;
					if(piksel.size() < this.N){
						int [] n_ = {n1,n2};
						piksel.add(n_);
					}
					else
						break;
				}
			}
			else{
				if(!trigger)
					n1 -= 1;
				else
					n2 -= 1;

				int [] n = {n1,n2};
				piksel.add(n);
				
				for( int j = i; j > 0; j--){
					n1 += 1;
					n2 -= 1;
					if(piksel.size() < this.N){
						int [] n_ = {n1,n2};
						piksel.add(n_);
					}
					else
						break;
				}
			}
			if(!trigger)
				i++;
			else
				i--;
		}
		/*for(int j = 0; j < piksel.size(); j++){
			System.out.println(piksel.get(j)[0]+" "+piksel.get(j)[1]);
			
		}*/
		return piksel;
		/*for(int j = 0; j < (int)Math.pow(2,red); j++){
			for(int k = 0; k < (int)Math.pow(2,red); k++){
				if((0 < (Math.abs(blok[j][k])))
						&& ((Math.abs(blok[j][k])) < 100)
						&&(j!=0)&&(k!=0))
				{
					if(piksel.size() == N)
						break;
					int [] n = {j,k};
					piksel.add(n);
				}
			}
		}
		return piksel;*/
	}
	
	public void set_number(int N){
		this.N = N;
	}

}
