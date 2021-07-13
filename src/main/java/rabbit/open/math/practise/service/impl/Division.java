package rabbit.open.math.practise.service.impl;

import rabbit.open.math.practise.service.Equation;
import rabbit.open.math.practise.service.NumberEquation;
import rabbit.open.math.practise.service.Operator;

public class Division extends Multi {

    public Division(Equation eFirst, Equation eSecond) {
        first = new NumberEquation(eFirst.calc() * eSecond.calc());
        second = eFirst;
        if (eFirst instanceof NumberEquation) {
            second = eSecond;
        }
        protectEquation();
        injectParent();
    }

    public Division() {
        first = random(20, 10);
        second = random(20, 10);
        first = new NumberEquation(first.calc() * second.calc());
        injectParent();
    }

    @Override
    protected Operator getOperator() {
        return Operator.DIVISION;
    }

    @Override
    public Long calc() {
        return first.calc() / second.calc();
    }
}
