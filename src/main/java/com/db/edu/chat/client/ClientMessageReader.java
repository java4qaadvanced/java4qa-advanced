package com.db.edu.chat.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMessageReader extends Thread {

    private Socket socket;

    public ClientMessageReader(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {

        while (!isInterrupted()) {
            try {
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = socketReader.readLine();
                if (message == null) break;

                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
