package com;
public class ServerThread extends Thread
{
	VM server;
public ServerThread(VM server){
	this.server=server;
	start();
}
public void run(){
	server.start();
}
}