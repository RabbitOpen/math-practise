package rabbit.open.math.practise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 等式
 */
public abstract class Equation {

    protected Equation first;

    protected Equation second;

    protected boolean protectedEquation = false;

    public Equation() {
    }

    public Equation(Equation first, Equation second) {
        this.first = first;
        this.second = second;
    }

    public boolean isProtectedEquation() {
        return protectedEquation;
    }

    public void setProtectedEquation(boolean protectedEquation) {
        this.protectedEquation = protectedEquation;
    }

    protected abstract Operator getOperator();

    private NumberEquation maskedEquation;
    /**
     * 计算并返回结果
     */
    public abstract Long calc();

    public Equation mask(int index) {
        List<NumberEquation> list = getElements();
        if (index >= list.size()) {
            index = 0;
        }
        maskedEquation = list.get(index);
        maskedEquation.setMasked(true);
        return this;
    }

    public Equation randomMask() {
        return mask(new Random().nextInt(getElements().size()));
    }

    public String getMaskedValue() {
        if (null != maskedEquation) {
            return maskedEquation.calc().toString();
        }
        return null;
    }

    private List<NumberEquation> getElements() {
        List<NumberEquation> list = new ArrayList<>();
        if (first instanceof NumberEquation) {
            list.add((NumberEquation) first);
        } else {
            list.addAll(first.getElements());
        }
        if (second instanceof NumberEquation) {
            list.add((NumberEquation) second);
        } else {
            list.addAll(second.getElements());
        }
        return list;
    }

    /**
     * 输出成文本
     */
    public String writeAsText() {
        String firstText = first.getText();
        if (first.isProtectedEquation()) {
            firstText = "(" + firstText + ")";
        }
        String secondText = second.getText();
        if (second.isProtectedEquation()) {
            secondText = "(" + secondText + ")";
        }
        return firstText + getOperator().code + secondText + " = " + calc();
    }

    protected String getText() {
        if (this instanceof NumberEquation) {
            return writeAsText();
        } else {
            String firstText = first.getText();
            if (first.isProtectedEquation()) {
                firstText = "(" + firstText + ")";
            }
            String secondText = second.getText();
            if (second.isProtectedEquation()) {
                secondText = "(" + secondText + ")";
            }
            return firstText + getOperator().code + secondText;
        }
    }

    protected void makeSureFirstIsBigger() {
        if (first.calc() < second.calc()) {
            Equation tmp = first;
            first = second;
            second = tmp;
        }
    }

}
