package controllers;

import enums.Modo;
import models.Cell;
import views.MazePanel;

import static enums.Modo.*;

public class MazeController {

    private Modo currentMode = Modo.WALL;

    private Cell startCell;

    private Cell endCell;

    private final MazePanel panel;


    public MazeController(MazePanel paramMazePanel) {
        this.panel = paramMazePanel;
        paramMazePanel.setController(this);
    }

    public void setModo(Modo paramMode){
        this.currentMode = paramMode;
    }


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
