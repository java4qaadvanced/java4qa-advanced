package com.db.edu.chat.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Collection;

public class ChatBusinessLogic implements BusinessLogic {
    private static final Logger logger = LoggerFactory.getLogger(ChatBusinessLogic.class);
    private Socket inSocket;
    private Collection<Socket> clientsSockets;

    public ChatBusinessLogic(Socket inSocket, Collection<Socket> clientsSockets) {
        this.inSocket = inSocket;
        this.clientsSockets = clientsSockets;
    }

    @Override
    public void handle() throws  IOException{

        BufferedReader socketReader = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
        String message = socketReader.readLine();
            for (Socket outSocket : clientsSockets) {

                if (message == null) {
                    logger.error("Message is null");
                    break;
                }

                logger.info("Message from client "
                        + inSocket.getInetAddress() + ":"
                        + inSocket.getPort() + "> "
                        + message);
                if (outSocket.isClosed()) continue;
                if (!outSocket.isBound()) continue;
                if (!outSocket.isConnected()) continue;
                if (outSocket == this.inSocket) continue;
                logger.info("Writing message " + message + " to socket " + outSocket);

                BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(outSocket.getOutputStream()));
                socketWriter.write(message);
                socketWriter.newLine();
                socketWriter.flush();


                logger.error("Removing connection " + outSocket);

                try {
                    outSocket.close();
                } catch (IOException innerE) {
                    logger.error("Error closing socket ", innerE);
                }


            }

    }
}
