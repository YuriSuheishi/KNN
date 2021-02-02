/**
 * eu só criei k, pra poder fazer uma lista de K
 * mas não tem nada aqui além de getters e setters
 * @author yuri e lucas
 */
public class K {
    private int classe;
    private double valor;
    
    public K(int classe,double valor){
        this.classe = classe;
        this.valor = valor;
    }
    
    public double getV(){
        return valor;
    }
    
    public int getClasse(){
        return classe;
    }
        
    public void setK(int classe, double valor){
        this.classe = classe;
        this.valor = valor;
    }
    
    public void setV(double valor){
        this.valor = valor;
    }
    
    public void setClasse(int classe){
        this.classe = classe;
    }
    
}