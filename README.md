# ![Logo Universidad](https://upload.wikimedia.org/wikipedia/commons/b/b0/Logo_Universidad_Polit%C3%A9cnica_Salesiana_del_Ecuador.png)

# **UNIVERSIDAD POLITECNICA SALESIANA**

**Materia:** Estructura de datos.

**Estudiantes:** Jonnathan Saavedra, Pedro Pesántez, Fernando Martinez, Mathias Añazco.

**Docente:** Pablo Torres.

**Fecha:** 2023-07-28

---

## 🧩 Descripción del problema

El problema consiste en desarrollar una aplicación que permita resolver laberintos utilizando diferentes algoritmos de búsqueda y recorrido como DFS, BFS y Backtracking. La aplicación debe permitir visualizar gráficamente el proceso de resolución y comparar la eficiencia de los algoritmos.

---

### 💡 Propuesta de solución

Se desarrolló una aplicación en Java utilizando la biblioteca Swing para la interfaz gráfica. La aplicación permite crear laberintos personalizados o aleatorios y seleccionar entre diferentes algoritmos para resolverlos. Se muestra visualmente el recorrido paso a paso.

### 🛠️ Tecnologías utilizadas

- Java 24  
- Java Swing  
- MVC (Modelo - Vista - Controlador)  
- DAO (Data Access Object)
- IDE: IntelliJ IDEA  
- Git y GitHub

### 📊 Diagrama UML y explicación

<img width="3019" height="1015" alt="diagrama de clases" src="https://github.com/user-attachments/assets/16d62ad9-7fac-4e7e-a1b9-11549fb34695" />

> El sistema está dividido en tres capas: `modelo`, `controlador` y `vista`.  
> - El modelo representa las celdas, el estado y el laberinto.  
> - El controlador ejecuta los algoritmos de resolución.  
> - La vista muestra la interfaz gráfica y permite al usuario interactuar.


### 💻 Código ejemplo: Algortmo Recursivo

#### Clase MazeSolverRecursivo

```java

public class MazeSolverRecursivo implements MazeSolver {
    private final Set<Cell> visitadas = new LinkedHashSet<>();
    private final List<Cell> camino = new ArrayList<>();

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        visitadas.clear();
        camino.clear();
        findPath(maze, start.getRow(), start.getCol(), end);
        return new SolveResults(new ArrayList<>(visitadas), new ArrayList<>(camino));
    }

```

**- Descripción del algoritmo recursivo:** Este método sobrescribe getPath del MazeSolver e inicia el proceso de búsqueda recursiva desde la celda de inicio (start) hasta la celda final (end). Limpia las estructuras antes de comenzar y retorna un objeto SolveResults con el recorrido completo y el camino final.


#### Método para buscar camino


``` java

private boolean findPath(Cell[][] maze, int row, int col, Cell end) {
    if (!isValid(maze, row, col)) return false;
    Cell cell = maze[row][col];
    if (visitadas.contains(cell)) return false;

    visitadas.add(cell);
    if (cell.equals(end)) {
        camino.add(cell);
        return true;
    }

    if (findPath(maze, row + 1, col, end) ||
        findPath(maze, row, col + 1, end)) {
        camino.add(cell);
        return true;
    }
    return false;
}

```

**- Descripción del algoritmo recursivo:** Este es el corazón del algoritmo de backtracking. Marca las celdas visitadas, verifica si se ha llegado al objetivo, y explora en profundidad hacia abajo y a la derecha. Si encuentra una solución, construye el camino en reversa.

#### Verificación de validez de la celda
``` java

private boolean isValid(Cell[][] maze, int row, int col) {
    return row >= 0 && row < maze.length &&
           col >= 0 && col < maze[0].length &&
           maze[row][col].getState() != CellState.WALL;
}

```

**- Descripción del algoritmo recursivo:** Este método garantiza que la celda a visitar esté dentro de los límites del laberinto y que no sea una pared (WALL). Es fundamental para evitar errores de índice y caminos inválidos.

### 🎦 Capturas de la interfaz gráfica

###### Captura de la interfaz gráfica del algoritmo recursivo:

<img width="980" height="733" alt="laberinto recursivo" src="https://github.com/user-attachments/assets/d463f603-a80a-4f5e-b653-072aa0ca4014" />

###### Captura de la interfaz gráfica del algoritmo recursivo completo con BT:

<img width="981" height="737" alt="laberinto completobt" src="https://github.com/user-attachments/assets/a6bf9c5a-7846-4003-b36b-9fa0fe0d3663" />

---

## CONCLUSIONES POR ESTUDIANTES

### Conclusión general: 

El desarrollo de este proyecto permitió aplicar de manera práctica algoritmos fundamentales de búsqueda como DFS, BFS y Backtracking, analizando su comportamiento en la resolución de laberintos. Se evidenció que BFS es el más eficiente cuando se busca el camino más corto, mientras que DFS puede ser más rápido en llegar a una solución, aunque no óptima. Backtracking, aunque completo, resulta menos eficiente en laberintos grandes.

Además, se reforzaron conceptos clave de diseño de software como el patrón MVC y DAO, mejorando la estructura y mantenibilidad del código. La implementación de una interfaz gráfica permitió visualizar el funcionamiento de los algoritmos en tiempo real, facilitando la comprensión del problema. En conjunto, el proyecto fortaleció tanto las habilidades técnicas como la capacidad de análisis y toma de decisiones algorítmicas de cada integrante del equipo.

### Conclusión de Jonnathan Saavedra:

Tras probar los tres algoritmos, se concluye que BFS es el más eficiente cuando se busca el camino más corto, ya que explora nivel por nivel. En cambio, DFS puede ser útil cuando se quiere llegar rápidamente a una solución sin importar la distancia, pero no es óptimo. Backtracking, aunque confiable para explorar exhaustivamente, resulta más costoso en tiempo y recursos, siendo menos práctico para laberintos grandes. Por lo tanto, BFS destaca por su precisión y balance entre tiempo y resultado.

### Conclusión de Pedro Pesántez:

Los distintos algoritmos de resolución de laberintos presentan enfoques variados con ventajas y limitaciones según su estrategia de búsqueda. El algoritmo Breadth-First Search (MazeSolverBFS) explora nivel por nivel y garantiza siempre el camino más corto, aunque puede ser lento en laberintos grandes por su complejidad O(n·m). En contraste, Depth-First Search (MazeSolverDFS) se adentra profundamente en un camino antes de retroceder, lo que le permite llegar más rápido a una solución, pero sin asegurar que sea la más corta. El MazeSolverRecursivo, al limitarse solo a moverse hacia abajo y derecha, es muy restringido y puede fallar incluso si existe una solución. El MazeSolverRecursivoCompleto explora en todas las direcciones, pero sin backtracking, lo que lleva a caminos erróneos y resultados incorrectos. Finalmente, el MazeSolverRecursivoCompletoBT incorpora retroceso real, permitiendo corregir rutas fallidas y encontrar una solución válida con eficiencia, aunque no siempre sea la más corta. En conclusión, si se necesita el camino más corto, BFS es el mejor, pero si se busca una solución más eficiente y general, el DFS con backtracking es la opción más equilibrada.

### Conclusión de Fernando Martinez:

Este proyecto me permitió entender mejor cómo funcionan los algoritmos de búsqueda y recorrido en laberintos, especialmente la implementación práctica de DFS, BFS y backtracking. Al trabajar con estos métodos, aprendí a evaluar sus ventajas y limitaciones en términos de eficiencia y aplicabilidad.

Además, pude aplicar principios de diseño de software como MVC y DAO, lo que mejoró la organización y modularidad del código, facilitando su mantenimiento y escalabilidad. La integración con una interfaz gráfica usando Java Swing también fue una experiencia valiosa para entender cómo combinar la lógica con la interacción del usuario.

En general, este trabajo fortaleció mis habilidades técnicas y de colaboración, y me dejó claro lo importante que es diseñar soluciones que no solo funcionen correctamente, sino que también sean claras y fáciles de usar.

### Conclusión de Mathias Añazco:

El desarrollo de esta aplicación de resolución de laberintos permitió comprender y aplicar de manera práctica conceptos fundamentales de estructuras de datos y algoritmos, como BFS, DFS y técnicas recursivas con y sin backtracking. La implementación de cada algoritmo evidenció sus fortalezas y limitaciones en diferentes escenarios, reforzando la importancia de elegir el método adecuado según el problema. Además, el uso del patrón de diseño MVC facilitó la organización, mantenimiento y escalabilidad del proyecto, mientras que la interfaz gráfica en Java Swing permitió una interacción clara e intuitiva para visualizar el comportamiento de los algoritmos en tiempo real. Este proyecto no solo fortaleció habilidades técnicas en programación, sino también la capacidad analítica para resolver problemas complejos de manera estructurada y eficiente.

---

## ©️ Créditos

Aplicación desarrollada por:
- Jonnathan Saavedra
- Pedro Pesántez
- Fernando Martinez
- Mathias Añazco

Ideas y métodos propuestos por:
- Ing. Pablo Torres

---
