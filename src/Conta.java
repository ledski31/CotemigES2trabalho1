import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {
	private int id;
	private boolean tipo;
	private double total;
	private List<Mov> extrato;
	private Map<LocalDateTime, Mov> historico;
	
	public Conta(int id, boolean tipo) {
		this.id = id;
		this.tipo = tipo;
		extrato = new ArrayList<Mov>();
		historico = new HashMap<LocalDateTime, Mov>();
	}

	public boolean Saque( double valor ) {
		if( total > valor ) {
			extrato.add( new Mov( valor ) );
			return true;
		}
		return false;
	}

	public boolean Deposito( double valor ) {
		extrato.add( new Mov( valor ) );
		return true;
	}

	public double Saldo() {
		return total;
	}

	public void Extrato() {

	}

	class Mov {
		public LocalDateTime data;
		public double valor;
		public double saldo; 

		public Mov(double valor) {
			total += valor;
			this.saldo = total;
			this.data = LocalDateTime.now();
			this.valor = valor;
		}
	}

}