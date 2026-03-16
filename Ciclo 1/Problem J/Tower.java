import java.util.*;
import javax.swing.JOptionPane;

/**
 * Graphic simulator for Problem J - Stacking Cups
 * 
 * @author Juan Garzon, Juan Rivera 
 * @version 3.0
 */

public class Tower
{
    //Conversion 1cm = 30px
    private int width;
    private int maxHeight;
    private boolean isVisible;
    private boolean lastOperationSuccessful;
    
    private List<Item> items = new ArrayList<>();
    private Canvas canvas;
    
    /**
     * name: Tower
     * use int: width (width of the tower and canvas); int maxHeight (height of the tower and canvas)
     * starts a new instance of the class and creates the canvas
     * returns nothing
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        this.isVisible = false;
        this.canvas = Canvas.getCanvas(this.width, this.maxHeight);
        this.canvas.setVisible(false);
        this.lastOperationSuccessful = true;
    }
    
    //Manage Cup
    
    /**
     * name: pushCup
     * use int : i (given cup identifier)
     * adds a "cup" on the items list (with type "cup" and real size) and cups list (only size)
     * returns nothing
     */
    public void pushCup(int i){
        //Checks if the cup alredy exists
        for(Item cups: items){
            if (cups instanceof Cup && cups.getNumber() == i){
                JOptionPane.showMessageDialog(null, "La taza "+i+" ya existe.");
                this.lastOperationSuccessful = false;
                return;
            }
        }
        //Creates the new cup
        Cup newCup = new Cup(i);
        this.items.add(newCup);
        if(this.isVisible){
            reDrawAll();
        }
        this.lastOperationSuccessful = true;
        return;
    }
    
    /**
     * name: popCup
     * use nothing
     * removes the last cup on cups and items lists
     * returns nothign
     */
    public void popCup(){
        //Checks if items is empty
        if (items.isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay tazas para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            this.lastOperationSuccessful = false;
            return;
        }
        
        //Search the last cup
        for (int i = items.size() - 1; i>=0 ; i--){
            if (items.get(i) instanceof Cup){
                Cup removed = (Cup) items.get(i);
                removed.makeInvisible();
                items.remove(i);
                break;
            }
        }
        
        if(this.isVisible){
            reDrawAll();
        }
        this.lastOperationSuccessful = true;
        return;
    }
    
    
    /**
     * name: removeCup
     * use int : i (given cup identifier to remove)
     * removes the cup with given "id"
     * returns nothing
     */
    public void removeCup(int i){
        //Search the specific Cup
        for (int j = 0; j<items.size(); j++){
            if (items.get(j) instanceof Cup && items.get(j).getNumber() == i){
                Cup removed = (Cup) items.get(j);
                removed.makeInvisible();
                items.remove(j);
                break;
            }   
        }
        if (this.isVisible) {reDrawAll();}
        this.lastOperationSuccessful = true;
        return;
    }
    
    
    //Manage Lid
    /**
     * name: pushLid
     * use int : i (given lid identifier)
     * adds a Lid to items and LidInstances
     * returns nothing
     */
    public void pushLid(int i){
        //Checks if the cup alredy exists
        for(Item lids: items){
            if (lids instanceof Lid && lids.getNumber() == i){
                JOptionPane.showMessageDialog(null, "La taza "+i+" ya existe.");
                this.lastOperationSuccessful = false;
                return;
            }
        }
        
        //Creates the new lid
        Lid newLid = new Lid(i);
        this.items.add(newLid);
        if(this.isVisible){
            reDrawAll();
        }
        this.lastOperationSuccessful = true;
        return;
    }
    
    /**
     * name: popLid
     * use nothing
     * Removes the last lid on lids and items lists
     * returns nothing
     */
    public void popLid(){
        //Checks if items is empty
        if (items.isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay tazas para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            this.lastOperationSuccessful = false;
            return;
        }
        
        //Search the last lid
        for (int i = items.size() - 1; i>=0 ; i--){
            if (items.get(i) instanceof Lid){
                Lid removed = (Lid) items.get(i);
                removed.makeInvisible();
                items.remove(i);
                break;
            }
        }
        if(this.isVisible){reDrawAll();}
        this.lastOperationSuccessful = true;
        return;
    }
    
    /**
     * name: removeLid
     * use int : i (given lid identifier to remove)
     * removes the specific lid on lids and items lists
     * returns nothing
     */
    public void removeLid(int i){
        //Checks if items is empty
        if (items.isEmpty()){
            JOptionPane.showMessageDialog(null, "No hay tazas para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Search the specific Lid
        for (int j = 0; j<items.size(); j++){
            if (items.get(j) instanceof Lid && items.get(j).getNumber() == i){
                Lid removed = (Lid) items.get(j);
                removed.makeInvisible();
                items.remove(j);
                break;
            }   
        }
        if (this.isVisible) {reDrawAll();}
        return;
    }
    
    //Reorganize Tower
    /**
     * name: orderTower
     * use nothing
     * Order tower from largest to smallest (mayor a menor)
     * returns nothing
     */
    public void orderTower() {
        //Ordenar items (mezcla de Cups y Lids)
        
        items.sort((a, b) ->{
            // Primero por number DESCENDENTE
            int cmpNumber = Integer.compare(b.getNumber(), a.getNumber());
            if (cmpNumber != 0) return cmpNumber;
    
            // Si mismo number, Cups van antes que Lids
            if (a instanceof Cup && b instanceof Lid) return -1;
            if (a instanceof Lid && b instanceof Cup) return 1;
    
            return 0; // mismo tipo y mismo number
        });
        
        //Redibujar si es visible
        if (isVisible) {
            reDrawAll();
        }
        return;
    }
    
    /**
     * name: reverseTower
     * use nothing
     * Order tower from smallest to largest (menor a mayor)
     * returns nothing
     */
    public void reverseTower() {
        //Ordenar items (mezcla de Cups y Lids)
        items.sort((a, b) -> {
            // Primero por number ASCENDENTE
            int cmpNumber = Integer.compare(a.getNumber(), b.getNumber());
            if (cmpNumber != 0) return cmpNumber;
    
            // Si mismo number, Cups van antes que Lids
            if (a instanceof Cup && b instanceof Lid) return -1;
            if (a instanceof Lid && b instanceof Cup) return 1;
    
            return 0; // mismo tipo y mismo number
        });
        
        //Redibujar si es visible
        if (isVisible) {
            reDrawAll();
        }
        return;
    }
    
    //Consult Information
    /**
     * name: height
     * use nothing
     * Calculate and give the tower's height
     * returns the total height of the tower
     */
    public int height() {
        if (items.isEmpty()) return 0;

        int totalHeight = 0;
        int currentTopLevel = 0;

        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            int size = item.getSize();
            if (item instanceof Cup){
                if (i == 0)
                {
                    //Primera taza en la base
                    totalHeight = size;
                    currentTopLevel = size;
                }
                else
                {
                    Item prevItem = items.get(i-1);
                    int prevSize = prevItem.getSize();
                    if (prevItem instanceof Cup)
                    {
                        if(size < prevSize){
                            //Taza mas pequeña se anida dentor de la anterior, No afecta la altura total
                        }else{
                            totalHeight += (size-prevSize);
                            currentTopLevel = totalHeight;
                        }
                    }else{
                        if(size > prevSize){
                            totalHeight += (size-1);
                            currentTopLevel = totalHeight;
                        }
                        else{
                            //Taza mas pequeña o igual que la tapa se anida
                        }
                    }
                }

            }else{
                if(i==0){
                    totalHeight = 1;
                    currentTopLevel = 1;
                }else{
                    Item prevItem = items.get(i-1);
                    int prevSize = prevItem.getSize();

                    if(prevItem instanceof Cup){
                        if(size == prevSize){
                            totalHeight += 1;
                            currentTopLevel = totalHeight;
                        }else if (size<prevSize){
                            //Taza mas pequeña - se anida dentro de la taza
                        }else{
                            totalHeight +=1;
                            currentTopLevel = totalHeight;
                        }
                    }else{
                        if(size <= prevSize){
                            //tapa igual o mas pequeña - se anida
                        }else{
                            totalHeight += 1;
                            currentTopLevel = totalHeight;
                        }
                    }
                }
            }

        }
        return totalHeight;
    }
    
    /**
     * name: lidedcups
     * use: nothing
     * The numbers of "closed" cups.
     * returns lidedCupslist 
     */
    public int[] lidedCups() {
        if(items.isEmpty()){
            return null;
        }
        List<Integer> lidedCupsList = new ArrayList<>();
        int idx1 = 0;
        int idx2 = 0;
        int currentBig = 0;
        while (idx1 < items.size()){
            if(items.get(idx1) instanceof Cup){
                idx2 = idx1+1;
                while (idx2<items.size() && currentBig < items.get(idx1).getNumber()){
                    if(items.get(idx2).getNumber() == items.get(idx1).getNumber() && items.get(idx2) instanceof Lid){
                        lidedCupsList.add(items.get(idx2).getNumber());
                        System.out.println("entra");
                        break;
                    }
                    else{
                        currentBig = items.get(idx2).getNumber();
                        idx2+=1;
                    }
                    System.out.println("idx2: " + idx2);
                }
            }
            idx1+=1;
        }
        Collections.sort(lidedCupsList);

        int[] result = new int[lidedCupsList.size()];
        for (int i = 0; i < lidedCupsList.size(); i++){
            result[i] = lidedCupsList.get(i);
        }
        return result;
    }
    
    /**
     * name: stackingItems
     * use: nothing
     * Shows the items from bottom to top.
     * returns String[][]
     */
    public String[][] stackingItems(){
        String[][] result = new String[items.size()][2];
        for(int i = 0; i < items.size(); i++){
            if(items.get(i) instanceof Cup){
                result[i][0] = "cup";
                result[i][1] = Integer.toString(items.get(i).getNumber());
            }
            else{
                result[i][0] = "lid";
                result[i][1] = Integer.toString(items.get(i).getNumber());
            }
            
        }
        return result;
    }
    
    //set Visibility
    public void makeVisible() {
        // Calcular altura actual de la torre
        int towerHeight = height();
        // Verificar si cabe en el canvas
        if (towerHeight*30 <= maxHeight) {
            isVisible = true;
            // Redibujar todo
            reDrawAll();  
        } 
    }
    
    /**
     * name: makeInvisible
     * use nothing
     * Hace invisible el simulador y oculta todos los elementos
     * returns nothing
     */
    public void makeInvisible() {
        if (isVisible) {
            // Ocultar todos los elementos gráficos
            for(Item item: items){
                item.makeInvisible();
            }
            // Limpiar el canvas
            Canvas canvas = Canvas.getCanvas(this.width, this.maxHeight);
            canvas.erase(this);
            
            // Actualizar estado
            this.isVisible = false;
            
            // Mensaje de confirmación
            JOptionPane.showMessageDialog(null, 
                "🔍 Simulador ocultado\n" +
                "Los datos de la torre se mantienen",
                "Simulador",
                JOptionPane.INFORMATION_MESSAGE);
            
            System.out.println("makeInvisible: Simulador ocultado");
            
        }
    }
    
    /**
     * name: redrawAll
     * use nothing
     * Draw method
     */
    private void reDrawAll() {
        if(!isVisible){return;}

        clearCanvas();
        int centerX = width /2;
        int[] yPositions = calculateAllYPositions();

        //Dibujar
        for (int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            item.setPosition(centerX, yPositions[i]);
            item.makeVisible();
        }

        drawHeightRuler();
        return;
    }
    
    /**
     * name: calculateAllYPositions
     * use nothing
     * Calcula la posición Y para cada elemento (borde superior)
     */
    private int[] calculateAllYPositions() {
        if (items.isEmpty()) {
            return new int[0];
        }
    
        int[] yPositions = new int[items.size()];
        int rulerMargin = 10; // Margen de la regla
        int baseY = maxHeight - 120 - rulerMargin; // Ajuste por el margen
        
        // Primero calculamos las alturas acumuladas como en height()
        int[] accumulatedHeights = new int[items.size()];
        
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int size = item.getSize();
            
            if (i == 0) {
                // Primer elemento en la base
                accumulatedHeights[i] = (item instanceof Cup) ? size : 1;
            } else {
                Item prevItem = items.get(i - 1);
                int prevSize = prevItem.getSize();
                int prevAccHeight = accumulatedHeights[i - 1];
                
                if (item instanceof Cup) {
                    if (prevItem instanceof Cup) {
                        if (size < prevSize) {
                            // Taza más pequeña se anida dentro de la anterior
                            // No afecta la altura acumulada
                            accumulatedHeights[i] = prevAccHeight;
                        } else {
                            // Taza más grande, añade la diferencia
                            accumulatedHeights[i] = prevAccHeight + (size - prevSize);
                        }
                    } else { // prevItem es Lid
                        if (size > prevSize) {
                            accumulatedHeights[i] = prevAccHeight + (size - 1);
                        } else {
                            // Taza más pequeña o igual que la tapa se anida
                            accumulatedHeights[i] = prevAccHeight;
                        }
                    }
                } else { // item es Lid
                    if (prevItem instanceof Cup) {
                        if (size == prevSize) {
                            // Tapa del mismo tamaño que la taza, va encima
                            accumulatedHeights[i] = prevAccHeight + 1;
                        } else if (size < prevSize) {
                            // Tapa más pequeña - se anida dentro de la taza
                            accumulatedHeights[i] = prevAccHeight;
                        } else {
                            // Tapa más grande - va encima
                            accumulatedHeights[i] = prevAccHeight + 1;
                        }
                    } else { // prevItem es Lid
                        if (size <= prevSize) {
                            // Tapa igual o más pequeña - se anida
                            accumulatedHeights[i] = prevAccHeight;
                        } else {
                            // Tapa más grande - va encima
                            accumulatedHeights[i] = prevAccHeight + 1;
                        }
                    }
                }
            }
        }
        
        // Encontrar la altura máxima acumulada para centrar verticalmente
        int maxAccumulatedHeight = 0;
        for (int h : accumulatedHeights) {
            maxAccumulatedHeight = Math.max(maxAccumulatedHeight, h);
        }
        
        // Ajustar baseY para centrar la torre verticalmente
        // La torre completa ocupará maxAccumulatedHeight * 30 píxeles
        int towerHeightPixels = maxAccumulatedHeight * 30;
        baseY = baseY - (towerHeightPixels / 2); // Centrar verticalmente
        
        // Ahora convertimos alturas acumuladas a posiciones Y
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int size = item.getSize();
            int accumulatedHeight = accumulatedHeights[i];
            
            // La posición Y del fondo del elemento
            int bottomY = baseY - (accumulatedHeight * 30);
            
            // Para dibujar, queremos la posición Y de la PARTE SUPERIOR del elemento
            yPositions[i] = bottomY - (size * 30);
        }
        
        return yPositions;
    }
        
    /**
     * name: clearCanvas
     * use nothing
     * Oculta todos los elementos gráficos del canvas
     * returns nothing
     */
    private void clearCanvas() {
        // Ocultar todas las tazas
        for (Item item :items){
            item.makeInvisible();
        }
        
        // Limpiar el canvas completamente
        Canvas canvas = Canvas.getCanvas(this.width, this.maxHeight);
        canvas.erase(this); // Esto limpia todo el canvas
    }
    
    /**
     * name: drawHeightRuler
     * use nothing
     * Dibuja una regla en el costado para medir la altura
     * returns nothing
     */
    private void drawHeightRuler() {
        if (!isVisible) return;
        
        Canvas canvas = Canvas.getCanvas(this.width, this.maxHeight);
        
        // Dibujar línea vertical principal
        Rectangle verticalLine = new Rectangle();
        verticalLine.changeColor("black");
        verticalLine.changeSize(maxHeight - 20, 2); // Alto, ancho
        verticalLine.setPosition(30, 10); // Posición X, Y
        verticalLine.makeVisible();
        
        // Dibujar marcas cada 1 cm (30 píxeles)
        for (int cm = 0; cm <= maxHeight; cm++) {
            int y = maxHeight - (cm * 30) - 10; // -10 por margen inferior
            
            // Marca más larga cada 5 cm
            int markWidth = (cm % 5 == 0) ? 15 : 8;
            
            Rectangle mark = new Rectangle();
            mark.changeColor("black");
            mark.changeSize(2, markWidth); // Alto 2px, ancho variable
            mark.setPosition(35 - markWidth, y);
            mark.makeVisible();
        }
        
        System.out.println("Regla de altura dibujada");
    }
    
    /**
     * name: ok
     * use: nothing
     * Checks if last operation was Successful
     * return boolean value for last operation
     */
    public boolean ok(){
        return lastOperationSuccessful;
    }
    //EXIT SIMULATOR
    /**
     * name: exit
     * use: nothing
     * Ask users if they want to exit the simulator
     * return nothing
     */
    public void exit() {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "¿Estás seguro de que quieres salir?", 
            "Confirmar salida", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    /**
     * Ciclo 2
     */
    
    /**
     * name: Tower
     * use: int cups (n cups for problem)
     * Constructor for Tower with n cups and Fixed size (500,1000)
     * return: nothing
     */
    public Tower(int cups){
        this(500,1000);
        for(int i = 1; i<=cups; i++){
            pushCup(i);
        }
        this.lastOperationSuccessful = true;
        
    }
    
    /**
     * name: Swap
     * use: String[] o1 (first object to swap); String[] o2 (second object to swap)
     * Swap two objects places in item list 
     * return: nothing
     */
    public void swap(String[] o1, String[] o2){
        int idx1 = 0;
        int idx2 = 0;
        int idx = -1;
        //Check if o1 exist on item list
        idx = checkIdxObjectForSwap(o1);
        if(idx == -1){
            this.lastOperationSuccessful = false;
            return;
        }else{idx1 = idx;}

        //Check if o2 exist on item list
        idx = checkIdxObjectForSwap(o2);
        if(idx == -1){
            this.lastOperationSuccessful = false;
            return;
        }else{idx2 = idx;}

        //Do swap
        Collections.swap(items, idx1, idx2);
        return;
    }
    /**
     * name: checkIdxObjectForSwap
     * use: String[] o: object type and number {"type", "number"} format
     * Aux method to look in item's list for a specific object and returns the idx, if it's not found return -1
     * return: index for searched object
     */
    private int checkIdxObjectForSwap(String[] o){
        if(o[0].equals("cup")){
            int id = getCupByIdx(Integer.parseInt(o[1]));
            if(id != -1){
                return id;
            }
        }else{
            int id = getCupByIdx(Integer.parseInt(o[1]));
            if(id != -1){
                return id;
            }
        }
        return -1;
    }
    
    /**
     * name: getCupByIdx
     * use: int number (number for the cup we're looking for)
     * Checks in item list for a specific cup by number 
     * return: index for cup
     */
    private int getCupByIdx(int number){
        for (int i = 0; i<items.size(); i++){
            if(items.get(i) instanceof Cup && items.get(i).getNumber() == number){return i;}
            }
        return -1;
        }
    
    /**
     * name: getLidByIdx
     * use: int number (number for the cup we're looking for)
     * Checks in item list for a specific lid by number 
     * return: index for lid
     */
    private int getLidByIdx(int number){
        for (int i = 0; i<items.size(); i++){
            if(items.get(i) instanceof Lid && items.get(i).getNumber() == number){
                return i;
            } 
        }
        return -1;
    }
    
    /**
     * name: cover
     * use: nothing
     * Joins cup and lid by number, if lid doesn't exist, looks for the next cup
     * return: nothing
     */
    public void cover() {
        if (items.isEmpty()) {
            this.lastOperationSuccessful = false;
            return;
        }
        
        boolean operationSuccess = false;
        
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Cup) {
                Cup currentCup = (Cup) items.get(i);
                
                // Buscar cualquier tapa después de esta copa
                for (int j = i + 1; j < items.size(); j++) {
                    if (items.get(j) instanceof Lid) {
                        Lid lid = (Lid) items.get(j);
                        
                        // Verificar si la tapa puede cubrir esta copa
                        // (asumiendo que una tapa debe ser del mismo tamaño o mayor)
                        if (lid.getSize() >= currentCup.getSize()) {
                            // Mover la tapa justo después de la copa
                            if (j != i + 1) {  // Solo si no está ya en la posición correcta
                                Collections.swap(items, i + 1, j);
                            }
                            operationSuccess = true;
                            break;
                        }
                    }
                }
                
                if (operationSuccess) {
                    break;  // Solo una operación por llamada
                }
            }
        }
        
        this.lastOperationSuccessful = operationSuccess;
    }
    
    /**
     * name: swapToReduce
     * use: nothing
     * Looks in items list, Cups to swap place and reduce the height
     * returns: swap instructions to reduce the tower height
     */
    public String[][] swapToReduce() {
        List<String[]> swapPairs = new ArrayList<>();
        
        for (int i = 0; i < items.size() - 1; i++) {
            if (!(items.get(i) instanceof Cup)) continue;
            
            Cup cupI = (Cup) items.get(i);
            
            for (int j = i + 1; j < items.size(); j++) {
                if (!(items.get(j) instanceof Cup)) continue;
                
                Cup cupJ = (Cup) items.get(j);
                
                // Si encontramos una copa más grande después de una más pequeña
                if (cupI.getNumber() < cupJ.getNumber()) {
                    // Par para la copa pequeña
                    String[] firstCup = new String[2];
                    firstCup[0] = "cup";
                    firstCup[1] = String.valueOf(cupI.getNumber());
                    
                    // Par para la copa grande
                    String[] secondCup = new String[2];
                    secondCup[0] = "cup";
                    secondCup[1] = String.valueOf(cupJ.getNumber());
                    
                    // Añadir ambos pares a la lista
                    swapPairs.add(firstCup);
                    swapPairs.add(secondCup);
                    break;  // Solo el primer candidato para cada i
                }
            }
        }
        
        // Convertir List<String[]> a String[][]
        return swapPairs.toArray(new String[0][2]);
    }
    
    
    // printAllLists Console:
    public void printAllLists(){
        System.out.println("Lista: Items");
        for(int i = 0; i<items.size(); i++){
            if(items.get(i) instanceof Cup){
                System.out.println("Taza; idx: "+i+ " number: "+items.get(i).getNumber() + " color: " + items.get(i).getColor());
            }
            else if(items.get(i) instanceof Lid){
                System.out.println("Tapa; idx: "+i+ " number: "+items.get(i).getNumber() + " color: " + items.get(i).getColor());
            }
        }

    }
    
    /**
     * name: getItems
     * use: nothing
     * Returns the item list (getter method)
     * return Item list
     */
    public List<Item> getItems(){
        return items;
    }
}
