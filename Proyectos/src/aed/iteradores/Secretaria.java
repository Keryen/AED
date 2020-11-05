package aed.iteradores;

import es.upm.aedlib.positionlist.*;
import java.util.Iterator;

/**
 * Administra una coleccion de asignaturas.
 */
public class Secretaria {
	private Iterable<AsignaturaAdmin> asignaturas;

	/**
	 * Empieza administrar una coleccion de asignaturas.
	 */
	public Secretaria(Iterable<AsignaturaAdmin> asignaturas) {
		this.asignaturas = asignaturas;
	}

	private AsignaturaAdmin findAsignatura(String asignatura) {
		Iterator<AsignaturaAdmin> it = asignaturas.iterator();
		AsignaturaAdmin res = null;
		while (it.hasNext() && res == null) {
			AsignaturaAdmin admin = it.next();
			if (admin.getNombreAsignatura().equals(asignatura)) {
				res = admin;
			}
		}
		return res;
	}

	private AsignaturaAdmin getAsignatura(String asignatura) throws InvalidAsignaturaException {
		AsignaturaAdmin admin = findAsignatura(asignatura);
		if (admin == null)
			throw new InvalidAsignaturaException();
		else
			return admin;
	}

	/**
	 * Matricula una coleccion de alumnos (representados por el parametro
	 * matriculas) en una asignatura.
	 * 
	 * @return los números de matricula de los alumnos matriculados.
	 * @throws InvalidAsignaturaException
	 *             si la asignatura no está siendo administrada por la
	 *             secretaría.
	 */
	public Iterable<String> matricular(String asignatura, Iterable<String> matriculas)
			throws InvalidAsignaturaException {
		return getAsignatura(asignatura).matricular(matriculas);
	}

	/**
	 * Desmatricula una coleccion de alumnos (representados por el parametro
	 * matriculas) de una asignatura.
	 * 
	 * @return las matriculas desmatriculados (que debían estar matriculados y
	 *         no tener nota).
	 * @throws InvalidAsignaturaException
	 *             si la asignatura no está siendo administrado por la
	 *             secretaria.
	 */
	public Iterable<String> desMatricular(String asignatura, Iterable<String> matriculas)
			throws InvalidAsignaturaException {
		return getAsignatura(asignatura).desMatricular(matriculas);
	}

	/**
	 * Calcula la nota media de un alumno (representado por su identificador de
	 * matrícula) en todas asignaturas en las que está matriculado. Si el
	 * alumno no tiene ninguna nota en ninguna asignatura, el metodo debe
	 * devolver 0.
	 * 
	 * @return la nota media del alumno.
	 */
	public double notaMediaExpediente(String matricula) {
		Iterator<AsignaturaAdmin> it = asignaturas.iterator();
		double media = 0.0;
		double cont = 0.0;
		try {
			while (it.hasNext()) {
				AsignaturaAdmin admin = it.next();
				if (admin.estaMatriculado(matricula) && admin.tieneNota(matricula)) {
					cont++;
					media += admin.getNota(matricula);
				}
			}
			if (cont != 0) {
				media = media / cont;
			}
		} catch (InvalidMatriculaException exc) {
			// Esta vacio porque no hace falta que trabajemos la excepcion
		}

		return media;
	}

	/**
	 * Devuelve el nombre de la asignatura que tiene la mejor nota media,
	 * calculada usando las notas de todos los alumnos que tienen notas para esa
	 * asignatura. Si la secretaria no esta administrando ninguna asignatura, el
	 * metodo debe devolver null. Similarmente, si ningún alumno tiene nota en
	 * ninguna asignatura, el metodo tambien debe devolver null.
	 * 
	 * @return el nombre de la asignatura con la mejor nota media.
	 */
	public String mejorNotaMedia() {
		Iterator<AsignaturaAdmin> it = asignaturas.iterator();
		String asig = null;
		double max = 0.0;
		while (it.hasNext()) {
			AsignaturaAdmin admin = it.next();
			if (admin.notaMedia() != 0 && admin.notaMedia() > max) {
				max = admin.notaMedia();
				asig = admin.getNombreAsignatura();
			}
		}
		return asig;
	}

	/**
	 * Devuelve todas las notas de un alumno (representado por su identificador
	 * de matrícula) como una coleccion de objetos Pair(NombreAsignatura,
	 * Nota).
	 * 
	 * @return una coleccion de pares de las notas de la matricula en todas las
	 *         asignaturas.
	 */
	public Iterable<Pair<String, Integer>> expediente(String matricula) {
		Iterator<AsignaturaAdmin> it = asignaturas.iterator();
		PositionList<Pair<String, Integer>> lista = new NodePositionList<Pair<String, Integer>>();
		try {
			while (it.hasNext()) {
				AsignaturaAdmin admin = it.next();
				if (admin.estaMatriculado(matricula) && admin.tieneNota(matricula)) {
					Pair<String, Integer> p = new Pair<String, Integer>(admin.getNombreAsignatura(),
							admin.getNota(matricula));
					lista.addLast(p);
				}
			}
		} catch (InvalidMatriculaException exc) {
			// Esta vacio porque no hace falta que trabajemos la excepcion
		}
		return lista;
	}

	/**
	 * Devuelve una coleccion con todas los pares de asignaturas --
	 * representados como Pair(NombreAsignatura1, NombreAsignatura2) -- que no
	 * tienen alumnos en comun. El metodo NO debe devolver nunca un par
	 * Pair(NombreAsignatura,NombreAsignatura), es decir, con nombres iguales de
	 * asignaturas. Si dos asignaturas A1 y A2 no tienen ningún alumno en
	 * común, para ellas se puede devolver: (i) Pair(A1,A2), o (ii)
	 * Pair(A1,A2), Pair(A2,A1), o (iii) Pair(A2,A1).
	 * 
	 * @return una coleccion que contiene todos los pares de asignaturas que no
	 *         tienen ningún alumno en comun.
	 */
	public Iterable<Pair<String, String>> asignaturasNoConflictivas() {
		Iterator<AsignaturaAdmin> it1 = asignaturas.iterator();
		PositionList<Pair<String, String>> lista = new NodePositionList<Pair<String, String>>();
		while (it1.hasNext()) {
			Iterator<AsignaturaAdmin> it2 = asignaturas.iterator();
			AsignaturaAdmin a1 = it1.next();
			while (it2.hasNext()) {
				AsignaturaAdmin a2 = it2.next();
				if (!compartenAlumnos(a1, a2) && a1 != a2) {
					Pair<String, String> p = new Pair<String, String>(a1.getNombreAsignatura(),
							a2.getNombreAsignatura());
					lista.addLast(p);
				}
			}
		}
		return lista;
	}

	/**
	 * Devuelve true si dos asignaturas a1 y a2 tienen algún alumno en comun.
	 * 
	 * @return true si las dos asignaturas no tienen ningún alumno en comun.
	 */
	private boolean compartenAlumnos(AsignaturaAdmin a1, AsignaturaAdmin a2) {
		boolean e = false;
		Iterator<String> it1 = a1.matriculados().iterator();
		while (it1.hasNext() && !e) {
			Iterator<String> it2 = a2.matriculados().iterator();
			String al1 = it1.next();
			while (it2.hasNext() && !e) {
				String al2 = it2.next();
				if (al1.equals(al2)) {
					e = true;
				}
			}
		}
		return e;
	}
}
