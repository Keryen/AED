//package aed.indexedlist;
//
//import es.upm.aedlib.indexedlist.*;
//
//public class Utils {
//	public static <E> IndexedList<E> deleteRepeated(IndexedList<E> l) {
//		IndexedList<E> nl = new ArrayIndexedList<E>();
//		if (l.size() == 0) { //lista vacia
//			return nl;
//		}
//		nl.add(0, l.get(0)); //aniado siempre el primer elemento
//		boolean iguala;
//		for (int i = 1; i < l.size(); i++) {
//			iguala = false; //reinicio el booleano
//			for (int j = 0; j < i && !iguala; j++) {
//				if (l.get(i).equals(l.get(j))) { //si un numero y alguno de sus anteriores son iguales, el booleano pasa a ser true
//					iguala = true;
//				}
//			}
//			if (iguala == false) { //si tras comprobar los numeros anteriores al de la posicion i el booleano sigue siendo falso, no hay ningun valor igual antes y metemos el numero de dicha posicion en la nueva lista nl 
//				nl.add(nl.size(), l.get(i));
//			}
//		}
//		return nl;
//	}
//}
package aed.indexedlist;

import es.upm.aedlib.indexedlist.*;

public class Utils {
	public static <E> IndexedList<E> deleteRepeated(IndexedList<E> l) {
		IndexedList<E> list = new ArrayIndexedList<E>();
		if (l.isEmpty()) { 
			return list;
		}
		list.add(0, l.get(0)); 
		for (int i = 1; i < l.size(); i++) {
			if(i==l.indexOf(l.get(i))){
			list.add(list.size(), l.get(i));
			}
		}
		return list;
	}
}
