package rabbit.open.math.test;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import rabbit.open.math.practise.service.Equation;
import rabbit.open.math.practise.service.NumberEquation;
import rabbit.open.math.practise.service.impl.Addition;
import rabbit.open.math.practise.service.impl.Division;
import rabbit.open.math.practise.service.impl.Minus;
import rabbit.open.math.practise.service.impl.Multi;

@RunWith(JUnit4.class)
public class EquationTest {

    @Test
    public void equationTest() {

        // 加法
        TestCase.assertEquals("23 + 22 = 45", new Addition(new NumberEquation(23L), new NumberEquation(22L)).writeAsText());
        TestCase.assertEquals("22 + 23 = 45", new Addition(new NumberEquation(22L), new NumberEquation(23L)).writeAsText());
        TestCase.assertEquals("60 - 22 + 23 = 61", new Addition(new Minus(new NumberEquation(22L), new NumberEquation(60L)), new NumberEquation(23L)).writeAsText());
        TestCase.assertEquals("23 + 60 - 22 = 61", new Addition(new NumberEquation(23L), new Minus(new NumberEquation(22L), new NumberEquation(60L))).writeAsText());

        Addition addition = new Addition(new NumberEquation(23L), new Minus(new NumberEquation(22L), new NumberEquation(60L)));
        TestCase.assertEquals("23 + X - 22 = 61", addition.mask(1).writeAsText());
        TestCase.assertEquals("60", addition.getMaskedValue());
        addition = new Addition(new NumberEquation(23L), new Minus(new NumberEquation(22L), new NumberEquation(60L)));
        TestCase.assertEquals("23 + 60 - X = 61", addition.mask(2).writeAsText());
        TestCase.assertEquals("22", addition.getMaskedValue());
        addition = new Addition(new NumberEquation(23L), new Minus(new NumberEquation(22L), new NumberEquation(60L)));
        TestCase.assertEquals("X + 60 - 22 = 61", addition.mask(3).writeAsText());
        TestCase.assertEquals("23", addition.getMaskedValue());

        //减法
        TestCase.assertEquals("100 - 23 = 77", new Minus(new NumberEquation(100L), new NumberEquation(23L)).writeAsText());
        TestCase.assertEquals("100 - 23 = 77", new Minus(new NumberEquation(23L), new NumberEquation(100L)).writeAsText());
        TestCase.assertEquals("88 - (12 + 13) = 63", new Minus(new NumberEquation(88L), new Addition(new NumberEquation(12L), new NumberEquation(13L))).writeAsText());
        TestCase.assertEquals("88 - (13 - 12) = 87", new Minus(new NumberEquation(88L), new Minus(new NumberEquation(12L), new NumberEquation(13L))).writeAsText());

        //乘法
        TestCase.assertEquals("30 * 22 = 660", new Multi(new NumberEquation(22L), new NumberEquation(30L)).writeAsText());
        TestCase.assertEquals("30 * X = 660", new Multi(new NumberEquation(22L), new NumberEquation(30L)).mask(1).writeAsText());
        TestCase.assertEquals("22 * (34 - 15) = 418", new Multi(new Minus(new NumberEquation(15L), new NumberEquation(34L)), new NumberEquation(22L)).writeAsText());
        TestCase.assertEquals("22 * (34 - X) = 418", new Multi(new Minus(new NumberEquation(15L), new NumberEquation(34L)), new NumberEquation(22L))
                .mask(2).writeAsText());
        Equation mask1 = new Multi(new Minus(new NumberEquation(15L), new NumberEquation(34L)), new NumberEquation(22L)).mask(2);
        TestCase.assertEquals("15", mask1.getMaskedValue());
        TestCase.assertEquals("(22 + 18) * (34 - 15) = 760", new Multi(new Minus(new NumberEquation(15L), new NumberEquation(34L)),
                    new Addition(new NumberEquation(22L), new NumberEquation(18L))).writeAsText());

        // 除法
        TestCase.assertEquals("200 ÷ 20 = 10", new Division(new NumberEquation(10L), new NumberEquation(20L)).writeAsText());
        TestCase.assertEquals("220 ÷ (3 + 8) = 20", new Division(new Addition(new NumberEquation(3L), new NumberEquation(8L)), new NumberEquation(20L)).writeAsText());
        TestCase.assertEquals("100 ÷ (8 - 3) = 20", new Division(new NumberEquation(20L), new Minus(new NumberEquation(3L), new NumberEquation(8L))).writeAsText());
        Division div1 = new Division(new NumberEquation(20L), new Minus(new NumberEquation(3L), new NumberEquation(8L)));
        Division div2 = new Division(div1, new NumberEquation(5L));
        TestCase.assertEquals("100 ÷ (100 ÷ (8 - 3)) = 5", div2.writeAsText());
        div2.mask(2);
        TestCase.assertEquals("100 ÷ (100 ÷ (X - 3)) = 5", div2.writeAsText());
        TestCase.assertEquals("8", div2.getMaskedValue());
    }

    @Test
    public void random() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Minus(new Minus(), new Addition()).writeAsText());
            System.out.println(new Addition(new Minus(), new Addition()).writeAsText());
        }
    }

    @Test
    public void reverseTest() {
        Multi multi = new Multi(new Minus(new NumberEquation(15L), new NumberEquation(34L)), new NumberEquation(22L));
        TestCase.assertEquals("22 * (34 - 15) = 418", multi.writeAsText());
        multi.mask(1);
        TestCase.assertEquals("22 * (X - 15) = 418", multi.writeAsText());

    }
}
