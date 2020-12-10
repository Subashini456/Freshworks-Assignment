package studentPackages.com;

// Class to encapsulate student details.
public class Student {

	// Variable to store student name.
	private String studentName;
	
	// Variable to store student total marks.
	private String totalMarks;

	// Function to get the student name.
	public String getName() {
		return studentName;
	}

	// Function to set student name.
	public void setName(String name) {
		this.studentName = name;
	}

	// Function to get student total marks.
	public String getTotalMarks() {
		return totalMarks;
	}

	// Function to set student total marks.
	public void setTotalMarks(String marks) {
		this.totalMarks = marks;
	}

	// Overrided function to display the student object details.
	@Override
	public String toString() {
		return "Student [studentName=" + studentName + ", totalMarks=" + totalMarks + "]";
	}

}
