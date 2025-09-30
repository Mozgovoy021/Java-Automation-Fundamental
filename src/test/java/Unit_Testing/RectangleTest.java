package Unit_Testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    @DisplayName("Test area")
    void testArea() {
        Rectangle r = new Rectangle(3, 4);
        assertEquals(12.0, r.calculateArea(), 1e-9);
    }

    @Test
    @DisplayName("Test perimeter")
    void testPerimeter() {
        Rectangle r = new Rectangle(3, 4);
        assertEquals(14.0, r.calculatePerimeter(), 1e-9);
    }

    @Test
    @DisplayName("Test diagonal")
    void testDiagonal() {
        Rectangle r = new Rectangle(3, 4);
        assertEquals(5.0, r.getDiagonal(), 1e-9);
    }

    @Test
    @DisplayName("Test angle")
    void testAngle() {
        Rectangle r = new Rectangle(3, 4);
        assertEquals(90.0, r.getAngle(), 1e-9);
    }

    @Test
    @DisplayName("Set invalid angle throws exception")
    void testInvalidAngle() {
        Rectangle r = new Rectangle(3, 4);
        assertThrows(IllegalArgumentException.class, () -> r.setAngle(45.0));
    }

    @Test
    @DisplayName("Set negative width")
    void testNegativeWidth() {
        Rectangle r = new Rectangle();
        r.setWidth(-5.0);
        assertEquals(-5.0, r.getWidth(), 1e-9);
    }

    @Test
    @DisplayName("Set negative height")
    void testNegativeHeight() {
        Rectangle r = new Rectangle();
        r.setHeight(-10.0);
        assertEquals(-10.0, r.getHeight(), 1e-9);
    }
}