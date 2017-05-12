/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author 224-2
 */
public class ServerModel {
    ServerSocket server;
    public  ArrayList<Client> clients = new ArrayList<Client>();
    int port = 4444;
    Thread waitingForClient;
    
    
    public void setWaitingForClientThread(Thread waitingForClient)
    {
        this.waitingForClient = waitingForClient;
    }
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public void Connect() throws IOException
    {
        server = new ServerSocket(port);
            
        waitingForClient.start();
    }
    
    public void Send(String message) 
    {  
        if(message.isEmpty()) return;
        for(Client c: clients)
            c.Send(message);
    }  
    
    public void SendToAllExceptClient(Client client, String message) 
    {  
        if(message.isEmpty()) return;
        for(Client c: clients)
            if(c != null && client != c)
                c.Send(message);
    }  
    
    public Socket NextConnection() throws IOException
    {
        return server.accept();
    }
    
    public void AddNewClient(Client client, Thread clientMessageListener) throws IOException
    {
        clients.add(client);
        clientMessageListener.start();
    }
    
    public void RemoveClient(Client client) throws IOException
    {
        client.Close();
        clients.remove(client);
    }
}
