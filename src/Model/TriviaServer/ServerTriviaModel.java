/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.TriviaServer;

import Model.Client;
import Model.ServerSide.ServerModel;
import java.io.IOException;

/**
 *
 * @author Acer
 */
public class ServerTriviaModel extends ServerModel {
    
    
    @Override
    public void didReceiveMessageFrom(String message, Client client) {
        super.didReceiveMessageFrom(message, client); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Connect() throws IOException {
        super.Connect(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
