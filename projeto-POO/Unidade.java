public class Unidade {

    private int valorUnidade;

    private String[] unidadesExtenso = {"zero", "um", "dois", 
                                        "três", "quatro", "cinco", 
                                        "seis", "sete", "oito", 
                                        "nove"};

                                            
    public Unidade(int valor){

        if(valor != 0) this.unidadesExtenso[0] = "";

        if(valor >= 1000) {
            int restoMilhar = valor/1000;

            valor = valor - (restoMilhar*1000);
        }

       if(valor >= 100) {
            int restoMilhar = valor/100;

            valor = valor - (restoMilhar*100);
        }

        if(valor >= 10){
            int dezena = valor / 10;
            valor = valor - (dezena * 10);
        }
        
        this.setValorUnidade(valor);
    }

    public void setValorUnidade(int valor){
        this.valorUnidade = valor;
    }

    public String getValorUnidadeExtenso(){
        return this.unidadesExtenso[this.valorUnidade];
    }

    public String getUnidadeExtenso(int valor){
        return this.unidadesExtenso[valor];
    }
}