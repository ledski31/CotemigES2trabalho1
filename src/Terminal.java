import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Terminal {
	//private List<Conta> db = new ArrayList<Conta>();
	private Map<String, Conta> contas = new HashMap<>();
	private Scanner sc = new Scanner( System.in );

	public Conta getConta( String id ) {
		if( Conta.isValid( id )) {
			contas.putIfAbsent( id, new Conta( id ));
			return contas.get( id );
		}
		return null;
	}

	public void iniciarUI() {
		clear();
		System.out.println("Terminal Bancario via texto");
		System.out.println("\nDigite o numero da conta (XXX-X)");
		String id = sc.next(); //"111-1";
		Conta conta = getConta( id );
		if( conta == null ) System.out.println( "Numero de conta invalido");
		else {
			//clear();
			System.out.println("\n\nConta " + id);
			System.out.println("Operacoes disponiveis");
			System.out.println("1 - Saque\n2 - Deposito\n3 - Saldo\n4 - Extrato\n0 - Sair");
			int op = sc.nextInt();
			escolherOperacao( conta, op );
		}
	}

	private void escolherOperacao( Conta c, int op ) {
		switch( op ) {
			case 0: System.exit(0);
			case 1: saque( c ); break;
			case 2: deposito( c ); break;
			case 3: saldo( c ); break;
			case 4: extrato( c ); break;
			default:	System.out.println( "Operacao nao-existente" );
		}
	}

	private void saque( Conta c ) {
		System.out.println("\nDigite o valor a ser sacado: ");
		Double v = sc.nextDouble();
		System.out.print( "\nSaque: " + String.valueOf( v ) );
		voltarOuSair();
	}

	private void saldo( Conta c ) {
		System.out.println( "\nSaldo: " + String.valueOf( c.saldo() ) );
		voltarOuSair();
	}

	private void deposito( Conta c ) {

	}

	private void extrato( Conta c ) {
	}

	private void voltarOuSair() {
		System.out.println( "\nDeseja fazer outra operacao (s/n) ?");
		String op = sc.next();
		if( op.equals( "n" ) )
			System.exit(0);
		else iniciarUI();
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
}