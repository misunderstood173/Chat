/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class ClientConsoleView implements IClientView {

    @Override
    public void addInputKeyListener(KeyListener inputKeyListener) {
        
    }
    
    ActionListener connect;
    ActionListener disconnect;
    ActionListener send;
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String lastInput;

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public String getUsername() {
        return "ConsoleMe";
    }

    @Override
    public void addConnectListener(ActionListener listenForConnect) {
        connect = listenForConnect;
    }

    @Override
    public void addDisconnectListener(ActionListener listenForDisconnect) {
        disconnect = listenForDisconnect;
    }

    @Override
    public String getUserInput() {
        return lastInput;
    }

    @Override
    public void addSendListener(ActionListener listenForSend) {
        send = listenForSend;
    }

    @Override
    public void WriteLine(String str) {
        System.out.println(str);
    }

    @Override
    public void start() {
        try {
            while(true){
                String input = stdIn.readLine();
                if ("--disconnect".equals(input))
                {
                    disconnect.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }

                else if ("--connect".equals(input))
                {
                    connect.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }

                else
                {
                    lastInput = input;
                    send.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
                    }
        } catch (IOException ex) {
            System.out.println("Can't read input");
        }
    }
    
}
