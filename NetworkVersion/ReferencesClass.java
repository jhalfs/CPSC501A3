
public class ReferencesClass {
	SimpleObjectClass simple;
	public ReferencesClass(){
		
	}

	public ReferencesClass(String title, int num, int numTwo) {
		simple = new SimpleObjectClass(title, num, numTwo);
	}
}
