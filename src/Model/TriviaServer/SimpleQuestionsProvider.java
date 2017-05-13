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
public class SimpleQuestionsProvider implements IQuestionsProvider{
    Question[] questions = 
        {
            new Question("Question1", new String[]{"Q1 H1", "Q1 H2", "Q1 H3"}, "answear1"),
            new Question("Question2", new String[]{"Q2 H1", "Q2 H2", "Q2 H3"}, "answear2"),
            new Question("Question3", new String[]{"Q3 H1", "Q3 H2", "Q3 H3"}, "answear3"),
            new Question("Question4", new String[]{"Q4 H1", "Q4 H2", "Q4 H3"}, "answear4"),
            new Question("Question5", new String[]{"Q5 H1", "Q5 H2", "Q5 H3"}, "answear5"),
            new Question("Question6", new String[]{"Q6 H1", "Q6 H2", "Q6 H3"}, "answear6"),
            new Question("Question7", new String[]{"Q7 H1", "Q7 H2", "Q7 H3"}, "answear7"),
        };
    
    int nextQuestionId = 0;
    
    @Override
    public Question getNextQuestion() {
        if(nextQuestionId > questions.length - 1)
            nextQuestionId = 0;
        questions[nextQuestionId].ResetQuestion();
        return questions[nextQuestionId++];
    }
}
