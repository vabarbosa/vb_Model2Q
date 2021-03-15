package domain.generators;

import java.util.ArrayList;
import java.util.Iterator;

public class QiskitGenerator {
	
	ArrayList<Integer> controlQubits = new ArrayList();  // Si controlled==true, el cable en el que estaba el símbolo de control. 
	ArrayList<Boolean> controlValues = new ArrayList();  // Si controlled==true, el valor que debe tener el bit de control, 0 o 1.
	String circuitName = "";
	
	public String generateAlgorithmCode(String name, ArrayList<ArrayList<ArrayList<Object>>> steps) {
		String code = "";
		circuitName = name;
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
		boolean controlled = false;  // Booleano para evitar hacer n veces la comprobación if(controlQubits.size() != 0){...}
		if(checkForControls(step)) {
			controlled = true;
		}
		Iterator iterator = step.iterator();
		int nQubit = 0;
		String code = "";
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
						code += circuitName + ".h(qreg_q[" + nQubit + "])";
					}else {
						code += controlledUnitaryGate("h", nQubit);
					}
					break;
				case "X":      // X gate.
					if(!controlled) {
						code += circuitName + ".x(qreg_q[" + nQubit + "])";
					}else {
						code += controlledUnitaryGate("x", nQubit);
					}
					break;
				case "Y":      // Y gate.
					if(!controlled) {
						code += circuitName + ".y(qreg_q[" + nQubit + "])";
					}else {
						code += controlledUnitaryGate("y", nQubit);
					}
					break;
				case "Z":     // Z gate.
					if(!controlled) {
						code += circuitName + ".z(qreg_q[" + nQubit + "])";
					}else {
						code += controlledUnitaryGate("z", nQubit);
					}
					break;
				case "Z^½":    // S gate.
					if(!controlled) {
						code += circuitName + ".s(qreg_q[" + nQubit + "])";
					}else {
						code += controlledUnitaryGate("s", nQubit);
					}
					break;
				case "Z^¼":    // T gate.
					if(!controlled) {
						code += circuitName + ".t(qreg_q[" + nQubit + "])";
					}else {
						code += controlledUnitaryGate("t", nQubit);
					}
					break;
				case "Swap":   // Swap gate.
					if(switchQubit == -1) {    // Si es la primera parte del Swap, simplemente almacenamos el número de qubit
						switchQubit = nQubit;
					}else {                    // Si es la segunda, escribimos.
						if(!controlled) {
							code += circuitName + "swap(qreg_q[" + switchQubit + "], qreg_q[" + nQubit + "])";
						}else {
							   // ToDo
						}
					}
					break;
				default:       // Control or identity gates.
					// Do nothing.
					break;
			}
			code += "\r\n";
			nQubit++;
		}
		code += "\r\n";
		return code;
	}
	
	private boolean checkForControls(ArrayList step) {
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
		if(controlQubits.size()==0) {return false;}
		return true;
	}
	
	private String controlledUnitaryGate(String gate, int nQubit) {
		// Temporal debido a la limitación de las puertas controladas
		// en el ecosistema IBM.
		
		String code = circuitName + ".";
		if(controlQubits.size() == 2) {
			code += "cc" + gate + "(qreg_q[" + controlQubits.get(0) + 
					"], qreg_q[" + controlQubits.get(1) + "], qreg_q[" + 
					nQubit + "])";
		}else if(controlQubits.size() == 1) {
			code += "c" + gate + "(qreg_q[" + controlQubits.get(0) + 
					"], qreg_q[" + nQubit + "])";
		}else {
			code = "";
		}
		return code;
	}
}
