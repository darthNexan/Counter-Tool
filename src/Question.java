

/**
 * Created by Group4 on 2/25/17.
 *
 * Used to represent a question in memory
 * @author Group4
 */
public class Question {

    private String question;
    private String[] choice;


    private char soln;
    private String resource;




    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Question(){
        choice = new String[4];
    }


    public char getSoln() {
        return soln;
    }

    public void setSoln(char soln) {
        if(soln == 'a' || soln == 'b' || soln == 'c' || soln == 'd') {
            this.soln = soln;
        }
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice0() {
        return choice[0];
    }

    public void setChoice0(String choice0) {
        this.choice[0] = choice0;
    }

    public String getChoice1() {
        return choice[1];
    }

    public void setChoice1(String choice1) {
        this.choice[1] = choice1;
    }

    public String getChoice2() {
        return choice[2];
    }

    public void setChoice2(String choice2) {
        this.choice[2] = choice2;
    }

    public String getChoice3() {
        return choice[3];
    }

    public void setChoice3(String choice3) {
        this.choice[3] = choice3;
    }


    /**
     * Tests out the user response
     * @param solution the user's response.
     * @return
     */
    public boolean isCorrect(char solution){

        return solution == this.soln;
    }

    /**
     * Displays the correct answer.
     * @return
     */
    public String displaySolution(){

        return (soln == 'a')? choice[0] :(soln =='b')? choice[1]: (soln == 'c')? choice[2] : choice[3];
    }




    /**
     * To make my life kinda easier
     * @return array with choices
     */
    public String[] getChoice(){

        String[] arrayToReturn = new String[choice.length];
        System.arraycopy(choice,0,arrayToReturn,0,choice.length);

        return arrayToReturn;
    }

}
