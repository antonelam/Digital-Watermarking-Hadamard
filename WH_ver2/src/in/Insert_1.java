package in;

import java.util.ArrayList;

import alpha.Alpha;
import asset.Image;
import block.BlockChoice;
import block.BlockChoiceAll;
import coef.CoefChoice;
import coef.CoefChoiceAll;

public class Insert_1 extends Insert{
	
	@Override
	public void inserting(Image img, Image w, BlockChoice b, CoefChoice pikseli, Alpha a){
		//zig
		BlockChoice blokoviW = new BlockChoiceAll();
		CoefChoice pikseliW = new CoefChoiceAll();
		
		ArrayList<Integer> blkW = blokoviW.fja_odaberi_blokove(w.get_hadmat());
		ArrayList<Double> koefW = new ArrayList<Double>();
		for(int rb: blkW){
			ArrayList<int[]> p = pikseliW.fja_odabir_koef_blok(w.get_hadmat().get(rb), w.get_red());
			for(int[] koo: p){
				koefW.add(w.get_hadmat().get(rb)[koo[0]][koo[1]]);
			}
		}

		//slika
		int count = 0;
		ArrayList<Integer> popis = new ArrayList<Integer>();
		//double alpha = a.fja_alpha(img.get_nizmat());
		
		ArrayList<Integer> blk = b.fja_odaberi_blokove(img.get_nizmat());

		for(int i: blk){
			ArrayList<int[]> p = pikseli.fja_odabir_koef_blok(img.get_hadmat().get(i), img.get_red());
			double alpha = a.fja_alpha(img.get_komadi()[i], img.get_hadmat().get(i));
			//System.out.println(alpha);
			for(int[] j: p){
				if(count < koefW.size()){
						img.get_hadmat().get(i)[j[0]][j[1]] = alpha * koefW.get(count);
					}
				count ++;
				}				
			}
		
		//System.out.println(popis.size());
		}

}
