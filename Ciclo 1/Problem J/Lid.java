/**
 * Write a description of class Lid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;

public class Lid {
    private int number;        // Número de la tapa (i)
    private int size;          // Tamaño en cm (ancho = 2i-1)
    private String color;      // Color de la tapa
    private boolean isVisible;
    private int xPosition;     // Posición X central
    private int yPosition;     // Posición Y del borde superior
    
    // La tapa es un solo rectángulo horizontal
    private Rectangle lid;
    
    /**
     * Constructor for objects of class Lid
     * @param number Número de la tapa (i)
     * @param color Color de la tapa
     */
    public Lid(int number, String color) {
        this.number = number;
        this.size = 2 * number - 1; // Ancho de la tapa en cm
        this.color = color;
        this.isVisible = false;
        this.xPosition = 0;
        this.yPosition = 0;
        
        // Crear el rectángulo de la tapa
        lid = new Rectangle();
        lid.changeColor(color);
    }
    
    /**
     * Get the lid number
     * @return int Número de la tapa
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Get the lid size (width) in cm
     * @return int Ancho en cm
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Set the position of the lid
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
     * Make the lid visible
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Make the lid invisible
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    /**
     * Draw the lid on the canvas
     */
    private void draw() {
        if (isVisible) {
            int pixelWidth = size * 30; // Ancho en píxeles
            int halfWidth = pixelWidth / 2;
            
            // La tapa es un rectángulo de 30px de alto (1cm)
            lid.changeColor(this.color);
            lid.changeSize(30, pixelWidth);
            lid.setPosition(xPosition - halfWidth, yPosition);
            lid.makeVisible();
            
            System.out.println("Lid #" + number + " dibujada en Y=" + yPosition);
        }
    }
    
    /**
     * Erase the lid from the canvas
     */
    private void erase() {
        if (isVisible) {
            lid.makeInvisible();
        }
    }
    
    /**
     * Change the lid's color
     * @param newColor Nuevo color
     */
    public void changeColor(String newColor) {
        this.color = newColor;
        lid.changeColor(newColor);
    }
    
    /**
     * Get the current Y position
     */
    public int getYPosition() {
        return yPosition;
    }
}