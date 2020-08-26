package domain.templates;

import java.util.ArrayList;
import java.util.Iterator;

public class QSharpTemplate extends AbstractTemplate{

	@Override
	protected String header(String fileName) {
		return "namespace QL_Test {\r\n" + 
				"    \r\n" + 
				"    open Microsoft.Quantum.Canon;\r\n" + 
				"    open Microsoft.Quantum.Intrinsic;\r\n" + 
				"    \r\n" + 
				"    operation " + fileName + "(qubits : Qubit[]) : Unit is Adj + Ctl{" +
				"    \r\n";
	}

	@Override
	protected String bodyStep(ArrayList column) {
		boolean controlled = false; // Si nos hemos encontrado con un símbolo de control y no hemos creado la puerta controlada correspondiente.
		int controlWire = 0;        // Si controlled==true, el cable en el que estaba el símbolo de control. 
		int controlValue = 0;       // Si controlled==true, el valor que debe tenre el bit de control, 0 o 1.
		String code = "        ";
		Iterator iterator = column.iterator();
		int nWire = 0;
		while(iterator.hasNext()) {
			Object item = iterator.next();
			String gate = "";
			if(item instanceof String) {
				gate = item.toString();
			}else {
				gate = "1";
			}

			switch (gate) {
				case "H":      // H gate.
					if(!controlled) {
						code += "H(qubits[" + nWire + "]);";
					}else {
						if(controlValue == 1) {
							code += "Controlled H([qubits[" + controlWire + "]], qubits[" + nWire + "]);";
							controlled = false;
						}else {
							// ToDo
						}
					}
					break;
				case "X":      // Z gate.
					if(!controlled) {
						code += "X(qubits[" + nWire + "]);";
					}else {
						if(controlValue == 1) {
							code += "Controlled X([qubits[" + controlWire + "]], qubits[" + nWire + "]);";
							controlled = false;
						}else {
							// ToDo
						}
					}
					break;
				case "Y":      // Y gate.
					if(!controlled) {
						// ToDo
					}else {
						// ToDo
						controlled = false;
					}
					break;
				case "Z^½":    // S gate.
					if(!controlled) {
						// ToDo
					}else {
						// ToDo
						controlled = false;
					}
					break;
				case "Swap":   // Swap gate
					if(!controlled) {
						// ToDo
					}else {
						// ToDo
						controlled = false;
					}
					break;
				case "•":      // Control symbol
					controlled = true;
					controlWire = nWire;
					controlValue = 1;
					break;
				case "◦":      // Anticontrol symbol
					// ToDo
					break;
				default:       // There is no gate.
					// Do nothing.
					break;
			}
			nWire++;
		}
		code += "\r\n";
		return code;
	}

	@Override
	protected String tail() {
		return "    }\r\n" + 
				"}";
	}


}
