package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.tree.*;


public class AttachableLinkedBinaryTree<E> extends LinkedBinaryTree<E> implements AttachableBinaryTree<E> {

	public void attach(Position<E> pos, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		if (pos != null && this.isExternal(pos)) {
			if (leftTree != null && !leftTree.isEmpty()) {
				this.insertLeft(pos, leftTree.root().element());
				attachRec(this.left(pos), leftTree, leftTree.root());
			}
			if (rightTree != null && !rightTree.isEmpty()) {
				this.insertRight(pos, rightTree.root().element());
				attachRec(this.right(pos), rightTree, rightTree.root());
			}
		}
	}

	private void attachRec(Position<E> pos, BinaryTree<E> tree, Position<E> paux) {
		if (tree.hasLeft(paux)) {
			this.insertLeft(pos, tree.left(paux).element());
			attachRec(this.left(pos), tree, tree.left(paux));
		}
		if (tree.hasRight(paux)) {
			this.insertRight(pos, tree.right(paux).element());
			attachRec(this.right(pos), tree, tree.right(paux));
		}
	}
}
