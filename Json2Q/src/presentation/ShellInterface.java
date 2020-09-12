package presentation;

import java.io.File;
import java.io.FileNotFoundException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import domain.Translator;

public class ShellInterface {
	public static void main (String args[]) {
		System.out.println("Json2Q\r\n");
		errorMessages(args);
		System.out.println("Reading from: " + args[0] + "...");
		System.out.println("Input format: " + args[1] + ".");
		System.out.println("Output languaje: " + args[2] + ".\r\n");
		Translator translator = new Translator();
		try{
			translator.translate(args[0], args[1], args[2]);
		}catch (Exception e) {
			if(e instanceof JsonSyntaxException 
					|| e instanceof JsonIOException 
					|| e instanceof FileNotFoundException) {
				System.out.println("Error while performing JSON file parse!");
			}
			e.printStackTrace();
		}
	}

	private static void errorMessages(String[] args) {
		if (checkParameters(args) == -1) {
			System.out.println("Usage mode is: ");
			System.out.println("              java Json2Q \"inputFilePath\" \"inputFormat\" \"outputLanguaje\"");
			return;
		}
		if (!new File(args[0]).exists()) {
			System.out.println("Error: file doesn´t exist!");
			return;
		}
	}

	private static int checkParameters(String[] args) {
		if(args.length != 3 
				|| (!args[1].equals("JSON"))
			    || (!args[2].equals("Q#") && !args[2].equals("Qiskit"))){
			return -1;
		}
		return 0;
	}
}
