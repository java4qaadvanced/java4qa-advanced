package com.db.edu.chat.client;

import java.io.*;
import java.net.Socket;


public class ClientConsoleReader extends Thread{
    final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    final BufferedWriter socketWriter;

    public ClientConsoleReader(Socket socket) throws IOException {
        this.socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run(){
        while(!isInterrupted()) {
            try {
                socketWriter.write(consoleReader.readLine());
                socketWriter.newLine();
                socketWriter.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
