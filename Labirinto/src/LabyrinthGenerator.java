import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LabyrinthGenerator {
	
	private static final int WIDTH = 15;
	private static final int HEIGHT = 10;
	
	public int getWidth(){
		return WIDTH;
	}
		
	public int getHeight(){
		return HEIGHT;
	}

    public static void main(String[] args) {
    	int i, j;

    	Cell[][] cells = new Cell[HEIGHT][WIDTH];

		for(i = 0; i < HEIGHT; i++) {
			for(j = 0; j < WIDTH; j++) {
				cells[i][j] = new Cell(i, j);
			}			
		}

		Edge[] edges = new Edge[HEIGHT * (WIDTH - 1) + (HEIGHT - 1) * WIDTH];

		int k = 0;

		for(i = 0; i < HEIGHT; i++) {
			for(j = 0; j < WIDTH - 1; j++) {
				edges[k] = new Edge(cells[i][j], cells[i][j + 1]);
				k++;
			}			
		}

		for(i = 0; i < HEIGHT - 1; i++) {
			for(j = 0; j < WIDTH; j++) {
				edges[k] = new Edge(cells[i][j], cells[i + 1][j]);
				k++;
			}			
		}

		Random random = new Random();

		ArrayList<Edge> tree = new ArrayList<>();

		for(int length = edges.length; length > 0; length--) {
			int index = random.nextInt(length);
			
			//System.out.println(length);

			Edge edge = edges[index];

			Cell u = edge.getU();
			Cell v = edge.getV();

			if(u.union(v)) {
				tree.add(edge);

				if(tree.size() == HEIGHT * WIDTH - 1) {
					break;
				}
			}

			edges[index] = edges[length - 1];
		}

    	final boolean[][] labyrinth = new boolean[2 * HEIGHT - 1][2 * WIDTH - 1];

		for(i = 0; i < HEIGHT; i++) {
			for(j = 0; j < WIDTH; j++) {
				labyrinth[2 * i][2 * j] = true;
			}
		}

    	for(Edge edge: tree) {
			Cell u = edge.getU();
			Cell v = edge.getV();

			labyrinth[u.getI() + v.getI()][u.getJ() + v.getJ()] = true;
    	}

		Charset charset = Charset.forName("US-ASCII");

		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream("labyrinth.txt");
		} catch (FileNotFoundException e) {
			System.err.println("could not open file");
			System.exit(1);
		}

		PrintWriter writer = new PrintWriter(new OutputStreamWriter(stream, charset));

		for(i = 0; i < labyrinth.length; i++) {
			for(j = 0; j < labyrinth[0].length; j++) {
				if(labyrinth[i][j]) {
					writer.print(' ');
				}
				else {
					writer.print('#');
				}
				
				//System.out.println(labyrinth[i][j]);
				
			}
			writer.println();
		}

		writer.close();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
            public void run() {
            	
                Screen screen = new Screen();

                JFrame frame = new JFrame("Labyrinth Generator");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setContentPane(screen);
                frame.pack();
                frame.setVisible(true);
                frame.addKeyListener(screen);
            }
        });
	/*	
     // APAGAR A PARTIR DAQUI SE TUDO DER ERRADO
    
    	int analisador_true = 0;
    	int analisador_false = 0;
    	
    	List<Integer> lista_i = new ArrayList<>();
    	List<Integer> lista_j = new ArrayList<>();
		
    	// PRIMEIRA LINHA ANALISADOR
    	
    	for(j=1;(j< WIDTH*2-2);j++){
    		
    		if(labyrinth[0][j]){
    			
    		
    			if(labyrinth[0][j+1]) {
					analisador_true++;
				}
				else{
					analisador_false++;
    		}
    			
    			if(labyrinth[0][j-1]) {
					analisador_true++;
				}
				else{
					analisador_false++;
			}
    			if(labyrinth[1][j]) {
					analisador_true++;
			}
				else{
					analisador_false++;
			}
    			
    			if (analisador_true > 2) {
					
					lista_i.add(0);
					lista_j.add(j);
				
				}
    			
    			analisador_false = 0;
				analisador_true = 0;
				
				}	
    			
    	}
    	
    	// PRIMEIRA COLUNA ANALISADOR
  
    	for(i=1;(i< HEIGHT*2-2);i++){
    		
    		if(labyrinth[i][0]){
    			
    		
    			if(labyrinth[i+1][0]) {
					analisador_true++;
				}
				else{
					analisador_false++;
    		}
    			
    			if(labyrinth[i-1][0]) {
					analisador_true++;
				}
				else{
					analisador_false++;
			}
    			if(labyrinth[i][1]) {
					analisador_true++;
			}
				else{
					analisador_false++;
			}
    			
    			if (analisador_true > 2) {
					
					lista_i.add(i);
					lista_j.add(0);
				
				}
    			
    			analisador_false = 0;
				analisador_true = 0;
				
				}	
    			
    	}
    	
    	//ANALISADOR ULTIMA LINHA
    	
    	for(j=1;(j< WIDTH*2-2);j++){
    		
    		if(labyrinth[HEIGHT*2-2][j]){
    			
    		
    			if(labyrinth[HEIGHT*2-2][j+1]) {
					analisador_true++;
				}
				else{
					analisador_false++;
    		}
    			
    			if(labyrinth[HEIGHT*2-2][j-1]) {
					analisador_true++;
				}
				else{
					analisador_false++;
			}
    			if(labyrinth[HEIGHT*2-3][j]) {
					analisador_true++;
			}
				else{
					analisador_false++;
			}
    			
    			if (analisador_true > 2) {
					
					lista_i.add(HEIGHT*2-2);
					lista_j.add(j);
				
				}
    			
    			analisador_false = 0;
				analisador_true = 0;
				
				}	
    			
    	}
    	
//ANALISADOR ULTIMA COLUNA

    	for(i=1;(i< HEIGHT*2-2);i++){
  		
  		if(labyrinth[18][j]){
  			
  		
  			if(labyrinth[i+1][WIDTH*2-2]) {
					analisador_true++;
				}
				else{
					analisador_false++;
  		}
  			
  			if(labyrinth[i-1][WIDTH*2-2]) {
					analisador_true++;
				}
				else{
					analisador_false++;
			}
  			if(labyrinth[i][WIDTH*2-3]) {
					analisador_true++;
			}
				else{
					analisador_false++;
			}
  			
  			if (analisador_true > 2) {
					
					lista_i.add(i);
					lista_j.add(WIDTH*2-2);
				
				}
  			
  			analisador_false = 0;
			analisador_true = 0;
				
				}	
  			
  	}
    	//ANALISADOR TODO LABIRINTO MENOS BORDAS
    	
    	for(i = 1; i < (HEIGHT*2-2); i++) {
			for(j = 1; j < (WIDTH*2-2); j++) {
				if(labyrinth[i][j]){
					if(labyrinth[i][j+1]) {
						analisador_true++;
					}
					else{
						analisador_false++;
					}
					if(labyrinth[i][j-1]) {
						analisador_true++;
					}
					else{
						analisador_false++;
					}
					if(labyrinth[i+1][j]) {
						analisador_true++;
					}
					else{
						analisador_false++;
					}
					if(labyrinth[i-1][j]) {
						analisador_true++;
					}
					else{
						analisador_false++;
					}
				
					if (analisador_true > 2) {
					
						lista_i.add(i);
						lista_j.add(j);
					
					}
				
					analisador_false = 0;
					analisador_true = 0;
					
					
							
				}
			}
			
			
			
    	}
    	
    	System.out.println(lista_i);
    	System.out.println(lista_j);
    	*/
    	
    }
       
}
