package aed.recursion;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.*;

public class RecursiveUtils {

	/**
	 * Return a^n.
	 * 
	 * @return a^n.
	 */
	public static int power(int a, int n) {
		if (n == 0) {
			return 1;
		} else {
			return a * power(a, n-1) ;
		}
	}

	/**
	 * Returns true if the list parameter does not contain a null element.
	 * 
	 * @return true if the list does not contain a null element.
	 */
	public static <E> boolean allNonNull(PositionList<E> l) {
		if (l == null) {
			return false;
		}
		return allNonNullRec(l, l.first());
	}

	private static <E> boolean allNonNullRec(PositionList<E> l, Position<E> cur) {
		if (cur == null) {
			return true;
		} else if (cur.element() == null) {
			return false;
		}
		return allNonNullRec(l, l.next(cur));
	}

	/**
	 * Returns the number of non-null elements in the parameter list.
	 * 
	 * @return the number of non-null elements in the parameter list.
	 */
	public static <E> int countNonNull(PositionList<E> l) {
		if (l == null || l.isEmpty()) {
			return 0;
		}
		return countNonNullRec(0, l, l.first());
	}

	private static <E> int countNonNullRec(int r, PositionList<E> l, Position<E> cur) {
		if (cur == null) {
			return r;
		} else if (cur.element() != null) {
			return countNonNullRec(r + 1, l, l.next(cur));
		}
		return countNonNullRec(r, l, l.next(cur));
	}
}
