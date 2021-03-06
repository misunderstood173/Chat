/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ClientSide.ClientModel;
import Model.ClientSide.IClientModelThreadFactory;
import View.IClientView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author Acer
 */
public class ClientController {

    ClientModel clientModel;
    IClientView clientView;
    
    public ClientController(ClientModel clientModel, IClientView clientView) {
        this.clientModel = clientModel;
        this.clientView = clientView;
        
        this.clientView.addConnectListener(new ConnectListener());
        this.clientView.addDisconnectListener(new DisconnectListener());
        this.clientView.addSendListener(new SendListener());
        this.clientView.addInputKeyListener(new InputKeyListener());
        
        this.clientModel.setMessageReceivedListenerFactory(new MessageReceivedListenerFactory());
    }
    
    public void start() {
        clientView.start();
    }
    
    void Send()
    {
        String message = clientView.getUserInput();
        if(message.isEmpty()) return;
        clientModel.Send(message);
        clientView.WriteLine(clientModel.getUsername() + ": " + message);
    }
    
    
    class ConnectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String host = clientView.getHost();
            String username = clientView.getUsername();

            try{
                clientModel.Connect(host, username);
                clientView.WriteLine("Connected to server");
            }
            catch (UnknownHostException e) {
                clientView.WriteLine("Unknown host");
            }
            catch (IOException e) {
                clientView.WriteLine("Could not connect to the server");
            }
        }
        
    }
    
    class DisconnectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                clientModel.Disconnect();
            } catch (IOException ex) {
                clientView.WriteLine("Problem occured when trying to disconnect. Perhaps you're not connected");
            }
        }
        
    }
    
    class SendListener implements ActionListener {

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
    
    class MessageReceivedListenerFactory implements IClientModelThreadFactory
    {

        @Override
        public Thread create() {
            return new MessageReceivedListener();
        }
        
    }
    
    class MessageReceivedListener extends Thread
    {

        @Override
        public void run() {
            try {
                String msg;
                while((msg = clientModel.Read()) != null)
                {
                    if(!msg.isEmpty())
                        clientView.WriteLine(msg);
                }
            }catch (IOException e) {
                clientView.WriteLine("Can't get message from server");
            }
        }
    }
    
}
