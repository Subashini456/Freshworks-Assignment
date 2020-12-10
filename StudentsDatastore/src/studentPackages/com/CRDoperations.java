package studentPackages.com;

import java.io.*;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Class to encapsulate create operation.
public class CRDoperations {

	// Function to create Json objects and write it in the provided file path.
	public void create(String filePath, Object object, boolean status) {
		File file = new File(filePath);

		// Converts hash map objects to Json objects by passing college object.
		String jsonObject = hashMapObjectToJsonObject(object);

		PrintWriter writer;
		try {
			// Write the Json objects in the specified file path.
			writer = new PrintWriter(new BufferedWriter(new FileWriter(file, status)));
			writer.println(jsonObject);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Function to convert hash map in college object to Json string objects format.
	private String hashMapObjectToJsonObject(Object object) {
		String jsonString = "";
		try {
			//Provides a Json string formatted college object.
			jsonString = new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	// Function to retrieve Json objects from the data store file and convert to Java Class objects.
	public Map<String, Student> retrieveJsonObjectsToJavaObjects(String filePath, College object) {
		ObjectMapper mapper = new ObjectMapper();
		College details = null;
		try {
			File file = new File(filePath);
			// Read the Json objects from the data store file and change it to Java College class type.
			details = mapper.readValue(file, College.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return details.getKeyValueMap();
	}

	// Function to determine json object value size.
	public boolean determineJsonObjectSizes(Student object) {
		// Converting 16 kilo bytes to bytes. 
		int jsonObjectSize = 16 * 1024; 
		try {
			//Retrieve the Json string object from Student object.
			String json = new ObjectMapper().writeValueAsString(object);
			//Check for Json object value size.
			if (determineStringBytes(json) > jsonObjectSize) {
				return false;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Function to determine the given string size in bytes.
	public long determineStringBytes(String value) {
		return value.getBytes().length;
	}

}
