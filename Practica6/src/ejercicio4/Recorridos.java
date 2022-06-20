package ejercicio4;


import tp02.ejercicio2.ListaEnlazadaGenerica;
import tp02.ejercicio2.ListaGenerica;
import tp02.ejercicio3.ColaGenerica;
import tp06.ejercicio3.Arista;
import tp06.ejercicio3.Grafo;
import tp06.ejercicio3.Vertice;


public class Recorridos<T> {
	// Retorna una lista de vértices con el recorrido en profundidad del grafo recibido como parámetro.
	public ListaGenerica<T> dfs(Grafo<T> grafo){
		boolean[] marca = new boolean[grafo.listaDeVertices().tamanio() + 1]; 
		
		ListaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		
		for (int i = 1; i <= grafo.listaDeVertices().tamanio() ; i++) {
			if (!marca[i]) //si no esta visitado
				this.dfs(i,grafo,marca,lista);
		}
		
		return lista;
	}
	
	private void dfs(int i, Grafo<T> grafo, boolean[] marca, ListaGenerica<T> lista) {
		marca[i]=true; //marco visitado el vertice
		Vertice<T> v = grafo.listaDeVertices().elemento(i); //obtengo el vertice
		lista.agregarFinal(v.dato());
		ListaGenerica<Arista<T>> ady = grafo.listaDeAdyacentes(v); //obtengo los adyacented del vertice actual
		//recorro los adyacentes, llamando recursivamente los que no esten visitados
		ady.comenzar();
		while (!ady.fin()) {
			int j = ady.proximo().verticeDestino().getPosicion(); //obtengo la posicion del prox adyacente
			if (!marca[j]) { 
				dfs(j,grafo,marca,lista);
			}
		}
	}
	
	public ListaGenerica<T> bfs(Grafo<T> grafo){
		ListaGenerica<T> lista = new ListaEnlazadaGenerica<>();
		boolean[] marca = new boolean[grafo.listaDeVertices().tamanio() + 1];
		for (int i = 1; i < marca.length; i++) {
			if (!marca[i])
				this.bfs(i, grafo, marca, lista);
		}
		return lista;
	}
	private void bfs(int i, Grafo<T> grafo, boolean[] marca, ListaGenerica<T> lista) {
		ListaGenerica<Arista<T>> ady;
		ColaGenerica<Vertice<T>> cola = new ColaGenerica<>();
		cola.encolar(grafo.listaDeVertices().elemento(i)); //encolo el vertice
		marca[i]=true; //marco visitado el vertice
		while (!cola.esVacia()) {
			Vertice<T> v = cola.desencolar();
			lista.agregarFinal(v.dato());

			//encola los adyacentes
			ady = grafo.listaDeAdyacentes(v);
			ady.comenzar();
			while (!ady.fin()) {
				Arista<T> arista = ady.proximo();
				int j = arista.verticeDestino().getPosicion();
				if (!marca[j]) { //si el vertice de la arista no esta visitado, lo encola y visita
					Vertice<T> vertDest = arista.verticeDestino();
					marca[j]=true;
					cola.encolar(vertDest);
				}
			}
		}
	}
}
