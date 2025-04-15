package Model;

public class Cardapio {
	
	private String id;
	private String nome;
	private String descricao;
	private String categoria;
	private String precoUnitario;
	
	public Cardapio() {
		super();
	}
	
	public Cardapio(String id, String nome, String descricao, String categoria, String precoUnitario) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.precoUnitario = precoUnitario;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(String precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

}
