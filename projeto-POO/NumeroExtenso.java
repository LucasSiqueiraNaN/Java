import java.util.Scanner;

public class NumeroExtenso {
    public static void main(String args[]){

        try{
            
            Scanner scan = new Scanner(System.in);
            
            int valorDigitado = Integer.parseInt(scan.next());

            if(valorDigitado >= 0 && valorDigitado < 10000){

                Milhar milhar = new Milhar(valorDigitado);
                Caracteres caracteres = new Caracteres(milhar.getValorMilharExtenso());
                
                System.out.println(caracteres.getInicioMaiusculo());
            }else{
                throw new Exception("Valor digitado menor que 0 ou maior que 9999.");
            }
        }
        catch(Exception ex){
            System.out.println("Ocorreu um Erro: " + ex);
        }

    }
}
