package Model;

public class Pedido {
	
	private String id;
	private String codeFuncionario;
	private String codeMesa;
	private String dataPedido;
	private String condicao;
	private String observacoes;
	private String desconto;
	private String precoTotal;
	
	public Pedido() {
		super();
	}
	
	public Pedido(String id, String codeFuncionario, String codeMesa, String dataPedido, String condicao,
			String observacoes, String desconto, String precoTotal) {
		super();
		this.id = id;
		this.codeFuncionario = codeFuncionario;
		this.codeMesa = codeMesa;
		this.dataPedido = dataPedido;
		this.condicao = condicao;
		this.observacoes = observacoes;
		this.desconto = desconto;
		this.precoTotal = precoTotal;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodeFuncionario() {
		return codeFuncionario;
	}
	public void setCodeFuncionario(String codeFuncionario) {
		this.codeFuncionario = codeFuncionario;
	}
	public String getCodeMesa() {
		return codeMesa;
	}
	public void setCodeMesa(String codeMesa) {
		this.codeMesa = codeMesa;
	}
	public String getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(String dataPedido) {
		this.dataPedido = dataPedido;
	}
	public String getCondicao() {
		return condicao;
	}
	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getDesconto() {
		return desconto;
	}
	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}
	public String getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(String precoTotal) {
		this.precoTotal = precoTotal;
	}

}
