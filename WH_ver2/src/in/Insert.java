package in;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import alpha.*;
import asset.Image;
import block.*;
import coef.*;

public class Insert {
	
	public Image preprocessing(int red, String path) throws IOException{
		Image img = new Image(red, path);
		
		img.start();
		
		return img;
	};
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
		ArrayList<Integer> blk = b.fja_odaberi_blokove(img.get_nizmat());
		
		Class<? extends BlockChoice> s = b.getClass();
		String name = s.getName();
		if(name.equalsIgnoreCase("block.BlockChoiceEntropy")){
			try{
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("entropy.txt")));
				for(int i = 0; i < blk.size(); i++){
					writer.write(String.valueOf(blk.get(i)));
					writer.newLine();
				}
				writer.close();
			}
			catch(IOException e){
				
			}
		}

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

	public void postprocessing(Image img, String path, String extension){
		
		img.start(path, extension);
		
	};

}
