package domain;

import java.util.ArrayList;

public class ProductFeatures {
	private String productName;
	private int nQubits;
	private ArrayList steps;
	
	public ProductFeatures(String productName, int nQubits, ArrayList steps) {
		super();
		this.productName = productName;
		this.nQubits = nQubits;
		this.steps = steps;
	}
	
	public String getProductName() {
		return productName;
	}
	public int getnQubits() {
		return nQubits;
	}
	public ArrayList getSteps() {
		return steps;
	}
}
