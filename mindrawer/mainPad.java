package minidrawer;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;

import javax.swing.*;

// ������
public class mainPad extends JFrame implements ActionListener {
	/**
	 * unigloria
	 */
	private static final long serialVersionUID = 1L;
	private JPanel bar;//���幦����
	private JPanel tools;//���幤�����
	private JPanel bntColor;//������ɫ���
	private JLabel select;//������ɫ���ָʾ��ǩ
	private JLabel help;//�����ڵײ�˵��δ���ð�ť�����ع���
	private DrawArea drawArea;//��������
	private NOSfile NOSfile;
	private String names[] = {"NEW","OPEN","SAVE","Line"
			,"Circle","RECT","TEXT","Black","darkGray","lightGray","Red"
			,"Yellow","Blue","Orange","Green"
			,"Magenta"};//����ͼ�������
	JButton[] buttons;//���尴ť
	public mainPad(String string) {
		super(string);//��������	    
	    
		//�������ĳ�ʼ��
	    bar = new JPanel();
		tools = new JPanel();
		
		//������������ɫ���ӵ�������
		bntColor = new JPanel();
	    bar.add(tools,BorderLayout.WEST);
	    bar.add(bntColor,BorderLayout.CENTER);
	    
	    //����ɫ���ָʾ��ǩ�ӵ���ɫ�����
	    select = new JLabel("Colors:");
	    bntColor.add(select);
	    
	    //����help����ʾ����
	    help = new JLabel("<html>"+"==================================="
	    		+"���ع��ܣ�����ͼ�Σ�ʹ��w���Ӵ�������ʹ��s����ϸ����"
	    		+ "==================================="+"<html>");
	    help.setForeground(Color.darkGray);
	    
	    //��ť��ʼ��
	    buttons = new JButton[names.length];
	    for(int i = 0 ;i<names.length;i++)
	    {
	    	
	    	if (i<7)
	    	{
	    		//������������еİ�ť	   
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
	    		//������ɫ����еİ�ť	 
	    		buttons[i] = new JButton(); 	
	    		buttons[i].setPreferredSize(new Dimension(26,26)); //������ɫ��ť�Ĵ�С
		    	buttons[i].addActionListener(this); //ע������¼�
	    		bntColor.add(buttons[i]);
	    	}
	    	
	    }
	    
	    //������ɫ��ť����ɫ
	    buttons[7].setBackground(Color.black);
	    buttons[8].setBackground(Color.gray);
	    buttons[9].setBackground(Color.lightGray);
	    buttons[10].setBackground(Color.red);
	    buttons[11].setBackground(Color.yellow);
	    buttons[12].setBackground(Color.blue);
	    buttons[13].setBackground(Color.orange);
	    buttons[14].setBackground(Color.green);
	    buttons[15].setBackground(Color.magenta);

	    
	    //�滭���ĳ�ʼ��
	    drawArea = new DrawArea(this);
	    //��ʼ��NOS����
	    NOSfile = new NOSfile(this,drawArea);   
	    
	    Container container = getContentPane();//�õ��������
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
////					System.out.println("�����ɫ�ʰ�ť����ǰisSelected��ֵΪ��"+drawArea.isSelected);//������
//					if (drawArea.isSelected)
//					{
////						System.out.println("�����ɫ����ǰisSelected��ֵΪ��"+drawArea.isSelected);//������
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


