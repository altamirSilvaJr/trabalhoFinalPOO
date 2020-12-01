package entities;

public class Caixa {
	 private double valorCaixa;

	    public Caixa() {
	    }

	    public double getValorCaixa() {
	        return valorCaixa;
	    }

	    public void setValorCaixa(double valorCaixa) {
	        this.valorCaixa = valorCaixa;
	    }

	    public void adicionarValor(double valor){
	        this.valorCaixa += valor;
	    }

	    public void retirarValor(double valor){
	        this.valorCaixa -= valor;
	    }
}
