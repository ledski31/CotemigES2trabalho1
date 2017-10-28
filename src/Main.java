import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		System.out.println("Terminal Bancário no Console");
		System.out.print("Digite o número da conta (XXX): ");
		Scanner sc = new Scanner( System.in );
		int i = sc.nextInt();
		System.out.println("\nConta número "+ i +"\nEscolha a operação:");
	}
}