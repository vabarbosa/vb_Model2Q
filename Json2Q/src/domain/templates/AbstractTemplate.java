package domain.templates;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractTemplate {

	public String generateCode(String fileName, int nQbits, ArrayList<ArrayList<ArrayList<Object>>> columns) {
		String code = "";
		code += header(fileName, nQbits);
		Iterator<ArrayList<ArrayList<Object>>> iterator = columns.iterator();
		while (iterator.hasNext()) {
			ArrayList<Object> column = (ArrayList) iterator.next();
			code += bodyStep(column);
		}
		code += tail();
		
		return code;
	}

	protected abstract String bodyStep(ArrayList column);
	
	protected abstract String tail();

	protected abstract String header(String fileName, int nWires);

}
