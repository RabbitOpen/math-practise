package rabbit.open.math.practise.service.impl;

import rabbit.open.math.practise.exception.NotSupportedException;
import rabbit.open.math.practise.service.Equation;
import rabbit.open.math.practise.service.NumberEquation;
import rabbit.open.math.practise.service.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 * 演进等式
 */
public class DeriveEquation extends Equation {

    protected Equation left;
    protected Equation right;

    public DeriveEquation(Equation left, Equation right) {
        this.left = left;
        this.right = right;
    }

    @Override
    protected Operator getOperator() {
        return null;
    }

    @Override
    public Long calc() {
        throw new NotSupportedException();
    }

    @Override
    public DeriveEquation derive() {
        return this;
    }

    public List<String> getDeriveEquationTextList() {
        List<String> stack = new ArrayList<>();
        stack.add(writeAsText());
        if (!(right instanceof NumberEquation)) {
            stack.add(this.left.getText() + " = " + this.right.calc());
        }
        if (!(this.left instanceof NumberEquation)) {
            DeriveEquation derive = this.left.derive();
            stack.addAll(derive.getDeriveEquationTextList());
        }
        return stack;
    }

    @Override
    public String writeAsText() {
        return left.getText() + " = " + right.getText();
    }
}
