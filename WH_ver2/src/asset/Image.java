package asset;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Image {
	int N, red;
	double [][] matrica;
	BufferedImage bufImg;
	BufferedImage []komadi;
	ArrayList<ArrayList<double[][]>> tri_grupe;
	ArrayList <double[][]> hadamard_mat;
	
	public Image(int N, String path) throws IOException{
		this.N = (int)Math.pow(2, N);
		this.red = N;
		this.matrica = hadamard(N);
		this.bufImg = fja_ulaz_slika(path);
	}

	static double[][] hadamard(int red){
		double[][] new_h = new double[(int) Math.pow(2, red)][(int)Math.pow(2, red)];
		if(red == 0){
			new_h[0][0] = 1;
			return new_h;
		}
		double[][] red_minus = new double[(int) Math.pow(2, red-1)][(int)Math.pow(2, red-1)];
		
		red_minus = hadamard(red - 1);
		for(int i = 0; i < (int)(Math.pow(2, red)); i++){
			for(int j = 0; j < (int)(Math.pow(2, red)); j++){
				if((i<(int)(Math.pow(2, red)/2))&&(j<(int)(Math.pow(2, red)/2)))
					new_h[i][j] = red_minus[i][j];
				
				else if((i<(int)(Math.pow(2, red)/2))&&(j>=(int)(Math.pow(2, red)/2)))
					new_h[i][j] = red_minus[i][j-(int)(Math.pow(2, red)/2)];
				
				else if((i>=(int)(Math.pow(2, red)/2))&&(j<(int)(Math.pow(2, red)/2)))
					new_h[i][j] = red_minus[i-(int)(Math.pow(2, red)/2)][j];
				
				else
					new_h[i][j] = (-1)*red_minus[i-(int)(Math.pow(2, red)/2)][j-(int)(Math.pow(2, red)/2)];
			}
		}
		
		return new_h;		
		
	}

	
	
	public double[][] mnozenje_matrica(double[][] A, double[][] B){
		double[][] C = new double[N][N];
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				for (int k = 0; k < N; k++){
					C[i][k] += A[i][j]*B[j][k];
				}
			}
		}
		return C;
	}
	
	public static double[] RGBtoYUV(int RGB){
		//System.out.println(RGB);
		int r = (RGB>>16)&0xff;
		int g = (RGB>>8)&0xff;
		int b = (RGB)&0xff;
		double y = (0.299*r) + (0.587*g) + (0.114*b);
		double u = 0.492*(b - y);
		double v = 0.877*(r - y);
		//System.out.println(r);//System.out.println(g);System.out.println(b);
		double[] YUV = {y,u,v};
		//System.out.println(Double.toString(YUV[0])+' '+Double.toString(YUV[1])+' '+Double.toString(YUV[2]));
		return YUV;
	}
	
	public int YUVtoRGB(double[] YUV){
		//System.out.println(Double.toString(YUV[0])+' '+Double.toString(YUV[1])+' '+Double.toString(YUV[2]));
		int r = (int) Math.round(YUV[0] + (1.140*YUV[2]));
		int g = (int) Math.round(YUV[0] - (0.395*YUV[1]) - (0.581*YUV[2]));
		int b = (int) Math.round(YUV[0] + (2.032*YUV[1]));
		//System.out.println(r);//System.out.println(g);System.out.println(b);
		
		r = r << 16;
		g = g << 8;
		r |= g;
		r |= b;
		//System.out.println(r);
		return r;
		
	}
	
	public ArrayList <double[][]> fja_hadamard_transformacija(int chunks, ArrayList <double[][]> niz_mat){
		//mnozenje matrica..bolje opcenita fja za matricno mnozenje
		ArrayList <double[][]> hadamard_mat = new ArrayList <double[][]> (chunks);
		for(int i = 0; i < niz_mat.size(); i++){
			double [][] rez = mnozenje_matrica(mnozenje_matrica(matrica,niz_mat.get(i)),matrica);
			for(int j = 0; j < N; j++){
				for(int k = 0; k < N; k++){
					rez[j][k] = rez[j][k]/(N);
					/*if(i==16){
						System.out.print(rez[j][k]);
						System.out.print(" ");
					}*/
				}
				/*if(i==16){
					System.out.println();
				}*/
			}
			hadamard_mat.add(rez);
		}
		return hadamard_mat;		
	}
	
	//inaèe, nije samo inverz, ujedno odmah obavim i transformaciju koeficijenata u RGB, jer znam da nikad nema meðukoraka
	void fja_hadamard_inverz(ArrayList <double[][]> hadamard_mat, BufferedImage[] komadi, ArrayList<double[][]> U, ArrayList<double[][]> V){
		for(int i = 0; i < hadamard_mat.size(); i++){
			double [][] rez = mnozenje_matrica(mnozenje_matrica(matrica,hadamard_mat.get(i)),matrica);
			for(int j = 0; j < N; j++){
				for(int k = 0; k < N; k++){
					//novi dio
					rez[j][k] /= (N);
					double y = Math.round(rez[j][k]);///(N);
					double u = U.get(i)[j][k];
					double v = V.get(i)[j][k];
					if(y<0)
						y=0;
					if(y>255)
						y=255;
					double[] YUV = {y,u,v};
					
					komadi[i].setRGB(j, k, YUVtoRGB(YUV));
					//System.out.println(komadi[i].getRGB(j, k));
					//System.out.println((komadi[i].getRGB(j, k)>>16)&0xff);
					/*int rgb = rez[j][k]/(N);
					int pom = rgb;
					rgb = rgb<<8;
					rgb |= pom;
					rgb = rgb<<8;
					rgb |= pom;
					komadi[i].setRGB(j, k, rgb);*/
					hadamard_mat.set(i, rez);
				}
			}
		}
	}
	
	public BufferedImage[] fja_kreiraj_blokove(int rows, int columns, int chunks){
		//stvaranje blokova slike
		int count = 0;
		BufferedImage komadi[] = new BufferedImage[chunks];				
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				komadi[count] = new BufferedImage(N,N,bufImg.getType());//BufferedImage.TYPE_BYTE_GRAY);
				Graphics2D gr = komadi[count++].createGraphics();
				gr.drawImage(bufImg, 0, 0, N, N, N*i,N*j,N*i + N, N*j + N, null);
				gr.dispose();
					
			}
		}
		//System.out.println(komadi[0].toString());
		return komadi;
	}
	
	public ArrayList <ArrayList<double[][]>> fja_blokovi_u_matrice(int chunks, BufferedImage komadi[]){
		ArrayList <double[][]> niz_mat = new ArrayList <double[][]> (chunks);
		//novi dio
		ArrayList <double[][]> u = new ArrayList <double[][]> (chunks);
		ArrayList <double[][]> v = new ArrayList <double[][]> (chunks);
		//
		for(int i = 0; i < chunks; i++){
			double [][] pom = new double [N][N];
			//novi dio
			double [][] pU = new double [N][N];
			double [][] pV = new double [N][N];
			//
			for(int j = 0; j < N; j++){
				for(int k = 0; k < N; k++){
					//novi dio
					double[] yuv =RGBtoYUV(komadi[i].getRGB(j, k));
					/*if(i == 16){
						System.out.print(yuv[0]);
						System.out.print(" ");
					}*/
					pom[j][k] = yuv[0];
					pU[j][k] = yuv[1];
					pV[j][k] = yuv[2];
					//pom[j][k] = (komadi[i].getRGB(j, k)>>16)&0xff;//ovdje je sad jedna oznaka a ne tri za rgb pa su brojke cudne..pogledaj kod za razdvajanje
					}
				//System.out.println();
				}
		niz_mat.add(pom);
		//novi dio
		u.add(pU);
		v.add(pV);
		}
		ArrayList <ArrayList<double[][]>> tri_grupe = new ArrayList<ArrayList<double[][]>>(3);
		tri_grupe.add(niz_mat);tri_grupe.add(u);tri_grupe.add(v);
		return tri_grupe;
	}
	
	int[] fja_spoji_blokove(int rows, int columns, BufferedImage komadi[], String path, String extension){
		//spajanje blokova
		BufferedImage test = new BufferedImage(bufImg.getWidth(),bufImg.getHeight(),bufImg.getType());//BufferedImage.TYPE_BYTE_GRAY);//bufImg.getType());
		Graphics2D gg = test.createGraphics();
		int ia = 0;
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				gg.drawImage(komadi[ia++], i*N,j*N,i*N+N,j*N+N,0,0,N,N,null);
				}
			}
		gg.dispose();
		//System.out.println(test.toString());
		try {
			ImageIO.write(test, extension, new File(path+"."+extension));
		} catch (IOException e) {
			e.printStackTrace();
			}
		
		int [] array = new int[test.getWidth()*test.getHeight()];
		test.getRGB(0, 0,test.getWidth(),test.getHeight(), array, 0, test.getWidth());
		return array;
		}
	
	public static BufferedImage fja_ulaz_slika(String path) throws IOException{
		//fja za import
		File imgPth = new File(path);
		BufferedImage bufImg = ImageIO.read(imgPth);
		//System.out.println(bufImg.toString());
		return bufImg;
	}
	
	public static boolean isValid(BufferedImage bufIm){
		if(bufIm.getHeight() != bufIm.getWidth())
			return false;
		return true;
	}
	
	public BufferedImage get_buf(){
		return this.bufImg;
	}
	
	public ArrayList <double[][]> get_hadmat(){
		return hadamard_mat;
	}
	
	public ArrayList <double[][]> get_nizmat(){
		return this.tri_grupe.get(0);
	}
	
	public BufferedImage[] get_komadi(){
		return this.komadi;
	}
	
	public int get_red(){
		return red;
	}
	
	public void start(){
		int rows = bufImg.getHeight()/(int)Math.pow(2, red);
		int columns = bufImg.getWidth()/(int)Math.pow(2, red);
		int chunks = rows * columns;
		
		komadi = fja_kreiraj_blokove(rows, columns, chunks);
		tri_grupe = fja_blokovi_u_matrice(chunks, komadi);
		ArrayList<double[][]> niz_mat = tri_grupe.get(0);
		hadamard_mat = fja_hadamard_transformacija(chunks, niz_mat);
	}
	
	public void start(String path, String extension){
		fja_hadamard_inverz(hadamard_mat, komadi, tri_grupe.get(1), tri_grupe.get(2));
		
		int rows = bufImg.getHeight()/(int)Math.pow(2, red);
		int columns = bufImg.getWidth()/(int)Math.pow(2, red);
		
		fja_spoji_blokove(rows, columns, komadi, path, extension);
	}
}
