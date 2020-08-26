package domain.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;

public class JsonParser {
	public static LinkedTreeMap readJSON(String jsonPath) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		Object object = gson.fromJson(new FileReader(jsonPath), Object.class);
		LinkedTreeMap jso = (LinkedTreeMap) object;
		return jso;
	}
}
