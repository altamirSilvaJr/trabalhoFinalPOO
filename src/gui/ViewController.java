package gui;

import java.util.ArrayList;
import java.util.List;

import entities.Fornecedor;
import entities.Produto;
import gui.utils.Alerts;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewController {
	
	//listas de controle
	private List<Fornecedor> fornecedores = new ArrayList<>();
	private List<Produto> produtos = new ArrayList<>(); 
	
	//Itens na aba fornecedores
	@FXML
	private TextField inputNomeFornecedor;
	@FXML
	private TextField inputCnpjFornecedor;
	@FXML
	private TextField inputTelefoneFornecedor;
	@FXML
	private TextField inputEnderecoFornecedor;
	@FXML
	private Button cancelarFornecedor;
	@FXML
	private Button salvarFornecedor;
	
	//Itens na aba de produtos
	@FXML
	private TextField inputNomeProduto;
	@FXML
	private TextField inputValorCompra;
	@FXML
	private TextField inputValorVenda;
	@FXML
	private TextArea inputDescricaoProduto;
	@FXML
	private TextField inputPeso;
	@FXML
	private TextField inputQtd;
	@FXML
	private TextField inputCodProd;
	@FXML
	private Button btnSalvarProd;
	@FXML
	private Button btnCancelarProd;

	@FXML
	private Button btAddProd;
	
	@FXML
	public void onbtAddProdAction() {
		System.out.println("Click");
	}
	
	@FXML
	public void onbtSalvarFornecedorAction() {
		Fornecedor forn = new Fornecedor();
		if(inputNomeFornecedor.getText().isEmpty() || inputCnpjFornecedor.getText().isEmpty() || inputTelefoneFornecedor.getText().isEmpty() || inputEnderecoFornecedor.getText().isEmpty()) {
			Alerts.showAlert("Erro de Preenchimento", "Erro", "Todos os campos são de preenchimento obrigatório", AlertType.ERROR);
		}else {
		forn.setNomeFornecedor(inputNomeFornecedor.getText());
		forn.setCnpjFornecedor(inputCnpjFornecedor.getText());
		forn.setTelefoneFornecedor(inputTelefoneFornecedor.getText());
		forn.setEnderecoFornecedor(inputEnderecoFornecedor.getText());
		fornecedores.add(forn);
		
		for(Fornecedor f : fornecedores) {
			System.out.println(f.getNomeFornecedor());
		}
		}
	}
	
	@FXML
	public void obbtCancelarFornecedorAction() {
		inputNomeFornecedor.setText("");
		inputCnpjFornecedor.setText("");
		inputTelefoneFornecedor.setText("");
		inputEnderecoFornecedor.setText("");
	}
	
	public void onbtSalvarProd() throws Exception {
		if(inputNomeProduto.getText().isEmpty() || inputValorCompra.getText().isEmpty()
				|| inputValorVenda.getText().isEmpty() || inputValorCompra.getText().isEmpty()
				|| inputDescricaoProduto.getText().isEmpty() || inputPeso.getText().isEmpty()
				|| inputQtd.getText().isEmpty() || inputCodProd.getText().isEmpty()) {
			Alerts.showAlert("Erro de Preenchimento", "Erro", "Todos os campos são de preenchimento obrigatório", AlertType.ERROR);
		}else{
			Produto prod = new Produto();
			prod.setNomeProduto(inputNomeProduto.getText());
			try {
				prod.setValorCompraProduto(Double.parseDouble(inputValorCompra.getText()));
				prod.setValorVendaProduto(Double.parseDouble(inputValorVenda.getText()));
				if(Double.parseDouble(inputPeso.getText()) < 0 ) {
					throw new Exception("O peso deve ser maior do que 0");
					//Alerts.showAlert("Erro de Preenchimento", "Erro", "O peso deve ser maior do que zero.", AlertType.ERROR);
				}else {
					prod.setPesoProduto(Double.parseDouble(inputPeso.getText()));
				}
			}catch (NumberFormatException e) {
				Alerts.showAlert("Erro de Preenchimento", "Erro", "Os campos valor de compra, valor de venda e peso devem ser um número decimal", AlertType.ERROR);
			}
			prod.setDescricaoProduto(inputDescricaoProduto.getText());
			prod.setQtdProduto(Integer.parseInt(inputQtd.getText()));
			prod.setCodProguto(Integer.parseInt(inputCodProd.getText()));

			produtos.add(prod);
			for(Produto p : produtos) {
				System.out.println(p.getNomeProduto());
			}
			
		}
	}
		
	public void onbtCancelarProd() {
		inputNomeProduto.setText("");
		inputValorCompra.setText("");
		inputValorVenda.setText("");
		inputDescricaoProduto.setText("");
		inputPeso.setText("");
		inputQtd.setText("");
		inputCodProd.setText("");
	}
	
}
