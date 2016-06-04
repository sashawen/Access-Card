package ymss.csc.stores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class AbstractJSONStore {
	protected String parseString(JSONObject o, String key, String _default) {
		if (!o.containsKey(key))
			return _default;
		return (String) o.get(key);
	}

	protected Integer parseInt(JSONObject o, String key, Integer _default) {
		if (!o.containsKey(key))
			return _default;
		return Math.toIntExact((Long) o.get(key));
	}

	protected Double parseDouble(JSONObject o, String key, Double _default) {
		if (!o.containsKey(key))
			return _default;
		return (double) o.get(key);
	}

	protected Boolean parseBoolean(JSONObject o, String key, Boolean _default) {
		if (!o.containsKey(key))
			return _default;
		return (boolean) o.get(key);
	}

	protected JSONArray parseArray(JSONObject o, String key) {
		if (!o.containsKey(key))
			return null;
		return (JSONArray) o.get(key);
	}

	protected JSONObject parseObject(JSONObject o, String key) {
		if (!o.containsKey(key))
			return null;
		return (JSONObject) o.get(key);
	}

	protected Date parseDate(JSONObject o, String key) {
		String dateString = parseString(o, key, null);
		if (dateString == null)
			return new Date();

		DateFormat df = DateFormat.getDateInstance();
		try {
			return df.parse(dateString);
		} catch (Exception e) {
			return new Date();
		}
	}

	public static void writeJSONToFile(JSONObject obj, String filename) {

		try {

			File file = new File(filename);

			// if file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(obj.toJSONString());
			bw.flush();
			bw.close();

			//System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
