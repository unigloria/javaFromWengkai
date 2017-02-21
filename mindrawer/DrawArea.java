package minidrawer;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

import java.util.Vector;



//��ͼ���
public class DrawArea extends JPanel{
	mainPad mainPad =null;
	Vector<shape> shapes = new Vector<shape>(); //�����е�ͼ�μ���
    private int currentCmd = 3;//����Ĭ�ϻ���ͼ��״̬Ϊ��ֱ��    
    private Color color = Color.black;//��ǰ���ʵ���ɫ
    int stroke = 1;//���û��ʵĴ�ϸ ��Ĭ�ϵ��� 1
    //��¼ѡ��ͼ�ζ���ʱ���ĳ�ʼλ��
    int oringeX = 0;
    int oringeY = 0;
    //�жϵ�ǰ�Ƿ��ж���ѡ�У�ȷ��ѡ�еĶ�����vector�е�λ��
    boolean isSelected = false;
    int selected = 0;
	
    DrawArea(mainPad mP) {
		mainPad = mP;
		// ���������״
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// ���û������ı����ǰ�ɫ
		setBackground(Color.white);
		addMouseListener(new MousePressRelease());// ������갴�º��ɿ�
		addMouseMotionListener(new MouseDragMove());//��������϶����ƶ�
		addKeyListener(new Key()); //�������̼���
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
	
	//�½�ͼ��
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
//    	System.out.println("this is setColor");//������
    	if (shapes.size()>1)
    	{
    		shapes.get(shapes.size()-1).setColor(color);
    	}    	
    }
    
    public void changeColor(Color color)
    {
//    	System.out.println("this is changeColor");//������
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
	

//��Ӧ���İ��º��ɿ�
class MousePressRelease extends MouseAdapter  {
	@Override
	public void mousePressed(MouseEvent me){
		// �������
		requestFocus(true);
		isSelected(me.getX(), me.getY());
		if(isSelected)
		{
//			System.out.println("press-istrue");//������
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			oringeX = me.getX();
//			System.out.println("oringeX:"+oringeX);//������
			oringeY = me.getY();
		}
		else
		{
//			System.out.println("press-isfalse");//������
			shapes.get(shapes.size()-1).x1 = shapes.get(shapes.size()-1).x2 = me.getX();
			shapes.get(shapes.size()-1).y1 = shapes.get(shapes.size()-1).y2 = me.getY();
		}				
		
		//׼����������ʱ
		if(currentCmd == 6){
			shapes.get(shapes.size()-1).x1 = me.getX();
			shapes.get(shapes.size()-1).y1 = me.getY();
			String input = JOptionPane.showInputDialog("���������֣�");
			shapes.get(shapes.size()-1).s = input;			
			currentCmd = 6;
			createNewitem();
			repaint();
		}			
	}

	@Override
	public void mouseReleased(MouseEvent me){
		// �ɿ����
		if (isSelected)
		{
//			System.out.println("release-istrue");//������
			isSelected = false;
		}
		else
		{
//			System.out.println("release-isfalse");//������
			shapes.get(shapes.size()-1).x2 = me.getX();
			shapes.get(shapes.size()-1).y2 = me.getY();
			repaint();
			createNewitem();
		}		
	}
	
	@Override
    public void mouseClicked(MouseEvent me){
		// �������
		isSelected(me.getX(), me.getY());
//		System.out.println("�������һ�ε���������isSelected��ֵΪ��"+isSelected);//������
	}
}
	
//��Ӧ�����϶����ƶ�
class MouseDragMove extends MouseMotionAdapter {
	  @Override  
	  public void mouseDragged(MouseEvent me)
      {
    	  if(isSelected)
  		{
//    		System.out.println("drag-istrue");//������
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
//    		  System.out.println("drag-isfalse");//������
    		  shapes.get(shapes.size()-1).x2 = me.getX();
        	  shapes.get(shapes.size()-1).y2 = me.getY();    	  
        	  repaint();
    	  }    	 
      }
      
	  @Override
      public void mouseMoved(MouseEvent me)
      {
    	  if(isSelected(me.getX(),me.getY())) //����ƶ�ʱ��ʾ�Ƿ��ڿ�ѡ��ͼ���״̬�����ԵĻ���ʾ�������
    	  {
    		  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    	  } 
    	  else
    	  {
    		  setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    	  }
    	  isSelected = false;//������굥���ƶ����Ƿ�ѡ����Ϣ�ĸ���
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
//			System.out.println("���̲�����"+toBeChange+"��ͼƬ");//������
		}
		
		if(toBeChange>=0)
		{
//			System.out.println("������̼�����");//������
			char charA = e.getKeyChar();
//			System.out.println(charA);//������
			switch (charA)
			{
				
				case 'w':{
//					System.out.println("������w��");//������					
					shapes.get(toBeChange).setStroke(shapes.get(toBeChange).getStroke()+1);
					repaint();
					
					break;
				}		
				case 's':{
//					System.out.println("������s��");//������
					if(shapes.get(toBeChange).getStroke()>1)
					{
						shapes.get(toBeChange).setStroke(shapes.get(toBeChange).getStroke()-1);
						repaint();						
					}
					break;					
				}	
				case 'a':{
//					System.out.println("������a��");//������
					repaint();
					break;
				}					
				case 'd':{
//					System.out.println("������d��");//������
					repaint();
					break;
				}						
			}
		}		
	}	
}


}
