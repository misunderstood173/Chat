/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Client;
import Model.IServerModelThreadFactory;
import Model.ServerModel;
import View.IServerView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Acer
 */
public class ServerController {
    ServerModel serverModel;
    IServerView serverView;
    
    public ServerController(ServerModel serverModel, IServerView serverView) {
        this.serverModel = serverModel;
        this.serverView = serverView;
        
        this.serverView.addConnectListener(new ConnectListener());
        this.serverView.addSendListener(new SendListener());
        this.serverModel.setWaitingForClientThread(new WaitingForClient());
        this.serverModel.setClientMessageListenerFactory(new ClientMessageListenerFactory());
    }
    
    public void start()
    {
        serverView.start();
    }
    
    
    class ConnectListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                serverModel.Connect();
                serverView.WriteLine("Server started");
            } catch (IOException ex) {
                serverView.WriteLine("Server could not start");
            }
        }
        
    }
    
    class SendListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String message = serverView.getUserInput();
            if(message.isEmpty()) return;
            serverModel.Send("*Admin:  " + message);
            serverView.WriteLine("*Admin:  " + message);
        }
        
    }
    
    class WaitingForClient extends Thread
    {

        @Override
        public void run() {
            
            try {
                while(true)
                {
                    Socket clientSocket = serverModel.NextConnection();
                    Client client = new Client(clientSocket);

                    String userName = client.Receive();
                    client.SetUsername(userName);
                    serverModel.AddNewClient(client);
                    serverView.WriteLine(userName + " has connected");
                    
                    serverModel.SendToAllExceptClient(client, userName + " has connected");
                }

            } catch (IOException ex) {
                serverView.WriteLine("Can't listen for new clients");
            }
        }
    }
    
    class ClientMessageListenerFactory implements IServerModelThreadFactory
    {

        @Override
        public Thread create(Client client) {
            return new ClientMessageListener(client);
        }
        
    }
    
    class ClientMessageListener extends Thread
    {
        private Client client;
        public ClientMessageListener(Client client) {
            this.client = client;
        }

        @Override
        public void run()
        {
            try {
                String msg;
                while((msg = client.Receive()) != null)
                {
                    if ("#!/end!#/".equalsIgnoreCase(msg)){
                        msg = client.getUsername() + " has disconnected";
                        serverView.WriteLine(msg);
                        serverModel.SendToAllExceptClient(client, msg);
                        
                        serverModel.RemoveClient(client);
                        break;
                    }
                    else 
                        msg = client.getUsername() + ": " + msg;
                    
                    serverView.WriteLine(msg);
                    serverModel.SendToAllExceptClient(client, msg);
                }

            } catch (IOException ex) {
                serverView.WriteLine("Can't read from client " + client.getUsername());
            }
        }
        
    }
}
