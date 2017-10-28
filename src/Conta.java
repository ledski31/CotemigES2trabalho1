import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {
	/*	PARTE ESTATICA */
	public static final String[] Operacoes = {"SAQUE","DEPOSITO","SALDO","EXTRATO"};
	public static enum Operacao {
		SAQUE, DEPOSITO, SALDO, EXTRATO
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

	/* PARTE INTACIAVEL */

	private String id;
	private double total;
	private List<Movimentacao> extrato;
	private Map<LocalDateTime, Movimentacao> historico;
	
	public Conta( String id ) {
		this.id = id;
		extrato = new ArrayList<Movimentacao>();
		historico = new HashMap<LocalDateTime, Movimentacao>();
	}

	public String getId() {
		return this.id;
	}

	public boolean saque( double valor ) {
		if( total > valor ) {
			total -= valor;
			Movimentacao m = new Movimentacao(valor, total, Conta.Operacao.SAQUE, null);
			extrato.add( m );
			return true;
		}
		return false;
	}

	public boolean deposito( double valor ) {
		if( valor > 0 ) {
			total += valor;
			extrato.add( new Movimentacao(valor, total, Conta.Operacao.DEPOSITO, null));
			return true;
		}
		return false;
	}

	public double saldo() {
		return total;
	}

	public void Extrato() {

	}

}