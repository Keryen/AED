package aed.find;

import es.upm.aedlib.tree.Tree;

import java.util.Iterator;

import es.upm.aedlib.Position;

public class Find {

	/**
	 * Busca ficheros con nombre igual que fileName dentro el arbol directory, y
	 * devuelve un PositionList con el nombre completo de los ficheros
	 * (incluyendo el camino).
	 */
	public static void find(String fileName, Tree<String> directory) {
		String currentPath = "";
		if (!directory.equals(null) || !directory.isEmpty()) {
			Position<String> cursor = directory.root();
			findInPos(cursor, currentPath, fileName, directory);
		} else
			return;
	}

	private static void findInPos(Position<String> cursor, String currentPath, String fileName,
			Tree<String> directory) {
		currentPath += "/" + cursor.element();
		if (cursor.element().equals(fileName)) {
			Printer.enableOutput();
			Printer.println(currentPath);
			return;
		}
		Iterator<Position<String>> it = directory.children(cursor).iterator();
		while (it.hasNext()) {
			cursor = it.next();
			findInPos(cursor, currentPath, fileName, directory);
		}
	}
}
