package Lab6.src.main.java.ro.ulbs.proiectaresoftware.lab6.advanced;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DoubleCalculatorTest {

    private DoubleCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new DoubleCalculator();
        calculator.init();
    }

    @AfterEach
    public void tearDown() {
        calculator = null;
    }

    @Test
    public void testAddPositive() {
        // Arrange
        Double expected = 15.5;
        // Act
        calculator.add(10.0).add(5.5);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testMultiplyBy0() {
        // Arrange
        Double expected = 0.0;
        // Act
        calculator.add(25.5).multiply(0.0);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testDividePositives() {
        // Arrange
        Double expected = 2.5;
        // Act
        calculator.add(5.0).divide(2.0);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testDivideBy0() {
        // Arrange
        calculator.add(10.0);
        // Act
        calculator.divide(0.0);
        // Assert
        Assertions.assertEquals(Double.POSITIVE_INFINITY, calculator.result());
    }
}