/**
 * Write a description of class Cup here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.*;

public class Cup {
    private int number;        // Número de la taza (i)
    private int size;          // Tamaño en cm (2i-1)
    private String color;      // Color de la taza
    private boolean isVisible;
    private int xPosition;     // Posición X central
    private int yPosition;     // Posición Y del borde superior
    // Rectángulos que componen la taza (exactamente como en la imagen)
    private Rectangle leftSide;   // Lado izquierdo (vertical)
    private Rectangle base;       // Base (horizontal abajo)
    private Rectangle rightSide;  // Lado derecho (vertical)
    
    /**
     * Constructor for objects of class Cup
     * @param number Número de la taza (i)
     * @param color Color de la taza
     */
    public Cup(int number, String color) {
        this.number = number;
        this.size = 2 * number - 1; // Calcular tamaño en cm
        this.color = color;
        this.isVisible = false;
        this.xPosition = 0;
        this.yPosition = 0;
        
        // Crear los tres rectángulos que forman la taza
        leftSide = new Rectangle();
        base = new Rectangle();
        rightSide = new Rectangle();
        
        // Configurar colores
        leftSide.changeColor(color);
        base.changeColor(color);
        rightSide.changeColor(color);
    }
    /**
     * Get the cup color
     * @return int color de la taza
     */
    public String getColor(){
        return color;
    }
    /**
     * Get the cup number
     * @return int Número de la taza
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Get the cup size in cm
     * @return int Tamaño en cm
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Set the position of the cup
     * @param x Posición X central
     * @param y Posición Y del borde superior
     */
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        if (isVisible) {
        draw();}
    }
    
    /**
     * Make the cup visible
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Make the cup invisible
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    /**
     * Draw the cup on the canvas - lado izquierdo, lado derecho y base
     */
    private void draw() {
        if (isVisible) {
            int pixelSize = size * 30; // Alto total en píxeles
            int halfWidth = pixelSize / 2;
            int sideWidth = 15; // Ancho de los lados (un poco más gruesos para que se vean)
            int baseHeight = 30; // Altura de la base (1cm)
            
            // LADO IZQUIERDO - rectángulo vertical del lado izquierdo
            leftSide.changeSize(pixelSize, sideWidth);
            leftSide.setPosition(xPosition - halfWidth, yPosition);
            leftSide.makeVisible();
            
            // LADO DERECHO - rectángulo vertical del lado derecho
            rightSide.changeSize(pixelSize, sideWidth);
            rightSide.setPosition(xPosition + halfWidth - sideWidth, yPosition);
            rightSide.makeVisible();
            
            // BASE - rectángulo horizontal en la parte inferior
            base.changeSize(baseHeight, pixelSize);
            base.setPosition(xPosition - halfWidth, yPosition + pixelSize - baseHeight);
            base.makeVisible();
            
            System.out.println("Cup #" + number + " dibujada en Y=" + yPosition);
        }
    }
    
    /**
     * Erase the cup from the canvas
     */
    private void erase() {
        if (isVisible) {
            leftSide.makeInvisible();
            base.makeInvisible();
            rightSide.makeInvisible();
        }
    }
    
    /**
     * Change the cup's color
     * @param newColor Nuevo color
     */
    public void changeColor(String newColor) {
        this.color = newColor;
        leftSide.changeColor(newColor);
        base.changeColor(newColor);
        rightSide.changeColor(newColor);
    }
    
    /**
     * Get the current Y position
     */
    public int getYPosition() {
        return yPosition;
    }
}