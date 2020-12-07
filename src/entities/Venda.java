package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venda {
	private List<ProdutoVenda> produtoVenda = new ArrayList<>();
    private int descontoVenda;
    private double precoTotalVenda;
    private LocalDate dataVenda;
    private int codigoVenda;

    public Venda(int descontoVenda, double precoTotalVenda, LocalDate dataVenda) {
		this.descontoVenda = descontoVenda;
		this.precoTotalVenda = precoTotalVenda;
		this.dataVenda = dataVenda;
	}

	public Venda() {
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public List<ProdutoVenda> getProdutoVenda() {
        return produtoVenda;
    }

    public void setProdutoVenda(ProdutoVenda produtoVenda) {
        this.produtoVenda.add(produtoVenda);
    }

    public int getDescontoVenda() {
        return descontoVenda;
    }

    public void setDescontoVenda(int descontoVenda) {
        this.descontoVenda = descontoVenda;
    }

    public double getPrecoTotalVenda() {
        return precoTotalVenda;
    }

    public void setPrecoTotalVenda(double precoTotalVenda) {
        this.precoTotalVenda = precoTotalVenda;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void adicionarVenda(ProdutoVenda produto){
        this.produtoVenda.add(produto);
    }

    public void retirarProduto(ProdutoVenda produto){
        this.produtoVenda.remove(produto);
    }

    public void valorTotal(){
        double preco = 0.0;
        for(ProdutoVenda prod : this.produtoVenda){
            preco += prod.getProduto().getValorVendaProduto() * prod.getQtdProduto();
        }
        this.precoTotalVenda = preco - (preco * (this.descontoVenda / 100));
    }
    
    public void inserirProdutos(List<ProdutoVenda> prodVenda) {
    	this.produtoVenda = prodVenda;
    }

    @Override
    public String toString() {
        return codigoVenda + " - " + dataVenda + " - " + descontoVenda + " - " + precoTotalVenda;
    }
}
