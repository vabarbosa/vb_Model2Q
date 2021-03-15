package domain.templates;

import java.util.ArrayList;

import domain.ProductFeatures;
import domain.generators.QiskitGenerator;

public class QiskitTemplate extends AbstractTemplate {
	
	QiskitGenerator generator = new QiskitGenerator();
	
	@Override
	public String generateCode(ProductFeatures productFeatures) {
		return  "from qiskit import QuantumRegister, ClassicalRegister, QuantumCircuit\r\n" + 
				"from numpy import pi    \r\n" +
				"\r\n" + 
				"qreg_q = QuantumRegister(" + productFeatures.getnQubits() + ", 'q')\r\n" + 
				"creg_c = ClassicalRegister(" + productFeatures.getnQubits() + ", 'c')\r\n" + 
				productFeatures.getProductName() + " = QuantumCircuit(qreg_q, creg_c)\r\n" + 
				"    \r\n" + 
				generator.generateAlgorithmCode(productFeatures.getProductName(), productFeatures.getSteps());
	}
}