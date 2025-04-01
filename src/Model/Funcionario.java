package Model;

public class Funcionario {
	
	private String id;
	private String nome;
	private String senha;
	private String cpf;
	private String email;
	private String telefone;
	private String genero;
	private String endereco;
	private String dataNasc;
	private String cargo;
	private String salario;
	private String dataAdms;
	
	public Funcionario() {
		super();
	}
	
	public Funcionario(String id, String nome, String senha, String cpf, String email, String telefone, String genero,
			String endereco, String dataNasc, String cargo, String salario, String dataAdms) {
		super();
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.genero = genero;
		this.endereco = endereco;
		this.dataNasc = dataNasc;
		this.cargo = cargo;
		this.salario = salario;
		this.dataAdms = dataAdms;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}
	public String getDataAdms() {
		return dataAdms;
	}
	public void setDataAdms(String dataAdms) {
		this.dataAdms = dataAdms;
	}

}
