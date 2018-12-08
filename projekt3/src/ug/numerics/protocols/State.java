package ug.numerics.protocols;

public class State {

    private int yes = 0;
    private int no = 0;

    public State() {}

    public State(int yes, int no) {
        this.yes = yes;
        this.no = no;
    }

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}
