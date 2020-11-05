package aed.cnossos;

import java.util.Iterator;
import es.upm.aedlib.Pair;
import es.upm.aedlib.positionlist.*;

public class TeseoExplorador {

	public static Pair<Object, PositionList<PuntoCardinal>> explora(Laberinto cnossos) {
		PositionList<PuntoCardinal> explorado = new NodePositionList<PuntoCardinal>();
		Pair<Object, PositionList<PuntoCardinal>> p = null;
		Object tes = exploraRec(explorado, cnossos, null, null);
		if (tes != null) {
			p = new Pair<Object, PositionList<PuntoCardinal>>(tes, explorado);
		}
		return p;
	}

	private static Object exploraRec(PositionList<PuntoCardinal> explorado, Laberinto l, Object tes, PuntoCardinal pc) {
		if (l.tieneTesoro()) {
			return l.getTesoro();
		}
		l.marcaSueloConTiza();
		Iterator<PuntoCardinal> cam = l.caminos().iterator();
		while (cam.hasNext()) {
			pc = cam.next();
			l.ir(pc);
			if (!l.sueloMarcadoConTiza()) {
				tes = exploraRec(explorado, l, tes, pc);
				if (tes != null) {
					explorado.addFirst(pc);
					return tes;
				}
			} 
			l.ir(PuntoCardinal.opuesto(pc));
		}
		return tes;
	}
}