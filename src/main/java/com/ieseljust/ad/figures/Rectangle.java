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

// Definim la classe rectangle a partir de la classe figura
// Heretarem per tant, la posició i el color
class Rectangle extends Figura {

    // Té un nou atribut que serà el radi
    private Integer llarg;
    private Integer alt;

    // Constructors
    Rectangle() {
        // Sense paràmetres:
        super(); // Invoca al constructor del pare
        this.llarg = 0;
        this.llarg = 0;
    }

    Rectangle(int x, int y, int llarg, int alt, String color) {
        // Amb paràmetres:
        super(x, y, color); // Invoquem al constructor pare
        this.llarg = llarg; // Indiquem el valor de la llargària
        this.alt = alt; // Indiquem el valor de l'altura
    }

    public void renderText() {
        // Escriu les propietats de la figura
        System.out.println("Rectangle en (" + this.posicio.getX() + "," + this.posicio.getY() + ") de llarg "
                + this.llarg + ", altura " + this.alt + " i color " + this.color);
    }

    ;

    public void render(GraphicsContext gc) {
        // Dibuixem el rectangle amb el seu color, la posició i les dimensions
        Color color = Color.web(this.color);
        gc.setFill(color);

        gc.fillRect(this.posicio.getX(), this.posicio.getY(), this.llarg, this.alt);
        // gc.fillOval(this.posicio.getX(), this.posicio.getY(), this.radi*2,
        // this.radi*2);
    }

    @Override
    public String getAsText() {
        return "rectangle " + this.posicio.getX() + " " + this.posicio.getY() + " " + this.llarg + " " + this.alt + " "
                + this.color;
    }

    @Override
    public Element getAsXML(Document documentoXML) {
        Element miElemento = documentoXML.createElement("rect");

        // Creamos el atributo color
        Attr atributo = documentoXML.createAttribute("fill");
        atributo.setValue(this.color);
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo height
        atributo = documentoXML.createAttribute("height");
        atributo.setValue(Integer.toString(this.alt));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo width
        atributo = documentoXML.createAttribute("width");
        atributo.setValue(Integer.toString(this.llarg));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo x
        atributo = documentoXML.createAttribute("x");
        atributo.setValue(Integer.toString(this.posicio.getX()));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo y
        atributo = documentoXML.createAttribute("y");
        atributo.setValue(Integer.toString(this.posicio.getY()));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        return miElemento;
    }

    @Override
    public JSONObject getAsJSON( ) {
        // Este objeto será la raíz del documento
          // Este objeto será la raíz del documento
        JSONObject figuraOut = new JSONObject();
        JSONObject figuraAux = new JSONObject();

        figuraAux.put("x", Integer.toString (this.posicio.getX()));
        figuraAux.put("width", Integer.toString (this.llarg));
        figuraAux.put("y", Integer.toString (this.posicio.getY()));
        figuraAux.put("fill", this.color);
        figuraAux.put("height", Integer.toString (this.alt));

        // Creamos raíz y guardamos todo el array
        figuraOut.put("rectangle", figuraAux);

        return figuraOut;
    }
}
