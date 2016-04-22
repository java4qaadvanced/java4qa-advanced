package com.db.edu.chat.client;

import com.db.edu.chat.server.Server;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Student on 15.04.2016.
 */
public class SocketFactory  {
    private Socket socket;

    public SocketFactory() {
        try {
            socket = new Socket(Server.HOST, Server.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SocketFactory(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final Socket getSocket(){
        return this.socket;
    }


}
