//package aed.loops;
//
//public class Utils {
//	public static int maxNumRepeated(Integer[] l, Integer elem) {
//		int j = 0, k = 0;
//
//		if (l.length == 0) { // array vacio
//			return 0;
//		}
//		if (l[0].equals(elem)) {
//			k = j = 1;
//		}
//		for (int i = 1; i < l.length; i++) {
//			if (l[i].equals(elem) && k == 0) {
//				k = j = 1;
//			}
//			if (k >= 1) {
//				if (l[i].equals(elem) && l[i - 1].equals(elem)) { // k aumenta en funcion de los valores iguales y
//																	// consecutivos
//					k++;
//					if (k > j) { // j guarda el numero maximo de numeros iguales consecutivos
//						j = k;
//					}
//				} else { // si dos consecutivos no son iguales, k se reinicia
//					k = 1;
//				}
//			}
//		}
//		return j;
//	}
//}

package aed.loops;

public class Utils {
    public static int maxNumRepeated(Integer[] l, Integer elem) {

        int resultado = 0;
        int resultado2 = 0;
        boolean cons = false;

        if (l.length == 0 || (l.length == 1 && !l[0].equals(elem))) {
            return 0;
        } else if (l.length == 1 && l[0].equals(elem)) {
            return 1;
        } else {
            for (int i = 0; i < l.length; i++) {
                if (l[i].equals(elem) && resultado == 0) {
                    cons = true;
                    for (int j = i; j < l.length && cons; j++) {
                        if (l[j].equals(elem)) {
                            resultado2++;
                            cons = true;
                            i++;
                        } else {
                            cons = false;
                            resultado = resultado2 = 1;
                        }
                    }
                }
                if (resultado2 > resultado) {
                    resultado = resultado2;
                }
            }
        }
        return resultado;
    }
}