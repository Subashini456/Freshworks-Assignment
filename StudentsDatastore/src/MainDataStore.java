import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import studentPackages.com.CRDoperations;
import studentPackages.com.College;
import studentPackages.com.Student;

// Class which holds main method.
public class MainDataStore {

	// Function to check whether given string is integer or not.
	public static boolean integerCheck(String details) {
		if (details != null && details.matches("[0-9]+")) {
			return true;
		} else {
			return false;
		}
	}

	// Function to check whether given string contains only alphabet.
	public static boolean stringCheck(String details) {
		if (details != null && details.matches("^[a-zA-Z]*$")) {
			return true;
		} else {
			return false;
		}
	}

	// Main function.
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		//object created for CRDoperations class
		CRDoperations operation = new CRDoperations();

		String filePath = "";
		
		//object created for College class
		College collegeDetails = new College();
		
		
		Map<String, Student> keyValueMap = new ConcurrentHashMap<>();

		// To create a file path
		try {

			System.out.println("Enter the file path");

			filePath = sc.nextLine();
			File file;

			// if file path is a empty string then it would creates file in the current
			// directory
			if (filePath.isEmpty()) {
				file = new File("dataStore.json");
				filePath = file.getAbsolutePath();
				System.out.println("File would be created in the file path :" + filePath);
			}

			// if file path is provided then it would create the file in the specified directory
			else {
				filePath = filePath + File.separator + "dataStore.json";

				file = new File(filePath);

				file.getParentFile().mkdir();

				System.out.println("File would be created in the file path :" + filePath);
			}

			// checks for file existence.If file is not in existence,creates a file
			if (file.createNewFile()) {

				System.out.println("File created: " + file.getName());

			} else {
				System.out.println("File already exists.");
				
				//retrieve the Json objects from the file and convert to java objects
				keyValueMap = operation.retrieveJsonObjectsToJavaObjects(filePath, collegeDetails);
				collegeDetails.setKeyValueMap(keyValueMap);
			}

		} catch (IOException e) {

			System.err.println("An error occurred.");
			e.printStackTrace();

		}

		// Infinite loop
		//Stops when case 4 is executed
		//exit case has to be executed everytime inorder to see updated data in the datastore.json file
		while (true) {

			try {
				String details = null, idString = null;

				System.out.println("\n1.Create the data \n2.Read the data from the file \n3.Delete the data in the file \n4.exit the program execution");
				System.out.print("Please select any one option : ");

				int options = sc.nextInt();

				// 1.create 2.read 3.delete 4.exit
				switch (options) {
				
				//Creating the data
				case 1:
					//getting key as student id from user
					System.out.print("Enter the student id : ");
					idString = sc.next();
					
					//validating student id
					if (!integerCheck(idString)) {
						throw new Exception("FAILED: enter valid number");
					}
					
					//checking constraints whether the length of student id is not more than 32 charcters.
					if (idString.length() > 32) {
						throw new Exception("FAILED : The length of student id must be less than or equal to 32");
					}
					
					//verifying whether the key to be inserted is already in existence in the file
					if (keyValueMap.containsKey(idString)) {
						throw new Exception("FAILED : Please enter unique student id");
					}
					
					//object created for Student class 
					Student newStudent = new Student();
					
					//getting student detail values from user
					
					System.out.print("Enter the student name : ");
					details = sc.next();
					
					//validating student name
					if (!stringCheck(details)) {
						throw new Exception("FAILED: enter valid string");
					}

					newStudent.setName(details);

					System.out.print("Enter the student total marks: ");
					details = sc.next();

					//validating student total marks
					if (!integerCheck(details)) {
						throw new Exception("FAILED: enter valid number");
					}

					newStudent.setTotalMarks(details);
					
					//adding the key(idString) and value(newStudent) as object in KeyValueMap(ConcurrentHashMap)
					keyValueMap.put(idString, newStudent);
					
					//validating whether the size of Json Object value is less than 16KB 
					if (operation.determineJsonObjectSizes(newStudent)) {
						collegeDetails.setKeyValueMap(keyValueMap);
					} else {
						keyValueMap.remove(idString);
						throw new Exception("FAILED: Current json value size exceeds 16KB");
					}

					break;
				
				//Reading the data from the file
				case 2:
					
					//getting key from user inorder to read the value.
					System.out.print("Enter the student id to read the details: ");
					details = sc.next();
					
					//checking for the existence of key
					if (!keyValueMap.containsKey(details)) {
						throw new Exception("FAILED : Please enter valid available student id");
					}
					
					System.out.println(keyValueMap.get(details).toString());
					
					break;
				
				//Deleting the data in the file
				case 3:
					//getting key from user inorder to delete the key-value pair.
					System.out.print("Enter the student id to delete the details: ");
					details = sc.next();
					
					//checking for the existence of key
					if (!keyValueMap.containsKey(details)) {
						throw new Exception("Please enter valid available student id to delete");
					}
					
					//removing the key-value pair
					keyValueMap.remove(details);
					System.out.printf("Student id %s was deleted sucessfully\n", details);
					break;
				
				//exit
				case 4:
					
					//to flush the already existing content inorder to update the file
					PrintWriter writer = new PrintWriter(filePath);
					writer.close();
					
					//updating the file
					operation.create(filePath, collegeDetails, true);
					System.out.println("\nProgram Terminated");
					System.exit(0);
					break;
					
				//this case would be executed if the entered option is invalid
				default:
					System.out.printf("%s is not a valid option. Please select a valid option\n", options);
					break;
				}

			}

			catch (Exception e) {
				System.out.println(e.toString());
				continue;
			}

		}
	}
}
