package com.ieseljust.ad.figures;

// Llibreríes per a poder dibuixar 
import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

abstract class Figura implements Serializable {

    /*
     * Aquesta classe serà una classe abstracta (amb mètodes abstractes) a partir de
     * la qual extendrem la resta de classes de figures geomètriques.
     */

    // La posició i el color seran atributs comuns a totes les figures
    protected Punt posicio;
    protected String color;

    // Constructors:
    // Els constructors inicialitzen la posició i el color
    // La posició és de tipus punt, classe que també hem definit a l'aplicació
    // El color és un string en format hexadecimal: #ff0000=roig, #00ff00=verd,
    // #0000ff=verd
    Figura() {
        // Constructor per defecte sense paràmetres
        this.posicio = new Punt();
        this.color = "#000000";
    }

    Figura(int x, int y) {
        // Constructor on s'especifica només pa posició
        this.posicio = new Punt(x, y);
        this.color = "#000000";
    }

    ;

    Figura(int x, int y, String color) {
        // Constructor on s'especifica la posició i el color
        this.posicio = new Punt(x, y);
        this.color = color;
    }

    // Mètodes abastractes que hauran d'implementar les subclasses
    public abstract void renderText(); // Per mostrar una descripció de la figura geomètrica

    public abstract void render(GraphicsContext gc); // Per dibuixar la figura al context gràfic especificat

    public abstract String getAsText(); // Para obtener las figuras en formato texto para la exportación

    public abstract Element getAsXML(Document documentoXML); // Para obtener las figuras en formato XML para la exportación

    public abstract JSONObject getAsJSON(); // Para obtener las figuras en formato JSON para la exportación

}
