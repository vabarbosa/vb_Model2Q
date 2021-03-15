package domain.templates;

import java.util.ArrayList;

import domain.ProductFeatures;
import domain.generators.QSharpGenerator;

public class QSharpTemplate extends AbstractTemplate{

	QSharpGenerator generator = new QSharpGenerator();
	
	@Override
	public String generateCode(ProductFeatures productFeatures) {
		return "namespace QL_Test {\r\n" + 
				"    \r\n" + 
				"    open Microsoft.Quantum.Canon;\r\n" + 
				"    open Microsoft.Quantum.Intrinsic;\r\n" + 
				"    \r\n" + 
				"    operation " + productFeatures.getProductName() + "(qubits : Qubit[]) : Unit is Adj + Ctl{" +
				"    \r\n" + 
				"    if(size(qubits) == " + productFeatures.getnQubits() + "){\r\n" +
				generator.generateAlgorithmCode(productFeatures.getSteps()) + 
				"    }\r\n" + 
				"}";
	}
}