package Model;

public class Mesa {
	
	private String id;
	private String capacidade;
	private String condicao;
	
	public Mesa() {
		super();
	}
	
	public Mesa(String id, String capacidade, String condicao) {
		super();
		this.id = id;
		this.capacidade = capacidade;
		this.condicao = condicao;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(String capacidade) {
		this.capacidade = capacidade;
	}
	public String getCondicao() {
		return condicao;
	}
	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

}
