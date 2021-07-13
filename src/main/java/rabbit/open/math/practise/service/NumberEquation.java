package rabbit.open.math.practise.service;

/**
 * 基础等式，等同long
 */
public class NumberEquation extends Equation {

    protected Long value;

    public NumberEquation(Long value) {
        this.value = value;
        setProtectedEquation(false);
    }

    @Override
    protected Operator getOperator() {
        return null;
    }

    @Override
    public Long calc() {
        return value;
    }

    @Override
    public String writeAsText() {
        return masked ? "X" :  String.valueOf(value);
    }

}
