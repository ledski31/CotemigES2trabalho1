
class Main {
	public static void main(String[] args) {
		Terminal t1 = new Terminal();
		t1.carregarContas( "contas.obj" );
		t1.listarContas();
		t1.login();
	}
}