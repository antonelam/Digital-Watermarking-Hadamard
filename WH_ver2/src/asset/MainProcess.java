package asset;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JProgressBar;

import alpha.Alpha;
import asset.Image;
import block.BlockChoice;
import block.BlockChoiceEntropy;
import coef.CoefChoice;
import in.Insert;
import out.Extract;

public class MainProcess {
	
	static public void startIn(JProgressBar bar, Insert i, BlockChoice b, CoefChoice p, Alpha a,
			int red_s, int red_w, String path_s, String path_w,
			String save, String extension) throws IOException{
		
		Image img = i.preprocessing(red_s, path_s);
		Image w = i.preprocessing(red_w, path_w);
		
		bar.setValue(20);
		
		i.inserting(img, w, b, p, a);
		
		bar.setValue(75);
		
		i.postprocessing(img, save, extension);
		
		bar.setValue(95);
	}
	
	static public void startOut(JProgressBar bar, Extract e, BlockChoice b, CoefChoice p, Alpha a,
			int red_s, int red_w, String path_s, String path_w,
			String save, String extension) throws IOException{
		
		Image img = e.preprocessing(red_s, path_s);
		Image w = e.preprocessing(red_w, path_w);
		
		bar.setValue(20);
		
		e.extracting(img, w, b, p, a);
		
		bar.setValue(75);
		
		e.postprocessing(w, save, extension);
		
		bar.setValue(95);
	}

	static public double mean_square_error(String bb, String ww){
		try{
		BufferedImage bufImg = Image.fja_ulaz_slika(bb);
		BufferedImage buf = Image.fja_ulaz_slika(ww);
		
		double MSE_r = 0;
		double MSE_g = 0;
		double MSE_b = 0;
		if((bufImg.getHeight()==buf.getHeight())&&(bufImg.getWidth()==buf.getWidth())){
			for(int i = 0; i < bufImg.getWidth(); i++){
				for(int j = 0; j < bufImg.getHeight(); j++){
					int r = (bufImg.getRGB(i, j)>>16)&0xff;
					int g = (bufImg.getRGB(i, j)>>8)&0xff;
					int b = (bufImg.getRGB(i, j))&0xff;
					
					int r_ = (buf.getRGB(i, j)>>16)&0xff;
					int g_ = (buf.getRGB(i, j)>>8)&0xff;
					int b_ = (buf.getRGB(i, j))&0xff;
					
					
					MSE_r += Math.pow(r - r_, 2);
					MSE_g += Math.pow(g - g_, 2);
					MSE_b += Math.pow(b - b_, 2);
				}
			}
			int mn = bufImg.getHeight() * bufImg.getWidth();
			
			double MSE = (MSE_r + MSE_g + MSE_b)/(3*mn);
			
			return MSE;
		}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	static public double peak_signal_to_noise_ratio(double MSE){
		double dB = 10*Math.log10(Math.pow(255, 2)/MSE);
		
		return dB;
	}
	
	static public double normalized_correlation(String bb, String ww){
		try{
		BufferedImage b = Image.fja_ulaz_slika(bb);
		BufferedImage w = Image.fja_ulaz_slika(ww);
		
		
		double b_sum_R = 0;double b_sum_G = 0;double b_sum_B = 0;
		double w_sum_R = 0;double w_sum_G = 0;double w_sum_B = 0;
		double b_w_R = 0;double b_w_G = 0;double b_w_B = 0;
		
		/*double[][] bY = new double[b.getWidth()][b.getHeight()];
		double[][] wY = new double[w.getWidth()][w.getHeight()];*/
		for(int i = 0; i < b.getWidth(); i++){
			for(int j = 0; j < b.getHeight(); j++){
				/*bY[i][j] = Image.RGBtoYUV(b.getRGB(i, j))[0];
				wY[i][j] = Image.RGBtoYUV(w.getRGB(i, j))[0];*/
					b_sum_R += Math.pow(Image.RGBtoYUV(b.getRGB(i, j))[0],2);
					b_sum_G += Math.pow(Image.RGBtoYUV(b.getRGB(i, j))[1],2);
					b_sum_B += Math.pow(Image.RGBtoYUV(b.getRGB(i, j))[2],2);
					
					w_sum_R += Math.pow(Image.RGBtoYUV(w.getRGB(i, j))[0],2);
					w_sum_G += Math.pow(Image.RGBtoYUV(w.getRGB(i, j))[1],2);
					w_sum_B += Math.pow(Image.RGBtoYUV(w.getRGB(i, j))[2],2);
					
					b_w_R += (Image.RGBtoYUV(b.getRGB(i, j))[0]*Image.RGBtoYUV(w.getRGB(i, j))[0]);
					b_w_G += (Image.RGBtoYUV(b.getRGB(i, j))[1]*Image.RGBtoYUV(w.getRGB(i, j))[1]);
					b_w_B += (Image.RGBtoYUV(b.getRGB(i, j))[2]*Image.RGBtoYUV(w.getRGB(i, j))[2]);
				
			}
		}
		/*double bE = BlockChoiceEntropy.entropy_calc(BlockChoiceEntropy.histogram_count(bY),b.getWidth()*b.getHeight());
		System.out.println(bE);
		double wE = BlockChoiceEntropy.entropy_calc(BlockChoiceEntropy.histogram_count(wY),w.getWidth()*w.getHeight());
		System.out.println(wE);
		double bwE = joint_entropy_calc(joint_hist_count(bY,wY), b.getWidth()*b.getHeight());
		System.out.println(bwE);
		return ((bE + wE) - bwE)/(bE);*/
		b_sum_R = Math.sqrt(b_sum_R);
		b_sum_G = Math.sqrt(b_sum_G);
		b_sum_B = Math.sqrt(b_sum_B);
		
		w_sum_R = Math.sqrt(w_sum_R);
		w_sum_G = Math.sqrt(w_sum_G);
		w_sum_B = Math.sqrt(w_sum_B);
		
		double NC_R = (b_w_R / (b_sum_R * w_sum_R));
		double NC_G = (b_w_G / (b_sum_G * w_sum_G));
		double NC_B = (b_w_B / (b_sum_B * w_sum_B));
		/*System.out.println(NC_R);
		System.out.println(NC_G);
		System.out.println(NC_B);*/
		if (b.getType() == BufferedImage.TYPE_BYTE_GRAY)
			return NC_R;
		return ( (NC_R + NC_G + NC_B) / 3 );
		//return NC_R;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	static public int[][] joint_hist_count(double[][] blokA, double[][] blokB){
		int[][] hist_c = new int[256][256];
		for(int i = 0; i < blokA.length; i++){
			for(int j = 0; j < blokA.length; j++){
				hist_c[(int) blokA[i][j]][(int) blokB[i][j]] += 1;
			}
		}
		return hist_c;		
	}
	static public double joint_entropy_calc(int[][] p, int size){
		double suma = 0;
		for(int i = 0; i < p.length; i++){
			for(int j = 0; j < p.length; j++){
				if(p[i][j]!=0){
					double x = (p[i][j] / (double)size);
					//System.out.println(p[i][j]);
					suma -= (x * Math.log(x));
				}
			}
		}
		return suma;
	}
	
	static public String verify(double num){
		if(num > 0.79)
			return "High similarity. Extracted watermark has been successfully verified.";
		if(num > 0.59)
			return "Medium similarity. Extracted watermark has been successfully verified.";
		if(num > 0.49)
			return "Low similarity. Extracted watermark has been verified.";
		return "No similarity. Extracted watermark cannot be verified.";
	}
}
