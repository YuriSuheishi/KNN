import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author yuri e lucas
 */
public class KNN {
    
    /**
     * @param n1 - lista ordenada com as classes com menor valor de distancia 
     * @param k - numero de votos a serem considerados    
     * @return classe do que tem mais votos a favor
     */
    public static int Voto(int[] n1,int k){
        int[] v = new int[50];
        int i;
        int voto,maiorV,c;
        maiorV = 0;
        c=0;
        
        //inicializando
        for(i=0;i<12;i++){
            v[i]=0;
        }
        
        //votação
        for(i=0;i<k;i++){
            voto = n1[i];
            v[voto-1]++;
        }
        
        //maior qnt votos
        for(i=0;i<12;i++){
            if(v[i]>maiorV){
                maiorV = v[i];
                c = i+1;
            }
            //em caso de empate, verifica prioridade maior
            else if((v[i]==maiorV) && verificarPM(n1,c,i+1)){
                maiorV = v[i];
                c = i+1;
                
            }
        }
        
        return c;
    }
    
    /**
     * @param n1 - lista ordenada com as classes com menor valor de distancia 
     * @param c1 - classe do atual    
     * @param c2 - classe do que vamos testar
     * @return true caso c2 tiver maior prioridade, ou false, caso c1 tiver mais prioridade
     */
    public static boolean verificarPM(int[] n1, int c1,int c2){
        int i;
        //verifica qual classe aparece antes na lista ordenada n1
        for(i=0;i<10;i++){
            if(n1[i]==c1){
                return false;
            }
            if(n1[i]==c2){
                return true;
            }
        }
        return false;
    }
    
    
    /** só ignora, é tão ridiculo o uso, que eu me nego a comentareu usei 
     * pra achar o k, eu lacos de repetição
     * 
     * @param i, de 0 a 3, representando, k = 1,3,5 ou 10
     * @return o valor k
     */
    public static int FindK(int j){
                    //eu sei que é mais simples switch case
                    //mas eu to mais acostumado com if
                    if(j==0){
                        return 1;
                    }
                    else if(j==1){
                        return 3;
                    }
                    else if(j==2){
                        return 5;
                    }
                    else if(j==3){
                        return 10;
                    }
                    else{
                        return 0;
                    }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
                Leitor L = new Leitor();                
                //array de arraylist, banco de dados
                //validação cruzada de 5 partições
                //então separei o bd em 5, e os dados agora estão separados em 5
                //agora que eu tava pensando eu pudia ter feito ele ler o bd inteiro
                //junto, e separar depois, com um for simples, mas já tive o trabalho de 
                //separar o bd manualmente...
                ArrayList<Leitor>[] bd = new ArrayList[5]; 
                bd[0] = L.lerBaseDeDados("dados1.txt");
                bd[1] = L.lerBaseDeDados("dados2.txt");
                bd[2] = L.lerBaseDeDados("dados3.txt");
                bd[3] = L.lerBaseDeDados("dados4.txt");
                bd[4] = L.lerBaseDeDados("dados5.txt");
                
                DTW dtw = new DTW();
                
                int i,j,y,x,h,resp,respVoto;  
                
                //eu fiz k como uma lista, com o classe,dtw e cod
                // o codigo eu criei pra verificar se a lista tinha valores diferentes
                //por que teve uns problemas com dtw, pq eu tinha feito a lista errada
                K[][] k = new K[10][2];
                double[] atualV = new double[2];
                int[] listaK = new int[10];
                int[] listaKeu = new int[10];
                
                atualV[0]=9999;
                atualV[1]=9999;
                
                //eu fiz essa pra ver os acertos por bd
                //[] pra definir o k, [] pra definir o bd, [] 0 pra dtw, 1 pra euclidiana
                int[][][] acertoV= new int[4][5][2];
                //esse para os acertos totais
                //[] para k, [] 0 para dtw, e 1 ára euclidiana
                int[][] acertoT = new int[4][2];
                
                //inicializar variaveis
                for(i=0;i<4;i++){
                    acertoT[i][0]=0;
                    acertoT[i][1]=0;
                    for(j=0;j<5;j++){
                        acertoV[i][j][0]=0;
                        acertoV[i][j][1]=0;
                    }
                }
                
                //teste de tudo 
                //y é o bd
                //x é a linha
                for(y=0; y<5; y++){
                for(x=0;x<240;x++){
                    
                
                resp = bd[y].get(x).classe; 
                
                for(i=0; i<10; i++){ 
                    k[i][0] = new K(20,atualV[0]);
                    k[i][1] = new K(20,atualV[0]);
                    listaK[i]=0;
                    listaKeu[i]=0;
                }
                              
                for(i=0; i<5; i++){
                    for(j=0; j<240; j++){
                        //excluindo o BD correspondente
                        if(i!=y){
                                                
                        atualV[0] = dtw.DTWbaiano(bd[y].get(x).dados, bd[i].get(j).dados);    
                        atualV[1] = dtw.DistEuclidiana(bd[y].get(x).dados, bd[i].get(j).dados);
                            
                        //desculpa rafael, eu sei que ta feio, mas foi o que eu consegui pensar pra funcionar
                        //0 para dtw e 1 para euclidiana
                        for(h=0;h<2;h++){
                        if(k[0][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setK(k[5][h].getClasse(),k[5][h].getV());
                            k[5][h].setK(k[4][h].getClasse(),k[4][h].getV());
                            k[4][h].setK(k[3][h].getClasse(),k[3][h].getV());
                            k[3][h].setK(k[2][h].getClasse(),k[2][h].getV());
                            k[2][h].setK(k[1][h].getClasse(),k[1][h].getV());
                            k[1][h].setK(k[0][h].getClasse(),k[0][h].getV());
                            k[0][h].setV(atualV[h]);
                            k[0][h].setClasse(bd[i].get(j).classe);
                        }
                        //pensa positivo, pelo menos funciona!
                        else if(k[1][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setK(k[5][h].getClasse(),k[5][h].getV());
                            k[5][h].setK(k[4][h].getClasse(),k[4][h].getV());
                            k[4][h].setK(k[3][h].getClasse(),k[3][h].getV());
                            k[3][h].setK(k[2][h].getClasse(),k[2][h].getV());
                            k[2][h].setK(k[1][h].getClasse(),k[1][h].getV());
                            k[1][h].setV(atualV[h]);
                            k[1][h].setClasse(bd[i].get(j).classe);
                        }
                        //agora só falta mais 8
                        else if(k[2][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setK(k[5][h].getClasse(),k[5][h].getV());
                            k[5][h].setK(k[4][h].getClasse(),k[4][h].getV());
                            k[4][h].setK(k[3][h].getClasse(),k[3][h].getV());
                            k[3][h].setK(k[2][h].getClasse(),k[2][h].getV());
                            k[2][h].setV(atualV[h]);
                            k[2][h].setClasse(bd[i].get(j).classe);
                        }
                        else if(k[3][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setK(k[5][h].getClasse(),k[5][h].getV());
                            k[5][h].setK(k[4][h].getClasse(),k[4][h].getV());
                            k[4][h].setK(k[3][h].getClasse(),k[3][h].getV());
                            k[3][h].setV(atualV[h]);
                            k[3][h].setClasse(bd[i].get(j).classe);
                        }
                        else if(k[4][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setK(k[5][h].getClasse(),k[5][h].getV());
                            k[5][h].setK(k[4][h].getClasse(),k[4][h].getV());
                            k[4][h].setV(atualV[h]);
                            k[4][h].setClasse(bd[i].get(j).classe);
                        }
                        else if(k[5][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setK(k[5][h].getClasse(),k[5][h].getV());
                            k[5][h].setV(atualV[h]);
                            k[5][h].setClasse(bd[i].get(j).classe);
                        }
                        else if(k[6][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setK(k[6][h].getClasse(),k[6][h].getV());
                            k[6][h].setV(atualV[h]);
                            k[6][h].setClasse(bd[i].get(j).classe);
                        }
                        else if(k[7][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setK(k[7][h].getClasse(),k[7][h].getV());
                            k[7][h].setV(atualV[h]);
                            k[7][h].setClasse(bd[i].get(j).classe);
                        }
                        else if(k[8][h].getV()>atualV[h]){
                            k[9][h].setK(k[8][h].getClasse(),k[8][h].getV());
                            k[8][h].setV(atualV[h]);
                            k[8][h].setClasse(bd[i].get(j).classe);
                        }
                        //pronto, fim da dor e sofrimento
                        else if(k[9][h].getV()>atualV[h]){
                            k[9][h].setV(atualV[h]);
                            k[9][h].setClasse(bd[i].get(j).classe);
                        }
                        }
                    }
                    }
                }
                                
               
                //jogando as classes para listaK e listaKeu
                for(i=0; i<10; i++){ 
                    listaK[i]=k[i][0].getClasse();
                    listaKeu[i]=k[i][1].getClasse();
                }
                
                //DTW k=1
                respVoto = Voto(listaK, 1);
                if(resp == respVoto){
                    acertoV[0][y][0]++;
                }
                //DTW k=3
                respVoto = Voto(listaK, 3);
                if(resp == respVoto){
                    acertoV[1][y][0]++;
                }
                //DTW k=5
                respVoto = Voto(listaK, 5);
                if(resp == respVoto){
                    acertoV[2][y][0]++;
                }
                //DTW k=10
                respVoto = Voto(listaK, 10);
                if(resp == respVoto){
                    acertoV[3][y][0]++;
                }
                
                //Euclidiana k=1
                respVoto = Voto(listaKeu, 1);
                if(resp == respVoto){
                    acertoV[0][y][1]++;
                }
                //Euclidiana k=3
                respVoto = Voto(listaKeu, 3);
                if(resp == respVoto){
                    acertoV[1][y][1]++;
                }
                //Euclidiana k=5
                respVoto = Voto(listaKeu, 5);
                if(resp == respVoto){
                    acertoV[2][y][1]++;
                }
                //Euclidiana k=10
                respVoto = Voto(listaKeu, 10);
                if(resp == respVoto){
                    acertoV[3][y][1]++;
                }
                
                
                
            }   
    
    }       //mostra acertos de cada BD, e soma os acertos, para achar os acertos totais
            for(i=0;i<5;i++){
                for(j=0;j<4;j++){
                    acertoT[j][0]+= acertoV[j][i][0];
                    acertoT[j][1]+= acertoV[j][i][1];
                    //se quiser verificar os resultados individuais de cada BD, é só tirar do comentario
//                    System.out.println("\n DTW com k="+ FindK(j) + ", BD" + (i+1) + " acertou = " + acertoV[j][i][0] + " resultado de " + acertoV[j][i][0]/(2.4) + "% de acerto");
//                }    
//                for(j=0;j<4;j++){
//                    System.out.println("\n Euclidiana com k="+ FindK(j) + ", BD" + (i+1) + " acertou = " + acertoV[j][i][1] + " resultado de " + acertoV[j][i][1]/(2.4) + "% de acerto");
                }
            }    
            //mostra acertos totais
            for(j=0;j<4;j++){
                System.out.println("\n DTW com k=" + FindK(j) + " acertou = " + acertoT[j][0]+ " resultado de " + acertoT[j][0]/12 + "% de acerto");
            }
            //deixei em for separado, pra melhorar a visualização dos resultados
            for(j=0;j<4;j++){    
                System.out.println("\n Euclidiana k=" + FindK(j) + " acertou = " + acertoT[j][1] + " resultado de " + acertoT[j][1]/12 + "% de acerto");
            }
    }
}
   