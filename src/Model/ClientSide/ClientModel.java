/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ClientSide;

import Model.Client;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Acer
 */
public class ClientModel {
    static Client client;
    String username;
    int port = 4444;
    IClientModelThreadFactory messageReceivedListenerFactory;
    
    public void setPort(int port)
    {
        this.port = port;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public boolean isOpen()
    {
        return client.isOpen();
    }
    
    public void setMessageReceivedListenerFactory(IClientModelThreadFactory messageReceivedListenerFactory)
    {
        this.messageReceivedListenerFactory = messageReceivedListenerFactory;
    }
    
    public void Connect(String host, String username) throws UnknownHostException, IOException 
    {         
        if(client != null && client.isOpen()) {
            Disconnect();
        }
        this.username = username;
        if(host.isEmpty()) 
            host = "localhost";
        if(this.username.isEmpty())
            this.username = "Anonymous";
        
        client = new Client(new Socket(host, port));
        client.Send(this.username);
        
        messageReceivedListenerFactory.create().start();
    }                                          

    public void Send(String message) 
    {  
        client.Send(message);
    }  
    
    public String Read() throws IOException
    {
        return client.Receive();
    }

    public void Disconnect() throws IOException
    {
        if(client == null) return;
        client.Send("#!/end!#/");
        client.Close();
    }                                          
}
