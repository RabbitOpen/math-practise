package rabbit.open.math.practise.service.impl;

import rabbit.open.math.practise.service.Equation;
import rabbit.open.math.practise.service.Operator;

public class Multi extends Addition {

    public Multi(Equation first, Equation second) {
        super(first, second);
        makeSureFirstIsBigger();
        protectEquation();
        injectParent();
    }

    protected void protectEquation() {
        if (first instanceof Minus || first instanceof Addition) {
            first.setProtectedEquation(true);
        }
        if (second instanceof Minus || second instanceof Addition) {
            second.setProtectedEquation(true);
        }
    }

    public Multi() {
        first = random(60, 5);
        second = random(50, 10);
        injectParent();
    }

    @Override
    protected Operator getOperator() {
        return Operator.MULTI;
    }

    @Override
    public Long calc() {
        return first.calc() * second.calc();
    }

    @Override
    public DeriveEquation doDerive() {
        if (first.isMasked()) {
            return new DeriveEquation(first, new Division(getResult(), second, false));
        } else {
            return new DeriveEquation(second, new Division(getResult(), first, false));
        }
    }
}
