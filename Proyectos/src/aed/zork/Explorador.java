package aed.zork;

import java.util.Iterator;

import aed.zork.Lugar;
import es.upm.aedlib.positionlist.*;

public class Explorador {

	public static Pair<Object, PositionList<Lugar>> explora(Lugar inicialLugar) {
		PositionList<Lugar> explorado = new NodePositionList<Lugar>();
		Pair<Object, PositionList<Lugar>> p = null;
		Object tes = exploraRec(explorado, inicialLugar);
		if (tes != null) {
			p = new Pair<Object, PositionList<Lugar>>(tes, explorado);
		}
		return p;
	}

	private static Object exploraRec(PositionList<Lugar> explorado, Lugar l) {
		Object tes = null;
		if (l.tieneTesoro()) {
			return l.getTesoro();
		}
		l.marcaSueloConTiza();
		Iterator<Lugar> cam = l.caminos().iterator();
		while (cam.hasNext()) {
			l = cam.next();
			if (!l.sueloMarcadoConTiza()) {
				tes = exploraRec(explorado, l);
				if (tes != null) {
					explorado.addLast(l);
					return tes;
				}
			}
		}
		return tes;
	}
}
