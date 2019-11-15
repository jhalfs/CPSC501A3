/*
 * Might have solved the issue on the Serialize side, though things are out of order. Need to change the order of things 
 * Need to change this side of things to interpret the Object references
 * Have some ideas on how to handle the arrays too, based on the assignment documentation
 * If I can get at least basic arrays working that would be good for marks
 */


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
	int arrayLoc = 0;

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
		if(name.contains("Array")){
			Class clasz = elements.get(0).getClass().getComponentType();
			for(Field f: clz.getDeclaredFields()){
				Element fd = clazz.getChild(f.getName());
				if(f.getName().equals("value")){
					try {
						Array.setInt(mv, 0, f.getInt(obj));
					} catch (ArrayIndexOutOfBoundsException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					arrayLoc++;
				}else if(f.getName().contains("Array")){
					mv = Array.newInstance(int.class, Integer.parseInt(fd.getAttributeValue("length")));
				}
			}
		}else{
			try {
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
					}else{
						f.set(mv, nm);
					}
					
				}
				
			}catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			}
		}
		
	}

public static void listChildren(Element current, int depth) {

	printSpaces(depth);
	if(current.getName().contains("serialized") | current.getName().contains("Class") ){
		System.out.println(current.getName());
	}else{
		System.out.println(current.getName() + " " + current.getValue());
	}
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
