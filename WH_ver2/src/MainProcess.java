
import java.io.IOException;
import java.util.ArrayList;

import alpha.Alpha;
import asset.Image;
import attack.Attack;
import block.BlockChoice;
import coef.CoefChoice;
import in.Insert;
import out.Extract;

public class MainProcess {
	
	static public void startIn(Insert i, BlockChoice b, CoefChoice p, Alpha a,
			int red_s, int red_w, String path_s, String path_w,
			String save, String extension) throws IOException{
		
		Image img = i.preprocessing(red_s, path_s);
		Image w = i.preprocessing(red_w, path_w);
		
		i.inserting(img, w, b, p, a);
		
		i.postprocessing(img, save, extension);
	}
	
	static public void startOut(Extract e, BlockChoice b, CoefChoice p, Alpha a,
			int red_s, int red_w, String path_s, String path_w,
			String save, String extension) throws IOException{
		
		Image img = e.preprocessing(red_s, path_s);
		Image w = e.preprocessing(red_w, path_w);
		
		e.extracting(img, w, b, p, a);
		
		e.postprocessing(w, save, extension);
	}
	
	static public void startAttack(String path){
		Attack.crop_left_h((Attack.preprocessing(path)));
	}

}
