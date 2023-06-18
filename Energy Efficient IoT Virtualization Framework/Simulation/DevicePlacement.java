package com;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;
public class DevicePlacement{
	static int size=40;
	static Simulator g;

public static void randomNodes(int s, int width, int height,Simulator nodes){
	g=nodes;
    randomNodes(s, width, height);
}
public static void randomNodes(int s, int width, int height){
	int x = getXPosition(100,900);
	int y = getYPosition(150,550);
	for(int i=1;i<=s;i++){
		boolean flag = checkDistance(x,y);
		if(!flag){
			Devices d = new Devices(new Point(x, y), size);
			d.setNode("IOT"+i);
			g.devices.add(d);
		}else{
			i = i - 1;
		}
		x = getXPosition(100,900);
		y = getYPosition(150,550);
	}	
}	
public static int getXPosition(int start,int end){
	Random rn = new Random();
	int range = end - start + 1;
	return rn.nextInt(range) + start;
}
public static int getYPosition(int start,int end){
	Random rn = new Random();
	int range = end - start + 1;
	return rn.nextInt(range) + start;
}
public static boolean checkDistance(int x,int y){
	boolean flag = false;
	for(int i=0;i<g.devices.size();i++){
		Devices d = g.devices.get(i);
		double d1 = getDistance(x,y,d.x,d.y);
		if(d1 < 50){
			flag = true;
			break;
		}
	}
	return flag;
}
public static double getDistance(int n1x,int n1y,int n2x,int n2y) {
	int dx = (n1x - n2x) * (n1x - n2x);
	int dy = (n1y - n2y) * (n1y - n2y);
	int total = dx + dy; 
	return Math.sqrt(total);
}
}