package aed.mergelists;

import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import java.util.Comparator;

public class MergeLists {

	/**
	 * Merges two lists ordered using the comparator cmp, returning a new
	 * ordered list.
	 * 
	 * @returns a new list which is the ordered merge of the two argument lists
	 */
	public static <E> PositionList<E> merge(final PositionList<E> l1, final PositionList<E> l2,
			final Comparator<E> comp) {
		PositionList<E> list = new NodePositionList<E>();
		Position<E> a = l1.first();
		Position<E> b = l2.first();
		if (!(l1.isEmpty() && l2.isEmpty())) {
			if (l1.isEmpty()) {
				while (b != null) {
					list.addLast(b.element());
					b = l2.next(b);
				}
			} else if (l2.isEmpty()) {
				while (a != null) {
					list.addLast(a.element());
					a = l1.next(a);
				}
			} else {
				while (a != null && b != null) {
					if (comp.compare(a.element(), b.element()) < 0) {
						list.addLast(a.element());
						a = l1.next(a);
					} else {
						list.addLast(b.element());
						b = l2.next(b);
					}
				}
				if (a == null) {
					while (b != null) {
						list.addLast(b.element());
						b = l2.next(b);
					}
				} else {
					while (a != null) {
						list.addLast(a.element());
						a = l1.next(a);
					}
				}
			}
		}
		return list;
	}

	/**
	 * Merges two lists ordered using the comparator cmp, returning a new
	 * ordered list.
	 * 
	 * @returns a new list which is the ordered merge of the two argument lists
	 */
	public static <E> IndexedList<E> merge(final IndexedList<E> l1, final IndexedList<E> l2, final Comparator<E> comp) {
		IndexedList<E> list = new ArrayIndexedList<E>();
		if (!(l1.isEmpty() && l2.isEmpty())) {
			if (l1.isEmpty()) {
				for (int j = 0; j < l2.size(); j++) {
					list.add(list.size(), l2.get(j));
				}
			} else if (l2.isEmpty()) {
				for (int j = 0; j < l1.size(); j++) {
					list.add(list.size(), l1.get(j));
				}
			} else {
				int j = 0;
				int k = 0;
				while (j < l1.size() && k < l2.size()) {
					if (comp.compare(l1.get(j), l2.get(k)) < 0) {
						list.add(list.size(), l1.get(j));
						j++;
					} else {
						list.add(list.size(), l2.get(k));
						k++;
					}
				}
				if (j == l1.size()) {
					while (k < l2.size()) {
						list.add(list.size(), l2.get(k));
						k++;
					}
				} else {
					while (j < l1.size()) {
						list.add(list.size(), l1.get(j));
						j++;
					}
				}
			}
		}
		return list;
	}
}
