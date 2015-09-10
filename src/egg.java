import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class egg {
	
	

	int row, col;
	int w=Yard.block_size;
	int h=Yard.block_size;
	private static Random r = new Random();
	
	public egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void reappear(){
		this.row=r.nextInt(Yard.rows-1)+1;
		this.col=r.nextInt(Yard.cols);
	}
	
	public egg(){
		this(r.nextInt(Yard.rows-1)+1,r.nextInt(Yard.cols));
	}
	
	public Rectangle getRect(){
		return new Rectangle(col*Yard.block_size,row*Yard.block_size,w,h);
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(new Color(r.nextInt(255),r.nextInt(255), r.nextInt(255)));
		g.fillOval((col)*Yard.block_size, (row)*Yard.block_size, w, h);
		g.setColor(c);
	}

	
}
