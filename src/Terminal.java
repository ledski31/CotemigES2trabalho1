import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
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



	// ================================================================================================================
	// METODOS DE EXIBICAO NA TELA

	public void listarContas() {
		cabecalho();
		say( "CONTAS EXISTENTES\n\n" );
		for ( String key : contas.keySet() )
			say( key + "\n" );
		say("...");
		pressEnter();
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
			if( id.equals("x") ) sair();
			else if( msg.length() == 0 ) {
				if( !existConta( id )) {
					say( "\nConta nao existente. Deseja cria-la? (s/n)\n" + caret );
					String in = input();
					if( in.equals( "s" ))
						createConta( id );
				}
				if( existConta( id ))
					menu( id );
			}
		}
	}



	private void menu( String idParcial ) {
		String msg = "";
		Conta cc = getConta ( idParcial + Conta.codigoCC );
		Conta pp = getConta ( idParcial + Conta.codigoPP );
		boolean opcaoInvalida = false;
		
		while (true) {
			cabecalho();
			say( "Ola " + cc.titular + "\n" );
			say( "Conta " + idParcial + "\n" );
			say( "\nOperacoes disponiveis\n" );
			say( "x - Sair\nv - Voltar\n");
			say( "\nConta corrente\n");
			say( "1 - Saque\n2 - Deposito\n3 - Saldo\n4 - Extrato\n" );
			say( "\nConta Poupanca\n");
			say( "5 - Saque\n6 - Deposito\n7 - Saldo\n8 - Extrato\n" );
			say( opcaoInvalida ? msg : "" );
			say( caret );
			
			String op = input();
			switch( op ) {
				case "x": sair();
				case "v": return;
				case "1": saque( cc ); opcaoInvalida = false; break;
				case "2": deposito( cc ); opcaoInvalida = false; break;
				case "3": saldo( cc ); opcaoInvalida = false; break;
				case "4": extrato( cc ); opcaoInvalida = false; break;
				case "5": saque( pp ); opcaoInvalida = false; break;
				case "6": deposito( pp ); opcaoInvalida = false; break;
				case "7": saldo( pp ); opcaoInvalida = false; break;
				case "8": extrato( pp ); opcaoInvalida = false; break;
				default:	opcaoInvalida = true;
			}
		}
	}



	// ================================================================================================================
	// METODOS DE CONFIGURACAO DE CONTA

	private boolean existConta( String id ) {
		return (
			contas.containsKey( id + Conta.codigoCC ) ||
			contas.containsKey( id + Conta.codigoPP )
		);
	}



	private void createConta( String id ) {
		String in;
		String nome;
		do {
			say( "\nDigite o nome do titular\n" + caret );
			nome = input().toUpperCase();
			say( "\nO nome esta correto? (s/n)\n" + nome + "\n" + caret );
			in = input();
		}
		while( !in.equals( "s" ) );
		contas.putIfAbsent( id + Conta.codigoCC, new Conta( id + Conta.codigoCC, nome  ));
		contas.putIfAbsent( id + Conta.codigoPP, new Conta( id + Conta.codigoPP, nome  ));
	}



	public Conta getConta( String idCompleto ) {
		return contas.get( idCompleto );
	}


	
	public void salvarContas() {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream("contas.obj");
			oos = new ObjectOutputStream(fos);
			oos.writeObject( contas );
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}



	public void carregarContas( String filepath ) {
		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream( filepath );
			ois = new ObjectInputStream(fis);
			this.contas = (Map<String, Conta>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			say( "\nO arquivo "+filepath+" não foi encontrado. Nao será possivel carregar contas salvas anteriormente\n");
			pressEnter();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	// ================================================================================================================
	// METODOS DE MOVIMENTACOES DE CONTA



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
		if( in.equals( "s" ) ) {
			Double sacado = c.saque( val );
			if( sacado == 0 )
				say( "\nNao ha saldo suficiente" );
			say( "\nSaldo: " + c.saldo() );
		}
		else
			say( "\nOperacao cancelada" );
		say("\n...");
		pressEnter();
	}



	private void saldo( Conta c ) {
		say( "\nSaldo: " + String.valueOf( c.saldo() ) );
		say( "\n..." );
		pressEnter();
	}



	private void deposito( Conta c ) {
		Double val = 0.0;
		String in;
		while( val == 0.0 ) {
			say( "\nDigite o valor para deposito\n" + caret );
			in = input();
			try {
				val = Double.valueOf( in );
			} catch ( Exception e ) {
				say( "\nO valor digitado e invalido" );		
			}
		}
		say( "\nConfirma o deposito do valor " + val + "? (s/n)\n" + caret );
		in = input();
		if( in.equals( "s" ) ) {
			c.deposito( val );
			// say( "\nDepositado: " + val );
			say( "\nSaldo: " + c.saldo() );
		}
		else
			say( "\nOperacao cancelada" );
		say("\n...");
		pressEnter();
	}



	private void extrato( Conta c ) {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM HH:mm");
		sb.append( "===============================================================\n" );
		sb.append( "EXTRATO DA CONTA " + c.getId() + "\n" );
		sb.append( "===============================================================\n" );
		sb.append( field( "DATA" ) + field( "OPERACAO" ) + field ( "VALOR" ) + field( "SALDO" ) + "\n" );
		sb.append( "===============================================================\n" );
		for( Movimentacao m : c.extrato() )
			sb.append( field( m.data.format(formatter) ) + field( m.operacao.name() ) + field( m.valor ) + field( m.saldo ) + field( m.parametro ) + "\n" );
			// sb.append( m.operacao.name() + "\t" + m.valor + "\t" + m.saldo + "\t" + m.parametro + "\n" );
		sb.append( "===============================================================\n" );
		cabecalho();
		say( sb.toString() );
		pressEnter();
	}



	// ================================================================================================================
	// METODOS AUXILIARES



	public String input() {
		String in; 
		do { in = sc.nextLine().toLowerCase(); }
		while ( in.length() == 0 );
		return in;
	}

	public void pressEnter() {
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String field( Double text ) {
		return field( String.valueOf( text ));
	}
	public String field( int text ) {
		return field( String.valueOf( text ));
	}
	public String field( String text ) {
		int fieldSize = 15;
		if( text.length() > fieldSize )
			 text = text.substring(0, fieldSize-1 );
		char[] fill = new char[fieldSize - text.length()];
		for( int i = 0; i < fill.length; i++ )
			fill[i] = ' ';
		return text + new String( fill );

		//String.format("%"+length+"s", str).replace(' ', fill);

		// int fieldSize = 20;
		// if( text.length() > fieldSize )
		// 	 text = text.substring(0, fieldSize-1 );
		// StringBuilder sb = new StringBuilder();
		// sb.append( text );
		// for( int i = 0; i < ( fieldSize - text.length() ); i++ )
		// 	sb.append( " " );
		
		// return sb.toString();
	}

	private void sair() {
		salvarContas();
		System.exit(0);
	}
	
}