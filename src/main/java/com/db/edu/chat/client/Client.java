package com.db.edu.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.db.edu.chat.server.Server;

public class Client  {
	private  ClientConsoleReader clientConsoleReader;
	private ClientMessageReader clientMessageReader;

	public Client() throws IOException{
		Socket socket = new SocketFactory().getSocket();
		clientConsoleReader = new ClientConsoleReader(socket);
		clientMessageReader = new ClientMessageReader(socket);


	}

	public void start(){
		clientConsoleReader.run();
		clientMessageReader.run();
	}


	public static void main(String... args) throws IOException {
		new Client().start();
	}
}
