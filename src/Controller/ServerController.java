/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Client;
import Model.ServerSide.IServerModel;
import Model.ServerSide.IServerModelThreadFactory;
import View.IServerView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Acer
 */
public class ServerController {
    IServerModel serverModel;
    IServerView serverView;
    
    public ServerController(IServerModel serverModel, IServerView serverView) {
        this.serverModel = serverModel;
        this.serverView = serverView;
        
        this.serverView.addConnectListener(new ConnectListener());
        this.serverView.addSendListener(new SendListener());
        this.serverView.addInputKeyListener(new InputKeyListener());
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
            Send();
        }
        
    }
    
    class InputKeyListener implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            if(ke.getKeyCode() == KeyEvent.VK_ENTER)
                Send();
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            
        }
        
    }
    
    void Send()
    {
        String message = serverView.getUserInput();
        if(message.isEmpty()) return;
        serverModel.Send("*Admin:  " + message);
        serverView.WriteLine("*Admin:  " + message);
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
                    String message = serverModel.FilterMessage(msg);
                    
                    if ("#!/end!#/".equalsIgnoreCase(message)){
                        message = client.getUsername() + " has disconnected";
                        serverView.WriteLine(message);
                        serverModel.SendToAllExceptClient(client, message);
                        
                        serverModel.RemoveClient(client);
                        break;
                    }
                    else 
                        message = client.getUsername() + ": " + message;
                    
                    serverView.WriteLine(message);
                    serverModel.SendToAllExceptClient(client, message);
                    
                    serverModel.didReceiveMessageFrom(msg, client);
                }

            } catch (IOException ex) {
                serverView.WriteLine("Can't read from client " + client.getUsername());
            }
        }
        
    }
}
