import static java.lang.System.out;

import org.jdom2.*;

import mypackage.Movie;

import java.lang.reflect.*;
import java.util.*;
public class Deserialize {
	Class clz;
	Constructor constructor;
	Object obj;
	Element root;
	Element clazz;
	Method meth;
	HashMap<Integer,Object> hashMap = new HashMap<Integer,Object>();

	public Deserialize(Document doc) {

		root = doc.getRootElement();
		List<Element> elements = root.getChildren();
		clazz = elements.get(0);
		Attribute cl = clazz.getAttribute("Name");
		String name = clazz.getAttributeValue(cl.getName());
		try {
			clz = Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		listChildren(root,0);
		Object mv=null;
		try {
			//This needs to be changed. It only works with the Movie class right now
			Constructor constructor = clz.getDeclaredConstructor();
			constructor.setAccessible(true);
			mv = constructor.newInstance();
			int id = mv.hashCode();
			hashMap.put(id, mv);


			for(Field f: clz.getDeclaredFields()){
				Element fd = clazz.getChild(f.getName());
				String nm = fd.getValue();
				if(java.lang.reflect.Modifier.isStatic(f.getModifiers())){
					continue;
				}
				if (f.getType().equals(int.class))
				{
					f.set(mv, Integer.valueOf(nm));
				}
			}
			
		
	}catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
		e.printStackTrace();
	}
}

public static void listChildren(Element current, int depth) {

	printSpaces(depth);
	System.out.println(current.getName());
	List children = current.getChildren();
	Iterator iterator = children.iterator();
	while (iterator.hasNext()) {
		Element child = (Element) iterator.next();
		listChildren(child, depth+1);
	}
}
private static void printSpaces(int n) {

	for (int i = 0; i < n; i++) {
		System.out.print(' '); 
	}

}
}
