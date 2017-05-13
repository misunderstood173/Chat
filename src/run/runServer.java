/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import Controller.ServerController;
import Model.ServerSide.ServerModel;
import Model.TriviaServer.ServerTriviaModel;
import View.ServerView;

/**
 *
 * @author Acer
 */
public class runServer {
    public static void main(String[] args) {
        new ServerController(new ServerTriviaModel(), new ServerView()).start();
    }
}
