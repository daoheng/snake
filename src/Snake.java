import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	private Node head=null;
	private Node tail=null;
	private int size=0;
	Dir lastdir;
	
	private Node n=new Node(15,18,Dir.L);
	private Yard y;
	
	public Snake(Yard y){
		head=n;
		tail=n;
		size=1;
		this.y=y;
	}
	
	public void addToTail(){
		Node node=null;
		switch (tail.dir){
		case L:
			node=new Node(tail.row,tail.col+1,tail.dir);
			break;
		case R:
			node=new Node(tail.row,tail.col-1,tail.dir);
			break;
		case U:
			node=new Node(tail.row+1,tail.col,tail.dir);
			break;
		case D:
			node=new Node(tail.row-1,tail.col,tail.dir);
			break;
		}
		tail.next=node;
		node.prev=tail;
		tail=node;
		size++;
	}
	
	public void addToHead(){
		Node node=null;
		switch (head.dir){
		case L:
			node=new Node(head.row,head.col-1,head.dir);
			break;
		case R:
			node=new Node(head.row,head.col+1,head.dir);
			break;
		case U:
			node=new Node(head.row-1,head.col,head.dir);
			break;
		case D:
			node=new Node(head.row+1,head.col,head.dir);
			break;
		}
		node.next=head;
		head.prev=node;
		head=node;
		size++;
	}
	
	public void draw(Graphics g){
		if(size<=0) return;
		move();
		for(Node n=head;n!=null;n=n.next){
			n.draw(g);
		}

	}
	
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		switch (key){
		case KeyEvent.VK_LEFT:
			if(lastdir!=Dir.R)
			head.dir=Dir.L;
			break;
		case KeyEvent.VK_RIGHT:
			if(lastdir!=Dir.L)
			head.dir=Dir.R;
			break;
		case KeyEvent.VK_UP:
			if(lastdir!=Dir.D)
			head.dir=Dir.U;
			break;
		case KeyEvent.VK_DOWN:
			if(lastdir!=Dir.U)
			head.dir=Dir.D;
			break;
		case KeyEvent.VK_Q:
			y.setScore(y.getScore()+50);
			break;
		}
	}
	
	public void move(){
		if(size==0) return;
		addToHead();
		deleteFromTail();
		collideWall();
		collideSelf();
		lastdir=head.dir;
	}
	
	private void collideSelf() {
		for(Node n=head.next;n!=null;n=n.next){
			if(this.getRect().intersects(n.getRect())){
				y.stop();
			}
		}
	}

	private void collideWall() {
		if(head.row<1||head.col<0||head.row>Yard.rows||head.col>Yard.cols){
			y.stop();
		}
	}

	public void deleteFromTail(){
		if(size==0) return;
		tail=tail.prev;
		tail.next=null;
		size--;
	}
	
	public void eat(egg e){
		if(this.getRect().intersects(e.getRect())){
			addToTail();
			do{e.reappear();
			}while(this.getRect().intersects(e.getRect()));
			y.setScore(y.getScore()+7);
		}
			
	}
	
	public Rectangle getRect(){
		return new Rectangle(head.col*Yard.block_size,head.row*Yard.block_size,head.w,head.h);
	}
	
	private class Node {
		
		int w=Yard.block_size;
		int h=Yard.block_size;
		int row , col;
		Dir dir=Dir.L;
		Node next=null;
		Node prev=null;
		
		public Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir=dir;
		}
		
		public Rectangle getRect(){
			return new Rectangle(col*Yard.block_size,row*Yard.block_size,w,h);
		}
		
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect((col)*Yard.block_size, (row)*Yard.block_size, w, h);
			g.setColor(c);
		}
	}
}
