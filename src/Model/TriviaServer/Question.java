/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.TriviaServer;

/**
 *
 * @author Acer
 */
public class Question {
    String question;
    String hints[];
    String answear;
    
    int nextHintId = 0;
    
    public Question(String question, String[] hints, String answear) {
        this.question = question;
        this.hints = hints;
        this.answear = answear;
    }
    
    public String getQuestion()
    {
        return question;
    }
    
    public String getAnswear()
    {
        return answear;
    }
    
    public String getHint(int hintIndex)
    {
        if(hintIndex >= hints.length)
            return "No hint";
        return hints[hintIndex];
    }
    
    public String getNextHint() {
        if(nextHintId > getNumberOfHints() - 1) 
            return "No more hints";
        return getHint(nextHintId++);
    }
    
    public void ResetQuestion()
    {
        nextHintId = 0;
    }
    
    public int getNumberOfHints()
    {
        return hints.length;
    }
}
