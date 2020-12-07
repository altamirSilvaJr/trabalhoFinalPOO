package entities;

public class ProdutoVenda {
	
	private Produto produto;
	private int qtdProduto;
	private double valorTotal;
	private String nomeProd;
	private int codProd;
	private double precoProd;
	
	public ProdutoVenda() {

	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQtdProduto() {
		return qtdProduto;
	}
	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
		
	public String getNomeProd() {
		return nomeProd;
	}
	public void setNomeProd(String nomeProd) {
		this.nomeProd = nomeProd;
	}
	public int getCodProd() {
		return codProd;
	}
	public void setCodProd(int codProd) {
		this.codProd = codProd;
	}
	public double getPrecoProd() {
		return precoProd;
	}
	public void setPrecoProd(double precoProd) {
		this.precoProd = precoProd;
	}
	public void attValorTotal() {
		valorTotal = produto.getValorVendaProduto() * qtdProduto;
	}
	
	public void setValores() {
		nomeProd = produto.getNomeProduto();
		codProd = produto.getCodProguto();
		precoProd = produto.getValorVendaProduto();
	}
	
}
