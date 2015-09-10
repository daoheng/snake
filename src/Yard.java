import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Yard extends Frame {

	private boolean flag=true;
	private boolean gameover=true;

	Font fontgameover=new Font("����",Font.BOLD,40);
	
	private int score=0;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static final int rows=30;
	public static final int cols=30;
	public static final int block_size=20;
	Image offScreenImage=null;
	
	Snake s=new Snake(this);
	egg e=new egg();
	
	/*
	 * create a interface
	 */
	public void launch(){
		this.setLocation(400, 100);
		this.setTitle("Snake--������̰���ߣ�yang��");
		this.setSize(rows*block_size, cols*block_size);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setVisible(true);
		this.setResizable(false);
		this.addKeyListener(new KeyMonitor());
		//i have make a big mistake here,forget to add a key listener on window.cost me 2 hours to debug.
		
		new Thread(new PaintThread()).start();
}
	
	public static void main(String[] args) {
		new Yard().launch();
	}

	/*
	 * this is to override the paint method to paint the thing into screen(non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		//this is the background color
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, cols*block_size, rows*block_size);
		/**
		 * this is to set the rows and cols of the background
		 */
		g.setColor(Color.DARK_GRAY);
		for(int i=1;i<rows;i++){
			g.drawLine(0, i*block_size, cols*block_size, i*block_size);
		}
		for(int i=1;i<cols;i++){
			g.drawLine(i*block_size, 0, i*block_size, rows*block_size);
		}
		
		g.setColor(Color.GREEN);
		//set the score
		g.drawString("score: "+score, 10, 60);
		if(score>10&&score<30){
			g.drawString("С�����߲�֮����ع�", 30, 100);
		}
		if(score>30&&score<90){
			g.drawString("��Ȼû����һ���Ǹ����⣬���ʮ��֮�ڱع�", 30, 100);
		}
		if(score>90&&score<200){
			g.drawString("�����������������й�������������ʵ���ǵ������ʵ���һ����ʮ���ٺ�", 30, 100);
		}
		if(score>200&&score<540){
			g.drawString("����Ի�����֪���ԳԳԳԣ��ü������������������ǵ������ʵ����������С���ܣ���q���ӷ�Ŷ", 30, 100);
		}
		if(score>540&&score<750){
			g.drawString("�㾹Ȼ���ڳԣ�Ϊʲô�ҵ�Ԥ��һ����һ��ʧ�ܣ��ⲻ��ѧ�����Ѿ�����ĳԻ���������˾��⣬��������", 30, 100);
		}
		if(score>750&&score<1000){
			g.drawString("�������ҽ���һ���������������ˣ������������ټ�һ�Ѿ����ܿ�͵�������", 30, 100);
		}
        if(score>1000&&score<1500){
        	g.drawString("���汨�棬Ӣ�µ��������ˣ������ڴ˵ع������ʱ�ˣ���Ҫ���������ֵ�ʮ�˱�����ľ�����͵͵�����㣬", 30, 100);
        	g.drawString("�ϰ���1500������,��q�ӷֿɳܳܳܳܳܰ���������", 60, 120);
		}
        if(score>1500&&score<3000){
        	g.drawString("�����ϰˣ��𾴵�ս�����浽����ҽ�������Ϸ������Ա��gg��������˶���ľ��⣬������һ�ݣ�", 30, 100);
        	g.drawString("�Ͼ���3000����̫Զ�˽���������ˣ���q�ӷֿɳܳܳܳܳܰ���������", 60, 120);
        }
        if(score>3000&&score<5000){
        	g.drawString("���������������������������й���������������һ���Ƿ��ˣ��ܴ�����Ҷ���֪��˵ʲô���ˣ�", 30, 100);
        	g.drawString("��boss��5000,�ԣ������ˣ������˽�ʡʱ��������������¡���q�ӷֿɳܳܳܳܳܰ���������", 60, 120);
		}
        if(score>5000){
			g.drawString("������ʮ����������·������һֱ�����㣡����׳ʿ�������������裡����������·��������", 30, 100);
		}
	
        if(flag==false){
        	g.drawString("��ײ�����ˣ�������ײ�����˰ɰ�������������", 100, 200);
        	
			g.setFont(fontgameover);
        	g.drawString("������", 50, 300);
        	g.drawString("����ӵ�����ļ��ʺ���", 90, 350);
        	g.drawString("������������г���",130, 400);
        	gameover=false;
        }
		g.setColor(c);
		
		e.draw(g);
		s.draw(g);
		s.eat(e);
	}

/**
 * update the offScreenImage and improve the painting effect
 */
	@Override
	public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage=this.createImage(cols*block_size, rows*block_size);
		}
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public class PaintThread implements Runnable{

		public void run() {
			while(gameover){
				repaint();
			try{
				Thread.sleep(200);
			}	catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter{

		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
		
	}
	
	public void stop(){
		flag = false;
	}
}
