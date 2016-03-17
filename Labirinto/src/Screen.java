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
import java.util.Timer;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements ActionListener ,KeyListener {
	
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 25;
	private static final int BORDER = 10;
	private final static int CELL_SIZE = 25;

	private int width;
	private int height;
	private Image image;
	private Image image2;
	
	private int xLeft;
	private int xRight;

	private int xBoneco;
	private int yBoneco;
	private int xAuto;
	private int yAuto;

	private int xQuadrado;
	private int yQuadrado;
	private int xLimiteL;
	
	private static String[][] labirinto2;

	private static boolean[][] caminho;
	
	private boolean[][] labyrinth;
	
	{	
	width = 2 * SIZE + 3 * BORDER;
	height = 2 * SIZE + 3 * BORDER;

	xLeft = BORDER + SIZE / 2;
	xRight = 2 * BORDER + 3 * SIZE / 2;

	
	xBoneco = 0;
	yBoneco = 0;
	
	xAuto = 0;
	yAuto = 0;
	
	xLimiteL = 300;

	xQuadrado = xLeft;
	yQuadrado = 2 * BORDER + 3 * SIZE / 2;
}

public void keyPressed(KeyEvent e) {
	
	int key = e.getKeyCode();

	
	if(key == KeyEvent.VK_LEFT) {
		
		if(yBoneco <= 0) {
			yBoneco = 0;
			
			}
		
		else if (labyrinth[(xBoneco/CELL_SIZE)][(yBoneco/CELL_SIZE)-1]){
					yBoneco = yBoneco - CELL_SIZE ;
			}
		
		repaint();
	}
		


	if(key == KeyEvent.VK_RIGHT) {
		
		
		if (yBoneco >= 700) {
			yBoneco = 700;
		}
			
		else if (labyrinth[(xBoneco/CELL_SIZE)][(yBoneco/CELL_SIZE)+1]){
					
					yBoneco = yBoneco + CELL_SIZE;
					
				}
			
		repaint();
			}
		
		
		if(key == KeyEvent.VK_UP) {
			if(xBoneco <= 0) {
				xBoneco = 0;
				
				}
			
			else if (labyrinth[xBoneco/CELL_SIZE -1][yBoneco/CELL_SIZE]){
				
			xBoneco = xBoneco - CELL_SIZE ;
			
			
		}
			
			repaint();
			
	}
	

		if(key == KeyEvent.VK_DOWN) {
			if(xBoneco >= 450) {
				xBoneco = 450;
				}
			
			else if(labyrinth[xBoneco/CELL_SIZE + 1][yBoneco/CELL_SIZE]){
				
			xBoneco = xBoneco + CELL_SIZE ;
		
			
		}
			
			repaint();
	}	
}

public void actionPerformed(ActionEvent e) {

	
	if(xBoneco == xLeft) {
		
		xBoneco = xRight;
	}

	else {
		
		xBoneco = xLeft;
	}

	
	repaint();
}

	

	public Screen(boolean[][] labyrinth) {
		this.labyrinth = labyrinth;

		this.width = this.labyrinth[0].length;
		this.height = this.labyrinth.length;

		setPreferredSize(new Dimension(this.width * CELL_SIZE, this.height * CELL_SIZE));
	}

	public void paintComponent(Graphics g) {
		for(int i = 0; i < this.height; i++) {
			int y = i * CELL_SIZE;

			for(int j = 0; j < this.width; j++) {
				int x = j * CELL_SIZE;
				
			     image = new ImageIcon(getClass().getResource("/img/example.png")).getImage();
			     image2 = new ImageIcon(getClass().getResource("/img/minion_labirinto.png")).getImage();
			  
			     
			    g.drawImage(image,yBoneco, xBoneco , SIZE, SIZE, null);
				g.drawImage(image2,yAuto, xAuto , SIZE, SIZE, null);
				
				if(labyrinth[i][j]) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
			}
		}

    	getToolkit().sync();
    }
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
private static void readFiles() throws IOException{
		
		List<String> argumentos = Files.lines(Paths.get("labyrinth.txt"))
	    .collect(Collectors.toList());
		//System.out.println(argumentos);
		
		int linha = 0;
		labirinto2 = new String[argumentos.size()][];
		for(String i : argumentos){
			String[] temp = i.split("");
			labirinto2[linha] = temp;
			System.out.println(temp);
			System.out.println(labirinto2);
			linha++;
		}
	
	}
}
