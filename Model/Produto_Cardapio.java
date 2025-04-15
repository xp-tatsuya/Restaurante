package Model;

public class Produto_Cardapio {
	
	private String id;
	private String codeCardapio;
	private String codeProduto;
	private String quantidade;
	
	public Produto_Cardapio() {
		super();
	}
	
	public Produto_Cardapio(String id, String codeCardapio, String codeProduto, String quantidade) {
		super();
		this.id = id;
		this.codeCardapio = codeCardapio;
		this.codeProduto = codeProduto;
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
	public String getCodeProduto() {
		return codeProduto;
	}
	public void setCodeProduto(String codeProduto) {
		this.codeProduto = codeProduto;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

}
