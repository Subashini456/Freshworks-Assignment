package studentPackages.com;

import java.util.HashMap;
import java.util.Map;

// Class to encapsulate key-value pair of student id and student details.
public class College {

	// Key value pair to store student id and student details.
	private Map<String, Student> keyValueMap = new HashMap<String, Student>();
	
	// Function to get mapped student id and student details.
	public Map<String, Student> getKeyValueMap() {
		return keyValueMap;
	}

	// Function to set key value map of student id and student details.
	public void setKeyValueMap(Map<String, Student> keyValueMap) {
		this.keyValueMap = keyValueMap;
	}
	
}
