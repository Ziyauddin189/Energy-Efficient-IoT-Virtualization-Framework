package com;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.UIManager;
public class InputScreen extends JFrame implements Runnable{
	JLabel l1,l2;
	JTextField tf1;
	Font f1;
	JPanel p1,p2,p3;
	LineBorder border;
	TitledBorder title;
	JTable table;
	static DefaultTableModel dtm;
	JScrollPane jsp;
	JButton b1,b2;
	Thread thread;
	int nsize = 0;
public InputScreen(){
	super("Mobile IOT Devices Simulation Form");
	getContentPane().setLayout(new BorderLayout());
	p1 = new JPanel();
	p1.setBackground(new Color(81,123,138));
	f1 = new Font("Times New Roman", Font.BOLD, 25);
	l1 = new JLabel("<HTML><BODY><CENTER>Energy Efficient IoT Virtualization Framework with Peer to Peer<br/>Networking and Processing</CENTER></BODY></HTML>");
	l1.setFont(this.f1);
    l1.setForeground(Color.blue);
    p1.add(l1);
	p2 = new JPanel();
	p2.setPreferredSize(new Dimension(600,150));
	p2.setBackground(Color.white);
	p2.setLayout(new MigLayout("wrap 2"));
	border = new LineBorder(new Color(42,140,241),1,true);
	title = new TitledBorder (border,"Simulation Configuration Screen",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION, new Font("Tahoma",Font.BOLD,16),Color.darkGray);
	p2.setBorder(title);
	f1 = new Font("Courier New",Font.BOLD,14);
	
	l2 = new JLabel("IOT Devices Size");
	l2.setFont(f1);
	p2.add(l2);
	tf1 = new JTextField(10);
	tf1.setFont(f1);
	p2.add(tf1);

	b1 = new JButton("Calculate IOT ID");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int num = Integer.parseInt(tf1.getText().trim());
			for(int i=0;i<num;i++){
				Object row[] = {i,"IOT"+i};
				dtm.addRow(row);
			}
		}
	});

	b2 = new JButton("View Simulation");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			createNode();
			IOTDevices iot = new IOTDevices(nsize);
			iot.setVisible(true);
			iot.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	});

	p3 = new JPanel();
	p3.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setFont(f1);
	table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));
	jsp = new JScrollPane(table);
	dtm.addColumn("IOT ID");
	dtm.addColumn("IOT Name");
	

	jsp.getViewport().setBackground(Color.white);
	jsp.setPreferredSize(new Dimension(600,500));
	p3.add(jsp,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);
	getContentPane().add(p3,BorderLayout.SOUTH);
	thread = new Thread(this);
	thread.start();
}
public void run(){
	try{
		while(true){
			l1.setForeground(Color.black);
			thread.sleep(500);
			l1.setForeground(Color.magenta);
			thread.sleep(500);
			
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void createNode(){
	String nodesize = tf1.getText();
	if(nodesize == null || nodesize.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Sensor size must be enter");
		tf1.requestFocus();
		return;
	}
	try{
		nsize = Integer.parseInt(nodesize.trim());
	}catch(NumberFormatException e){
		JOptionPane.showMessageDialog(this,"Sensor size must be numeric");
		tf1.requestFocus();
		return;
	}
}	
public static void main(String a[])throws Exception{
	 UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
	 InputScreen screen = new InputScreen();
	 screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
	 screen.setVisible(true);
	 
}
}