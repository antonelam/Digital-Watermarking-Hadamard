package out;

import java.util.ArrayList;

import alpha.Alpha;
import asset.Image;
import block.BlockChoice;
import block.BlockChoiceAll;
import coef.CoefChoice;
import coef.CoefChoiceAll;

public class Extract_1 extends Extract{
	
	@Override
	public void extracting(Image img, Image w, BlockChoice b, CoefChoice pikseli, Alpha a){		
		ArrayList<Integer> blk = b.fja_odaberi_blokove(img.get_nizmat());
		ArrayList<Double> koefW = new ArrayList<Double>();

		for(int i: blk){
			ArrayList<int[]> p = pikseli.fja_odabir_koef_blok(img.get_hadmat().get(i), img.get_red());
			double alpha = a.fja_alpha(img.get_komadi()[i], img.get_hadmat().get(i));
			for(int[] j: p){
				koefW.add(img.get_hadmat().get(i)[j[0]][j[1]] / alpha);
				
			}
		}
		
		//zig
		int count = 0;
		BlockChoice blokoviW = new BlockChoiceAll();
		CoefChoice pikseliW = new CoefChoiceAll();
		
		ArrayList<Integer> blkW = blokoviW.fja_odaberi_blokove(w.get_hadmat());
		
		for(int rb: blkW){
			ArrayList<int[]> p = pikseliW.fja_odabir_koef_blok(w.get_hadmat().get(rb), w.get_red());
			for(int[] koo: p){
				if(count < koefW.size()){
					w.get_hadmat().get(rb)[koo[0]][koo[1]] = koefW.get(count);
				}
				count ++;
			}
		}
		
	}

}
