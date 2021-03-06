package iut2.legendal_carminav_edl.modele;

public class Multiplication {

    private int operande1;
    private int operande2;
    private int reponse;

    public Multiplication(int operande1, int operande2) {
        this.operande1 = operande1;
        this.operande2 = operande2;
    }

    public int getOperande1() {
        return operande1;
    }

    public int getOperande2() {
        return operande2;
    }

    public int getResultat() {
        return getOperande1() * getOperande2();
    }

    public int getReponse() {
        return reponse;
    }

    public void setReponse(int reponse) {
        this.reponse = reponse;
    }

    public boolean isCorrect() {
        return reponse == getResultat();
    }
}