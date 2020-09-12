package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import domain.parsers.AbstractParser;
import domain.parsers.JsonParser;
import domain.templates.AbstractTemplate;
import domain.templates.QSharpTemplate;
import domain.templates.QiskitTemplate;
import persistence.FileBroker;

public class Translator {

	String fileName = "";
	int nQbits = 0;
	AbstractParser parser;
	AbstractTemplate template;
	String code = "";
	
	public void translate(String inputPath, String inputFormat, String outputLanguaje) throws JsonSyntaxException, JsonIOException, FileNotFoundException, Exception {
		// Creamos el parser adecuado para el formato del que queremos traducir.
		instanceParser("JSON");
		// Creamos la plantilla adecuada para el lenguaje al que queremos traducir.
		instanceTemplate(outputLanguaje);
		// Parseamos el archivo JSON recibido.
		LinkedTreeMap jso = parser.readFile(inputPath);
		System.out.println("JSON file obtained:");
		System.out.println("\t" + jso.toString().replaceAll("\r\n", "\r\n\t") + "\r\n");
		// Extraemos las columnas del archivo JSON (correspondientes a "pasos del circuito").
		ArrayList columns = (ArrayList) jso.get("cols");
		// Calculamos el n�mero de qbits necesarios para el circuito.
		nQbits = getMaxColumnSize(columns);
		// Tomamos el nombre del archivo JSON para usarlo como nombre de la operaci�n.
		fileName = new File(inputPath).getName();
		fileName = fileName.substring(0, fileName.length()-5);
		// Generamos el c�digo por medio de la plantilla.
		code = template.generateCode(fileName, nQbits, columns);
		// Creamos el directorio de salida.
		File parentFile = new File(new File(inputPath).getParentFile().getAbsolutePath() + System.getProperty("file.separator") 
				+ "Json2Q" + System.getProperty("file.separator"));
		parentFile.mkdirs();
		// Creamos el archivo de salida y lo escribimos.
		File outputFile = new File(parentFile + System.getProperty("file.separator") + fileName + ".qs" + System.getProperty("file.separator"));
		FileBroker.write(outputFile, code);
		System.out.println("Generated code:");
		System.out.println("\t" + code.replaceAll("\r\n", "\r\n\t"));
		System.out.println("\r\nCode successfully written!");
	}

	private void instanceParser(String format) throws Exception {
		if(format.equals("JSON")) {
			parser = new JsonParser();
		}else {
			Exception e = new Exception("Formato de entrada incorrecto");
			throw e;
		}
	}
	
	private void instanceTemplate(String languaje) throws Exception {
		if(languaje.equals("Q#")) {
			template = new QSharpTemplate();
		}else if(languaje.equals("Qiskit")) {
			template = new QiskitTemplate();
		}else {
			Exception e = new Exception("Formato de entrada incorrecto");
			throw e;
		}
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
