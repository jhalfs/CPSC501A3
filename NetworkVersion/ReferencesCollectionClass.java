import java.util.LinkedList;

public class ReferencesCollectionClass {
	LinkedList<Object> objectsList = new LinkedList<Object>();
	
	public ReferencesCollectionClass(){
		
	}
	
	public ReferencesCollectionClass(Object o, Object p, Object q){
		objectsList.add(o);
		objectsList.add(p);
		objectsList.add(q);
	}
}
