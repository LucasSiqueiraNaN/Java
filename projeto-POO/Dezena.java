public class Dezena extends Unidade{
    
    private String[] dezenasExtenso = {"vinte", "trinta", "quarenta", 
                                        "cinquenta", "sessenta", "setenta", 
                                        "oitenta", "noventa"};
                                        
    private String[] distintos = {"dez", "onze", "doze", 
                                    "treze", "catorze", "quinze", 
                                    "dezesseis", "dezessete", "dezeoito", 
                                    "dezenove"};

    private int valorDezena;

    public Dezena(int valor){
        super(valor);

        if(valor >= 1000) {
            int restoMilhar = valor/1000;

            valor = valor - (restoMilhar*1000);
        }

       if(valor >= 100) {
            int restoMilhar = valor/100;

            valor = valor - (restoMilhar*100);
        }

        if(valor >= 10){
            this.setValorDezena(valor);
        }

    }

    public String getValorDezenaExtenso(){

        if(this.valorDezena == 0){
            return getValorUnidadeExtenso();
        }
        else if(this.valorDezena < 20){
            return this.distintos[this.valorDezena-10];
        }else{
             
            if(this.valorDezena%10 == 0){
                this.valorDezena = this.valorDezena/10;
                return this.dezenasExtenso[this.valorDezena-2];
            }
            else{
                int dezena = this.valorDezena/10;

                return this.dezenasExtenso[dezena-2] + " e " + getValorUnidadeExtenso();
            }

        }
        
    }

    public void setValorDezena(int valor){
        this.valorDezena = valor;
    }
}