package com.db.edu.chat.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
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
    public void handle() throws MessageException{
        BufferedReader socketReader;
        BufferedWriter socketWriter;
        String message = null;
        for (Socket outSocket : clientsSockets) {
            try {
                socketReader = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
                message = socketReader.readLine();
                logger.info("The line was read");
                if (outSocket.isClosed()) break;
                if (!outSocket.isBound()) continue;
                if (!outSocket.isConnected()) continue;
                if (outSocket == this.inSocket) continue;

                if (message == null) throw new MessageException("Message is null");

                logger.info("Message from client "
                        + inSocket.getInetAddress() + ":"
                        + inSocket.getPort() + "> "
                        + message);

                logger.info("Writing message " + message + " to socket " + outSocket);

                socketWriter = new BufferedWriter(new OutputStreamWriter(outSocket.getOutputStream()));
                socketWriter.write(message);
                socketWriter.newLine();
                socketWriter.flush();




//                try {
//                    outSocket.close();
//                } catch (SocketException innerE) {
//                    logger.error("Error closing socket ", innerE);
//                }
            } catch (IOException e) {
                logger.error("Error: ", e);
                throw new MessageException(e);

            }

        }

    }
}
