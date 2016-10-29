package alpha;
import java.awt.image.BufferedImage;

public class AlphaManual extends Alpha{
	private double a = 0.5;
	
	@Override
	public void set_alpha(double alpha) {
		this.a = alpha;
	}

	@Override
	public double fja_alpha(BufferedImage org_block, double[][] coef_block) {
		// TODO Auto-generated method stub
		return a;
	}

}
