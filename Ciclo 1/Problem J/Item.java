
/**
 * Write a description of class Item here.
 * 
 * @author Juan Garzon, Juan Rivera 
 * @version 3.0
 */
public abstract class Item
{
    private int number; //i value
    private int size;
    private String color;
    private boolean isVisible;
    private int xPosition;
    private int yPosition;
    
    public Item(int number){
        this.number = number;
        this.size = 2 * number - 1;
        this.color = getColorForNumber(number);
        this.isVisible = false;
        this.xPosition = 0;
        this.yPosition = 0;
    }
    
    //Getters
    public int getNumber(){return number;}
    public int getSize(){return size;}
    public String getColor(){return color;}
    public boolean getIsVisible(){return isVisible;}
    public int getXPosition(){return xPosition;}
    public int getYPosition(){return yPosition;}
    
    //Setters
    public void setPosition(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }
    public void changeColor(String newColor){
        this.color = newColor;
    }
    
    //Visibility methods
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    //Graph
    public abstract void draw();
    public abstract void erase();
    
    /**
     * name: getColorForNumber
     * use int: number (número de taza/tapa)
     * returns String: color correspondiente al número
     * Los colores están asignados para que taza y tapa del mismo número tengan el MISMO color
     */
    public String getColorForNumber(int number) {
        switch(number) {
            case 1: return "red";
            case 2: return "blue";
            case 3: return "green";
            case 4: return "yellow";
            case 5: return "magenta";
            case 6: return "orange";
            case 7: return "cyan";
            case 8: return "pink";
            case 9: return "gray";
            default: return "black";
        }
    }
}