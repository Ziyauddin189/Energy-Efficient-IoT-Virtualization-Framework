package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileWriter;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.io.File;
import java.io.FileInputStream;
import org.jfree.ui.RefineryUtilities;
import javax.swing.JTextField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import java.awt.Cursor;
public class IOTDevices extends JFrame{
	Simulator node;
	JPanel p1,p2;
	int left,top;
	Font f1;
	JButton b1,b2,b3,b4,b5;
	JLabel l1,l2;
	static JLabel status;
	int size;
	JFileChooser chooser;
	File file;
	double filesize;
	
	String choosen;
	JComboBox c1;
	String close;
	int xx,yy;
	Devices source_device;
	int img_height;
	int img_width;
public IOTDevices(int s){
	super("Simulation Screen");
	size = s;

	chooser = new JFileChooser(new File("samples"));
	
	f1 = new Font("Times New Roman",Font.BOLD,14);
	node = new Simulator(30);
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	p1.add(node,BorderLayout.CENTER);
	p1.setBackground(new Color(81,123,138));
	getContentPane().add(p1,BorderLayout.CENTER);
	
	p2 = new JPanel();
	p2.setBackground(Color.white);
	p2.setLayout(new MigLayout("wrap 2")); 

	l1 = new JLabel("Device ID");
	l1.setFont(f1);
	p2.add(l1);
	c1 = new JComboBox();
	c1.setFont(f1);
	p2.add(c1);
	for(int i=1;i<=size;i++){
		c1.addItem("IOT"+i);
	}
	
	status = new JLabel("Status:");
	status.setFont(f1);
	p2.add(status,"span,wrap");
	l2 = new JLabel("Propose Status:");
	l2.setFont(f1);
	//p2.add(l2,"span,wrap");

	b1 = new JButton("Upload Image");
	p2.add(b1,"span,split 3");
	b1.setFont(f1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(IOTDevices.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				filesize = (double)file.length()/1000;
				status.setText("Uploaded file : "+file.getName()+" File Size in KB : "+filesize);
			}
		}
	});

	b4 = new JButton("Choose Closer & Free Relay Resource");
	p2.add(b4);
	b4.setFont(f1);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			chooseRelay();
		}
	});

	b5 = new JButton("Relay Task To VM & Run Image Resize Algorithm");
	p2.add(b5);
	b5.setFont(f1);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			img_width = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter image new width"));
			img_height = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter image new height"));
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			Runnable r = new Runnable(){
				public void run(){
					new OffloadTask(node,source_device,xx,yy,file,img_width,img_height,choosen);
				}
			};
			try{
				Thread t = new Thread(r);
				t.start();
				t.join();
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	});


	b3 = new JButton("Exit");
	p2.add(b3);
	b3.setFont(f1);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	});

	
	getContentPane().add(p2,BorderLayout.NORTH);
	left = getInsets().left;
    top = getInsets().top;
	DevicePlacement.randomNodes(size,600,500,node);
	node.option=0;
    node.repaint();
	
}
public String getCloseVM(String source){
	String close = "none";
	Devices device = null;
	for(int i=0;i<node.devices.size();i++){
		Devices d = node.devices.get(i);
		if(d.getNode().equals(source)){
			device = d;
			source_device = d;
			break;
		}
	}
	double distance1 = DevicePlacement.getDistance(180,40,device.getX(),device.getY());
	double distance2 = DevicePlacement.getDistance(480,40,device.getX(),device.getY());
	double distance3 = DevicePlacement.getDistance(880,40,device.getX(),device.getY());
	if(distance1 < distance2 && distance1 < distance3) {
		close = "VM 1";
		xx = 180;
		yy = 40;
	} else if (distance2 < distance1 && distance2 < distance3) {
		close = "VM 2";
		xx = 480;
		yy = 40;
	} else if (distance3 < distance1 && distance3 < distance2) {
		close = "VM 3";
		xx = 880;
		yy = 40;
	}
	return close;
}

public void chooseRelay(){
	String source = c1.getSelectedItem().toString().trim();
	choosen = getCloseVM(source);
	status.setText("Choosen Close VM/Relay is : "+choosen);
}
}