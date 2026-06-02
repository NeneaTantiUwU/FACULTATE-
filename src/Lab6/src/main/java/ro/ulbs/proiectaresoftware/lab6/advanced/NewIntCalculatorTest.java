package Lab6.src.main.java.ro.ulbs.proiectaresoftware.lab6.advanced;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class NewIntCalculatorTest {

    private NewIntCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new NewIntCalculator();
        calculator.init();
    }

    @AfterEach
    public void tearDown() {
        calculator = null;
    }

    @Test
    public void testAddPositive() {
        // Arrange
        Integer expected = 15;
        // Act
        calculator.add(10).add(5);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testAddNegatives() {
        // Arrange
        Integer expected = -15;
        // Act
        calculator.add(-10).add(-5);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testSubtractPositives() {
        // Arrange
        Integer expected = 5;
        // Act
        calculator.add(10).subtract(5);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testMultiplyNegatives() {
        // Arrange
        Integer expected = 50;
        // Act
        calculator.add(-10).multiply(-5);
        // Assert
        Assertions.assertEquals(expected, calculator.result());
    }

    @Test
    public void testDivideBy0() {
        // Arrange
        calculator.add(10);
        // Act & Assert
        // Verificăm dacă împărțirea la 0 aruncă excepția potrivită
        Assertions.assertThrows(ArithmeticException.class, () -> {
            calculator.divide(0);
        });
    }
}