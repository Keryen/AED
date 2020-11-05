package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.Entry;
import es.upm.aedlib.tree.*;
import es.upm.aedlib.priorityqueue.*;


public class Huffman {
  private BinaryTree<Character> huffmanTree;
  

  public Huffman(String text) {
    this.huffmanTree = constructHuffmanTree(text);
  }
  
  private BinaryTree<Character> constructHuffmanTree(String text) {
    // CAMBIA este metodo
    return null;
  }

  public static int[] countChars(String text) {
    // CAMBIA este metodo
    return null;
  }
}



