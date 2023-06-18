package com;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.FileInputStream;
import java.io.File;
import javax.swing.JTextArea;
import java.io.FileOutputStream;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class RequestHandler extends Thread{
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
public RequestHandler(Socket soc,JTextArea area){
    socket=soc;
	this.area=area;
	try{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
	}catch(Exception e){
        e.printStackTrace();
    }
}
@Override
public void run(){
	try{
		process();		
    }catch(Exception e){
        e.printStackTrace();
    }
}
public static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
}
public void process()throws Exception{
	Object input[]=(Object[])in.readObject();
	String type=(String)input[0];
	if(type.equals("image")){
		long start = System.currentTimeMillis();
		long start1 = start - 2000;
		long start2 = start - 6000;
		String file = (String)input[1];
		byte img[] = (byte[])input[2];
		int width = Integer.parseInt((String)input[3]);
		int height = Integer.parseInt((String)input[4]);
		String vm = (String)input[5];
		String key = file+","+width+","+height;
		FileOutputStream fout = new FileOutputStream(file);
		fout.write(img,0,img.length);
		fout.close();
		BufferedImage bi = ImageIO.read(new File(file));
		bi = getScaledImage(bi,width,height);
		int index = file.lastIndexOf(".")+1;
		String ext = file.substring(index,file.length());
		ImageIO.write(bi,ext,new File(file));
		FileInputStream fin = new FileInputStream(file);
		byte b[] = new byte[fin.available()];
		fin.read(b,0,b.length);
		fin.close();
		File temp = new File(file);
		temp.delete();
		Object res[] = {b};
		area.append(vm+" received & processed image resize to "+width+" and "+height+"\n");
		out.writeObject(res);
		out.flush();
		long end = System.currentTimeMillis();
		VM.relay = end - start;
		VM.object = end - start2;
		VM.hybrid = end - start1;
	}
}
}
