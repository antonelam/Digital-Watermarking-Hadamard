package out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import alpha.Alpha;
import asset.Image;
import block.*;
import coef.*;

public class Extract {
	public Image preprocessing(int red, String path) throws IOException{
		Image img = new Image(red, path);
		
		img.start();
		
		return img;
	};
	public void extracting(Image img, Image w, BlockChoice b, CoefChoice pikseli, Alpha a){
		ArrayList<Integer> blk = new ArrayList<Integer>();
		Class<? extends BlockChoice> s = b.getClass();
		String name = s.getName();
		if(name.equalsIgnoreCase("block.BlockChoiceEntropy")){
			try{
				BufferedReader reader = new BufferedReader(new FileReader(new File("entropy.txt")));
				int size = (img.get_nizmat().size() - 1);
				Stream<String> stream = reader.lines();
				java.util.Iterator<String> iterator = stream.iterator();
				while(iterator.hasNext()){
					int num = Integer.parseInt(iterator.next());
					if(num <= size)
						blk.add(num);
				}
				reader.close();
			}
			catch(IOException e){
				
			}
		}
		else
			blk = b.fja_odaberi_blokove(img.get_nizmat());
		
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
	

	public void postprocessing(Image img, String path, String extension){
		img.start(path, extension);
		
	}

}
