package Model;

public class Cardapio_Pedido {
	
	private String id;
	private String codeCardapio;
	private String codePedido;
	private String quantidade;
	
	public Cardapio_Pedido() {
		super();
	}
	
	public Cardapio_Pedido(String id, String codeCardapio, String codePedido, String quantidade) {
		super();
		this.id = id;
		this.codeCardapio = codeCardapio;
		this.codePedido = codePedido;
		this.quantidade = quantidade;
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

}
