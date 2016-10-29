package alpha;

import java.awt.image.BufferedImage;

public class AlphaTexture extends Alpha{

	@Override
	public void set_alpha(double alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double fja_alpha(BufferedImage org_block, double[][] coef_block) {
		double sum = 0;
		for(double[] dd : coef_block){
			for(double d : dd){
				sum += (d*d);
			}
		}
		
		long x = 1000;
		double y = 0.1;
		
		while( (sum < x) && (y <= 1) ){
			x*=10;
			y+=0.05;
		}
		
		return y;
		
		
	}
	
}
