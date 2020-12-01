package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
	private List<Produto> produtoVenda = new ArrayList<>();
    private int descontoVenda;
    private double precoTotalVenda;
    private Date dataVenda;
    private int codigoVenda;

    public Venda() {
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public List<Produto> getProdutoVenda() {
        return produtoVenda;
    }

    public void setProdutoVenda(Produto produtoVenda) {
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

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void adicionarVenda(Produto produto){
        this.produtoVenda.add(produto);
    }

    public void retirarProduto(Produto produto){
        this.produtoVenda.remove(produto);
    }

    public void valorTotal(){
        double preco = 0.0;
        for(Produto prod : this.produtoVenda){
            preco += prod.getValorVendaProduto();
        }
        this.precoTotalVenda = preco - (preco * (this.descontoVenda / 100));
    }

    @Override
    public String toString() {
        return codigoVenda + " - " + dataVenda + " - " + descontoVenda + " - " + precoTotalVenda;
    }
}
