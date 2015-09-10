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

	Font fontgameover=new Font("楷体",Font.BOLD,40);
	
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
		this.setTitle("Snake--燕羊羊贪吃蛇（yang）");
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
			g.drawString("小样，七步之内你必跪", 30, 100);
		}
		if(score>30&&score<90){
			g.drawString("竟然没跪，这一定是个意外，你二十步之内必跪", 30, 100);
		}
		if(score>90&&score<200){
			g.drawString("啊啊啊啊啊啊啊，有鬼啊！！！！！其实我是第三条彩蛋，一共有十条嘿嘿", 30, 100);
		}
		if(score>200&&score<540){
			g.drawString("你个吃货，就知道吃吃吃吃，该减肥拉拉拉，括号我是第四条彩蛋，告诉你个小秘密，按q键加分哦", 30, 100);
		}
		if(score>540&&score<750){
			g.drawString("你竟然还在吃，为什么我的预言一次又一次失败，这不科学，我已经对你的吃货精神产生了敬意，我是老五", 30, 100);
		}
		if(score>750&&score<1000){
			g.drawString("请允许我叫你一声蛇王，蛇王大人，我是老六，再加一把劲，很快就到老七了", 30, 100);
		}
        if(score>1000&&score<1500){
        	g.drawString("报告报告，英勇的蛇王大人，我已在此地恭候你多时了，我要代表我们兄弟十人表达对你的敬意我偷偷告诉你，", 30, 100);
        	g.drawString("老八在1500处等你,用q加分可耻耻耻耻耻啊啊啊啊啊", 60, 120);
		}
        if(score>1500&&score<3000){
        	g.drawString("我是老八，尊敬的战神，能玩到这里，我谨代表游戏开发人员大gg表达他个人对你的敬意，请受我一拜，", 30, 100);
        	g.drawString("老九在3000处，太远了建议你别玩了，用q加分可耻耻耻耻耻啊啊啊啊啊", 60, 120);
        }
        if(score>3000&&score<5000){
        	g.drawString("啊啊啊啊啊啊啊啊啊啊啊啊，有鬼啊！！！！！！你一定是疯了，能打到这里，我都不知道说什么好了，", 30, 100);
        	g.drawString("老boss在5000,乖，别玩了，年轻人节省时间做点有意义的事。用q加分可耻耻耻耻耻啊啊啊啊啊", 60, 120);
		}
        if(score>5000){
			g.drawString("我是老十，接下来的路将由我一直陪着你！！！壮士来干了这碗热翔！！！我们上路！！！！", 30, 100);
		}
	
        if(flag==false){
        	g.drawString("猪撞树上了？？？你撞猪上了吧啊哈哈哈！！！", 100, 200);
        	
			g.setFont(fontgameover);
        	g.drawString("快别吃啦", 50, 300);
        	g.drawString("积极拥护党的减肥号召", 90, 350);
        	g.drawString("共建社会主义和谐社会",130, 400);
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
