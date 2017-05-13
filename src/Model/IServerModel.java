/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Acer
 */
public interface IServerModel {
    
    public void setWaitingForClientThread(Thread waitingForClient);
    
    public void setClientMessageListenerFactory(IServerModelThreadFactory clientMessageListenerFactory);
    
    public void setPort(int port);
    
    public void Connect() throws IOException;
    
    public void Send(String message);
    
    public void SendToAllExceptClient(Client client, String message);
    
    public Socket NextConnection() throws IOException;
    
    public void AddNewClient(Client client);
    
    public void RemoveClient(Client client) throws IOException;
    
    public void didReceiveMessageFrom(String message, Client client);
    
    public String FilterMessage(String message);
}
