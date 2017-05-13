/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ServerSide;

import Model.Client;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author 224-2
 */
public class ServerModel implements IServerModel{
    ServerSocket server;
    ArrayList<Client> clients = new ArrayList<Client>();
    int port = 4444;
    Thread waitingForClient;
    IServerModelThreadFactory clientMessageListenerFactory;
    
    
    @Override
    public void setWaitingForClientThread(Thread waitingForClient)
    {
        this.waitingForClient = waitingForClient;
    }
    
    @Override
    public void setClientMessageListenerFactory(IServerModelThreadFactory clientMessageListenerFactory)
    {
        this.clientMessageListenerFactory = clientMessageListenerFactory;
    }
    
    @Override
    public void setPort(int port)
    {
        this.port = port;
    }
    
    @Override
    public void Connect() throws IOException
    {
        server = new ServerSocket(port);
            
        waitingForClient.start();
    }
    
    @Override
    public void Send(String message) 
    {  
        if(message.isEmpty()) return;
        for(Client c: clients)
            c.Send(message);
    }  
    
    @Override
    public void SendToAllExceptClient(Client client, String message) 
    {  
        if(message.isEmpty()) return;
        for(Client c: clients)
            if(c != null && client != c)
                c.Send(message);
    }  
    
    @Override
    public Socket NextConnection() throws IOException
    {
        return server.accept();
    }
    
    @Override
    public void AddNewClient(Client client)
    {
        clients.add(client);
        clientMessageListenerFactory.create(client).start();
    }
    
    @Override
    public void RemoveClient(Client client) throws IOException
    {
        client.Close();
        clients.remove(client);
    }
    
    @Override
    public void didReceiveMessageFrom(String message, Client client)
    {
        
    }
    
    @Override
    public String FilterMessage(String message)
    {
        return message;
    }
}
