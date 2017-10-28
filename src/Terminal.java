import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Terminal {
	//private List<Conta> db = new ArrayList<Conta>();
	private Map<String, Conta> contas = new HashMap<>();
	private Scanner sc = new Scanner( System.in );
	private final String caret = "\n:";

	public Terminal( Map<String, Conta> contas ) {
		this.contas = contas;
	}
	public Terminal() {}





	// METODOS DE EXIBICAO NA TELA

	public void listarContas() {
		cabecalho();
		say( "Contas existentes" );
		for ( String key : contas.keySet() )
			say( key + "\n" );
	}

	public void login() {
		String msg = "";
		while (true) {
			cabecalho();
			say( "Digite o numero da conta (XXX) ou 'x' para sair\n" );
			say( msg );
			say( caret );
			String id = input();
			msg = Conta.isIDparcial( id ) ? "" : "Numero de conta invalido. Tente novamente\n";
			if( id.equals("x") ) System.exit(0);
			else if( msg.length() == 0 ) {
				getConta( id );
				menu( id );
			}
		}
	}

	private void menu( String idParcial ) {
		String msg = "";
		while (true) {
			cabecalho();
			say( "Conta " + idParcial + " FULANO DA SILVA SAURO\n" );
			say( "\nOperacoes disponiveis\n" );
			say( "x - Sair\nv - Voltar\n");
			say( "\nConta corrente\n");
			say( "1 - Saque\n2 - Deposito\n3 - Saldo\n4 - Extrato\n" );
			say( "\nConta Poupanca\n");
			say( "5 - Saque\n6 - Deposito\n7 - Saldo\n8 - Extrato\n" );
			say( msg );
			say( caret );
			
			String op = input();
			Conta cc = getConta ( idParcial + Conta.codigoCC );
			Conta pp = getConta ( idParcial + Conta.codigoPP );
			switch( op ) {
				case "x": System.exit(0);
				case "v": return;
				case "1": saque( cc ); break;
				case "2": deposito( cc ); break;
				case "3": saldo( cc ); break;
				case "4": extrato( cc ); break;
				case "5": saque( pp ); break;
				case "6": deposito( pp ); break;
				case "7": saldo( pp ); break;
				case "8": extrato( pp ); break;
				default:	msg = "\nOperacao invalida. Escolha novamente.\n";
			}
		}
	}





	// METODOS DE OPERACOES INTERNAS

	public Conta getConta( String idCompleto ) {
		if( Conta.isIDcompleto( idCompleto ))
			contas.putIfAbsent( idCompleto, new Conta( idCompleto ));
		return contas.get( idCompleto );
	}

	private void saque( Conta c ) {
		Double val = 0.0;
		String in;
		while( val == 0.0 ) {
			say( "\nDigite o valor a ser sacado\n" + caret );
			in = input();
			try {
				val = Double.valueOf( in );
			} catch ( Exception e ) {
				say( "\nO valor digitado e invalido" );		
			}
		}
		say( "\nConfirma o saque do valor " + val + "? (s/n)\n" + caret );
		in = input();
		if( in.equals( "s" ) )
			say( "\nSaque: " + val );
		else
			say( "\nOperacao cancelada" );
			espera();
	}

	private void saldo( Conta c ) {
		System.out.println( "\nSaldo: " + String.valueOf( c.saldo() ) );
	}

	private void deposito( Conta c ) {

	}

	private void extrato( Conta c ) {
	}





	// METODOS AUXILIARES ==========================================================

	public String input() {
		String in; 
		do { in = sc.nextLine(); }
		while ( in.length() == 0 );
		return in;
	}

	private void cabecalho() {
		clear();
		System.out.println( "Terminal Bancario via texto\n" );
		//say( "\n===============================================\n\n" );
	}

	private void clear() {
		final String os = System.getProperty("os.name");
		if (os.contains("Windows"))
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
				Runtime.getRuntime().exec("clear");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void say( String s ) {
		System.out.print( s );
	}

	private void espera() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {}
	}
}