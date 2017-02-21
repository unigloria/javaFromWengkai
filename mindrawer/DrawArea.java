package minidrawer;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

import java.util.Vector;



//画图面板
public class DrawArea extends JPanel{
	mainPad mainPad =null;
	Vector<shape> shapes = new Vector<shape>(); //画板中的图形集合
    private int currentCmd = 3;//设置默认基本图形状态为画直线    
    private Color color = Color.black;//当前画笔的颜色
    int stroke = 1;//设置画笔的粗细 ，默认的是 1
    //记录选中图形对象时鼠标的初始位置
    int oringeX = 0;
    int oringeY = 0;
    //判断当前是否有对象被选中，确定选中的对象在vector中的位置
    boolean isSelected = false;
    int selected = 0;
	
    DrawArea(mainPad mP) {
		mainPad = mP;
		// 设置鼠标形状
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// 设置绘制区的背景是白色
		setBackground(Color.white);
		addMouseListener(new MousePressRelease());// 监听鼠标按下和松开
		addMouseMotionListener(new MouseDragMove());//监听鼠标拖动和移动
		addKeyListener(new Key()); //监听键盘键入
		createNewitem();		
	}
    
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		for (int i = 0; i<shapes.size(); i++)
		{
			draw(g2d, shapes.get(i));
		}		
	}
	void draw(Graphics2D g2d , shape i)
	{
		i.draw(g2d);
	}
	
	//新建图形
	void createNewitem(){
		if(currentCmd == 6)
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		}			
		else
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}			
			switch(currentCmd){			
			case 3: shapes.add(new Line());break;
			case 4: shapes.add(new Circle());break; 		
			case 5: shapes.add(new Rect());break; 			
			case 6: shapes.add(new Word());break; 
		}
	    shapes.get(shapes.size()-1).stroke = stroke ;	 
	}
   
    public void setColor(Color color)
    {
    	this.color = color; 
//    	System.out.println("this is setColor");//测试用
    	if (shapes.size()>1)
    	{
    		shapes.get(shapes.size()-1).setColor(color);
    	}    	
    }
    
    public void changeColor(Color color)
    {
//    	System.out.println("this is changeColor");//测试用
    	shapes.get(selected).setColor(color);
    }

	public void setStroke(int stroke)
	{
		this.stroke = stroke;
		if (shapes.size()>1)
		{
			shapes.get(shapes.size()-1).setStroke(stroke);		
		}		
	}
	
	public void setCurrentCmd(int i )
	{
		currentCmd = i;
	}
	
	boolean isSelected(int x, int y)
	{
		for (int i=shapes.size()-1; i>=0; i--)
		{
			if (shapes.get(i).isInside(x, y))
			{
				selected = i;
				isSelected = true;
				return true;
			}
		}
		return false;		
	}
	

//响应鼠标的按下和松开
class MousePressRelease extends MouseAdapter  {
	@Override
	public void mousePressed(MouseEvent me){
		// 按下鼠标
		requestFocus(true);
		isSelected(me.getX(), me.getY());
		if(isSelected)
		{
//			System.out.println("press-istrue");//测试用
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			oringeX = me.getX();
//			System.out.println("oringeX:"+oringeX);//测试用
			oringeY = me.getY();
		}
		else
		{
//			System.out.println("press-isfalse");//测试用
			shapes.get(shapes.size()-1).x1 = shapes.get(shapes.size()-1).x2 = me.getX();
			shapes.get(shapes.size()-1).y1 = shapes.get(shapes.size()-1).y2 = me.getY();
		}				
		
		//准备输入文字时
		if(currentCmd == 6){
			shapes.get(shapes.size()-1).x1 = me.getX();
			shapes.get(shapes.size()-1).y1 = me.getY();
			String input = JOptionPane.showInputDialog("请输入文字：");
			shapes.get(shapes.size()-1).s = input;			
			currentCmd = 6;
			createNewitem();
			repaint();
		}			
	}

	@Override
	public void mouseReleased(MouseEvent me){
		// 松开鼠标
		if (isSelected)
		{
//			System.out.println("release-istrue");//测试用
			isSelected = false;
		}
		else
		{
//			System.out.println("release-isfalse");//测试用
			shapes.get(shapes.size()-1).x2 = me.getX();
			shapes.get(shapes.size()-1).y2 = me.getY();
			repaint();
			createNewitem();
		}		
	}
	
	@Override
    public void mouseClicked(MouseEvent me){
		// 单击鼠标
		isSelected(me.getX(), me.getY());
//		System.out.println("鼠标做了一次单击，现在isSelected的值为："+isSelected);//测试用
	}
}
	
//响应鼠标的拖动和移动
class MouseDragMove extends MouseMotionAdapter {
	  @Override  
	  public void mouseDragged(MouseEvent me)
      {
    	  if(isSelected)
  		{
//    		System.out.println("drag-istrue");//测试用
  			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
  			shapes.get(selected).x1 += me.getX()-oringeX;
  			shapes.get(selected).x2 += me.getX()-oringeX;
  			shapes.get(selected).y1 += me.getY()-oringeY;
  			shapes.get(selected).y2 += me.getY()-oringeY;
  			oringeX = me.getX();
  			oringeY = me.getY();
  			repaint();
  		}
    	  else
    	  {
//    		  System.out.println("drag-isfalse");//测试用
    		  shapes.get(shapes.size()-1).x2 = me.getX();
        	  shapes.get(shapes.size()-1).y2 = me.getY();    	  
        	  repaint();
    	  }    	 
      }
      
	  @Override
      public void mouseMoved(MouseEvent me)
      {
    	  if(isSelected(me.getX(),me.getY())) //鼠标移动时显示是否处于可选中图像的状态，可以的话显示手形鼠标
    	  {
    		  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	  } 
    	  else
    	  {
    		  setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    	  }
    	  isSelected = false;//消除鼠标单纯移动对是否选中信息的干扰
      }	
	}

class Key extends KeyAdapter{	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int toBeChange = -1;
		if(isSelected)
		{
			toBeChange = selected;
		}
		else 
		{
			toBeChange = shapes.size()-2;
//			System.out.println("键盘操作第"+toBeChange+"个图片");//测试用
		}
		
		if(toBeChange>=0)
		{
//			System.out.println("进入键盘监听：");//测试用
			char charA = e.getKeyChar();
//			System.out.println(charA);//测试用
			switch (charA)
			{
				
				case 'w':{
//					System.out.println("按下了w键");//测试用					
					shapes.get(toBeChange).setStroke(shapes.get(toBeChange).getStroke()+1);
					repaint();
					
					break;
				}		
				case 's':{
//					System.out.println("按下了s键");//测试用
					if(shapes.get(toBeChange).getStroke()>1)
					{
						shapes.get(toBeChange).setStroke(shapes.get(toBeChange).getStroke()-1);
						repaint();						
					}
					break;					
				}	
				case 'a':{
//					System.out.println("按下了a键");//测试用
					repaint();
					break;
				}					
				case 'd':{
//					System.out.println("按下了d键");//测试用
					repaint();
					break;
				}						
			}
		}		
	}	
}


}
