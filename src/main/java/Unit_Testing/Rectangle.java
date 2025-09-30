package Unit_Testing;

public class Rectangle {
    private double width;
    private double height;
    private double angle;

    public Rectangle() {
        this.width = 0.0;
        this.height = 0.0;
        this.angle = 90.0;
    }

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
        this.angle = 90.0;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getAngle() {
        return angle;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setAngle(double angle) {
        if (angle == 90.0) {
            this.angle = angle;
        } else {
            throw new IllegalArgumentException("Rectangle must have a right angle (90 degrees).");
        }
    }

    public double calculateArea() {
        return width * height;
    }

    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    public double getDiagonal() {
        return Math.sqrt(width * width + height * height);
    }
}
