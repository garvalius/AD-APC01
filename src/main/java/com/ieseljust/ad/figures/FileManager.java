package com.ieseljust.ad.figures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class FileManager {

    public FileManager() {

    }

    private boolean validaInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public Boolean Exists(String file) {
        /**
         * ************************************** TO-DO: Mètode a implementar:
         * * Retorna si el fitxer existeix o no *
         * ***************************************
         */
        File f = new File(file);
        return f.exists();

    }

    public Escena importFromText(String file) {

        /**
         * ********************************************************* TO-DO:
         * Mètode a implementar: * Llegirà el fitxer indicat, en format text, i
         * importarà * la llista de figures. *
         * **********************************************************
         */
        /*
         * dimensions 500 500 rectangle 10 10 480 480 #ccccee cercle 250 250 100 #aaaaaa
         */
        // Creamos una excena por defecto, para evitar el nullPointerException.
        // Si posteriormente en el fichero encontramos "dimensions" le cambiaremos el
        // tamaño
        // con el método apropiado
        Escena escena = new Escena();

        // Leemos el fichero
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // Realizamos la lectura del fichero
            int numeroLineaFichero = 1;
            while (br.ready()) {
                String[] ordenSeparada = br.readLine().split(" ");

                try {
                    // Si el comando es cambiar las dimensiones, actualizamos las dimensiones de la
                    // escena y si no verificamos que sea una figura válida
                    if (ordenSeparada[0].equals("dimensions")) {
                        int x = Integer.parseInt((ordenSeparada[1]));
                        int y = Integer.parseInt((ordenSeparada[2]));
                        escena.dimensions(x, y);
                    } else {
                        anadirFiguraEscena(escena, ordenSeparada);
                    }

                } catch (Exception e) {
                    System.out.println("ERROR: El archivo " + file + " tiene un error en la línea " + numeroLineaFichero
                            + ". Se ignorará la línea");
                } finally {
                    numeroLineaFichero++;
                }

            }
        } catch (IOException ex) {
            System.out.println("ERROR: El archivo " + file + " no existe o su formato no es correcto");
        } finally {
            try {
                // Cerramos los archivos.
                br.close();
                fr.close();
            } catch (IOException e) {
                System.out.println("ERROR: No se han podido cerrar los archivos correctamente.");
            }

        }

        return escena;

    }

    public Escena importFromObj(String file) {

        /**
         * **********************************************************************
         * TO-DO: Mètode a implementar: * Llegirà el fitxer indicat, en format
         * d'objectes seriats, i importa * la llista de figures. *
         * **********************************************************************
         */
        // Comentar o elimina aquestes línies quan implementeu el mètode
        // Creamos una excena por defecto, para evitar el nullPointerException.
        // Si posteriormente en el fichero encontramos "dimensions" le cambiaremos el
        // tamaño
        // con el método apropiado
        Escena escena = new Escena();

        // Leemos el fichero
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            // Realizamos la lectura del fichero y al casting a Escena
            while (fis.available() > 0) {
                escena = (Escena) ois.readObject();

            }
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: El archivo " + file + " tiene un formato de objeto no reconocido");

        } catch (IOException ex) {
            System.out.println("ERROR: El archivo " + file + " tiene un formato de objeto no reconocido");

        } finally {
            try {
                // Cerramos los archivos.
                ois.close();
                fis.close();
            } catch (IOException e) {
                System.out.println("ERROR: No se han podido cerrar los archivos correctamente.");
            }

        }

        return escena;

    }

    public Boolean exportText(Escena escena, String file) {

        /**
         * ************************************************ TO-DO: Mètode a
         * implementar: * exporta l'escena donada a un fitxer de text, * en
         * format per poder ser importat posteriorment.*
         * ************************************************
         */
        // Comentar o elimina aquestes línies quan implementeu el mètode
        boolean out = true;
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(file, false);
            bw = new BufferedWriter(fw);

            // Guardamos características escena
            bw.write("dimensions " + escena.getX() + " " + escena.getY());
            bw.newLine();

            for (Figura figura : escena.LlistaFigures) {
                bw.write(figura.getAsText());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("ERROR: Imposible escribir en el fichero " + file);
            // Error en la exportación
            out = false;
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println("ERROR: Cerrando el fichero " + file);
                // Error en la exportación
                out = false;
            }
        }

        return out;

    }

    public Boolean exportObj(Escena escena, String file) {

        /**
         * ********************************************************** TO-DO:
         * Mètode a implementar: * exporta l'escena donada a un fitxer binari
         * d'objectes, * per poder ser importat posteriorment. *
         * **********************************************************
         */
        // Comentar o elimina aquestes línies quan implementeu el mètode
        boolean out = true;

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(escena);

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Guardando el fichero de objetos " + file);
            // Error al guardar
            out = false;
        } catch (IOException e) {
            System.out.println("ERROR: Guardando el fichero de objetos " + file);
            // Error al guardar
            out = false;
        }

        try {
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("ERROR: Guardando el fichero de objetos " + file);
            // Error al guardar
            out = false;
        }

        return out;

    }

    public Boolean exportSVG(Escena escena, String file) {
        /**
         * ********************************************************** TO-DO:
         * Mètode a implementar: * exporta l'escena donada a un fitxer SVG
         * (format XML). * El fitxer s'haurà de poder obrir amb Inkscape. *
         * **********************************************************
         */
        /*
         * <?xmlversion="1.0"encoding="UTF-8"standalone="no"?> 2
         * <svgheight="500"width="500"> <rect fill="#ccccee" height="480" width="480"
         * x="10" y="10"/> <circle cx="250" cy="250" fill="#aaaaaa" r="100"/> <line
         * stroke="#aaaaaa" stroke-width="3" x1="50" x2="450" y1="250" y2="250"/> <line
         * stroke="#aaaaaa" stroke-width="3" x1="50" x2="50" y1="50" y2=" 450"/> <line
         * stroke="#aaaaaa" stroke-width="3" x1="450" x2="450" y1="40" y2= "450"/>
         * </svg>
         */

        // Creamos el documento//
        ///////////////////////
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document documentoXML = null;
        Element elementoPadre = null;

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            documentoXML = dBuilder.newDocument();

            // Creamos nodo principal
            // Creamos el elemento
            elementoPadre = documentoXML.createElement("svg");
            documentoXML.appendChild(elementoPadre);

            // Creamos atributos elemento // Creamos el atributo height
            Attr atributo = documentoXML.createAttribute("height");
            atributo.setValue(Integer.toString(escena.getY()));
            // Escribimos el atributo en el elemento
            elementoPadre.setAttributeNode(atributo);

            // Creamos el atributo width
            atributo = documentoXML.createAttribute("width");
            atributo.setValue(Integer.toString(escena.getX()));
            // Escribimos el atributo en el elemento
            elementoPadre.setAttributeNode(atributo);

        } catch (ParserConfigurationException e) {
            System.out.println("ERROR en la creación del documento XML " + file);
            return false;
        }

        // Componemos el documento a partir de las figuras
        for (Figura figura : escena.LlistaFigures) {
            elementoPadre.appendChild(figura.getAsXML(documentoXML));
        }

        // Escribimos el documento en disco//
        ////////////////////////////////////
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documentoXML);
            StreamResult result = new StreamResult(new File(file));

            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("ERROR en la creación del documento XML " + file);
            return false;
        }
        return true;
    }

    public Boolean exportJSON(Escena escena, String filename) {

        /**
         * ********************************************** TO-DO: Mètode a
         * implementar: * exporta l'escena donada a un fitxer JSON. *
         * **********************************************
         */
        // Cramos objetos JSON
        HashMap mapa = new HashMap();
        JSONObject documentoJSON = new JSONObject();

        JSONArray jsarray = new JSONArray();

        // Creamos las dimensiones de la escena
        mapa.put("width", escena.getX());
        mapa.put("height", escena.getY());

        // Creamos array de figuras JSON
        for (Figura figura : escena.LlistaFigures) {
            jsarray.put(figura.getAsJSON());
        }

        // Cargamos las figuras al mapa
        mapa.put("figures", jsarray);

        documentoJSON.put("escena", mapa);

        try {
            FileWriter file = new FileWriter(filename);
            // 4 espacios de identación
            file.write(documentoJSON.toString(4));
            file.close();

        } catch (IOException e) {
            System.out.println("Error escribiendo el fichero " + filename);
            return false;
        }

        return true;
    }

    // Si alguno de los parámetros no es correcta enviaremos el throws hacia arriba
    // para que nos los captura importFromText
    private void anadirFiguraEscena(Escena escena, String[] figura) throws Exception {

        switch (figura[0]) {

            case "cercle":
                int x = Integer.parseInt((figura[1]));
                int y = Integer.parseInt((figura[2]));
                int radi = Integer.parseInt((figura[3]));
                String color = figura[4];
                escena.add(new Cercle(x, y, radi, color));
                break;

            case "rectangle":
                x = Integer.parseInt((figura[1]));
                y = Integer.parseInt((figura[2]));
                int llarg = Integer.parseInt((figura[3]));
                int alt = Integer.parseInt((figura[4]));
                color = figura[5];
                escena.add(new Rectangle(x, y, llarg, alt, color));
                break;

            case "linia":
                x = Integer.parseInt((figura[1]));
                y = Integer.parseInt((figura[2]));
                int x2 = Integer.parseInt((figura[3]));
                int y2 = Integer.parseInt((figura[4]));
                color = figura[5];
                escena.add(new Linia(x, y, x2, y2, color));
                break;

            // Si no es ninguna de las anteriores forzamos un throws "hacia arriba" para que
            // lo diagnostique como línea incorrecta
            default:
                throw new Exception();
        }
    }

}
