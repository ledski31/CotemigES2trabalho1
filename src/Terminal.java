import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Terminal {
	//private List<Conta> db = new ArrayList<Conta>();
	private Map<String, Conta> contas = new HashMap<>();
	private Scanner sc = new Scanner( System.in );

	public String input() {
		Scanner s = new Scanner( System.in );
		String in = s.next();
		s.close();
		return in;
	}

	public String read() {
		String input = null;
		byte[] typed = new byte[5]; 
		try {

			// System.out.println( "input available: " + System.in.available() );
			// // remove todo input anterior
			// while( System.in.available() > 0 )
			// 	System.in.skip(1);
			
			// System.out.println( "input available: " + System.in.available() );

			// le somente 5 bytes (5 caracteres) do input

			InputStreamReader isReader = new InputStreamReader(System.in);
			BufferedReader bufReader = new BufferedReader(isReader);
			input = bufReader.readLine();
			
			
			//System.in.read(typed);
			//input = new String(typed, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	public Conta getConta( String id ) {
		if( Conta.isValid( id )) {
			contas.putIfAbsent( id, new Conta( id ));
			return contas.get( id );
		}
		return null;
	}

	private void cabecalho() {
		clear();
		System.out.println("Terminal Bancario via texto");
	}

	public void iniciar() {
		cabecalho();
		System.out.println("\nDigite o numero da conta (XXX-X) ou (x) para sair");
		String id = read();
		Conta c = getConta( id );
		if( c == null ) {
			System.out.println( "Numero de conta invalido");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
			iniciar();
		}
		else menu ( c );
	}

	private void menu( Conta c ) {
		cabecalho();
		System.out.println("\nConta " + c.getId() );
		System.out.println("Operacoes disponiveis");
		System.out.println("1 - Saque\n2 - Deposito\n3 - Saldo\n4 - Extrato\n0 - Sair");
		
		//String op = input();
		Scanner s = new Scanner( System.in );
		String op = s.next();
		s.close();

		escolherOperacao( c, op );
	}

	private void escolherOperacao( Conta c, String op ) {
		switch( op ) {
			case "0": iniciar();
			case "1": saque( c ); break;
			case "2": deposito( c ); break;
			case "3": saldo( c ); break;
			case "4": extrato( c ); break;
			default:	System.out.println( "Operacao nao-existente" );
		}
	}

	private void saque( Conta c ) {
		System.out.println("\nDigite o valor a ser sacado: ");
		Double v = sc.nextDouble();
		System.out.print( "\nSaque: " + String.valueOf( v ) );
		voltarOuSair( c );
	}

	private void saldo( Conta c ) {
		System.out.println( "\nSaldo: " + String.valueOf( c.saldo() ) );
		voltarOuSair( c );
	}

	private void deposito( Conta c ) {

	}

	private void extrato( Conta c ) {
	}

	private void voltarOuSair( Conta c ) {
		System.out.println( "\nDeseja fazer outra operacao (s/n) ?");
		String op = sc.next();
		if( op.equals( "n" ) )
			System.exit(0);
		else menu( c );
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