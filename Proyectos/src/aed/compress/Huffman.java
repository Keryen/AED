package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.Entry;
import es.upm.aedlib.InvalidKeyException;
import es.upm.aedlib.tree.*;
import es.upm.aedlib.priorityqueue.*;

public class Huffman {
	private BinaryTree<Character> huffmanTree;

	public Huffman(String text) {
		this.huffmanTree = constructHuffmanTree(text);
	}

	private BinaryTree<Character> constructHuffmanTree(String text) {
		PriorityQueue<Integer, BinaryTree<Character>> Q = new SortedListPriorityQueue<Integer, BinaryTree<Character>>();
		int[] charCode = countChars(text);
		for (int i = 0; i < charCode.length; i++) {
			if (charCode[i] > 0) {
				AttachableLinkedBinaryTree<Character> T = new AttachableLinkedBinaryTree<Character>();
				T.addRoot((char) i);
				Q.enqueue(charCode[i], T);
			}
		}
		while (Q.size() > 1) {
			Entry<Integer, BinaryTree<Character>> Tl = Q.dequeue();
			Entry<Integer, BinaryTree<Character>> Tr = Q.dequeue();
			AttachableLinkedBinaryTree<Character> T = new AttachableLinkedBinaryTree<Character>();
			T.addRoot(' ');
			T.attach(T.root(), Tl.getValue(), Tr.getValue());
			Q.enqueue(Tr.getKey() + Tl.getKey(), T);
		}
		return Q.dequeue().getValue();
	}

	public static int[] countChars(String text) {
		int[] arr = new int[256];
		for (int i = 0; i < text.length(); i++) {
			arr[text.charAt(i)]++;
		}
		return arr;
	}
}


