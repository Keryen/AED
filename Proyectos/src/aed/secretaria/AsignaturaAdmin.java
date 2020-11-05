package aed.secretaria;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class AsignaturaAdmin {

	private String nombreAsignatura;

	private PositionList<Pair<String, Integer>> notas;

	public AsignaturaAdmin(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
		this.notas = new NodePositionList<Pair<String, Integer>>();
	}

	public String getNombreAsignatura() {
		return nombreAsignatura;
	}

	public PositionList<String> matricular(PositionList<String> matriculas) {
		PositionList<String> nm = new NodePositionList<String>();
		if (!matriculas.isEmpty()) {
			Position<String> a = matriculas.first();
			while (a != null) {
				if (!estaMatriculado(a.element())) {
					Pair<String, Integer> p = new Pair<String, Integer>(a.element(), null);
					notas.addLast(p);
					nm.addLast(a.element());
				}
				a = matriculas.next(a);
			}
		}
		return nm;
	}

	public PositionList<String> desMatricular(PositionList<String> matriculas) {
		PositionList<String> nm = new NodePositionList<String>();
		if (!matriculas.isEmpty()) {
			Position<String> a = matriculas.first();
			while (a != null) {
				if (estaMatriculado(a.element()) && buscarMatricula(a.element()).element().getRight() == null) {
					notas.remove(buscarMatricula(a.element()));
					nm.addLast(a.element());
				}
				a = matriculas.next(a);
			}
		}
		return nm;
	}

	private Position<Pair<String, Integer>> buscarMatricula(String matricula) {
		Position<Pair<String, Integer>> b = notas.first();
		while (b != null && !b.element().getLeft().equals(matricula)) {
			b = notas.next(b);
		}
		return b;
	}

	public boolean estaMatriculado(String matricula) {
		Position<Pair<String, Integer>> b = buscarMatricula(matricula);
		return b != null;
	}

	/**
	 * Checks whether a matricula has received a nota.
	 * 
	 * @return true if the matricula has a nota, and false otherwise.
	 * @throws InvalidMatriculaException
	 *             if the matricula has not been added to the asignatura (or was
	 *             removed)
	 */
	public boolean tieneNota(String matricula) throws InvalidMatriculaException {
		Position<Pair<String, Integer>> b = buscarMatricula(matricula);
		if (b == null) {
			throw new InvalidMatriculaException();
		}
		return b.element().getRight() != null;
	}

	public int getNota(String matricula) throws InvalidMatriculaException {
		Position<Pair<String, Integer>> b = buscarMatricula(matricula);
		if (b == null || b.element().getRight() == null) {
			throw new InvalidMatriculaException();
		}
		return b.element().getRight();
	}

	public void setNota(String matricula, int nota) throws InvalidMatriculaException {
		Position<Pair<String, Integer>> b = buscarMatricula(matricula);
		if (b == null) {
			throw new InvalidMatriculaException();
		}
		b.element().setRight(nota);
	}

	public PositionList<String> alumnosEnRango(int minNota, int maxNota) {
		PositionList<String> nm = new NodePositionList<String>();
		if (!notas.isEmpty()) {
			Position<Pair<String, Integer>> b = notas.first();
			while (b != null) {
				if (b.element().getRight() != null && b.element().getRight() >= minNota
						&& b.element().getRight() <= maxNota) {
					nm.addLast(b.element().getLeft());
				}
				b = notas.next(b);
			}
		}
		return nm;

	}

	public double notaMedia() {
		Position<Pair<String, Integer>> b = notas.first();
		double res = 0.0;
		if (!notas.isEmpty()) {
			int tam = notas.size();
			while (b != null) {
				if (b.element().getRight() != null) {
					res += b.element().getRight();
				} else {
					tam--;
				}
				b = notas.next(b);
			}
			if (tam != 0) {
				res = res / tam;
			}
		}
		return res;
	}
}
