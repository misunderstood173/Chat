/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.TriviaServer;

import Model.Client;
import Model.ServerSide.ServerModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class ServerTriviaModel extends ServerModel {
    IQuestionsProvider questionsProvider = new SimpleQuestionsProvider();
    Question currentQuestion;
    
    @Override
    public void didReceiveMessageFrom(String message, Client client) {
        if(client == null) return;
        if(currentQuestion == null) return;
        
        String username = client.getUsername();
        String answear = currentQuestion.getAnswear();
        if(message.equalsIgnoreCase(answear))
        {
            Send(username + " got it right!\n The answear was: " + answear);
            new AskNextQuestion().start();
        }
    }

    @Override
    public void Connect() throws IOException {
        super.Connect();
        
        new AskNextQuestion().start();
    }
    
    class AskNextQuestion extends Thread
    {

        @Override
        public void run() {
            try {
                Question question = questionsProvider.getNextQuestion();
                currentQuestion = question;
                Send(question.getQuestion());
                this.sleep(2500);

                if(question != currentQuestion) return;
                Send(question.getNextHint());
                this.sleep(2500);
                
                if(question != currentQuestion) return;
                Send(question.getNextHint());
                this.sleep(2500);
                
                if(question != currentQuestion) return;
                Send(question.getNextHint());
                this.sleep(2500);
                
                if(question != currentQuestion) return;
                Send("No one got it? The answear was: " + question.getAnswear() + "\nNext question comming...");
                currentQuestion = null;
                this.sleep(2500);
                new AskNextQuestion().start();
                
            } catch (InterruptedException ex) {
                System.out.println("thread interrupted");
            }
        }
        
    }
}
