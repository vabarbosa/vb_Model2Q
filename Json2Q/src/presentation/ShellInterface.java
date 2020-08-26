package presentation;

import java.io.FileNotFoundException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import domain.Translator;

public class ShellInterface {
	public static void main (String args[]) {
		System.out.println("Json2Q\r\n");
		if (checkParameters(args) == -1) {
			System.out.println("Usage mode is: java Json2Q \"jsonFilePath\"");
			return;
		}
		Translator translator = new Translator();
		try{
			translator.translate(args[0], args[1]);
		}catch (Exception e) {
			if(e instanceof JsonSyntaxException 
					|| e instanceof JsonIOException 
					|| e instanceof FileNotFoundException) {
				System.out.println("Error while performing JSON file parse!");
			}
			e.printStackTrace();
		}
	}

	private static int checkParameters(String[] args) {
		if(args.length != 2 
				|| (args[1].equals("Q#") && args[1].equals("Qiskit"))) {
			return -1;
		}
		return 0;
	}
}
