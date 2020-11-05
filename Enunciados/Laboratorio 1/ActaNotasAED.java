package aed.actasnotas;

import java.util.Comparator;

import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;

public class ActaNotasAED implements ActaNotas {

	IndexedList<Calificacion> cal = new ArrayIndexedList<Calificacion>();

	private int buscarMatricula(String matricula) {
		int centro = 0;
		int inic = 0;
		int sup = cal.size() - 1;
		boolean enc = false;
		while (inic <= sup && !enc) {
			centro=(inic+sup)/2;
			if (matricula.equals(cal.get(centro).getMatricula())) {
				enc = true;
			} else if (matricula.compareTo(cal.get(centro).getMatricula()) < 0) {
				sup = centro - 1;
			} else {
				inic = centro + 1;
			}
		}
		if (!enc) {
			centro = -1;
		}
		return centro;
	}

	@Override
	public void addCalificacion(String nombre, String matricula, int nota) throws CalificacionAlreadyExistsException {
		Calificacion c1 = new Calificacion(nombre, matricula, nota);
		if (cal.isEmpty()) {
			cal.add(0, c1);
		} else {
			boolean enc = false;
			for (int i = 0; i < cal.size() && !enc; i++) {
				if (cal.get(i).getMatricula().equals(matricula)) {
					throw new CalificacionAlreadyExistsException();
				}
				if (cal.get(i).getMatricula().compareTo(matricula) > 0) {
					cal.add(i, c1);
					enc = true;
				}
			}
			if (enc == false) {
				cal.add(cal.size(), c1);
			}
		}
	}

	@Override
	public void updateNota(String matricula, int nota) throws InvalidMatriculaException {
		if (buscarMatricula(matricula) == -1) {
			throw new InvalidMatriculaException();
		}
		cal.get(buscarMatricula(matricula)).setNota(nota);
	}

	@Override
	public void deleteCalificacion(String matricula) throws InvalidMatriculaException {
		if (buscarMatricula(matricula) == -1) {
			throw new InvalidMatriculaException();
		}
		cal.removeElementAt(buscarMatricula(matricula));

	}

	@Override
	public Calificacion getCalificacion(String matricula) throws InvalidMatriculaException {
		if (buscarMatricula(matricula) == -1) {
			throw new InvalidMatriculaException();
		}
		return cal.get(buscarMatricula(matricula));
	}

	@Override
	public IndexedList<Calificacion> getCalificaciones(Comparator<Calificacion> cmp) {
		IndexedList<Calificacion> c = new ArrayIndexedList<Calificacion>();
		if (cal.isEmpty()) {
			return c;
		}
		boolean enc = false;
		int j = 0;
		c.add(0, cal.get(0));
		for (int i = 1; i < cal.size(); i++) {
			enc = false;
			j = 0;
			while (j < c.size() && !enc) {
				if (cmp.compare(cal.get(i), c.get(j)) < 0) {
					c.add(j, cal.get(i));
					enc = true;
				}
				j++;
			}
			if (!enc) {
				c.add(c.size(), cal.get(i));
			}
		}
		return c;
	}

	@Override
	public IndexedList<Calificacion> getAprobados(int notaMinima) {
		IndexedList<Calificacion> ap = new ArrayIndexedList<Calificacion>();
		if (cal.isEmpty()) {
			return ap;
		}
		for (int i = 0; i < cal.size(); i++) {
			if (cal.get(i).getNota() >= notaMinima) {
				ap.add(ap.size(), cal.get(i));
			}
		}
		return ap;
	}
}
