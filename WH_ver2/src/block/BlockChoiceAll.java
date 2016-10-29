package block;
import java.util.ArrayList;

public class BlockChoiceAll extends BlockChoice{
	public ArrayList<Integer> fja_odaberi_blokove(ArrayList <double[][]> niz_mat){
		ArrayList <Integer> niz = new ArrayList <Integer>();
		for(int i = 0; i < niz_mat.size(); i++){
			niz.add(i);
		}
		return niz;
	}

}
