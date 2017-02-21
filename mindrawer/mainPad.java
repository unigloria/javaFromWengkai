package minidrawer;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;

import javax.swing.*;

// 主界面
public class mainPad extends JFrame implements ActionListener {
	/**
	 * unigloria
	 */
	private static final long serialVersionUID = 1L;
	private JPanel bar;//定义功能区
	private JPanel tools;//定义工具面板
	private JPanel bntColor;//定义颜色面板
	private JLabel select;//定义颜色面板指示标签
	private JLabel help;//用来在底部说明未设置按钮的隐藏功能
	private DrawArea drawArea;//画板区域
	private NOSfile NOSfile;
	private String names[] = {"NEW","OPEN","SAVE","Line"
			,"Circle","RECT","TEXT","Black","darkGray","lightGray","Red"
			,"Yellow","Blue","Orange","Green"
			,"Magenta"};//定义图标的名称
	JButton[] buttons;//定义按钮
	public mainPad(String string) {
		super(string);//画板命名	    
	    
		//工具栏的初始化
	    bar = new JPanel();
		tools = new JPanel();
		
		//将工具面板和颜色面板加到功能区
		bntColor = new JPanel();
	    bar.add(tools,BorderLayout.WEST);
	    bar.add(bntColor,BorderLayout.CENTER);
	    
	    //将颜色面板指示标签加到颜色面板中
	    select = new JLabel("Colors:");
	    bntColor.add(select);
	    
	    //定义help的显示文字
	    help = new JLabel("<html>"+"==================================="
	    		+"隐藏功能：单击图形，使用w键加粗线条，使用s键收细线条"
	    		+ "==================================="+"<html>");
	    help.setForeground(Color.darkGray);
	    
	    //按钮初始化
	    buttons = new JButton[names.length];
	    for(int i = 0 ;i<names.length;i++)
	    {
	    	
	    	if (i<7)
	    	{
	    		//创建工具面板中的按钮	   
	    		buttons[i] = new JButton(names[i]); 	
		    	buttons[i].addActionListener(this);	     
		    	tools.add(buttons[i]);
		    	if(i<3)
		    	{
		    		buttons[i].setBackground(Color.lightGray);
		    	}
		    	else
		    	{
		    		buttons[i].setBackground(Color.white);		    		
		    	}
	    	}
	    	else
	    	{	
	    		//创建颜色面板中的按钮	 
	    		buttons[i] = new JButton(); 	
	    		buttons[i].setPreferredSize(new Dimension(26,26)); //定义颜色按钮的大小
		    	buttons[i].addActionListener(this); //注册监听事件
	    		bntColor.add(buttons[i]);
	    	}
	    	
	    }
	    
	    //定义颜色按钮的颜色
	    buttons[7].setBackground(Color.black);
	    buttons[8].setBackground(Color.gray);
	    buttons[9].setBackground(Color.lightGray);
	    buttons[10].setBackground(Color.red);
	    buttons[11].setBackground(Color.yellow);
	    buttons[12].setBackground(Color.blue);
	    buttons[13].setBackground(Color.orange);
	    buttons[14].setBackground(Color.green);
	    buttons[15].setBackground(Color.magenta);

	    
	    //绘画区的初始化
	    drawArea = new DrawArea(this);
	    //初始化NOS对象
	    NOSfile = new NOSfile(this,drawArea);   
	    
	    Container container = getContentPane();//得到内容面板
	    container.add(bar, BorderLayout.NORTH);
	    container.add(drawArea,BorderLayout.CENTER);
	    container.add(help,BorderLayout.SOUTH);
	    setBounds(40,40,850,850);
	    setVisible(true);
	    validate();
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		for(int i = 3; i<buttons.length;i++)
		{
			if(i<7)
			{
				if(e.getSource() ==buttons[i])
				{
					drawArea.setCurrentCmd(i);
					drawArea.createNewitem();
					drawArea.repaint();
			    }
			}
			else
			{
				if(e.getSource()==buttons[i])
				{
////					System.out.println("点击了色彩按钮，当前isSelected的值为："+drawArea.isSelected);//测试用
//					if (drawArea.isSelected)
//					{
////						System.out.println("进入改色，当前isSelected的值为："+drawArea.isSelected);//测试用
//						drawArea.changeColor(buttons[i].getBackground());
//					}
//					else
//					{
//						drawArea.setColor(buttons[i].getBackground());
//					}
					drawArea.setColor(buttons[i].getBackground());
				}
			}						
		}
		
		if(e.getSource()==buttons[0])
		{
			NOSfile.newFile();
		}
		else if(e.getSource()==buttons[1])
		{
			NOSfile.openFile();
		}
		else if(e.getSource()==buttons[2])
		{
			NOSfile.saveFile();
		}
	}	

}


