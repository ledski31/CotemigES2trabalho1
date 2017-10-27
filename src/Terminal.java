import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal 

	private int id;
	private double total;
	private List<Mov> extrato;
	
	public Terminal(int id) {
		this.id = id;
		extrato = new ArrayList<Mov>();
	}

	public void Menu() {
		Scanner sc = new Scanner( System.in );
		int i = sc.nextInt();
	}

	public void Saque( double valor ) {
		extrato.add( new Mov( valor ) );
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