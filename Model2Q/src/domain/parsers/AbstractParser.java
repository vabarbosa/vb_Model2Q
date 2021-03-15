package domain.parsers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

import domain.ProductFeatures;

public abstract class AbstractParser {
	
	public abstract ProductFeatures readFile(String jsonPath) throws JsonSyntaxException, JsonIOException, FileNotFoundException;
	protected int getMaxColumnSize(ArrayList columns) {
		int maxSize = 0;
		Iterator<ArrayList> iterator = columns.iterator();
		while (iterator.hasNext()) {
			ArrayList column = iterator.next();
			if(column.size() > maxSize) {
				maxSize = column.size();
			}
		}
		return maxSize;
	}
}
	
