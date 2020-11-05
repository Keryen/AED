package aed.laberinto;

import java.util.Iterator;

import es.upm.aedlib.lifo.*;

public class Exploradora {

	/**
	 * Busca un tesoro en el laberinto, empezando en el lugar inicial: inicialLugar.
	 * 
	 * @return un Objeto tesoro encontrado, o null, si ningun tesoro existe en la
	 *         parte del laberinto que es alcanzable desde la posicion inicial.
	 */
	public static Object explora(Lugar inicialLugar) {
		LIFO<Lugar> faltaPorExplorar = new LIFOList<Lugar>();
		faltaPorExplorar.push(inicialLugar);
		Object tes = null;
		while (!faltaPorExplorar.isEmpty() && tes == null) {
			Lugar l = faltaPorExplorar.top();
			if (!l.sueloMarcadoConTiza()) {
				if (l.tieneTesoro()) {
					tes = l.getTesoro();
				}
				l.marcaSueloConTiza();
				faltaPorExplorar.pop();
				Iterator<Lugar> cam = l.caminos().iterator();
				while (cam.hasNext()) {
					faltaPorExplorar.push(cam.next());
				}
			}
		}
		return tes;
	}
}
