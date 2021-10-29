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

// Definim la classe cercle a partir de la classe figura
// Heretarem per tant, la posició i el color
class Cercle extends Figura implements Serializable {

    // Té un nou atribut que serà el radi
    private Integer radi;

    // Constructors
    Cercle() {
        // Sense paràmetres:
        super(); // Invoca al constructor del pare
        this.radi = 0;
    }

    Cercle(int x, int y, int radi, String color) {
        // Amb paràmetres:
        super(x - radi, y - radi, color); // Invoquem al constructor pare
        this.radi = radi; // Indiquem el valor del rado
        // Nota: La posició del cercle serà el seu punt central, per
        // aquest motiu restem el radi a X i a Y
    }

    public void renderText() {
        // Escriu les propietats de la figura
        System.out.println("Cercle en (" + this.posicio.getX() + "," + this.posicio.getY() + ") de radi " + this.radi
                + " i color " + this.color);
    }

    public void render(GraphicsContext gc) {
        // Dibuixem el cercle amb el seu color, la posició i el radi
        Color color = Color.web(this.color);
        gc.setFill(color);

        // Per dibuixar al canvas de JavaFX no hi ha una primitiva per dibuixar cercles,
        // per tant, hem de dibuixar un òval. Aquesta figura espera que li indiquem els
        // diàmetres major i menor,
        // pel que caldrà multiplicar per 2 el radi.
        gc.fillOval(this.posicio.getX(), this.posicio.getY(), this.radi * 2, this.radi * 2);
    }

    @Override
    public String getAsText() {
        return "cercle " + (this.posicio.getX() + this.radi) + " " + (this.posicio.getY() + this.radi) + " " + this.radi
                + " " + this.color;
    }

    @Override
    public Element getAsXML(Document documentoXML) {
        Element miElemento = documentoXML.createElement("circle");

        // Creamos el atributo cx
        Attr atributo = documentoXML.createAttribute("cx");
        atributo.setValue(Integer.toString(this.posicio.getX() + this.radi));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo cy
        atributo = documentoXML.createAttribute("cy");
        atributo.setValue(Integer.toString(this.posicio.getY() + this.radi));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo color
        atributo = documentoXML.createAttribute("fill");
        atributo.setValue(this.color);
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        // Creamos el atributo r
        atributo = documentoXML.createAttribute("r");
        atributo.setValue(Integer.toString(this.radi));
        // Escribimos el atributo en el elemento
        miElemento.setAttributeNode(atributo);

        return miElemento;
    }

    @Override
    public JSONObject getAsJSON() {
        // Este objeto será la raíz del documento
        JSONObject figuraOut = new JSONObject();
        JSONObject figuraAux = new JSONObject();

        figuraAux.put("r", Integer.toString (this.radi));
        figuraAux.put("cx", Integer.toString (this.posicio.getX() + this.radi));
        figuraAux.put("cy", Integer.toString (this.posicio.getY() + this.radi));
        figuraAux.put("fill", this.color);

                
        // Creamos raíz y guardamos todo el array
        figuraOut.put("cercle", figuraAux);

        return figuraOut;
    }
}
