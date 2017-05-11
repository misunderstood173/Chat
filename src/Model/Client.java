/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Acer
 */
public class Client {
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    String username;
    
    boolean closed = true;
    
    public Client(Socket socket) throws IOException{
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        closed = false;
    }
    
    public void SetUsername(String username){
        this.username = username;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void Close() throws IOException
    {
        if(closed == true)
            return;
        
        in.close();
        out.close();
        socket.close();
        closed = true;
    }
    
    public boolean isOpen(){
        return !closed;
    }
    
    public void Send(String text)
    {
        if(closed == true) return;
        
        out.println(text);
    }
    
    public String Receive() throws IOException
    {
        return in.readLine();
    }
}
