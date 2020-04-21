package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami;
	private Set<Esame> ottimo;
	private double mediaBest;

	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami = dao.getTuttiEsami();
	}

	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {

		Set<Esame> parziale = new HashSet<>();
		cerca2(parziale, 0, numeroCrediti);

		return ottimo;
	}

	private void cerca(Set<Esame> parziale, int L, int m) {

		int crediti = sommaCrediti(parziale);

		// Caso TERMINALE
		if (crediti > m)
			return;

		if (crediti == m) {
			double media = calcolaMedia(parziale);
			if (media > mediaBest) {
				ottimo = new HashSet<>(parziale);
				mediaBest = media;
			}
		}

		if (L == esami.size()) {
			return;
		}

		// GENERAZIONE SOTTOPROBLEMA

		// provo ad aggiungerlo
		parziale.add(esami.get(L));
		cerca(parziale, L + 1, m);
		parziale.remove(esami.get(L));

		// provo a non aggiungerlo
		cerca(parziale, L + 1, m);

	}

	private void cerca2(Set<Esame> parziale, int L, int m) {

		int crediti = sommaCrediti(parziale);

		// Caso TERMINALE
		if (crediti > m)
			return;

		if (crediti == m) {
			double media = calcolaMedia(parziale);
			if (media > mediaBest) {
				ottimo = new HashSet<>(parziale);
				mediaBest = media;
			}
		}

		if (L == esami.size()) {
			return;
		}

		// GENERAZIONE SOTTOPROBLEMA

		for (Esame e : esami) {
			if (!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale, L + 1, m);
				parziale.remove(e);
			}
		}

	}

	public double calcolaMedia(Set<Esame> parziale) {
		int somma = 0;
		int crediti = 0;
		for (Esame e : parziale) {
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		return somma / crediti;
	}

	private int sommaCrediti(Set<Esame> parziale) {
		int sommaCrediti = 0;
		for (Esame e : parziale) {
			sommaCrediti += e.getCrediti();
		}
		return sommaCrediti;
	}

}
