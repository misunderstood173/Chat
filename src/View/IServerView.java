/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.event.ActionListener;

/**
 *
 * @author 224-2
 */
public interface IServerView {
    public String getUserInput();
    public void addSendListener(ActionListener listenForSend);
    public void addConnectListener(ActionListener listenForConnect);
    public void WriteLine(String str);
    public void start();
}
