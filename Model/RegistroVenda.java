package Model;

public class RegistroVenda {
	private String numeroPedido;
	private String nomeFuncionario;
	private String numeroMesa;
	private String codigoCardapio;
	private String nomeCardapio;
	private String quantidade;
	private String valorUnitario;
	private String desconto;
	private String valorTotal;
	
	public RegistroVenda(String numeroPedido, String nomeFuncionario, String numeroMesa, String codigoCardapio,
			String nomeCardapio, String quantidade, String valorUnitario, String desconto, String valorTotal) {
		super();
		this.numeroPedido = numeroPedido;
		this.nomeFuncionario = nomeFuncionario;
		this.numeroMesa = numeroMesa;
		this.codigoCardapio = codigoCardapio;
		this.nomeCardapio = nomeCardapio;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
		this.desconto = desconto;
		this.valorTotal = valorTotal;
	}

	public RegistroVenda() {
		super();
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(String numeroMesa) {
		this.numeroMesa = numeroMesa;
	}

	public String getCodigoCardapio() {
		return codigoCardapio;
	}

	public void setCodigoCardapio(String codigoCardapio) {
		this.codigoCardapio = codigoCardapio;
	}

	public String getNomeCardapio() {
		return nomeCardapio;
	}

	public void setNomeCardapio(String nomeCardapio) {
		this.nomeCardapio = nomeCardapio;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getDesconto() {
		return desconto;
	}

	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
}
