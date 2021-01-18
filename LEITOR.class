import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author lucas e yuri
 */
public class Leitor {
    int classe;
    ArrayList<Double> dados;
    
     /**
     * @param classe
     * @param dados
     * usado no lerBaseDeDados para criar leitor para depois adicionar
     * na arraylist de leitor
     */
    public Leitor(int classe, ArrayList<Double> dados){
        this.classe = classe;
        this.dados = dados;
    }

    /**
     * @param usado para declarar o leitor no main
     */
    public Leitor(){
        
    }
    
    /**
     * @param nome, nome do aqruivo que vai ler
     * @return arraylist com classe + dados lidos
     * 
     */
    public ArrayList<Leitor> lerBaseDeDados(String nome) throws FileNotFoundException {
        ArrayList<Leitor> BD= new ArrayList<>();
        File file = new File(nome);
        Scanner bd;
        bd = new Scanner(file);
        
        while (bd.hasNextLine()){
            String linha = bd.nextLine();
            //lê a linha
            String[] dados = linha.split(" ");
            //transforma o primeiro dado da linha no int classe
            int classe = Integer.parseInt(dados[0]);
            ArrayList<Double> conteudo = new ArrayList<>();
            //transforma o resto dos dados em um arraylist de double, que são os dados do giroscopio
            for (int i=1; i<dados.length; i++) {
                conteudo.add(Double.parseDouble(dados[i]));
            }
            
            //cria um leitor, para depois adicionar ao arraylist de leitor
            Leitor L = new Leitor(classe, conteudo);
            BD.add(L);
            }
        bd.close();
        return BD;
    }
    
}