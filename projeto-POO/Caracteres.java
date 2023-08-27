public class Caracteres {
   
    private String valores;

    public Caracteres(String valores){
        this.setValores(valores);
    }

    public void setValores(String valores){
        this.valores = valores;
    }

    public String getInicioMaiusculo(){
        this.valores = this.valores.trim();
        String primeiraLetra = this.valores.substring(0, 1).toUpperCase();

        return primeiraLetra + this.valores.substring(1);
    }

}
