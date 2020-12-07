package gui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entities.Fornecedor;
import entities.Produto;
import entities.ProdutoVenda;
import entities.Venda;
import gui.utils.Alerts;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewController {
	
	//formatando numeros decimais para 2 casas.
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	//variável de controle try/catch para garantir que um item não será inserido caso caia em algum catch.
	private boolean erroDePreenchimento = false;
	
	//listas de controle
	private List<Fornecedor> fornecedores = new ArrayList<>();
	private static List<Produto> produtos = new ArrayList<>(); 
	private List<Venda> vendas = new ArrayList<>();
	private List<ProdutoVenda> prodVendas = new ArrayList<>();
	
	private ObservableList<Venda> obsListVendas;
	private static ObservableList<Produto> obsListProd;
	private ObservableList<ProdutoVenda> obsProdListVenda;
	
	//criando lista de produtos iniciais
		public static void prodInit() {
			Produto prod1 = new Produto();
			prod1.setCodProguto(123456);
			prod1.setDescricaoProduto("Caixa de morangos");
			prod1.setNomeProduto("Caixa de Morangos");
			prod1.setPesoProduto(120);
			prod1.setQtdProduto(50);
			prod1.setValorCompraProduto(2.50);
			prod1.setValorVendaProduto(5.99);
			produtos.add(prod1);
			
			Produto prod2 = new Produto();
			prod2.setCodProguto(123457);
			prod2.setDescricaoProduto("Lata Cerveja 350ml");
			prod2.setNomeProduto("Lata Cerveja");
			prod2.setPesoProduto(350);
			prod2.setQtdProduto(132);
			prod2.setValorCompraProduto(1.99);
			prod2.setValorVendaProduto(2.49);
			produtos.add(prod2);
			
			obsListProd = FXCollections.observableArrayList(produtos);
		}
	
	//itens na aba Registrar Venda
		@FXML
		private DatePicker dataVenda;
		@FXML
		private TextField descontoVenda;
		@FXML
		private Button btAddProd;
		@FXML
		private TextField valorTotalVenda;
		@FXML
		private Button btCancelarVenda;
		@FXML
		private Button btSalvarVenda;
		@FXML
		private ComboBox<Produto> selectProdutos;
		@FXML
		private TextField qtdProdutoVenda;
		@FXML
		private TableView<ProdutoVenda> tableViewProdutosVenda;
		@FXML
		private TableColumn<ProdutoVenda, Integer> tableProdVendaColumnCodProd;
		@FXML
		private TableColumn<ProdutoVenda, String> tableProdVendaColumnProd;
		@FXML
		private TableColumn<ProdutoVenda, Double> tableProdVendaColumnPrecoUn;
		@FXML
		private TableColumn<ProdutoVenda, Integer> tableProdVendColumnQtd;
		@FXML
		private TableColumn<ProdutoVenda, Double> tableProdVendaColumnPrecoTotal;
		@FXML
		private TableColumn<ProdutoVenda, ProdutoVenda> tableProdVendaColumnBtExcluir;
		
		//alimentando o combobox da tela de vendas sempre q ele for clicado
		public void setData() {
				selectProdutos.setItems(obsListProd);
		}
		
		@FXML
		public void onbtCancelarVenda() {
			//limpando todos os campos da tela de vendas caso o botão cancelar tenha sido clicado.
			descontoVenda.setText("");
			valorTotalVenda.setText("");
			dataVenda.setValue(LocalDate.now());
			qtdProdutoVenda.setText("");
			prodVendas.clear();
			atualizaProdsVenda();
		}
		
		public void atualizaProdsVenda() {
			obsProdListVenda = FXCollections.observableArrayList(prodVendas);
			tableProdVendaColumnCodProd.setCellValueFactory(new PropertyValueFactory<>("codProd"));
			tableProdVendaColumnProd.setCellValueFactory(new PropertyValueFactory<>("nomeProd"));
			tableProdVendaColumnPrecoUn.setCellValueFactory(new PropertyValueFactory<>("precoProd"));
			tableProdVendColumnQtd.setCellValueFactory(new PropertyValueFactory<>("qtdProduto"));
			tableProdVendaColumnPrecoTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
			tableViewProdutosVenda.setItems(obsProdListVenda);
			initExcluiProdButton();
		}
		
		private void initExcluiProdButton() {
			tableProdVendaColumnBtExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableProdVendaColumnBtExcluir.setCellFactory(param -> new TableCell<ProdutoVenda, ProdutoVenda>(){
				private final Button button = new Button("Excluir");
				
				@Override
				protected void updateItem(ProdutoVenda obj, boolean empty) {
					if(obj == null) {
						setGraphic(null);
						return;
					}
					
					setGraphic(button);
					button.setOnAction(event -> removeProdVenda(obj));
				}
			});
		}
		
		//método para remover venda ao clicar no botão de cancelar na tela de relatório de venda
		private void removeProdVenda(ProdutoVenda obj) {
			//Alerta pra confirmar a deleção do daddo.
			Optional<ButtonType> result = Alerts.showConfirmation("Confirmar", "Tem certeza que deseja remover esse produto?");
			
			if(result.get() == ButtonType.OK) { 
				try {
					prodVendas.remove(obj);
					atualizaProdsVenda();
					double total = 0;
					for(ProdutoVenda pV : prodVendas) {
						total += pV.getValorTotal();
					}
					double desconto;
					if(descontoVenda.getText().isEmpty()) {
						desconto = 0.0;
					}else {
						desconto = Integer.parseInt(descontoVenda.getText());
					}
					desconto = total - total * (desconto / 100);
					String totalFinal = df2.format(desconto);
					valorTotalVenda.setText(totalFinal);
				}catch(Exception e) {
					Alerts.showAlert("Erro ao remover a venda", null, e.getMessage(), AlertType.ERROR);
				}
			}
		}
		
		public void onbtAddProdVenda() {
			erroDePreenchimento = false;
			
			ProdutoVenda pVenda = new ProdutoVenda();
			pVenda.setProduto(selectProdutos.getValue());
			try {
			pVenda.setQtdProduto(Integer.parseInt(qtdProdutoVenda.getText()));
			}catch(Exception e) {
				Alerts.showAlert("Erro de Preenchimento", "Erro", "O campo quantidade deve ser um número inteiro", AlertType.ERROR);
				erroDePreenchimento = true;
			}
			pVenda.attValorTotal();
			
			if(erroDePreenchimento == false) {
				pVenda.setValores();
				prodVendas.add(pVenda);
				atualizaProdsVenda();
				double total = 0;
				for(ProdutoVenda pV : prodVendas) {
					total += pV.getValorTotal();
				}
				double desconto;
				if(descontoVenda.getText().isEmpty()) {
					desconto = 0.0;
				}else {
					desconto = Integer.parseInt(descontoVenda.getText());
				}
				desconto = total - total * (desconto / 100);
				String totalFinal = df2.format(desconto);
				valorTotalVenda.setText(totalFinal);
			}
		}
		
		@FXML
		public void onbtSalvarVenda() {
			erroDePreenchimento = false;
			//criando objeto para ser incluído na lista de vendas.
			Venda vend = new Venda();
			vend.setDataVenda(dataVenda.getValue());
			try {
			vend.setDescontoVenda(Integer.parseInt(descontoVenda.getText()));
			}catch(Exception e) {
				Alerts.showAlert("Erro de Preenchimento", "Erro", "O campo desconto deve ser um número inteiro", AlertType.ERROR);
				erroDePreenchimento = true;
			}
			vend.setPrecoTotalVenda(Double.parseDouble(valorTotalVenda.getText().replaceAll(",", ".")));
			vend.inserirProdutos(prodVendas);
			if(erroDePreenchimento == false) {
				vendas.add(vend);
				Alerts.showAlert("Sucesso", "Sucesso", "Venda adicionada com sucesso", AlertType.INFORMATION);
				onbtCancelarVenda();
				prodVendas.clear();
				atualizaProdsVenda();
			}
		}
	
	//Aba Relatório Vendas
	@FXML
	private TableView<Venda> tableViewVenda;
	@FXML
	private TableColumn<Venda, LocalDate> tableVendaColumnData;
	@FXML
	private TableColumn<Venda, Integer> tableVendaColumnDesconto;
	@FXML
	private TableColumn<Venda, Double> tableVendaColumnValorTotal;
	@FXML
	private TableColumn<Venda, Venda> tableRelVendBtExcluir;
	
	@FXML
	public void atualizaRelatorioVenda() {
		obsListVendas = FXCollections.observableArrayList(vendas);
		tableVendaColumnData.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
		tableVendaColumnDesconto.setCellValueFactory(new PropertyValueFactory<>("descontoVenda"));
		tableVendaColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("precoTotalVenda"));
		tableViewVenda.setItems(obsListVendas);
		initCancelaButton();
	}
	
	//método para criar o botão de cancelar Veneda
		private void initCancelaButton() {
			tableRelVendBtExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableRelVendBtExcluir.setCellFactory(param -> new TableCell<Venda, Venda>(){
				private final Button button = new Button("Excluir");
				
				@Override
				protected void updateItem(Venda obj, boolean empty) {
					if(obj == null) {
						setGraphic(null);
						return;
					}
					
					setGraphic(button);
					button.setOnAction(event -> removeVenda(obj));
				}
			});
		}
		
		//método para remover venda ao clicar no botão de cancelar na tela de relatório de venda
		private void removeVenda(Venda obj) {
			//Alerta pra confirmar a deleção do daddo.
			Optional<ButtonType> result = Alerts.showConfirmation("Confirmar", "Tem certeza que deseja excluir essa venda?");
			
			if(result.get() == ButtonType.OK) { 
				try {
					vendas.remove(obj);
					atualizaRelatorioVenda();
				}catch(Exception e) {
					Alerts.showAlert("Erro ao remover a venda", null, e.getMessage(), AlertType.ERROR);
				}
			}
		}
		
	
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
	
	//adicionando novo fornecedor a lista de fornecedores
		@FXML
		public void onbtSalvarFornecedorAction() {
			//declarando fornecedor novo para garantir que toda vez que o método for chamado a variável vai apontar para um novo endereço de memória e descartar os dados anteriores
			Fornecedor forn = new Fornecedor();
			//verificando se todos os dados estão preenchidos
			if(inputNomeFornecedor.getText().isEmpty() || inputCnpjFornecedor.getText().isEmpty() || inputTelefoneFornecedor.getText().isEmpty() || inputEnderecoFornecedor.getText().isEmpty()) {
				//exibindo alerta caso algum dado não foi prrenchido
				Alerts.showAlert("Erro de Preenchimento", "Erro", "Todos os campos são de preenchimento obrigatório", AlertType.ERROR);
			}else {
			//setando dados do fornecedor no objeto 
			forn.setNomeFornecedor(inputNomeFornecedor.getText());
			forn.setCnpjFornecedor(inputCnpjFornecedor.getText());
			forn.setTelefoneFornecedor(inputTelefoneFornecedor.getText());
			forn.setEnderecoFornecedor(inputEnderecoFornecedor.getText());
			fornecedores.add(forn);
			Alerts.showAlert("Fornecedor adicionado com sucesso", "Sucesso", "Fornecedor adicionado com sucesso", AlertType.INFORMATION);
			obbtCancelarFornecedorAction();
			}
		}
		
		@FXML
		public void obbtCancelarFornecedorAction() {
			//método para limpar todos os campos da tela de cadastro de fornecedor caso tenha clicado no botão cancelar
			inputNomeFornecedor.setText("");
			inputCnpjFornecedor.setText("");
			inputTelefoneFornecedor.setText("");
			inputEnderecoFornecedor.setText("");
		}
	
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
	
	//método para salvar os produtos na lista de produtos.
	@FXML
	public void onbtSalvarProd() throws Exception {
		erroDePreenchimento = false;
		//verificando se todos os cmapos estão preenchidos
		if(inputNomeProduto.getText().isEmpty() || inputValorCompra.getText().isEmpty()
				|| inputValorVenda.getText().isEmpty() || inputValorCompra.getText().isEmpty()
				|| inputDescricaoProduto.getText().isEmpty() || inputPeso.getText().isEmpty()
				|| inputQtd.getText().isEmpty() || inputCodProd.getText().isEmpty()) {
			//caso algum campo não esteja preenchido, exibir alerta de erro
			Alerts.showAlert("Erro de Preenchimento", "Erro", "Todos os campos são de preenchimento obrigatório", AlertType.ERROR);
		}else{
			Produto prod = new Produto();
			prod.setNomeProduto(inputNomeProduto.getText());
			try {
				//garantindo que o programa não quebrará caso sejam inseridos caracteres inválidos nos campos que são numéricos
				prod.setValorCompraProduto(Double.parseDouble(inputValorCompra.getText()));
				prod.setValorVendaProduto(Double.parseDouble(inputValorVenda.getText()));
				if(Double.parseDouble(inputPeso.getText()) < 0 ) {
					//caso os campos dentro do try não sejam convertidos para double por algum motivo, capturar o erro e mostrar ao usuário
					Alerts.showAlert("Erro de Preenchimento", "Erro", "O peso deve ser maior do que zero.", AlertType.ERROR);
				}else {
					prod.setPesoProduto(Double.parseDouble(inputPeso.getText()));
				}
			}catch (NumberFormatException e) {
				erroDePreenchimento = true;
				Alerts.showAlert("Erro de Preenchimento", "Erro", "Os campos valor de compra, valor de venda e peso devem ser um números decimais", AlertType.ERROR);
			}
			prod.setDescricaoProduto(inputDescricaoProduto.getText());
			try {
			prod.setQtdProduto(Integer.parseInt(inputQtd.getText()));
			prod.setCodProguto(Integer.parseInt(inputCodProd.getText()));
			}catch (NumberFormatException e) {
				erroDePreenchimento = true;
				Alerts.showAlert("Erro de Preenchimento", "Erro", "Os campos quantidade e código de produtos devem ser um números inteiros", AlertType.ERROR);
			}
			
			if(erroDePreenchimento == false) {
			produtos.add(prod);
			obsListProd = FXCollections.observableArrayList(produtos);
			Alerts.showAlert("Produto adicionado com sucesso", "Sucesso", "Produto adicionado com sucesso", AlertType.INFORMATION);
			onbtCancelarProd();
			}
		}
	}
	
	@FXML
	public void onbtCancelarProd() {
		//limpando todos os campos da tela de cadastro de produtos caso o botão cancelar tenha sido clicado
		inputNomeProduto.setText("");
		inputValorCompra.setText("");
		inputValorVenda.setText("");
		inputDescricaoProduto.setText("");
		inputPeso.setText("");
		inputQtd.setText("");
		inputCodProd.setText("");
	}
		
}
