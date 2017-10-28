import java.time.LocalDateTime;

class Movimentacao {
	public LocalDateTime data;
	public double valor;
	public double saldo;
	public int operacao;
	public String parametro;

	public Movimentacao(double valor, double total, Conta.Operacao operacao, String parametro) {
		this.saldo = total + valor;
		this.data = LocalDateTime.now();
		this.valor = valor;
		this.parametro = parametro;
	}
}