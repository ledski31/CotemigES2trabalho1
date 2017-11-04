import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conta implements Serializable {

	// ================================================================================================================
	//	PARTE ESTATICA

	private static final long serialVersionUID = 1L;
	public static enum Operacao {
		SAQUE, DEPOSITO, SALDO, EXTRATO
	}
	public static final String codigoCC = "-0";
	public static final String codigoPP = "-1";
	
	public static boolean isIDparcial( String id ) {
		return id.matches( "[0-9]{3}" );//(-[01])?
	}

	public static boolean isIDcompleto( String id ) {
		return id.matches( "[0-9]{3}-[01]" );
	}

	public static boolean isIDcc( String id ) {
		return id.matches( "[0-9]{3}-0" );
	}

	public static boolean isIDpp( String id ) {
		return id.matches( "[0-9]{3}-1" );
	}

	public static String sayTipo( String id ) {
		if( isIDparcial(id) ) {
			String tipo = id.substring(id.length() - 2);
			if( tipo.equals( codigoCC ) ) return "Corrente";
			else if( tipo.equals( codigoPP ) ) return "Poupanca";
		}
		return "Invalida";
	}



	// ================================================================================================================
	// PARTE INSTANCIAVEL


	
	public final String id;
	public final String titular;
	private double total = 0;
	private List<Movimentacao> extrato;
	
	public Conta( String id, String titular ) {
		this.id = id;
		this.titular = titular;
		extrato = new ArrayList<Movimentacao>();
	}

	public String getId() {
		return this.id;
	}

	public double saque( double valor ) {
		if( total > valor ) {
			this.total -= valor;
			Movimentacao m = new Movimentacao(valor * -1.0, total, Conta.Operacao.SAQUE, "");
			extrato.add( m );
			return valor;
		}
		return 0;
	}

	public boolean deposito( double valor ) {
		return deposito( valor, "" );
	}
	private boolean deposito( double valor, String origem ) {
		if( valor > 0 ) {
			this.total += valor;
			extrato.add( new Movimentacao( valor, total, Conta.Operacao.DEPOSITO, origem ));
			return true;
		}
		return false;
	}

	public double saldo() {
		return total;
	}

	public List<Movimentacao> extrato() {
		List<Movimentacao> extratoCopia = new ArrayList<>();
		for( Movimentacao m : extrato )
			extratoCopia.add( new Movimentacao( m ));
		return extratoCopia;
	}

	public boolean transfer( double valor, Conta c ) {
		return true;
		// if( valor > 0 || this.total < valor ) 
		// 	return false;
		// else {
		// 	this.total -= valor;
		// 	c.deposito( valor, "de " + this.id );
		// 	extrato.add( new Movimentacao(valor, total, Conta.Operacao.DEPOSITO, "para " + c.id ));
		// 	return true;
		// }
	}

}



// ================================================================================================================
// CLASSE AUXILIARES



class Movimentacao implements Serializable {
	private static final long serialVersionUID = 1L;
	public LocalDateTime data;
	public double valor;
	public double saldo;
	public Conta.Operacao operacao;
	public String parametro;

	public Movimentacao(double valor, double total, Conta.Operacao operacao, String parametro) {
		this.data = LocalDateTime.now();
		this.operacao = operacao;
		this.valor = valor;
		this.saldo = total;
		this.parametro = parametro;
	}

	public Movimentacao ( Movimentacao m ) {
		this.operacao = m.operacao;
		this.data = m.data;
		this.valor = m.valor;
		this.saldo = m.saldo;
		this.parametro = m.parametro;
	}
}