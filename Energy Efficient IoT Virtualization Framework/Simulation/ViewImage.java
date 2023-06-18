package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class ViewImage extends JFrame{	
	JPanel p1;
	JScrollPane jsp;
	ImageIcon icon;
	JLabel l1;
public static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
}
public ViewImage(){
	setTitle("View Face Recognition Screen");
	p1 = new JPanel();
	l1 = new JLabel();
	try{
		BufferedImage buffer = ImageIO.read(new File("test.jpg"));
		//buffer = getScaledImage(buffer,600,600);
		icon = new ImageIcon(buffer);
		icon.getImage().flush();
		l1.setIcon(icon);
	}catch(Exception e){
		e.printStackTrace();
	}
	jsp = new JScrollPane(l1);
	p1.add(jsp);
	jsp.setPreferredSize(new Dimension(icon.getIconWidth()+100,icon.getIconHeight()+100));
	p1.setPreferredSize(new Dimension(icon.getIconWidth()+100,icon.getIconHeight()+100));
    getContentPane().add(p1);
}
}