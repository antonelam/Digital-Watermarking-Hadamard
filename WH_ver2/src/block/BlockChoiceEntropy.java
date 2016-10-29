package block;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class BlockChoiceEntropy extends BlockChoice{
	@Override
	public ArrayList<Integer> fja_odaberi_blokove(ArrayList<double[][]> niz_mat) {
		ArrayList <Integer> niz = new ArrayList <Integer>();
		HashMap <Integer, Double> mapa = new HashMap <Integer, Double>();
		
		for(int i = 0; i < niz_mat.size(); i++){
			int[] h_c = histogram_count(niz_mat.get(i));
			double e = entropy_calc(h_c, (int)Math.pow(niz_mat.get(i).length, 2));
			mapa.put(i, e);
			
		}
		Stream<Entry<Integer, Double>> sorted = mapa.entrySet().stream().sorted(Map.Entry.comparingByValue());
		java.util.Iterator<Entry<Integer, Double>> t = sorted.iterator();
		
		while(t.hasNext()){
			Entry<Integer, Double> combo = t.next();
			niz.add(combo.getKey());
		}
		return niz;
	}
	
	public static int[] histogram_count(double[][] blok){
		int[] hist_c = new int[256];
		for(int i = 0; i < blok.length; i++){
			for(int j = 0; j < blok.length; j++){
				int c = (int) Math.round(blok[i][j]);
				hist_c[c] += 1;
			}
		}
		return hist_c;
	}
	public static double entropy_calc(int[] p, int size){
		double suma = 0;
		for(double x: p){
			if(x != 0){
				x /= size;
				suma -= (x * Math.log(x));
			}
		}
		return suma;
	}

}
