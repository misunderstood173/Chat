/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.ServerSide;

import Model.Client;

/**
 *
 * @author Acer
 */
public interface IServerModelThreadFactory {
    public Thread create(Client client);
}
