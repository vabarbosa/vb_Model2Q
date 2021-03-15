package domain.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import domain.ProductFeatures;

public class JsonParser extends AbstractParser {
	
	public ProductFeatures readFile(String inputPath) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		// Usamos Gson para parsear el archivo JSON.
		Gson gson = new Gson();
		Object object = gson.fromJson(new FileReader(inputPath), Object.class);
		LinkedTreeMap jso = (LinkedTreeMap) object;
		System.out.println("JSON file obtained:");
		System.out.println("\t" + jso.toString().replaceAll("\r\n", "\r\n\t") + "\r\n");
		// Extraemos las columnas del archivo JSON (correspondientes a "pasos de computación").
		ArrayList steps = (ArrayList) jso.get("cols");
		// Calculamos el n�mero de qbits necesarios para el circuito.
		int nQubits = getMaxColumnSize(steps);
		// Tomamos el nombre del archivo JSON para usarlo como nombre de la operaci�n.
		String productName = new File(inputPath).getName();
		productName = productName.substring(0, productName.length()-5);
		// Creamos la instancia de ProductFeatures.
		ProductFeatures pFeatures = new ProductFeatures(productName, nQubits, steps);
		return pFeatures;
	}
}
