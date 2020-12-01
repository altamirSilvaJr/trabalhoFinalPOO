package entities;

public class Produto {
	private String nomeProduto;
    private double valorVendaProduto;
    private double valorCompraProduto;
    private String descricaoProduto;
    private double pesoProduto;
    private int qtdProduto;
    private int codProguto;

    public Produto() {
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getValorVendaProduto() {
        return valorVendaProduto;
    }

    public void setValorVendaProduto(double valorVendaProduto) {
        this.valorVendaProduto = valorVendaProduto;
    }

    public double getValorCompraProduto() {
        return valorCompraProduto;
    }

    public void setValorCompraProduto(double valorCompraProduto) {
        this.valorCompraProduto = valorCompraProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public double getPesoProduto() {
        return pesoProduto;
    }

    public void setPesoProduto(double pesoProduto) {
        this.pesoProduto = pesoProduto;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public int getCodProguto() {
        return codProguto;
    }

    public void setCodProguto(int codProguto) {
        this.codProguto = codProguto;
    }

    public void aumentarQtd(int qtd){
        this.qtdProduto += qtd;
    }

    public void diminuitQtd(int qtd){
        this.qtdProduto -= qtd;
    }

    public void mudarPreco(double novoPreco){
        this.valorVendaProduto = novoPreco;
    }

    @Override
    public String toString() {
        return  codProguto + " - " + nomeProduto + " - " + valorVendaProduto + " - " + qtdProduto;
    }

}
