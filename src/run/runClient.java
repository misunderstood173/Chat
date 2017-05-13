/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import Controller.ClientController;
import Model.ClientSide.ClientModel;
import View.ClientConsoleView;
import View.ClientView;

/**
 *
 * @author Acer
 */
public class runClient {
    public static void main(String[] args) {
        new ClientController(new ClientModel(), new ClientView()).start();
    }
}
