package domain.parsers;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

public abstract class AbstractParser {
	public abstract LinkedTreeMap readFile(String jsonPath) throws JsonSyntaxException, JsonIOException, FileNotFoundException;
}
	
