package com.ieseljust.ad.figures;

// Llibreríes per a poder dibuixar 
import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// Definim la classe Línia a partir de la classe figura
// Heretarem per tant, la posició i el color
class Linia extends Figura implements Serializable {

    // Té un nou atribut que serà el radi
    private Punt vector;

    // Constructors
    Linia() {
        // Sense paràmetres:
        super(); // Invoca al constructor del pare
        this.vector = new Punt(0, 0);
    }

    Linia(int x, int y, int x2, int y2, String color) {
        // Amb paràmetres:
        super(x, y, color); // Invoquem al constructor pare
        this.vector = new Punt(x2, y2); // Indiquem el vector de la línia
    }

    public void renderText() {
        // Escriu les propietats de la figura
        System.out.println("Linia de (" + this.posicio.getX() + "," + this.posicio.getY() + ") fins a ("
                + this.vector.getX() + ", " + this.vector.getY() + ") i color " + this.color);
    }

    ;

    public void render(GraphicsContext gc) {
        // Dibuixem la linia amb el seu color
        Color color = Color.web(this.color);
        gc.setLineWidth(3); // Grossor per defecte de la línia: 3
        gc.setStroke(color);
        gc.strokeLine(this.posicio.getX(), this.posicio.getY(), this.vector.getX(), this.vector.getY());

    }

    @Override
    public String getAsText() {
        return "linia " + this.posicio.getX() + " " + this.posicio.getY() + " " + this.vector.getX() + " "
                + this.vector.getY() + " " + this.color;
    }

    @Override
    public Element getAsXML(Document documentoXML) {
        Element miElemento = documentoXML.createElement("line");

        // Creamos el atributo color y por defecto 3 pixel anchura
        Attr atributo = documentoXML.createAttribute("stroke");
        atributo.setValue(this.color);
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);
        atributo = documentoXML.createAttribute("stroke-width");
        atributo.setValue("3");
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo x1
        atributo = documentoXML.createAttribute("x1");
        atributo.setValue(Integer.toString(this.posicio.getX()));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo x2
        atributo = documentoXML.createAttribute("x2");
        atributo.setValue(Integer.toString(this.vector.getX()));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo x1
        atributo = documentoXML.createAttribute("y1");
        atributo.setValue(Integer.toString(this.posicio.getY()));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo x2
        atributo = documentoXML.createAttribute("y2");
        atributo.setValue(Integer.toString(this.vector.getY()));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        return miElemento;
    }

    @Override
    public JSONObject getAsJSON( ) {
        // Este objeto será la raíz del documento
        JSONObject figuraOut = new JSONObject();
        JSONObject figuraAux = new JSONObject();

        figuraAux.put("y1", Integer.toString (this.posicio.getY()));
        figuraAux.put("x1", Integer.toString (this.posicio.getX()));
        figuraAux.put("y2", Integer.toString (this.vector.getY()));
        figuraAux.put("x2", Integer.toString (this.vector.getX()));
        figuraAux.put("stroke-width", "3");
        figuraAux.put("stroke", this.color);

        // Creamos raíz y guardamos todo el array
        figuraOut.put("linia", figuraAux);

        return figuraOut;
    }
}
