public class Centena extends Dezena{
    
    private String[] valoresExtenso = {"cento", "duzentos", "trezentos",
                                    "quatrocentos", "quinhentos", "seiscentos",
                                    "setessentos", "oitocentos", "novecentos"};
    private int valorCentena;

    public Centena(int valor){
        super(valor);

        if(valor >= 1000) {
            int restoMilhar = valor/1000;

            valor = valor - (restoMilhar*1000);
        }

        if(valor > 100){
            int restoCentena = valor/100;

            this.SetValorCentena(restoCentena);
        }

        if(valor == 100){
            this.SetValorCentena(valor);
        }
    }

    public void SetValorCentena(int valor){
        this.valorCentena = valor;
    }

    public String getValorCentenaExtenso(){
        if(this.valorCentena == 100)
        {
            return "cem";
        } 
        else if(this.valorCentena != 0)
        {
            return this.valoresExtenso[this.valorCentena-1] + " e " + getValorDezenaExtenso();  
        }else{
            return "e " + getValorDezenaExtenso();
        }
    }
}