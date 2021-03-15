package domain.templates;

import java.util.ArrayList;
import java.util.Iterator;

import domain.ProductFeatures;

public abstract class AbstractTemplate {
	public abstract String generateCode(ProductFeatures productFeatures);
}
