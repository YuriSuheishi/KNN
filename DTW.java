import java.util.ArrayList;

/**
 *
 * @author yuri e lucas
 */
public class DTW{
    
    /**
     * @param n1 - lista1 de dados do giroscopio 
     * @param n2 - lista2 de dados do giroscopio  
     * @return distancia entre n1 e n2 usando dtw
     */
    public double DTWbaiano(ArrayList<Double> n1, ArrayList<Double> n2) {
                //o nome é em homenagem, a um colega, que não faz parte do meu grupo,
                //que me mostrou como faz a caminho, por que até então, eu só tinha feito a
                //distancia local, e não global, vlw baiano
                int n,m,K;
                
		n = n1.size();	
		m = n2.size();
		K = 1;

                //distancia acumulada
		double distance = 0.0;
		
                //distancia local
		double[][] d = new double[n][m];	
                //distancia global
		double[][] D = new double[n][m];	
		
                //inicio formula do dtw do wiki
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				d[i][j] = dist(n1.get(i), n2.get(j));
			}
		}
		
		D[0][0] = d[0][0];
		for (int i = 1; i < n; i++) {
			D[i][0] = d[i][0] + D[i - 1][0];
		}

		for (int j = 1; j < m; j++) {
			D[0][j] = d[0][j] + D[0][j - 1];
		}
		//até aqui é o dtw normal do wiki
                
		//aqui ele começa a somar os menores globais a distancia total, achando o caminho 
                //com melhor ajuste
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				distance = min(D[i-1][j], D[i-1][j-1], D[i][j-1]);
				distance += d[i][j];
				D[i][j] = distance;
			}
		}
		distance = D[n - 1][m - 1]; 
		K= n + m - 2;
                
		return distance / K;
    }
    
    /**
     * IS DEAD, esse eu que fiz sozinho
     */
//    public double DTWjapa(ArrayList<Double> n1, ArrayList<Double> n2){
//        //ignora eu tentei usar esse, que era só o dtw do wiki, sem considerar o caminho inteiro
//        //só que a acuracia deu 35%, ai eu falei com o baiano que explicou o negocio do caminho
//        //deixei como lembrança da dor e sofrimento    
//            int x,y;
//            x = n1.size();
//            y = n2.size();
//            
//            int i,j;
//            double distance;
//            double[][] D = new double[x][y];
//            
//            for(i=0; i<x; i++){
//                for(j=0; j<y; j++){
//                D[i][j] = 9999;
//                }
//            }
//            D[0][0] = 0;
//            
//            for(i=0; i<x; i++){
//                for(j=0; j<y; j++){
//                distance = dist(n1.get(i),n2.get(j));
//                if(i>0 && j>0){
//                     D[i][j] = distance + min(D[i-1][j],D[i][j-1],D[i-1][j-1]);
//                }
//                else{
//                     D[i][j] = distance;
//                }
//                }
//            }
//            return D[x-1][y-1]/(x+y-2);
//            
//    }
    
    /**
     * @param double a
     * @param double b
     * @param double c
     * @return menor valor entre eles
     */
    double min(double a, double b, double c){
            if(a<b && a<c){  
                    return a;
                }
            else if(b<a && b<c){
                    return b;
                }
            else{
                    return c;
                }
}
    /**
     * @param n1 - lista1 de dados do giroscopio 
     * @param n2 - lista2 de dados do giroscopio  
     * @return distancia entre n1 e n2 usando euclidiana
     */
    public double DistEuclidiana(ArrayList<Double> n1, ArrayList<Double> n2){
            int n,m;
            n = n1.size();
            m = n2.size();
            double total=0;
            //soma a distancia ao longo do caminho
            if(n>m){
                for(int i=0; i<m; i++){
                    total = total + dist(n1.get(i),n2.get(i));
                }
                  return total/m;
            }
            else{
                for(int i=0; i<n; i++){
                    total = total + dist(n1.get(i),n2.get(i));
                }
                  return total/n;
            }
    }
    
    /**
     * @param x, é o valor de um ponto qlqr de n1
     * @param y,  é o valor de um ponto qlqr de n1
     * @return distancia euclidiana entre x e y
     */
    double dist(double x, double y){
        return ((x-y)*(x-y));
    }
    }
