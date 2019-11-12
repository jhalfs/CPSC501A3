
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
	public void serialize(Object obj, Element root) {

		this.root = root;
		this.obj = obj;
		clazz =  obj.getClass();		
		serializeClass();

	}

	private void serializeClass() {

		clz = new Element(clazz.getSimpleName());

		int id = obj.hashCode();
		clz.setAttribute("Index", String.valueOf(id));

		clz.setAttribute("Name", clazz.getName());


		if(clazz.getDeclaredFields().length>0){

			serializeFields();
		}
		root.addContent(clz);

	}

	private void serializeFields() {
		Field[] fields = clazz.getDeclaredFields();
		for(Field f : fields) {
			f.setAccessible(true);
			Element el = new Element(f.getName());
			el.setAttribute("Modifier", String.valueOf(f.getModifiers()));
			el.setAttribute("Type", f.getType().getName());
			try {
				el.setText(String.valueOf(f.get(obj)));

			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			clz.addContent(el);
		}
	}


}
