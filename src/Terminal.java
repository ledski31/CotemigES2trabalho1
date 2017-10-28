import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Terminal {
	//private List<Conta> db = new ArrayList<Conta>();
	private Map<String, Conta> contas = new HashMap<>();

	public void iniciarUI() {
		System.out.println("Terminal Bancario via texto");
		System.out.print("\nDigite o numero da conta (XXX-X): ");
		// Scanner sc = new Scanner( System.in );
		// int id = sc.nextInt();
		// sc.close();
		String id = "111-1";
		Conta c = getConta( id );
		if( c == null ) System.out.println( "Numero de conta invalido");
		else {
			System.out.println("\n\nConta " + id);
			System.out.println("Operacoes disponiveis");
			System.out.println("1 - Saque\n2 - Deposito\n3 - Saldo\n4 - Extrato\n0 - Sair");
		}
	}

	public Conta getConta( String id ) {
		if( Conta.isValid( id )) {
			contas.putIfAbsent( id, new Conta( id ));
			return contas.get( id );
		}
		return null;
	}
}