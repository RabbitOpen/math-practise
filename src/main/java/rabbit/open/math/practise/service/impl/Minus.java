package rabbit.open.math.practise.service.impl;

import rabbit.open.math.practise.service.Equation;
import rabbit.open.math.practise.service.Operator;

public class Minus extends Addition {

    public Minus(Equation eFirst, Equation eSecond) {
        super(eFirst, eSecond);
        makeSureFirstIsBigger();
        if (second instanceof Addition || second instanceof Minus) {
            second.setProtectedEquation(true);
        }
        injectParent();
    }

    public Minus() {
        first = random(100, 20);
        second = random(100, 20);
        makeSureFirstIsBigger();
        injectParent();
    }

    @Override
    protected Operator getOperator() {
        return Operator.MINUS;
    }

    @Override
    public Long calc() {
        return first.calc() - second.calc();
    }
}
