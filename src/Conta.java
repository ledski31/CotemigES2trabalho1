import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {
	private String id;
	private boolean tipo;
	private double total;
	private List<Mov> extrato;
	private Map<LocalDateTime, Mov> historico;
	
	public Conta( String id ) {
		this.id = id;
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

	public static boolean isValid( String id ) {
		return id.matches( "[0-9]{3}-[01]{1}" );
	}

	public static String sayTipo( String id ) {
		if( isValid(id) ) {
			String tipo = id.substring(id.length() - 2);
			if( tipo.equals( "-0" ) ) return "Corrente";
			else if( tipo.equals( "-1" ) ) return "Poupanca";
		}
		return "Invalida";
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