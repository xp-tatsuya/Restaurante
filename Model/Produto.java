package Model;

public class Produto {
	
	private String id;
	private String codeFornecedor;
	private String nome;
	private String dataFab;
	private String dataVal;
	private String marca;
	private String categoria;
	private String precoUn;
	private String estoque;
	private String precoTotal;
	
	public Produto() {
		super();
	}
	
	public Produto(String id, String codeFornecedor, String nome, String dataFab, String dataVal, String marca,
			String categoria, String precoUn, String estoque, String precoTotal) {
		super();
		this.id = id;
		this.codeFornecedor = codeFornecedor;
		this.nome = nome;
		this.dataFab = dataFab;
		this.dataVal = dataVal;
		this.marca = marca;
		this.categoria = categoria;
		this.precoUn = precoUn;
		this.estoque = estoque;
		this.precoTotal = precoTotal;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodeFornecedor() {
		return codeFornecedor;
	}
	public void setCodeFornecedor(String codeFornecedor) {
		this.codeFornecedor = codeFornecedor;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataFab() {
		return dataFab;
	}
	public void setDataFab(String dataFab) {
		this.dataFab = dataFab;
	}
	public String getDataVal() {
		return dataVal;
	}
	public void setDataVal(String dataVal) {
		this.dataVal = dataVal;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPrecoUn() {
		return precoUn;
	}
	public void setPrecoUn(String precoUn) {
		this.precoUn = precoUn;
	}
	public String getEstoque() {
		return estoque;
	}
	public void setEstoque(String estoque) {
		this.estoque = estoque;
	}
	public String getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(String precoTotal) {
		this.precoTotal = precoTotal;
	}

}
