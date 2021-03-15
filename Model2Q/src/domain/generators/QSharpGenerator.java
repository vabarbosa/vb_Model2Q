package domain.generators;

import java.util.ArrayList;
import java.util.Iterator;

public class QSharpGenerator{
	
	ArrayList<Integer> controlQubits = new ArrayList();  // Si controlled==true, el cable en el que estaba el símbolo de control. 
	ArrayList<Boolean> controlValues = new ArrayList();  // Si controlled==true, el valor que debe tener el bit de control, 0 o 1.
	
	public String generateAlgorithmCode(ArrayList<ArrayList<ArrayList<Object>>> steps) {
		String code = "";
		Iterator iterator = steps.iterator();
		while(iterator.hasNext()) {
			ArrayList step = (ArrayList) iterator.next();
			code += generateStepCode(step);
		}
		return code;
	}
	
	protected String generateStepCode(ArrayList step) {
		int switchQubit = -1;  // Si hemos encontrado una puerta switch pero no su pareja, marca el número del qubit desemparejado. De lo contrario, -1.
		controlQubits = new ArrayList<Integer>();
		controlValues = new ArrayList<Boolean>();
		checkControls(step);
		boolean controlled = false;  // Booleano para evitar hacer n veces la comprobación if(controlQubits.size() != 0){...}
		if(controlQubits.size() != 0) {
			controlled = true;
		}
		Iterator iterator = step.iterator();
		int nQubit = 0;
		String code = "        ";  // Tabulamos el código.
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
						code += "H(qubits[" + nQubit + "]);";
					}else {
						code += controlledUnitaryGate("H", nQubit);
					}
					break;
				case "X":      // X gate.
					if(!controlled) {
						code += "X(qubits[" + nQubit + "]);";
					}else {
						code += controlledUnitaryGate("X", nQubit);
					}
					break;
				case "Y":      // Y gate.
					if(!controlled) {
						code += "Y(qubits[" + nQubit + "]);";
					}else {
						code += controlledUnitaryGate("Y", nQubit);
					}
					break;
				case "Z":    // Z gate.
					if(!controlled) {
						code += "Z(qubits[" + nQubit + "]);";
					}else {
						code += controlledUnitaryGate("S", nQubit);
					}
					break;
				case "Z^½":    // S gate.
					if(!controlled) {
						code += "S(qubits[" + nQubit + "]);";
					}else {
						code += controlledUnitaryGate("S", nQubit);
					}
					break;
				case "Z^¼":    // T gate.
					if(!controlled) {
						code += "T(qubits[" + nQubit + "]);";
					}else {
						code += controlledUnitaryGate("S", nQubit);
					}
					break;
				case "Swap":   // Swap gate.
					if(switchQubit == -1) {    // Si es la primera parte del Swap, simplemente almacenamos el número de qubit
						switchQubit = nQubit;
					}else {                    // Si es la segunda, escribimos.
						if(!controlled) {
							code += "SWAP(qubits[" + switchQubit + "], qubits[" + nQubit + "])";
						}else {
							// ToDo
						}
					}
					break;
				default:       // Control or identity gates.
					// Do nothing.
					break;
			}
			nQubit++;
		}
		code += "\r\n";
		return code;
	}
	
	private void checkControls(ArrayList step) {
		String gate = "";
		int nQubit = 0;
		Iterator iterator = step.iterator();
		while(iterator.hasNext()) {
			Object item = iterator.next();
			if(item instanceof String) {
				gate = item.toString();
			}else {
				gate = "1";
			}
			switch (gate) {
			case "•":      // Control symbol.
				controlQubits.add(nQubit);
				controlValues.add(true);
				break;
			case "◦":      // Anticontrol symbol.
				controlQubits.add(nQubit);
				controlValues.add(false);
				break;
			default:       // There is no gate.
				// Do nothing.
				break;
			}
			nQubit++;
		}
	}
	
	private String controlledUnitaryGate(String gate, int nQubit) {
		String code = "(ControlledOnBitString([";
		Iterator iterator = controlValues.iterator();
		while(iterator.hasNext()) {
			code += iterator.next();
		}
		code += "]," + gate + "))([";
		iterator = controlQubits.iterator();
		while(iterator.hasNext()) {
			code += "qubits[" + iterator.next() + "]";
			if(iterator.hasNext()) {
				code += ", ";
			}
		}
		code += "], " + nQubit + ");";	
		return code;
	}
}
