package minidrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.Serializable;


public class shape implements Serializable {

	/**
	 * unigloria
	 */
	private static final long serialVersionUID = 1L;
	int x1,x2,y1,y2;   	    
	Color color;
	int stroke ;			
	String s;				

    void draw(Graphics2D g2d ){
    	
    }

    void setColor(Color color){
    	this.color = color;	
    }
    
    void setStroke(int stroke){
    	this.stroke = stroke;
    }
    
    int getStroke(){
    	return stroke;
    }
    
    boolean isInside(int x, int y){    	
    	return false;
    }
}

class Line extends shape{
	void draw(Graphics2D g2d) {
		g2d.setPaint(color);
		g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);		
	}
	
	 boolean isInside(int x, int y){    	
//  	 �ж������Ƿ���ֱ���ڣ���Ҫ��̫��ȷ��	
//		����һ��
//		 ��x1,y1����x2,y2�ľ��룺distance=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))
//		 ����㵽��x1,y1�ľ��룺distance1=Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y))
// 		����㵽��x2,y2�ľ��룺 distance2=Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y))
//		���distance1+distance2�ľ������ distance������������ֱ����
//		Ϊ������������������distance+2
		 
//		 ���������ֲڰ棨ʡ��Դ��
//		�����㻮һ�������Σ�������ڳ�����֮�ڣ��򷵻�true 
//		 
		 if(Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y))+
				 Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y))<=
				 Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))+2)
		 {
			 return true;
		 }
		 else
		 {
			 return false;
		 }		 
	}
}

class Rect extends shape{
	void draw(Graphics2D g2d ){
		g2d.setPaint(color);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2-x1), Math.abs(y2-y1));
	}
	
	boolean isInside(int x, int y){
//		�ж������Ƿ��ڳ������ڣ�
//		x��x1,x2֮�䣬����y��y1,y2֮��
		if((x>Math.min(x1, x2)&&x<Math.max(x1, x2))&&(y>Math.min(y1, y2)&&y<Math.max(y1, y2)))
		{
			return true;
		}
		else
		{
			return false;		
		}					
	}
}

class Circle extends shape{
	void draw(Graphics2D g2d ){
		g2d.setPaint(color);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2),  Math.min(Math.abs(x1-x2), 
				Math.abs(y1-y2)), Math.min(Math.abs(x1-x2), Math.abs(y1-y2)));
	}
	
    boolean isInside(int x, int y){		
//		���������Ƿ���Բ�ڣ�
//    	�뾶r=Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2
//    	Բ������:
//    	xcore:Math.min(x1,x2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2
//    	ycore:Math.min(y1,y2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2
//    	�����x��xcore�Ĳ��ƽ��+��y��ycore�Ĳ��ƽ��<=�뾶r��ƽ���������Բ��
//    	(x-(Math.min(x1,x2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2)*(x-(Math.min(x1,x2)+Math.abs(x1-x2)/2))+
//    	(y-(Math.min(y1,y2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2)*(y-(Math.min(y1,y2)+Math.abs(y1-y2)/2))<=
//    	(Math.abs(x1-x2)/2)*(Math.abs(x1-x2)/2)
    	
    	if((x-(Math.min(x1,x2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2))*(x-(Math.min(x1,x2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2))+
    			(y-(Math.min(y1,y2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2))*(y-(Math.min(y1,y2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2))
    			<=(Math.abs(x1-x2)/2)*(Math.abs(x1-x2)/2))
		{
			return true;
		}
		else
			return false;				
	}
}

class Word extends shape{
	void draw(Graphics2D g2d ){
		g2d.setPaint(color);
		g2d.setFont(new Font("",1,((int)stroke)*18));
	    if(s != null)
	    {
	    	g2d.drawString(s,x1,y1);
		    x2 = x1+g2d.getFontMetrics().stringWidth(s);
		    y2 = y1-g2d.getFontMetrics().getHeight();
	    }		
	}
	
	boolean isInside(int x, int y){		
//		�ж������Ƿ����ֿ��ڣ�		
//		��draw��������ó�������Ϣ��������x2��y2�У�
//		�õ��ı�������Ϣ��FontMetrics font = g2d.getFontMetrics();
//	              �õ��ı�ͼƬ�ĸ߶ȣ�int height = font.getHeight();
//	              �õ��ı�ͼƬ�Ŀ��int width = font.stringWidth(s);
//		�����ı�ͼƬ����һ����(x1,y1ָ������ͼƬ���½ǵ����꣩
//		x2 = x1+width;
//		y2 = y1-height;		
//		�γɾ��������жϵ��Ƿ�������
		
		if((x>x1 && x<x2) && (y<y1 && y>y2))
		{
			return true;
		}
		else
		{
			return false;		
		}		
	}	
	
}