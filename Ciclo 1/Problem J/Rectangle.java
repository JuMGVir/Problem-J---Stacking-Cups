import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @version 1.0  (15 July 2000)()
 */

public class Rectangle {
    public static int EDGES = 4;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    
    // Referencia al canvas (para evitar obtenerlo múltiples veces)
    private static Canvas canvas;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 0;
        yPosition = 0;
        color = "magenta";
        isVisible = false;
    }
    
    /**
     * Create a rectangle with specific dimensions
     * @param height Altura en píxeles
     * @param width Ancho en píxeles
     * @param x Posición X
     * @param y Posición Y
     * @param color Color del rectángulo
     */
    public Rectangle(int height, int width, int x, int y, String color) {
        this.height = height;
        this.width = width;
        this.xPosition = x;
        this.yPosition = y;
        this.color = color;
        this.isVisible = false;
    }

    /**
     * Make this rectangle visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        if (!isVisible) {
            isVisible = true;
            draw();
        }
    }
    
    /**
     * Make this rectangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        if (isVisible) {
            erase();
            isVisible = false;
        }
    }
    
    /**
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidth the new width in pixels. newWidth must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        if (newHeight >= 0 && newWidth >= 0) {
            erase();
            height = newHeight;
            width = newWidth;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param newColor the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor) {
        if (isValidColor(newColor)) {
            color = newColor;
            draw();
        }
    }
    
    /**
     * Set the position of the rectangle
     * @param x Nueva posición X
     * @param y Nueva posición Y
     */
    public void setPosition(int x, int y) {
        erase();
        this.xPosition = x;
        this.yPosition = y;
        draw();
    }
    
    /**
     * Get the X position
     * @return int Posición X
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Get the Y position
     * @return int Posición Y
     */
    public int getYPosition() {
        return yPosition;
    }
    
    /**
     * Get the height
     * @return int Altura en píxeles
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Get the width
     * @return int Ancho en píxeles
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Validate if the color is supported
     * @param color Color a validar
     * @return boolean true si el color es válido
     */
    private boolean isValidColor(String color) {
        return color.equals("red") || color.equals("yellow") || 
               color.equals("blue") || color.equals("green") ||
               color.equals("magenta") || color.equals("black") ||
               color.equals("white") || color.equals("orange") ||
               color.equals("cyan");
    }

    /*
     * Draw the rectangle with current specifications on screen.
     */
    private void draw() {
        if(isVisible) {
            if (canvas == null) {
                canvas = Canvas.getCanvas(800, 600); // Tamaño por defecto
            }
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, 
                                       width, height));
            canvas.wait(10);
        }
    }

    /*
     * Erase the rectangle on screen.
     */
    private void erase() {
        if(isVisible && canvas != null) {
            canvas.erase(this);
        }
    }
    
    /**
     * Set the canvas size (call this before making visible)
     * @param width Ancho del canvas
     * @param height Alto del canvas
     */
    public static void setCanvasSize(int width, int height) {
        canvas = Canvas.getCanvas(width, height);
    }
}
