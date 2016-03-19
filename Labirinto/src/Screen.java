import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;
import javax.swing.Timer;
import java.util.stream.Collectors;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Screen extends JPanel implements ActionListener ,KeyListener {
	
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 10;
	private static final int BORDER = 10;
	private final static int CELL_SIZE = 25;


	
	private Timer timer;
	private int width;
	private int height;
	private Image image;
	private Image image2;
	private Image image3;
	

	
	private int xLeft;
	private int xRight;

	private int yBoneco;
	private int xBoneco;
	private static int xAuto;
	private static int yAuto;

	private int xQuadrado;
	private int yQuadrado;
	private int xLimiteL;
	
	
	
	//pilha com todas as migalhas
	public Stack<Crumb> stack = new Stack<Crumb>();
	
	private static String[][] labirinto2;

	private static boolean[][] path;
	
	private static boolean[][] labyrinth;
//inicializa Screen(Construtor)	
public Screen() {
		//Lendo o arquivo texto do labirinto	
		// try e catch para se tiver exception avisar
		try {
			Screen.readFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		timer = new Timer(100,this);
		//pote aparece em lugares randomicos
		
	//tamanho da tela	
	width = labyrinth[0].length;
	height = labyrinth.length;
	// ?? 
	xLeft = BORDER + SIZE / 2;
	xRight = 2 * BORDER + 3 * SIZE / 2;
	//posições iniciais
	yBoneco = 0;
	xBoneco = 0;
	
	xAuto = 0;
	yAuto = 0;
	
	xLimiteL = 300;

	xQuadrado = xLeft;
	yQuadrado = 2 * BORDER + 3 * SIZE / 2;
	//define tamanho da tela
	setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));
	
	//definindo imagens
	image = new ImageIcon(getClass().getResource("/img/example.png")).getImage();
    image2 = new ImageIcon(getClass().getResource("/img/minion_labirinto.png")).getImage();
    image3= new ImageIcon(getClass().getResource("/img/pot.png")).getImage();
    // definindo o item (0,0) da pilha
    stack.push(new Crumb (yAuto, xAuto));
    
  //Erro Abre e fecha
	// path[xAuto][yAuto] = false; 
    
	 timer.start();
//Premio aleatório (posição)

}
//listener para eventos(clicar uma tecla é um evento)

public void keyPressed(KeyEvent e) {
	
	if (xBoneco == ((width-1))*CELL_SIZE && yBoneco == ((height-1))*CELL_SIZE ) {
		timer.stop();
		System.out.println("Boneco Wins");	
	}
	int key = e.getKeyCode();

	//se tecla for esquerda
	if(key == KeyEvent.VK_LEFT) {
		//comapara 
		//garante que o boneco não sai da tela pela esquerda
		if(xBoneco <= 0) {
			xBoneco = 0;
			
			}
		//se for true anda caso não ele nao entra na condicional
		else if (labyrinth[(yBoneco/CELL_SIZE)][(xBoneco/CELL_SIZE)-1]){
					xBoneco = xBoneco - CELL_SIZE ;
			}
		//repinta a tela no que esta no momento
		repaint();
	}
		

	//tecla direita
	if(key == KeyEvent.VK_RIGHT) {
		//compara
		//garante que o boneco não sai da tela pela direita
		if (xBoneco >= width*CELL_SIZE) {
			xBoneco = width*CELL_SIZE;
		}
			
		else if (labyrinth[(yBoneco/CELL_SIZE)][(xBoneco/CELL_SIZE)+1]){
					
					xBoneco = xBoneco + CELL_SIZE;
					
				}
			
		repaint();
			}
		
		//para cima
		if(key == KeyEvent.VK_UP) {
			if(yBoneco == 0) {
				yBoneco = 0;
				
				}
			
			else if (labyrinth[yBoneco/CELL_SIZE -1][xBoneco/CELL_SIZE]){
				
			yBoneco = yBoneco - CELL_SIZE ;
			
			
		}
			
			repaint();
			
	}
	
		//para baixo
		if(key == KeyEvent.VK_DOWN) {
			if(yBoneco >= height*CELL_SIZE) {
				yBoneco = height*CELL_SIZE;
				}
			
			else if(labyrinth[yBoneco/CELL_SIZE + 1][xBoneco/CELL_SIZE]){
				
			yBoneco = yBoneco + CELL_SIZE ;
		
			
		}
			
			repaint();
	}	
}

	
	//pintar tudo
	public void paintComponent(Graphics g) {
		for(int i = 0; i < this.height; i++) {
			int y = i * CELL_SIZE;

			for(int j = 0; j < this.width; j++) {
				
				int x = j * CELL_SIZE;
				 //Erro Tudo branco
				
				/*if(!path[i][j] && labyrinth[i][j]) {
					g.setColor(Color.GREEN);
				}*/
				
				//fundo 
				if(labyrinth[i][j]) {
					g.setColor(Color.WHITE);
				}
				//paredes
				else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
			}
			
		
		}
		int yA = stack.peek().getI()*CELL_SIZE;
		int xA = stack.peek().getJ()*CELL_SIZE;
		//desenha as imagens no jogo(basicamente um print na Graphic User Interface (GUI))
		g.drawImage(image,xBoneco, yBoneco , CELL_SIZE, CELL_SIZE, null);
		g.drawImage(image2,xA, yA, CELL_SIZE, CELL_SIZE, null);
		g.drawImage(image3, (width-1)*CELL_SIZE, (height-1)*CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
		

    	getToolkit().sync();
    }
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	//passo com o timer
	//movimento
	public void actionPerformed(ActionEvent e) {
		//cria uma crumb
		Crumb crumb = stack.peek();
		yAuto= stack.peek().getI();
		xAuto= stack.peek().getJ();
		//path[xAuto][yAuto] = false;
		// || é igual a or(para lembrar)
		if (xAuto == ((width-1)) && yAuto == ((height-1)) ) {
			timer.stop();
			System.out.println("Minion Wins");
			
		}
		
		else {
				if(crumb.getPassos() == 0){
					if(xAuto >0 && path[yAuto][(xAuto)-1]){
						//path[yAuto/CELL_SIZE][xAuto/CELL_SIZE] = false;	
						xAuto = xAuto - 1;
						stack.add(new Crumb(yAuto, xAuto));
						

					}
					crumb.adicionarPassos();
					path[yAuto][xAuto] = false;
				}
							
				else if(crumb.getPassos() == 1){
					if(xAuto < this.width-1 && path[yAuto][(xAuto)+1]){
						//path[yAuto][xAuto] =false;	
						xAuto = xAuto + 1;
						
						stack.add(new Crumb(yAuto, xAuto));
						
					}
					crumb.adicionarPassos();		
				}
				
				else if(crumb.getPassos()==2){
					if(yAuto < this.height -1 && path[yAuto+1][xAuto]){
						//path[yAuto][yAuto] =false;	
						yAuto = yAuto + 1;
						stack.add(new Crumb(yAuto, xAuto));
						
						
					}
					crumb.adicionarPassos();
				}
				
				else if(crumb.getPassos()==3) {
					if(yAuto > 0 && path[yAuto-1][xAuto]){
						//path[yAuto][xAuto] =false;	
						yAuto = yAuto - 1;
						stack.add(new Crumb(yAuto, xAuto));
						
					}
					crumb.adicionarPassos();
					
				}
				
				else{
					//path[xAuto/CELL_SIZE][yAuto/CELL_SIZE] =false;
					stack.pop();
					
				}
					repaint();
					
				}
			}

//lendo o arquivo e transforma matriz de booleans
	
private static void readFiles() throws IOException{
		//gera uma lista de strings apartir do arquivo
		List<String> argumentos = Files.lines(Paths.get("labyrinth.txt")).collect(Collectors.toList());
		//labirirnto2 é uma matriz de strings do tamanho do argumento SIZE
		int linha = 0;
		labirinto2 = new String[argumentos.size()][];
		
		//rodando em cima de cada item da lista
		for(String i : argumentos){
			String[] t = i.split("");
			labirinto2[linha] = t;
			
			linha++;
		}
		
		//duas novas listas com comprimento de y é a altura  
		//da lista de fora(engloba todos os valores de x) e 
		//o x é o numero de itens da lista na horizontal
		
		labyrinth = new boolean[labirinto2.length][labirinto2[0].length];
		path=(new boolean[labirinto2.length][labirinto2[0].length]);
		
		// for que roda em j e i para os dois novos labirintos e 
		// transformando de # e espaço para booleans
		for(int j = 0; j != labirinto2.length; j++){
			for(int i = 0; i != labirinto2[0].length; i++){
				
				if(labirinto2[j][i].startsWith("#")){
					labyrinth[j][i] = false;
					path[j][i] = false;
				}
				else{
					labyrinth[j][i] = true;
					path[j][i] = true;
					
				}
			}
		}
		
		
	}
/*
public static boolean[][] path() {
	return path;
}

public static void setPath(boolean[][] path) {
	Screen.path = path;
}


		while(true) {
			xPremio = pote.nextInt(this.width-1);
			yPremio = pote.nextInt(this.height-1);
						
			if(labyrinth[yPremio][xPremio]) {
				break;
		
			}
		}
*/
}
