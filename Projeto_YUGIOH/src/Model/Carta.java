package Model;

public class Carta {
	private int id;
	private String nome_carta;
	private String codigo;
	private String colecao;
	private String ano_compra;
	private float valor_pago;
	private String ano_venda;
	private float valor_venda;
	private String raridade;
	private String qualidade;
	private int quantidade;
	private byte[] icone;

	public Carta(String nome_carta, String codigo, String colecao, String ano_compra, float valor_pago, String raridade,
			String qualidade) {
		super();

		this.nome_carta = nome_carta;
		this.codigo = codigo;
		this.colecao = colecao;
		this.ano_compra = ano_compra;
		this.valor_pago = valor_pago;
		this.raridade = raridade;
		this.qualidade = qualidade;

	}

	public byte[] getIcone() {
		return icone;
	}

	public void setIcone(byte[] icone) {
		this.icone = icone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome_carta() {
		return nome_carta;
	}

	public void setNome_carta(String nome_carta) {
		this.nome_carta = nome_carta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getColecao() {
		return colecao;
	}

	public void setColecao(String colecao) {
		this.colecao = colecao;
	}

	public String getAno_compra() {
		return ano_compra;
	}

	public void setAno_compra(String ano_compra) {
		this.ano_compra = ano_compra;
	}

	public float getValor_pago() {
		return valor_pago;
	}

	public void setValor_pago(float valor_pago) {
		this.valor_pago = valor_pago;
	}

	public String getAno_venda() {
		return ano_venda;
	}

	public void setAno_venda(String ano_venda) {
		this.ano_venda = ano_venda;
	}

	public float getValor_venda() {
		return valor_venda;
	}

	public void setValor_venda(float valor_venda) {
		this.valor_venda = valor_venda;
	}

	public String getRaridade() {
		return raridade;
	}

	public void setRaridade(String raridade) {
		this.raridade = raridade;
	}

	public String getQualidade() {
		return qualidade;
	}

	public void setQualidade(String qualidade) {
		this.qualidade = qualidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Carta [id=" + id + ", nome_carta=" + nome_carta + ", codigo=" + codigo + ", colecao=" + colecao
				+ ", ano_compra=" + ano_compra + ", valor_pago=" + valor_pago + ", ano_venda=" + ano_venda
				+ ", valor_venda=" + valor_venda + ", raridade=" + raridade + ", qualidade=" + qualidade
				+ ", quantidade=" + quantidade + "]";
	}

}
