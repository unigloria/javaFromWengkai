package minidrawer;

import java.awt.Color;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;

//处理NEW、OPEN、SAVE三种指令，文件类型设为后缀为"。unigloria" muahaha~~

public class NOSfile {
   private mainPad mainPad;
   DrawArea drawarea = null;
   NOSfile(mainPad mP,DrawArea DA) {
		mainPad = mP;
		drawarea = DA;
	}
    
	public void newFile() {
		// 新建
		drawarea.shapes.removeAllElements();
		drawarea.setCurrentCmd(3);
		drawarea.setColor(Color.black);
		drawarea.setStroke(1);
		drawarea.selected = 0;
		drawarea.createNewitem();
		drawarea.repaint();
		drawarea.isSelected = false;
	}

	public void openFile() {		
		 //打开
		 
		 JFileChooser chooser = new JFileChooser();
		 chooser.showOpenDialog(null);
		    File file = chooser.getSelectedFile();
		    if(file == null)
		    {
		    	JOptionPane.showMessageDialog(null, "没有选择文件");  
		    }		    
		    else {
		    	    newFile();		    	
					try {
						FileInputStream fis = new FileInputStream(file);
						ObjectInputStream ois = new ObjectInputStream(fis);						 
						shape inputRecord;
						int countNumber = ois.readInt();
						for(int i=0; i<countNumber; i++)
						{
							inputRecord = (shape)ois.readObject();
							drawarea.shapes.add(inputRecord);
						}
						drawarea.createNewitem();
						ois.close();
						drawarea.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}				
		    	}
	}
    

	
	public void saveFile(){  
        JFileChooser chooser = new JFileChooser();  
        chooser.showSaveDialog(null);  
        File file =chooser.getSelectedFile();            
        if(file==null){  
            JOptionPane.showMessageDialog(null, "没有保存文件");  
        }
        else 
        {   
            try {  
                FileOutputStream fos = new FileOutputStream(file+".unigloria");  
                ObjectOutputStream oos = new ObjectOutputStream(fos);  
                oos.writeInt(drawarea.shapes.size());
                for(int i=0; i<drawarea.shapes.size(); i++)
                {
                	shape p = drawarea.shapes.get(i);
                	oos.writeObject(p);
                }
                JOptionPane.showMessageDialog(null, "保存成功！");  
                oos.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
        }  
    }
}
