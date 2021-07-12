package rabbit.open.math.practise.service;

public enum Operator {

    DIVISION(" ÷ "),
    MULTI(" * "),
    MINUS(" - "),
    ADD(" + ");

    String code;
    Operator(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }


}
