package block;

import java.util.ArrayList;
import java.util.Random;

public class BlockChoicePseudoRandom extends BlockChoice{
	private int seed;
	public BlockChoicePseudoRandom(int num){
		seed = num;
	}
	@Override
	public ArrayList<Integer> fja_odaberi_blokove(ArrayList<double[][]> hadamard_mat) {
		
		ArrayList <Integer> niz = new ArrayList <Integer>();
		Random r = new Random(seed);
		int num_block = hadamard_mat.size();
		
		if(num_block >= 4){
			for(int i = 0; i < (num_block/4); i++){
				int the_choosen_one = r.nextInt(num_block);
				while(niz.contains(the_choosen_one)){
					the_choosen_one = r.nextInt(num_block);
				}
				niz.add(the_choosen_one);				
			}
		}
		else{
			for(int i = 0; i < num_block; i++){
				niz.add(i);
			}
		}
		
		return niz;
	}

}
