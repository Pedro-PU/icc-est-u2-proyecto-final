package controllers;

import enums.Modo;

import static enums.Modo.*;

public class MazeController {

    private Modo currentMode = WALL;

    /*
    Los métodos del método de abajo que están comentados por favor revisen
    ⬇️⬇️

    En el controller del profe esta creando un enum, yo lo que hice fue crear un package, enum y una
    clase enum llamada Modo si es que tiene que usar algún enum como el del profe revisen bien y coloquen
    de nuestra clase Modo

    */

    public void celdaClickeada(int parametro1, int parametro2) {
        switch (this.currentMode) {
            case START:
                //setStartCell(parametro1, parametro2);
                break;
            case END:
                //setEndCell(parametro1, parametro2);
                break;
            case WALL:
                //toggleWall(parametro1, parametro2);
                break;
        }
    }
    
}
