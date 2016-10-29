package alpha;
import java.awt.image.BufferedImage;

public abstract class Alpha {
	//alfa za jedan blok
	public abstract double fja_alpha(BufferedImage org_block, double[][] coef_block);
	public abstract void set_alpha(double alpha);
}
