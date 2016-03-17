

public class Crumb {

	private int i;
	private int j;
	private int passos;
		
	public Crumb(int i, int j) {
		this.i = i;
		this.j = j;
		passos = 0;	
	}
	
	public int getI(){
		
		return i;
		
	}
	
	public int getJ(){
		
		return j;
		
	}
	
	
	public int getPassos(){
		
		return passos;
		
	}
	
	public void adicionarPassos(){
		
		passos++;
		
	}
	
	
}

