package aed.huffman;

import es.upm.aedlib.Position;
import es.upm.aedlib.tree.*;

/**
 * Defines metodos for Huffman encoding of text strings.
 */
public class Huffman {
	private LinkedBinaryTree<Character> huffmanTree;

	public Huffman(LinkedBinaryTree<Character> huffmanTree) {
		this.huffmanTree = huffmanTree;
	}

	/**
	 * Creates a Huffman tree (and stores it in the attribute huffmanTree). The
	 * shape of the (binary) tree is defined by the array of char-codes.
	 */
	public Huffman(CharCode[] paths) {
		this.huffmanTree = new LinkedBinaryTree<Character>();
		if (paths == null || paths.length == 0) {
			return;
		}
		huffmanTree.addRoot(' ');
		for (int i = 0; i < paths.length; i++) {
			huffmanRec(huffmanTree, huffmanTree.root(), paths[i], 1);
		}
	}

	private void huffmanRec(LinkedBinaryTree<Character> tree, Position<Character> pos, CharCode letra, int a) {
		if (letra.getCode().length() <= a) {
			if (letra.getCode().charAt(a - 1) == '1') {
				tree.insertRight(pos, letra.getCh());
			} else {
				tree.insertLeft(pos, letra.getCh());
			}
			return;
		} else if (letra.getCode().charAt(a - 1) == '1') {
			if (!tree.hasRight(pos)) {
				tree.insertRight(pos, ' ');
			}
			huffmanRec(tree, tree.right(pos), letra, ++a);
		} else if (letra.getCode().charAt(a - 1) == '0') {
			if (!tree.hasLeft(pos)) {
				tree.insertLeft(pos, ' ');
			}
			huffmanRec(tree, tree.left(pos), letra, ++a);
		}
	}
	//////////////////////////////////////////////////////////////////////

	/**
	 * Huffman encodes a text, returning a new text string containing only
	 * characters '0' and '1'.
	 */
	public String encode(String text) {
		String path = "";
		for (int i = 0; i < text.length(); i++) {
			path = findCharacterCode(text.charAt(i), huffmanTree, huffmanTree.root(), path);
		}
		return path;
	}

	private String findCharacterCode(Character ch, BinaryTree<Character> tree, Position<Character> pos, String path) {
		String res = null;
		if (tree.isExternal(pos) && pos.element().equals(ch)) {
			res = path;
		}
		if (res == null && tree.hasLeft(pos)) {
			res = findCharacterCode(ch, tree, tree.left(pos), path + "0");
		}
		if (res == null && tree.hasRight(pos)) {
			res = findCharacterCode(ch, tree, tree.right(pos), path + "1");
		}
		return res;
	}

	//////////////////////////////////////////////////////////////////////

	/**
	 * Given the Huffman encoded text argument (a string of only '0' and '1's),
	 * returns the corresponding normal text.
	 */
	public String decode(String encodedText) {
		String res = "";
		Position<Character> aux = huffmanTree.root();
		for (int i = 0; i < encodedText.length(); i++) {
			if (encodedText.charAt(i) == '1') {
				aux = huffmanTree.right(aux);
			} else if (encodedText.charAt(i) == '0') {
				aux = huffmanTree.left(aux);
			}
			if (!aux.element().equals(' ')) {
				res += aux.element().toString();
				aux = huffmanTree.root();
			}
		}
		return res;
	}
}
