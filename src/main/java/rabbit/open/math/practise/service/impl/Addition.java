package rabbit.open.math.practise.service.impl;

import rabbit.open.math.practise.service.NumberEquation;
import rabbit.open.math.practise.service.Equation;
import rabbit.open.math.practise.service.Operator;

import java.util.Random;

public class Addition extends Equation {

    public Addition(Equation first, Equation second) {
        super(first, second);
    }
    public Addition() {
        first = random(100, 10);
        second = random(50, 10);
    }

    protected NumberEquation random(int sed, int min) {
        return new NumberEquation(Long.valueOf(new Random().nextInt(sed) + min));
    }

    @Override
    protected Operator getOperator() {
        return Operator.ADD;
    }

    @Override
    public Long calc() {
        return first.calc() + second.calc();
    }

    @Override
    public String writeAsText() {
        return super.writeAsText();
    }
}
