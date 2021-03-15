package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileBroker {
	
	// Toma como entrada un directorio y lo copia recusivamente en el directorio indicado.
	public static void copyFolder(File src, File dest) throws IOException {
		if(src==null || dest==null)
			return;
		if(!src.isDirectory())
			return;
		if(dest.exists()){
			if(!dest.isDirectory()){
				System.out.println("destination not a folder " + dest);
				return;
			}
		} else {
			dest.mkdir();
		}

		if(src.listFiles()==null || src.listFiles().length==0)
			return;

		for(File file: src.listFiles()){
			File fileDest = new File(dest, file.getName());
			if(file.isDirectory()){
				copyFolder(file, fileDest);
			}else{
				if(fileDest.exists())
					continue;
				
				Files.copy(file.toPath(), fileDest.toPath());
			}
		}
	}

	//Escribe una cadena en un archivo.
	public static void write(String inputPath, String productName, String code, String outputLanguaje) throws IOException {
		// Creamos el directorio de salida.
		File parentFile = new File(new File(inputPath).getParentFile().getAbsolutePath() + System.getProperty("file.separator") );
		// Seleccionamos la extensión del archivo en función del lenguaje. 
		String extension = fileExtension(outputLanguaje);
		File outputFile = new File(parentFile + System.getProperty("file.separator") + productName + extension + System.getProperty("file.separator"));
		outputFile.createNewFile();
		FileWriter myWriter = new FileWriter(outputFile);
		myWriter.write(code);
		myWriter.close();
	}
	
	private static String fileExtension(String outputLanguaje) {
		String extension = "";
		switch (outputLanguaje){
			case "Q#":
				extension = ".qs";
				break;
			case "Qiskit":
				extension = ".py";
				break;
			default:
		}
		return extension;
	}
}

