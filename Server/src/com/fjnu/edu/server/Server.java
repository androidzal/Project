package com.fjnu.edu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.fjnu.edu.database.DBHelper;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(1314);
			Socket socket = null;
			System.out.println("The server is already open!");
			while (true) {
				socket = server.accept();
				System.out.println(socket.getInetAddress().getHostAddress() + ":start");
				Thread thread = new Thread(new ServerThread(socket));
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
