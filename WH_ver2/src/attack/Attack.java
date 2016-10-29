package attack;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import asset.GaussianFilter;
import javaxt.io.Image;


public class Attack {
	
	static public Image preprocessing(String path){
		File imgPth = new File(path);
		Image bufImg = new Image(imgPth);
		
		return bufImg;
	};
	
	static public Image clock_rotation(Image i){		
		i.rotateClockwise();
		return i;
	}
	
	static public Image counter_rotation(Image i){
		i.rotateCounterClockwise();
		return i;
	}
	
	static public Image sharpen(Image i){
		i.sharpen();
		return i;
	}
	
	static public Image scale(Image i, int num){	
		if(num<0){
			num -= 1; 
			i.resize(i.getWidth()/Math.abs(num),i.getHeight()/Math.abs(num), false);
		}
		else if(num>0){
			num += 1;
			i.resize(i.getWidth()*num,i.getHeight()*num, false);
		}
		return i;
	}
	
	static public Image crop_left_h(Image i){
		Image c = i.copyRect(i.getWidth()/2,0,i.getWidth()/2,i.getHeight());
		return c;
	}
	static public Image crop_right_h(Image i){
		Image c = i.copyRect(0,0,i.getWidth()/2,i.getHeight());
		return c;
	}
	static public Image crop_top_v(Image i){
		Image c = i.copyRect(0,i.getHeight()/2,i.getWidth(),i.getHeight()/2);
		return c;
	}
	static public Image crop_bottom_v(Image i){
		Image c = i.copyRect(0,0,i.getWidth(),i.getHeight()/2);
		return c;
	}
	static public Image crop_out(Image i){
		
		Image c = i.copyRect(i.getWidth()/4,i.getHeight()/4,i.getWidth()/2,i.getHeight()/2);
		return c;
	}
	
	static public Image gauss(Image i, double num){
		 GaussianFilter blur = new GaussianFilter();
		 blur.setStandardDeviation(num);
		 BufferedImage o = blur.filter(i.getBufferedImage());
		 Image r = new Image(o);
		 return r;
	}
	@SuppressWarnings("resource")
	static public void jpeg_compression(String path) throws IOException{

        	File imageFile = new File(path);
       		File compressedImageFile = new File("privremeno.jpg");
        	InputStream is = new FileInputStream(imageFile);
        	OutputStream os = new FileOutputStream(compressedImageFile);
        	float quality = 0.5f;
        	// create a BufferedImage as the result of decoding the supplied InputStream
        	BufferedImage image = ImageIO.read(is);
        	// get all image writers for JPG format
        	Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        	if (!writers.hasNext())
            		throw new IllegalStateException("No writers found");
        	ImageWriter writer = (ImageWriter) writers.next();
        	ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        	writer.setOutput(ios);
        	ImageWriteParam param = writer.getDefaultWriteParam();
        	// compress to a given quality
        	param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        	param.setCompressionQuality(quality);
        	// appends a complete image stream containing a single image and
        	//associated stream and image metadata and thumbnails to the output
        	writer.write(null, new IIOImage(image, null, null), param);
        	// close all streams
        	is.close();
        	os.close();
        	ios.close();
        	writer.dispose();
		
        	BufferedImage notsofast = ImageIO.read(compressedImageFile);
        	ImageIO.write(notsofast, "jpg", imageFile);
        	compressedImageFile.delete();
	}
	static public void postprocessing(Image i, String path, int num) throws IOException{
		
		//i.saveAs(path);
		/*
		* NOTE: 0 -> crop left
		* 1 -> crop top
		* 2 -> crop out
		*/
		String[] split = path.split("\\.");
		BufferedImage bufImg = i.getBufferedImage();
		BufferedImage bufO = ImageIO.read(new File(path));
		BufferedImage test = new BufferedImage(bufO.getWidth(),bufO.getHeight(),bufO.getType());
		if ( num == 4)
			test = new BufferedImage(bufImg.getWidth(),bufImg.getHeight(),bufO.getType());
		Graphics2D gg = test.createGraphics();
		//System.out.println(num);
		if(num == 0)
			gg.drawImage(bufImg,bufImg.getWidth(),0,bufImg.getWidth(),bufImg.getHeight(), null);
		else if(num == 1)
			gg.drawImage(bufImg,0,bufImg.getHeight(),bufImg.getWidth(),bufImg.getHeight(), null);
		else if(num == 2)
			gg.drawImage(bufImg,bufImg.getWidth()/2,bufImg.getHeight()/2,bufImg.getWidth(),bufImg.getHeight(), null);
		else
			gg.drawImage(bufImg,0,0,bufImg.getWidth(),bufImg.getHeight(), null);
		gg.dispose();
		
		try {
			ImageIO.write(test, split[1], new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public ImageIcon middle_step(Image i){
		i.resize(128, 128, true);
		
		BufferedImage bufImg = i.getBufferedImage();
		BufferedImage test = new BufferedImage(bufImg.getWidth(),bufImg.getHeight(), bufImg.getType());
		Graphics2D gg = test.createGraphics();
		gg.drawImage(bufImg, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
		gg.dispose();
		
		ImageIcon icon = new ImageIcon(test);

		return icon;
	}

}
