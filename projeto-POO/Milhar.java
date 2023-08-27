public class Milhar extends Centena{

    private int valorMilhar = 0;

    public Milhar(int valor)
    {
        super(valor);

        if(valor >= 1000) {
            int restoMilhar = valor/1000;

            this.setValorMilhar(restoMilhar);
        }
    }

    public void setValorMilhar(int valor){
        this.valorMilhar = valor;
    }

    public String getValorMilharExtenso(){
        if(this.valorMilhar != 0){
            return this.getUnidadeExtenso(this.valorMilhar) + " mil " + getValorCentenaExtenso();
        }else{
            return getValorCentenaExtenso();
        }
    }

}