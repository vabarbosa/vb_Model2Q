package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import domain.parser.JsonParser;
import domain.templates.AbstractTemplate;
import domain.templates.QSharpTemplate;
import domain.templates.QiskitTemplate;

public class Translator {

	String fileName = "";
	int nQbits = 0;
	AbstractTemplate template;
	String code = "";
	
	public void translate(String jsonPath, String languaje) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		// Parseamos el archivo JSON recibido.
		LinkedTreeMap jso = JsonParser.readJSON(jsonPath);
		System.out.println("JSON file obtained:");
		System.out.println("\t" + jso.toString().replaceAll("\r\n", "\r\n\t") + "\r\n");
		// Extraemos las columnas del archivo JSON (correspondientes a "pasos del circuito").
		ArrayList columns = (ArrayList) jso.get("cols");
		// Calculamos el n�mero de qbits necesarios para el circuito.
		nQbits = getMaxColumnSize(columns);
		// Creamos la plantilla adecuada para el lenguaje al que queremos traducir.
		template = instanceTemplate(languaje);
		// Tomamos el nombre del archivo JSON para usarlo como nombre de la operaci�n.
		fileName = new File(jsonPath).getName();
		fileName = fileName.substring(0, fileName.length()-6);
		// Generamos el c�digo por medio de la plantilla.
		code = template.generateCode(fileName, nQbits, columns);
		System.out.println("Generated code:");
		System.out.println("\t" + code.replaceAll("\r\n", "\r\n\t"));
	}

	private AbstractTemplate instanceTemplate(String languaje) {
		if(languaje.equals("Q#")) {
			return new QSharpTemplate();
		}else if(languaje.equals("Qiskit")) {
			return new QiskitTemplate();
		}
		return null;
	}

	private int getMaxColumnSize(ArrayList columns) {
		int maxSize = 0;
		Iterator<ArrayList> iterator = columns.iterator();
		while (iterator.hasNext()) {
			ArrayList column = iterator.next();
			if(column.size() > maxSize) {
				maxSize = column.size();
			}
		}
		return maxSize;
	}

}
