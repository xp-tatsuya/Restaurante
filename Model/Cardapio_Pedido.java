package Model;

public class Cardapio_Pedido {
	
	private String id;
	private String codeCardapio;
	private String codePedido;
	private String quantidade;
	private String observacao;
	
	public Cardapio_Pedido(String id, String codeCardapio, String codePedido, String quantidade, String observacao) {
		super();
		this.id = id;
		this.codeCardapio = codeCardapio;
		this.codePedido = codePedido;
		this.quantidade = quantidade;
		this.observacao = observacao;
	}

	public Cardapio_Pedido() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeCardapio() {
		return codeCardapio;
	}

	public void setCodeCardapio(String codeCardapio) {
		this.codeCardapio = codeCardapio;
	}

	public String getCodePedido() {
		return codePedido;
	}

	public void setCodePedido(String codePedido) {
		this.codePedido = codePedido;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	

}
