
/**
 * Write a description of class Tower here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.*;

//Conversion 1cm = 30px
public class Tower{
    private int width;
    private int maxHeight;
    private boolean isVisible;
    private boolean lastOperationSuccessful = true;
    public List<Integer> cups = new ArrayList<>();
    public List<Integer> lids = new ArrayList<>();
    public List<String[]> items = new ArrayList<>();
    private List<Cup> cupInstances = new ArrayList<>();
    private List<Lid> lidInstances = new ArrayList<>();
    private Canvas canvas;
    
    
    /**
     * name: Tower
     * use int: width (width of the tower and canvas); int maxHeight (height of the tower and canvas 
     * starts a new instance of the class and creates de canvas
     * returns nothing
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        this.isVisible = false;
        this.lastOperationSuccessful = true;
        this.canvas = Canvas.getCanvas(this.width, this.maxHeight);
        this.canvas.setVisible(false);
    }
    
    // MANAGE CUPS
    /**
     * name: pushCup
     * use int : i (given cup identifier)
     * adds a "cup" on the items list (with type "cup" and real size) and cups list (only size)
     * returns nothing
     */
    public void pushCup(int i) {
        if(cups.contains(i)){
           this.lastOperationSuccessful = false;
           if (this.isVisible){
               JOptionPane.showMessageDialog(null, "La taza " + i + " ya existe.");
            }
            return;
        }
        String color = getColorForNumber(i);
        Cup newCup = new Cup(i, color);
        this.cups.add(i);
        this.cupInstances.add(newCup);
        this.items.add(new String[]{"cup", String.valueOf(i)}); // String.ValueOF(i), vuelve i en un texto. porque tenemos una lista heterogenia
        this.lastOperationSuccessful = true;
        if (this.isVisible) {
        redrawAll();}
    }
    
    /**
     * name: popCup
     * use nothing
     * removes the last cup on cups and items lists
     * returns nothign
     */
    public void popCup() {
    if (cups.isEmpty()) {
        lastOperationSuccessful = false;
        return;
    }
    
    // Encontrar la última taza en items para saber su índice
    for (int i = items.size() - 1; i >= 0; i--) {
        if (items.get(i)[0].equals("cup")) {
            // 1. Eliminar de lista de datos
            items.remove(i);
            break;
        }
    }
    
    // 2. Eliminar de cups (el último elemento)
    if (!cups.isEmpty()) {
        cups.remove(cups.size() - 1);
    }
    
    // 3. ELIMINAR LA INSTANCIA GRÁFICA
    if (!cupInstances.isEmpty()) {
        Cup removed = cupInstances.remove(cupInstances.size() - 1);
        removed.makeInvisible(); // Ocultarla del canvas
    }
    
    // 4. Redibujar si es visible
    if (isVisible) {
        redrawAll();
    }
    
    lastOperationSuccessful = true;
}
    
/**
     * name: removeCup
     * use int : i (given cup identifier to remove)
     * removes the cup with given "id"
     * returns nothing
     */
    public void removeCup(int i) {
    int size = (2*i)-1;
    int number = i; // El número de taza
    
    // Buscar el índice en cupInstances
    int instanceIndex = -1;
    for (int idx = 0; idx < cupInstances.size(); idx++) {
        Cup cup = cupInstances.get(idx);
        // Asumiendo que Cup tiene un método getNumber()
        if (cup.getNumber() == number) {
            instanceIndex = idx;
            break;
        }
    }
    
    // Buscar en items
    int itemIndex = -1;
    for (int idx = 0; idx < items.size(); idx++) {
        String[] item = items.get(idx);
        if (item[0].equals("cup") && Integer.parseInt(item[1]) == size) {
            itemIndex = idx;
            break;
        }
    }
    
    // Eliminar de todas las listas
    if (itemIndex != -1) items.remove(itemIndex);
    cups.remove(Integer.valueOf(size));
    
    if (instanceIndex != -1) {
        Cup removed = cupInstances.remove(instanceIndex);
        removed.makeInvisible();
    }
    
    if (isVisible) redrawAll();
}
/**pushLid
 * genera una tapa del tamaño correspondiente a una copa.
 */
    public void pushLid(int i) {
    if(lids.contains(i)){
               this.lastOperationSuccessful = false;
               if (this.isVisible){
                   JOptionPane.showMessageDialog(null, "la tapa " + i + " ya existe.");
                }
                return;
            }
    String color = "black";
    if (cups.contains(i)) {
        for (Cup c : cupInstances) {
            if (c.getSize() == i) {
                color = c.getColor();
                break;
            }
        }
    }
    else{
        color = getColorForNumber(i);
    }
    Lid newLid = new Lid(i, color);
    this.lids.add(i);
    this.lidInstances.add(newLid);
    this.items.add(new String[]{"lid", String.valueOf(i)});
    this.lastOperationSuccessful = true;
    if (this.isVisible) {
                redrawAll();
            }
}
        /**
     * name: popLid
     * use nothing
     * Removes the last lid on lids and items lists
     * returns nothing
     */
    public void popLid() {
        // Verificar si hay tapas para eliminar
        if (lids.isEmpty()) {
            lastOperationSuccessful = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "No hay tapas para eliminar","Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        
        // 1. Encontrar y eliminar la última tapa de items
        int lastLidIndex = -1;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i)[0].equals("lid")) {
                lastLidIndex = i;
                break;
            }
        }
        
        if (lastLidIndex != -1) {
            String[] removedItem = items.remove(lastLidIndex);
            int size = Integer.parseInt(removedItem[1]);
            int number = (size + 1) / 2;
            
            // 2. Eliminar de lids list
            if (!lids.isEmpty()) {
                lids.remove(lids.size() - 1);
            }
            
            // 3. ELIMINAR LA INSTANCIA GRÁFICA correspondiente
            Lid lidToRemove = findLidByNumber(number);
            if (lidToRemove != null) {
                lidToRemove.makeInvisible();
                lidInstances.remove(lidToRemove);
            }
            
            System.out.println("Tapa #" + number + " eliminada");
        }
        
        // 4. Redibujar si es visible
        if (isVisible) {
            redrawAll();
        }
        
        lastOperationSuccessful = true;
        
        // Debug
        System.out.println("popLid completado. Tapas restantes: " + lids.size());
    }
        
    /**
     * name: removeLid
     * use int : i (given lid identifier to remove)
     * removes the specific lid on lids and items lists
     * returns nothing
     */
    public void removeLid(int i){
        int size = (2*i)-1;
        if (!lids.isEmpty()){lids.remove(Integer.valueOf(size));}
        
        if (!items.isEmpty()){
            for (int j = items.size()-1; i>=0; i--){
                if (items.get(j)[0].equals("lid") && items.get(j)[1].equals(Integer.toString(size))){
                    items.remove(j);
                    break;
                }
            }
        }
        // pruebas
        items.forEach(item -> System.out.println("Tipo: " + item[0] + ", Tamaño: " + item[1]));
        System.out.println("Lista lids"+lids);
        return;
    }
    
    //REORGANIZE TOWER
    /**
 * name: orderTower
 * use nothing
 * Order tower from largest to smallest (mayor a menor)
 * returns nothing
 */
public void orderTower() {
    // 1. Ordenar las listas de datos
    Collections.sort(cups, Collections.reverseOrder()); // Tazas de mayor a menor
    Collections.sort(lids, Collections.reverseOrder()); // Tapas de mayor a menor
    
    // 2. Ordenar items con la lógica correcta:
    // - De mayor a menor tamaño
    // - Si misma tamaño, las tazas van antes que las tapas
    items.sort((a, b) -> {
        int sizeA = Integer.parseInt(a[1]);
        int sizeB = Integer.parseInt(b[1]);
        String typeA = a[0];
        String typeB = b[0];
        
        // Primero por tamaño DESCENDENTE (mayor a menor)
        if (sizeA != sizeB) {
            return Integer.compare(sizeB, sizeA); // Mayor primero
        }
        
        // Mismo tamaño: tazas van antes que tapas
        if (typeA.equals(typeB)) return 0;
        if (typeA.equals("cup")) return -1; // cup antes
        return 1; // lid después
    });
    
    // 3. REORDENAR LAS INSTANCIAS GRÁFICAS para que coincidan con el nuevo orden
    reorderInstances();
    
    // 4. Redibujar si es visible
    if (isVisible) {
        redrawAll();
    }
    
    // 5. Mostrar resultado
    System.out.println("=== TORRE ORDENADA (mayor a menor) ===");
    items.forEach(item -> 
        System.out.println("  " + item[0] + " tamaño " + item[1]));
    
    lastOperationSuccessful = true;
}

/**
 * name: reverseTower
 * use nothing
 * Order tower from smallest to largest (menor a mayor)
 * returns nothing
 */
public void reverseTower() {
    // 1. Ordenar las listas de datos
    Collections.sort(cups); // Tazas de menor a mayor
    Collections.sort(lids); // Tapas de menor a mayor
    
    // 2. Ordenar items con la lógica correcta:
    // - De menor a mayor tamaño
    // - Si misma tamaño, las tazas van antes que las tapas
    items.sort((a, b) -> {
        int sizeA = Integer.parseInt(a[1]);
        int sizeB = Integer.parseInt(b[1]);
        String typeA = a[0];
        String typeB = b[0];
        
        // Primero por tamaño ASCENDENTE (menor a mayor)
        if (sizeA != sizeB) {
            return Integer.compare(sizeA, sizeB); // Menor primero
        }
        
        // Mismo tamaño: tazas van antes que tapas
        if (typeA.equals(typeB)) return 0;
        if (typeA.equals("cup")) return -1; // cup antes
        return 1; // lid después
    });
    
    // 3. REORDENAR LAS INSTANCIAS GRÁFICAS para que coincidan con el nuevo orden
    reorderInstances();
    
    // 4. Redibujar si es visible
    if (isVisible) {
        redrawAll();
    }
    
    // 5. Mostrar resultado
    System.out.println("=== TORRE INVERSA (menor a mayor) ===");
    items.forEach(item -> 
        System.out.println("  " + item[0] + " tamaño " + item[1]));
    
    lastOperationSuccessful = true;
}

/**
 * name: reorderInstances
 * use nothing
 * Reordena las listas de instancias gráficas para que coincidan con el orden de items
 * Esto es crucial para que al redibujar, cada elemento tenga su instancia correcta
 */
private void reorderInstances() {
    // Crear nuevas listas temporales
    List<Cup> newCupInstances = new ArrayList<>();
    List<Lid> newLidInstances = new ArrayList<>();
    
    // Recorrer items en el NUEVO orden y agregar las instancias correspondientes
    for (String[] item : items) {
        String type = item[0];
        int size = Integer.parseInt(item[1]);
        int number = (size + 1) / 2;
        
        if (type.equals("cup")) {
            Cup cup = findCupByNumber(number);
            if (cup != null) {
                newCupInstances.add(cup);
            } else {
                System.err.println("Error: Cup #" + number + " no encontrada al reordenar");
            }
        } else { // lid
            Lid lid = findLidByNumber(number);
            if (lid != null) {
                newLidInstances.add(lid);
            } else {
                System.err.println("Error: Lid #" + number + " no encontrada al reordenar");
            }
        }
    }
    
    // Reemplazar las listas antiguas con las nuevas (ordenadas)
    cupInstances = newCupInstances;
    lidInstances = newLidInstances;
    
    System.out.println("Instancias reordenadas: " + cupInstances.size() + 
                       " tazas, " + lidInstances.size() + " tapas");
}
    
    //CONSULT INFORMATION
    /**
     * name: height
     * use nothing
     * Calculate and give the tower's height
     * returns the total height of the tower
     */
    public int height() {
    if (items.isEmpty()) return 0;
    
    int totalHeight = 0;
    int currentTopLevel = 0; // Nivel actual de la superficie superior
    
    for (int i = 0; i < items.size(); i++) {
        String[] item = items.get(i);
        String type = item[0];
        int size = Integer.parseInt(item[1]); // size = 2i-1
        
        if (type.equals("cup")) {
            if (i == 0) {
                // Primera taza en la base
                totalHeight = size;
                currentTopLevel = size;
                System.out.println("Taza base #" + ((size+1)/2) + " - Altura: " + size);
            } else {
                // Verificar el elemento anterior para saber si la taza se apoya o se anida
                String[] prevItem = items.get(i-1);
                String prevType = prevItem[0];
                int prevSize = Integer.parseInt(prevItem[1]);
                
                if (prevType.equals("cup")) {
                    if (size < prevSize) {
                        // Taza más pequeña se anida dentro de la anterior
                        // No afecta la altura total
                        System.out.println("  Taza #" + ((size+1)/2) + " se anida - no suma altura");
                    } else {
                        // Taza más grande: se apoya en el borde de la anterior
                        totalHeight += (size - prevSize);
                        currentTopLevel = totalHeight;
                        System.out.println("  Taza #" + ((size+1)/2) + " más grande +" + (size-prevSize));
                    }
                } else { // prevType es "lid"
                    // Si el anterior es tapa, verificar si la tapa es del mismo tamaño
                    if (size > prevSize) {
                        // Taza más grande que la tapa anterior
                        totalHeight += (size - 1); // La tapa ya aportó 1cm
                        currentTopLevel = totalHeight;
                        System.out.println("  Taza #" + ((size+1)/2) + " sobre tapa +" + (size-1));
                    } else {
                        // Taza más pequeña o igual que la tapa - se anida
                        System.out.println("  Taza #" + ((size+1)/2) + " se anida bajo tapa - no suma");
                    }
                }
            }
        } else { // lid
            // Verificar dónde se coloca la tapa
            if (i == 0) {
                // Primera tapa en la base (caso raro pero posible)
                totalHeight = 1;
                currentTopLevel = 1;
                System.out.println("Tapa base #" + ((size+1)/2) + " - Altura: 1");
            } else {
                String[] prevItem = items.get(i-1);
                String prevType = prevItem[0];
                int prevSize = Integer.parseInt(prevItem[1]);
                
                if (prevType.equals("cup")) {
                    if (size == prevSize) {
                        // Tapa del mismo tamaño que la taza - CIERRA la taza
                        totalHeight += 1;
                        currentTopLevel = totalHeight;
                        System.out.println("  Tapa #" + ((size+1)/2) + " cierra taza +1");
                    } else if (size < prevSize) {
                        // Tapa más pequeña - se anida dentro de la taza
                        System.out.println("  Tapa #" + ((size+1)/2) + " se anida - no suma");
                    } else {
                        // Tapa más grande (no debería pasar, pero por si acaso)
                        totalHeight += 1;
                        currentTopLevel = totalHeight;
                        System.out.println("  Tapa #" + ((size+1)/2) + " más grande +1");
                    }
                } else { // prevType es "lid"
                    // Tapa sobre otra tapa
                    if (size <= prevSize) {
                        // Tapa igual o más pequeña - se anida
                        System.out.println("  Tapa #" + ((size+1)/2) + " se anida sobre tapa - no suma");
                    } else {
                        // Tapa más grande
                        totalHeight += 1;
                        currentTopLevel = totalHeight;
                        System.out.println("  Tapa #" + ((size+1)/2) + " más grande +1");
                    }
                }
            }
        }
    }
    
    System.out.println("Altura final: " + totalHeight + "cm");
    return totalHeight;
}
    
    /**
     * 
     */
    public int[] lidedCups() {
    List<Integer> lidedCupsList = new ArrayList<>();
    
    for (int i = 0; i < items.size() - 1; i++) {
        String[] current = items.get(i);
        String[] next = items.get(i + 1);
        
        // Verificar si hay una taza seguida de su tapa
        if (current[0].equals("cup") && next[0].equals("lid")) {
            int cupSize = Integer.parseInt(current[1]);
            int lidSize = Integer.parseInt(next[1]);
            int cupNumber = (cupSize + 1) / 2; // Convertir tamaño a número
            int lidNumber = (lidSize + 1) / 2;
            
            // Si son del mismo número y están juntos
            if (cupNumber == lidNumber && cupSize == lidSize) {
                lidedCupsList.add(cupNumber);
            }
        }
    }
    
    // Ordenar de menor a mayor como pide el requisito
    Collections.sort(lidedCupsList);
    
    // Convertir a int[]
    int[] result = new int[lidedCupsList.size()];
    for (int i = 0; i < lidedCupsList.size(); i++) {
        result[i] = lidedCupsList.get(i);
    }
    
    return result;
}
    
    /**
     * 
     */
    public String[][] stackingItems() {
    String[][] result = new String[items.size()][2];
    
    for (int i = 0; i < items.size(); i++) {
        String[] item = items.get(i);
        // Convertir a minúsculas como pide el requisito
        result[i][0] = item[0].toLowerCase();
        result[i][1] = item[1];
    }
    
    return result;
}
    
    // SET VISIBILITY

   /**
 * name: redrawAll
 * use nothing
 * Redibuja todos los elementos con el orden correcto:
 * 1. Tazas más grandes (atrás)
 * 2. Tazas más pequeñas (adelante)
 * 3. Tapas (siempre adelante)
 */
private void redrawAll() {
    if (!isVisible) return;
    clearCanvas();
    int centerX = width / 2;
    int[] yPositions = calculateAllYPositions();
    
    // Crear lista de índices ordenados por tamaño de taza (MAYOR a MENOR)
    //List<Integer> cupIndices = new ArrayList<>();
    for (int i = 0; i < items.size(); i++) {
        if (items.get(i)[0].equals("cup")) {
            int idABuscar = Integer.parseInt(items.get(i)[1]);
            Cup cup = findCupByNumber(idABuscar);
            if (cup != null) {
                cup.setPosition(centerX, yPositions[i]);
                cup.makeVisible();
            }
        }
    }    
    // DIBUJAR TAPAS (siempre adelante)
    System.out.println("\n=== DIBUJANDO TAPAS (adelante) ===");
    for (int i = 0; i < items.size(); i++) {
        if (items.get(i)[0].equals("lid")) {
                    int idABuscar = Integer.parseInt(items.get(i)[1]);
                    Lid lid = findLidByNumber(idABuscar);
                    
                    if (lid != null) {
                        lid.setPosition(centerX, yPositions[i]);
                        lid.makeVisible();
                    }
                }
    }
    
    drawHeightRuler();
}
    
    
    /**
     * name: clearCanvas
     * use nothing
     * Oculta todos los elementos gráficos del canvas
     * returns nothing
     */
    private void clearCanvas() {
        // Ocultar todas las tazas
        for (Cup cup : cupInstances) {
            cup.makeInvisible();
        }
        
        // Ocultar todas las tapas
        for (Lid lid : lidInstances) {
            lid.makeInvisible();
        }
        
        // Limpiar el canvas completamente
        Canvas canvas = Canvas.getCanvas(this.width, this.maxHeight);
        canvas.erase(this); // Esto limpia todo el canvas
        
        System.out.println("Canvas limpiado - " + cupInstances.size() + 
                           " tazas y " + lidInstances.size() + " tapas ocultadas");
    }
    
    /**
 * name: calculateAllYPositions
 * use nothing
 * Calcula la posición Y para cada elemento (borde superior)
 * AHORA CON LA LÓGICA CORRECTA DE ANIDAMIENTO
 */
private int[] calculateAllYPositions() {
    if (items.isEmpty()) {
        return new int[0];
    }
    
    int[] yPositions = new int[items.size()];
    int baseY = maxHeight - 120; // Margen inferior
    
    // Primero, identificar todas las tazas y su jerarquía
    List<Integer> cupIndices = new ArrayList<>();
    for (int i = 0; i < items.size(); i++) {
        if (items.get(i)[0].equals("cup")) {
            cupIndices.add(i);
        }
    }
    
    // Calcular posiciones de tazas de la BASE hacia arriba
    int currentBaseY = baseY;
    
    for (int i = 0; i < items.size(); i++) {
        String[] item = items.get(i);
        String type = item[0];
        int size = Integer.parseInt(item[1]);
        int number = (size + 1) / 2;
        
        if (type.equals("cup")) {
            // Las tazas se apilan: cada nueva taza se apoya en el borde de la anterior
            if (i == 0 || !items.get(i-1)[0].equals("cup")) {
                // Primera taza o después de una tapa
                yPositions[i] = currentBaseY - (size * 30);
                currentBaseY = yPositions[i] + (size * 30); // Nueva base para el siguiente
                System.out.println("Taza #" + number + " (nueva base): Y=" + yPositions[i]);
            } else {
                // Taza después de otra taza
                int prevSize = Integer.parseInt(items.get(i-1)[1]);
                
                if (size < prevSize) {
                    // Taza MÁS PEQUEÑA: se ANIDA dentro de la anterior
                    // Se coloca DENTRO, con su base en la base de la taza grande
                    int prevY = yPositions[i-1];
                    int prevBottomY = prevY + (prevSize * 30);
                    yPositions[i] = prevBottomY - (size * 30);
                    System.out.println("  Taza #" + number + " ANIDADA dentro de #" + 
                                     ((prevSize+1)/2) + " en Y=" + yPositions[i]);
                } else {
                    // Taza MÁS GRANDE: se apoya en el borde
                    yPositions[i] = yPositions[i-1] - ((size - prevSize) * 30);
                    System.out.println("  Taza #" + number + " APOYADA en Y=" + yPositions[i]);
                }
                currentBaseY = yPositions[i] + (size * 30);
            }
        }
    }
    
    // Calcular posiciones de tapas (dependen de las tazas)
    for (int i = 0; i < items.size(); i++) {
        String[] item = items.get(i);
        String type = item[0];
        int size = Integer.parseInt(item[1]);
        int number = (size + 1) / 2;
        
        if (type.equals("lid")) {
            // Buscar si hay una taza del MISMO tamaño
            boolean foundMatch = false;
            for (int j = 0; j < items.size(); j++) {
                String[] other = items.get(j);
                if (other[0].equals("cup") && Integer.parseInt(other[1]) == size) {
                    // Tapa sobre su taza
                    yPositions[i] = yPositions[j] - 30;
                    System.out.println("  Tapa #" + number + " SOBRE taza #" + number + 
                                     " en Y=" + yPositions[i]);
                    foundMatch = true;
                    break;
                }
            }
            
            if (!foundMatch) {
                // Buscar la taza más grande donde anidar esta tapa
                int largestCupIndex = -1;
                int largestCupSize = 0;
                
                for (int j = 0; j < items.size(); j++) {
                    String[] other = items.get(j);
                    if (other[0].equals("cup")) {
                        int cupSize = Integer.parseInt(other[1]);
                        if (cupSize > size && cupSize > largestCupSize) {
                            largestCupSize = cupSize;
                            largestCupIndex = j;
                        }
                    }
                }
                
                if (largestCupIndex != -1) {
                    // Anidar la tapa dentro de la taza más grande
                    // La colocamos a una altura que se vea dentro
                    int cupY = yPositions[largestCupIndex];
                    int cupBottomY = cupY + (largestCupSize * 30);
                    yPositions[i] = cupY + (largestCupSize * 30) / 2 - 15;
                    System.out.println("  Tapa #" + number + " ANIDADA en taza #" + 
                                     ((largestCupSize+1)/2) + " en Y=" + yPositions[i]);
                } else {
                    // No hay taza, va en la base
                    yPositions[i] = baseY - 30;
                    System.out.println("  Tapa #" + number + " en BASE en Y=" + yPositions[i]);
                }
            }
        }
    }
    
    return yPositions;
}
    /**
 * name: makeVisible
 * use nothing
 * Hace visible el simulador. Si la torre no cabe, muestra mensaje de error
 * returns nothing
 */
public void makeVisible() {
    // Calcular altura actual de la torre
    int towerHeight = height();
    // Verificar si cabe en el canvas
    if (towerHeight <= maxHeight) {
        isVisible = true;
        lastOperationSuccessful = true;
        
        // Redibujar todo
        redrawAll();
        
        // Mostrar mensaje de confirmación (solo si hay elementos)
        if (!items.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "✅ Torre visible\n" +
                "Altura: " + towerHeight + " cm\n" +
                "Elementos: " + items.size() + "\n" +
                "Tazas: " + cups.size() + ", Tapas: " + lids.size(),
                "Simulador",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        System.out.println("makeVisible: Torre visible - Altura " + towerHeight + "cm");
        
    } else {
        // No cabe: no se hace visible
        isVisible = false;
        lastOperationSuccessful = false;
        
        // Mensaje de error detallado
        String mensaje = String.format(
            "❌ LA TORRE NO CABE EN LA PANTALLA\n\n" +
            "Altura de la torre: %d cm\n" +
            "Altura máxima: %d cm\n" +
            "Exceso: %d cm\n\n" +
            "Sugerencias:\n" +
            "• Reduzca el número de elementos\n" +
            "• Cambie el orden (los más grandes abajo)\n" +
            "• Use reverseTower() para invertir el orden",
            towerHeight, maxHeight, (towerHeight - maxHeight));
        
        JOptionPane.showMessageDialog(null, 
            mensaje,
            "Error de visualización",
            JOptionPane.ERROR_MESSAGE);
        
        System.out.println("makeVisible: Torre NO cabe - " + towerHeight + 
                           "cm > " + maxHeight + "cm");
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
        for (Cup cup : cupInstances) {
            cup.makeInvisible();
        }
        for (Lid lid : lidInstances) {
            lid.makeInvisible();
        }
        
        // Limpiar el canvas
        Canvas canvas = Canvas.getCanvas(this.width, this.maxHeight);
        canvas.erase(this);
        
        // Actualizar estado
        isVisible = false;
        lastOperationSuccessful = true;
        
        // Mensaje de confirmación
        JOptionPane.showMessageDialog(null, 
            "🔍 Simulador ocultado\n" +
            "Los datos de la torre se mantienen",
            "Simulador",
            JOptionPane.INFORMATION_MESSAGE);
        
        System.out.println("makeInvisible: Simulador ocultado");
        
    } else {
        // Ya estaba invisible
        lastOperationSuccessful = false;
        
        JOptionPane.showMessageDialog(null, 
            "El simulador ya está invisible",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }
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
 * name: getColorForNumber
 * use int: number (número de taza/tapa)
 * returns String: color correspondiente al número
 * Los colores están asignados para que taza y tapa del mismo número tengan el MISMO color
 */
private String getColorForNumber(int number) {
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

/**
 * name: findCupByNumber
 * use int: number (número de taza a buscar)
 * returns Cup: la instancia de Cup con ese número, o null si no existe
 */
private Cup findCupByNumber(int number) {
    for (Cup cup : cupInstances) {
        if (cup.getNumber() == number) {
            return cup;
        }
    }
    return null;
}

/**
 * name: findLidByNumber
 * use int: number (número de tapa a buscar)
 * returns Lid: la instancia de Lid con ese número, o null si no existe
 */
private Lid findLidByNumber(int number){
    for (Lid lid : lidInstances) {
        if (lid.getNumber() == number) {
            return lid;
        }
    }
    return null;
}

    //EXIT SIMULATOR
    /**
     * Salimos del simulador 
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
 * name: Tower
 * use int: cups (amount of cups)
 */
public Tower(int cups) {
    this(500, 500);
        for (int i = 1; i <= cups; i++) {
        pushCup(i);
    }
}

/**
 * Intercambia la posición de dos objetos de la torre 
 * name: swap
 */
public void swap(String[] o1, String[] o2) {
    int index1 = -1;
    int index2 = -1;

    // Buscamos los índices en la lista que representa la estructura de la torre
    for (int i = 0; i < items.size(); i++) {
        String[] current = items.get(i);
    if (current[0].trim().equalsIgnoreCase(o1[0].trim()) && current[1].trim().equals(o1[1].trim())) {
        index1 = i;
    }
    if (current[0].trim().equalsIgnoreCase(o2[0].trim()) && current[1].trim().equals(o2[1].trim())) {
        index2 = i;
    }   }

    if (index1 != -1 && index2 != -1) {
        java.util.Collections.swap(items, index1, index2);
        this.lastOperationSuccessful = true;
        if(isVisible){
        redrawAll();}
    } else {
        this.lastOperationSuccessful = false;
        if (isVisible) {
        String msg = "No existe: " + o1[0] + " " + o1[1] + " o " + o2[0] + " " + o2[1];
                JOptionPane.showMessageDialog(null, msg);
        }
    }
}

/**
 * Debe permitir tapar las tazas que tienen sus tapas en la torre 
 * 
 */
public void cover() {
    if(!items.isEmpty()){
    String[] top = items.get(items.size() - 1); // Obtenemos el objeto que este en la cima de la torre.
    if(top[0].equalsIgnoreCase("cup")){
     int size = Integer.parseInt(top[1]);
     pushLid(size);
     this.lastOperationSuccessful = true;
    }else{lastOperationSuccessful = true;}
    }
    else{
        this.lastOperationSuccessful=false;
    }
}
/**
 * retorna el valor de la última operacion
 */
public boolean ok() {
    return this.lastOperationSuccessful;
}
}