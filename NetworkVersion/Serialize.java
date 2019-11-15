
import org.jdom2.*;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Vector;

import static java.lang.System.out;

//This should work for normal stuff, IDK about arrays
public class Serialize {
	Class clazz;
	Constructor constructor;
	Object obj;
	Element root;
	Element clz;
	Element clz1;
	int recursion = 0;
	int length;
	
	public void serialize(Object obj, Element root) {
		this.root = root;
		this.obj = obj;
		clazz =  obj.getClass();
		serializeClass();
		
	}

	//Make a new element for the recursive stuff
	//That way the base clz will stay the same and we shouldn't run into the duplicate content issue
	private void serializeClass() {
		if(recursion == 1){
			clz1 = new Element(clazz.getSimpleName());

			int id = obj.hashCode();
			clz1.setAttribute("Index", String.valueOf(id));

			clz1.setAttribute("Name", clazz.getName());
			if(clazz.getName().contains("Reference")){
				recursion = 1;
			}

			if(clazz.getDeclaredFields().length>0){
				serializeFields();
			}
			//We're adding the same thing to the root twice, so it gives us the error
			//clz.detach();
			System.out.println(clz1.getName());
			//root.addContent(clz1);
			//clz.removeContent();
		}else{
			clz = new Element(clazz.getSimpleName());

			int id = obj.hashCode();
			clz.setAttribute("Index", String.valueOf(id));

			clz.setAttribute("Name", clazz.getName());
			if(clazz.getName().contains("Reference")){
				recursion = 1;
			}

			if(clazz.getDeclaredFields().length>0){
				serializeFields();
			}
			//We're adding the same thing to the root twice, so it gives us the error
			//clz.detach();
			System.out.println(clz.getName());
			root.addContent(clz);
			//clz.removeContent();
		}
		

	}
	
	
	//Adding the same thing twice??
	private void serializeFields() {
		Field[] fields = clazz.getDeclaredFields();
		for(Field f : fields) {
			f.setAccessible(true);
			Element el = new Element(f.getName());

			
			if(String.valueOf(f.getType().getName()).equals("SimpleObjectClass") && recursion == 1){
				el.setAttribute("Type", "reference");
				try {
					el.setText(String.valueOf(f.hashCode()));

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				clz.addContent(el);
				try {
					serialize(f.get(obj), root);
					clz.setAttribute("Name", clazz.getName());
				} catch (IllegalAccessException| IllegalArgumentException e) {
					e.printStackTrace();
				}
			}else if(f.getType().getName().equals("[I")){
				el.setAttribute("Class", f.getType().getName());
				try {
					el.setText(String.valueOf(f.get(obj)));
					length = Array.getLength(f.get(obj));
					el.setAttribute("length", String.valueOf(length));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				  clz.addContent(el);
				for(int i=0; i<length; i++){
					Element arrayVal = new Element("value");
					try {
						arrayVal.setText(String.valueOf(Array.get(f.get(obj), i)));
					} catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					clz.addContent(arrayVal);
				}
			}else {
				el.setAttribute("Modifier", String.valueOf(f.getModifiers()));
				el.setAttribute("Type", f.getType().getName());
				try {
					el.setText(String.valueOf(f.get(obj)));

				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				//root.addContent(clz);
				clz.addContent(el);
				
			}

		}
	}


}
