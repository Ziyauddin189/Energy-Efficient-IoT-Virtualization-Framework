package com;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
public class OffloadTask extends Thread{
	Simulator sim;
	Devices source;
	int xx,yy;
	File file;
	int img_height;
	int img_width;
	String choosen;
public OffloadTask(Simulator s,Devices source,int xx,int yy,File file,int w,int h,String c){
	sim = s;
	this.source = source;
	this.xx = xx;
	this.yy = yy;
	this.file = file;
	img_width = w;
	img_height = h;
	choosen = c;
	start();
}
public void run(){
	try{
		FileInputStream fin = new FileInputStream(file);
		byte b[] = new byte[fin.available()];
		fin.read(b,0,b.length);
		fin.close();
		Socket socket = new Socket("localhost",7777);
		ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
		Object req[]={"image",file.getName(),b,img_width+"",img_height+"",choosen};
		out.writeObject(req);
		out.flush();
		Object res[] = (Object[])in.readObject();
		byte img[] = (byte[])res[0];
		FileOutputStream fout = new FileOutputStream("test.jpg");
		fout.write(img,0,img.length);
		fout.close();
		for(int k=0;k<5;k++){
			sim.setNode(source,xx,yy);
			sim.option=1;
			sim.repaint();
			sleep(100);
			sim.option=0;
			sim.repaint();
			sleep(50);
		}
		ViewImage vi = new ViewImage();
		vi.setVisible(true);
		vi.setSize(800,800);
		
	}catch(Exception e){
		e.printStackTrace();
	}
}

}