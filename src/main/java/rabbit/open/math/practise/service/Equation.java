package rabbit.open.math.practise.service;

import rabbit.open.math.practise.exception.DeriveException;
import rabbit.open.math.practise.exception.NotSupportedException;
import rabbit.open.math.practise.service.impl.DeriveEquation;
import rabbit.open.math.practise.service.impl.Minus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 等式
 */
public abstract class Equation {

    // 等式的第一个参数
    protected Equation first;

    // 等式的第二个参数
    protected Equation second;

    // 等式的结果
    protected Equation result;

    // 父级等式
    public Equation parent = null;

    protected boolean protectedEquation = false;

    // 设置为X
    protected boolean masked = false;

    public Equation() { }

    public Equation(Equation first, Equation second) {
        this.first = first;
        this.second = second;
        injectParent();
    }

    protected void injectParent() {
        this.second.parent = this;
        this.first.parent = this;
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

    public String getText() {
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

    /**
     * 演进转换
     * @return
     */
    public DeriveEquation derive() {
        if (!isMasked()) {
            throw new DeriveException("equation should be masked before do this operation");
        }
        return doDerive();
    }

    protected DeriveEquation doDerive() {
        throw new NotSupportedException();
    }

    protected Equation getResult() {
        return new NumberEquation(calc());
    }

    public void setResult(Equation result) {
        this.result = result;
    }

    public void setMasked(boolean masked) {
        this.masked = masked;
        if (null != parent) {
            parent.setMasked(masked);
        }
    }

    public boolean isMasked() {
        return masked;
    }
}
