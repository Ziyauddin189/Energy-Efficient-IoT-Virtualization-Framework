package com;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
public class Simulator extends JComponent{
	String col="empty";
	public int option=0;
	public ArrayList<Devices> devices = new ArrayList<Devices>();
	float dash1[] = {10.0f};
	BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash1, 0.0f);
	BasicStroke rect=new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1f,new float[] {2f},0f);
	int size;
	
	Devices source = null;
	int xx,yy;
public void setNode(Devices source,int xx,int yy){
	this.source = source;
	this.xx = xx;
	this.yy = yy;
}

public Simulator(int size) {
	super.setBackground(new Color(81,123,138));
	this.size = size;
    this.setBackground(new Color(81,123,138));
}

public ArrayList<Devices> getList(){
	return devices;
}
public void removeAll(){
	option=0;
	devices.clear();
	col="empty";
	repaint();
}

public void paintComponent(Graphics g1){
	super.paintComponent(g1);
	GradientPaint gradient = new GradientPaint(0, 0, Color.blue, 175, 175, Color.red,true); 
	Graphics2D g = (Graphics2D)g1;
	g.setPaint(gradient);
	g.setStroke(rect);
	Rectangle2D rectangle = new Rectangle2D.Double(100,10,200,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Relay/VM 1",180,40);
	rectangle = new Rectangle2D.Double(400,10,200,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Relay/VM 2",480,40);
	rectangle = new Rectangle2D.Double(800,10,200,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Relay/VM 3",880,40);
	
	if(option == 0){
		for(int i=0;i<devices.size();i++){
			Devices d = devices.get(i);
			if(d.getNode() != null){
				d.draw(g,"fill");
				g.drawString(d.getNode(),d.x+10,d.y+50);
			}
		}
		g.setPaint(gradient);
	}
	if(option == 1){
		for(int i=0;i<devices.size();i++){
			Devices d = devices.get(i);
			if(d.getNode() != null){
				d.draw(g,"fill");
				g.drawString(d.getNode(),d.x+10,d.y+50);
			}
		}
		g.drawLine(source.x+10,source.y+10,xx,yy+10);
		g.setPaint(gradient);
	}
}
}