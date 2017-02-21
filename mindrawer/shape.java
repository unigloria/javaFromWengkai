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
//  	 判断坐标是否在直线内（不要求太精确）	
//		方案一：
//		 点x1,y1到点x2,y2的距离：distance=Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))
//		 坐标点到点x1,y1的距离：distance1=Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y))
// 		坐标点到点x2,y2的距离： distance2=Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y))
//		如果distance1+distance2的距离等于 distance，则新坐标在直线内
//		为了留出操作余量，让distance+2
		 
//		 方案二：粗糙版（省资源）
//		以两点划一个长方形，坐标点在长方形之内，则返回true 
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
//		判断坐标是否在长方形内：
//		x在x1,x2之间，并且y在y1,y2之间
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
//		计算坐标是否在圆内：
//    	半径r=Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2
//    	圆心坐标:
//    	xcore:Math.min(x1,x2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2
//    	ycore:Math.min(y1,y2)+Math.min(Math.abs(x1-x2),Math.abs(y1-y2))/2
//    	如果点x与xcore的差的平方+点y与ycore的差的平方<=半径r的平方，则点在圆内
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
//		判断坐标是否文字框内：		
//		由draw函数计算得出以下信息，保存在x2，y2中：
//		得到文本属性信息：FontMetrics font = g2d.getFontMetrics();
//	              得到文本图片的高度：int height = font.getHeight();
//	              得到文本图片的宽度int width = font.stringWidth(s);
//		计算文本图片的另一顶点(x1,y1指定的是图片左下角的坐标）
//		x2 = x1+width;
//		y2 = y1-height;		
//		形成矩形区域，判断点是否在其中
		
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