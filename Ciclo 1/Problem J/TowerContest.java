import java.util.*;
public class TowerContest {
    private boolean isSolvable;
    private List<Integer> numbers = new ArrayList<>();
    private List<Integer> listSolution = new ArrayList<>();
      /**

     * Constructor for objects of class TowerContest

     */
    public TowerContest() {
        this.isSolvable = false;
        
    }
      /**

     * Generador de solucion del problema

     */
    public String solve(int n, int h) {
        int minHeight = (n * 2) - 1;
        int maxHeight = n * n;
        
        
        if (h > maxHeight || h < minHeight || n <= 0) { return null; } //casos de no creacion
        
        if (h == minHeight) { //trivial de encapsulamiento del mayor
            for (int i = n; i > 0; i--) {
                listSolution.add((i * 2) - 1);
            }
            return convertListSolutionToString();
        } else if (h == maxHeight) { //caso trivial de impresion menor a mayor
            for (int i = 1; i <= n; i++) {
                listSolution.add((i * 2) - 1);
            }
            return convertListSolutionToString();
        } else { //casos donde determinamos los valores para saber las posibles alturas si pongo todo dentro
            int interMin = ((n - 1) * 2) - 1; // esa es la altura minima altura que tengo con las tasas que tengo
            int interMax = (n - 1) * (n - 1); // la maxima altura que alcanzo a cubrir con las tazas que tengo
            int interReach = h - 1; //altura que me falta por cubrir

            if (interReach > interMax || interReach < interMin) { //si la altura que falta es insatisfacible con las copas que tengo disponible
                List<Integer> lista = new ArrayList<>(); // guardo las copas que tengo disponible
                for (int i = 1; i < n; i++) {
                    lista.add((i * 2) - 1);
                }
                List<Integer> subInternalSolution = new ArrayList<>(); // son las que que se apilan
                int objetive = h - ((n * 2) - 1); // la altura eliminando la mas grande
                
                search(lista, objetive, 0, 0, new ArrayList<>(), subInternalSolution);
                
                if (!subInternalSolution.isEmpty()) { // si obtuvimos candidatos 
                    listSolution.addAll(subInternalSolution);//estos valores van antes del mas grande
                    listSolution.add((n * 2) - 1);//luego va el mas grande
                    for (int i = n - 1; i >= 1; i--) {//ponemos los valores que van a dentro 
                        int cup = (i * 2) - 1;
                     if (!listSolution.contains(cup)) {
                            listSolution.add(cup);
                        }
                    }
                }
            } else { 
                listSolution.add((n * 2) - 1);// la solucion va con base mas grande y altura restante por dentro
                String subSolution = solve(n - 1, h - 1);// llamada recurrente
                if (subSolution != null) {// construimos la solucion
                    // solve ya agrega los valores a listSolution
                }
            }
        }
        System.out.println(listSolution);
        return listSolution.isEmpty() ? null : convertListSolutionToString();
    }
      /**

     * Buscador de la combinacion adecueada para amontonar las copas.

     */
    private boolean search(List<Integer> torre, int objetive, int index, int suma, List<Integer> actual, List<Integer> lista) {
        if (suma == objetive) { // la suma de los elementos tenemos que esos elementos van a apilarse
        lista.addAll(actual);
            return true;
        }

        for (int i = index; i < torre.size(); i++) {// usamos index para eviar valores repetidos en la recurrencia planteada, para no evaluar mas de una vez el mismo valor   
            if (suma + torre.get(i) > objetive) { // el romper si la copa hace que me pase del objetivo
                break;
            }
            actual.add(torre.get(i)); // añadimos  en la lista de candidatos
            if (search(torre, objetive, i + 1, suma + torre.get(i), actual, lista)) { // volvemos a la recurrencia, para conocer con la nueva copa puesta si el resto sirve
                return true;
            }
            actual.remove(actual.size() - 1); // si no sirve la copa i, probamos con la sigueinte copa
        }
        return false; // la copa que da false va a dentro.
    }
    
    /**
     * name: simulate
     * use: int n, int h
     * ?
     * return:
     */
    public void simulate(int n, int h) {
        Tower tower = new Tower(500, 1000);
    
        solve(n, h);
    
        for (Integer cup : listSolution) {
            tower.pushCup(cup);
        }
    
        tower.printAllLists();
        
        
    }
    private String convertListSolutionToString(){
        if(listSolution.isEmpty()){
            return "";
        }
        String stringSolution = Integer.toString(listSolution.get(0));
        for(int i = 1; i<listSolution.size(); i++){
            stringSolution = stringSolution + " " + Integer.toString(listSolution.get(i));
        }
        return stringSolution;
    }
}
