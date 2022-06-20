package ejercicio5;

import tp02.ejercicio2.ListaEnlazadaGenerica;
import tp02.ejercicio2.ListaGenerica;
import tp06.ejercicio3.Arista;
import tp06.ejercicio3.Grafo;
import tp06.ejercicio3.Vertice;

public class Mapa {
	private Grafo<String> mapaCiudades;
	
	public Mapa(Grafo<String> mapa) {
		mapaCiudades= mapa;
	}
	/*
	 El método devolverCamino (String ciudad1, String ciudad2): ListaGenerica<String> //
Retorna la lista de ciudades que se deben atravesar para ir de ciudad1 a ciudad2 en caso que se pueda
llegar, si no retorna la lista vacía. (Sin tener en cuenta el combustible).
	 */
	
	public ListaGenerica<String> devolverCamino(String ciudad1, String ciudad2){
		boolean[] marca = new boolean[mapaCiudades.listaDeVertices().tamanio() + 1];
		
		ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
		if (!mapaCiudades.esVacio()) {
			boolean encontro = false;
			int i = 1;
			while ((i<marca.length) && (!encontro)){
				if (mapaCiudades.listaDeVertices().elemento(i).dato().equals(ciudad1)) {
					//busca el camino hacia ciudad 2
					ListaGenerica<String> camAct = new ListaEnlazadaGenerica<>();
					this.devolverCaminoDfs(i, marca, mapaCiudades,camAct, lista, ciudad2);
					encontro=true;
				}
				else
					i++;
			}
			
		}
		return lista;
	}
	private void devolverCaminoDfs(int i,boolean[] marca, Grafo<String> grafo, ListaGenerica<String> camAct,ListaGenerica<String> lista , String ciudad2) {
		marca[i]=true; //marca la ciudad visitada
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		camAct.agregarFinal(v.dato());
		if (!v.dato().equals(ciudad2)) {
			ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
			ady.comenzar();
			while (!ady.fin() && lista.esVacia()) {
				int j = ady.proximo().verticeDestino().getPosicion(); 
				if (!marca[j]) {
					devolverCaminoDfs(j, marca, grafo, camAct,lista, ciudad2);
				}
			}
		}
		else {  //clono el camAct en lista
			camAct.comenzar();
			while (!camAct.fin())
				lista.agregarFinal(camAct.proximo());
		}
		camAct.eliminarEn(camAct.tamanio());
		//no es necesario backtracking de marca, no?
	}
	/*
	El método devolverCaminoExceptuando (String ciudad1, String ciudad2, ListaGenerica<String> ciudades): ListaGenerica<String> 
	// Retorna la lista de ciudades que 	forman un camino desde ciudad1 a ciudad2, sin pasar por las ciudades que están contenidas en la lista
	ciudades pasada por parámetro, si no existe camino retorna la lista vacía. (Sin tener en cuenta el combustible). 
	
	 */
	public ListaGenerica<String> devolverCaminoExceptuando(String ciudad1, String ciudad2, ListaGenerica<String> ciudades){
		boolean[] marca = new boolean[mapaCiudades.listaDeVertices().tamanio() + 1];
		
		ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
		if (!mapaCiudades.esVacio()) {
			boolean encontro = false;
			int i = 1;
			while ((i<marca.length) && (!encontro)){
				if (mapaCiudades.listaDeVertices().elemento(i).dato().equals(ciudad1)) {
					//busca el camino hacia ciudad 2
					ListaGenerica<String> camAct = new ListaEnlazadaGenerica<>();
					this.dfsExceptuando(i, marca, mapaCiudades,camAct, lista, ciudad2,ciudades);
					encontro=true;
				}
				else
					i++;
			}
			
		}
		return lista;
	}
	private void dfsExceptuando(int i,boolean[] marca, Grafo<String> grafo, ListaGenerica<String> camAct,ListaGenerica<String> lista , String ciudad2,ListaGenerica<String> ciudades) {
		marca[i]=true; //marca la ciudad visitada
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		
		//if (!ciudades.incluye(v.dato())) puede ir aca asi abarca los casos extremos
		camAct.agregarFinal(v.dato());
		
		if (!v.dato().equals(ciudad2)) {
			if (!ciudades.incluye(v.dato())) { //si la ciudad actual no esta en la lista de exceptuados, continua. Puede ir afuera
				ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
				ady.comenzar();
				while (!ady.fin() && lista.esVacia()) {
					int j = ady.proximo().verticeDestino().getPosicion(); 
					if (!marca[j]) {
						dfsExceptuando(j, marca, grafo, camAct,lista, ciudad2, ciudades);
					}
				}
			}
		}
		else {  //clono el camAct en lista
			camAct.comenzar();
			while (!camAct.fin())
				lista.agregarFinal(camAct.proximo());
		}
		
		camAct.eliminarEn(camAct.tamanio());
		//no es necesario backtracking de marca, no?
	}
	
	/*
	 El método caminoMasCorto(String ciudad1, String ciudad2): ListaGenerica<String> //
	Retorna la lista de ciudades que forman el camino más corto para llegar de ciudad1 a ciudad2, si no
	existe camino retorna la lista vacía. (Las rutas poseen la distancia). (Sin tener en cuenta el
	combustible).
	 */
	
	public ListaGenerica<String> caminoMasCorto(String ciudad1, String ciudad2){
		boolean[] marca = new boolean[mapaCiudades.listaDeVertices().tamanio() + 1];
		
		ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
		if (!mapaCiudades.esVacio()) {
			boolean encontro = false;
			int i = 1;
			while ((i<marca.length) && (!encontro)){
				if (mapaCiudades.listaDeVertices().elemento(i).dato().equals(ciudad1)) {
					int costo = 0;
					MinCosto min= new MinCosto(Integer.MAX_VALUE);
					//busca el camino hacia ciudad 2
					ListaGenerica<String> camAct = new ListaEnlazadaGenerica<>();
					this.dfsMasCorto(i, marca, mapaCiudades,camAct, lista, ciudad2,costo,min); //puedo mandar 0 en vez de costo
					encontro=true;
				}
				else
					i++;
			}
			
		}
		return lista;
	}
	private void dfsMasCorto(int i,boolean[] marca, Grafo<String> grafo, ListaGenerica<String> camAct,ListaGenerica<String> lista , String ciudad2, int costo,MinCosto min) {
		marca[i]=true; //marca la ciudad visitada
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		camAct.agregarFinal(v.dato());
		if (!v.dato().equals(ciudad2)) {
			ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
			ady.comenzar();
			int peso;
			while (!ady.fin()) { //debo recorrer todos los caminos
				Arista<String> arista = ady.proximo();
				int j = arista.verticeDestino().getPosicion(); 
				if (!marca[j]) {
					peso = arista.peso(); //OBTENER PESO ASI!!! PARA EVITAR BUGS!!
					this.dfsMasCorto(j, marca, grafo, camAct,lista, ciudad2,costo+peso,min);
				}
			}
		}
		else if (costo<min.getMinimo()){  //clono el camAct en lista si el costo es menor
			min.setMinimo(costo);
			while (!lista.esVacia()) { //elimino todos los elementos
				lista.eliminarEn(1); 
			}
			camAct.comenzar(); 
			while (!camAct.fin())
				lista.agregarFinal(camAct.proximo());
		}
		camAct.eliminarEn(camAct.tamanio());
		marca[i]=false;
	}
	
	
	/*
	 El método caminoSinCargarCombustible(String ciudad1, String ciudad2, int tanqueAuto):
	ListaGenerica<String> // Retorna la lista de ciudades que forman un camino para llegar de ciudad1
	a ciudad2. El auto no debe quedarse sin combustible y no puede cargar. Si no existe camino retorna la
	lista vacía.
	 */
	public ListaGenerica<String> caminoSinCargarCombustible(String ciudad1, String ciudad2,int tanqueAuto){
		boolean[] marca = new boolean[mapaCiudades.listaDeVertices().tamanio() + 1];
		
		ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
		if (!mapaCiudades.esVacio()) {
			boolean encontro = false;
			int i = 1;
			while ((i<marca.length) && (!encontro)){
				if (mapaCiudades.listaDeVertices().elemento(i).dato().equals(ciudad1)) {
					//busca el camino hacia ciudad 2
					ListaGenerica<String> camAct = new ListaEnlazadaGenerica<>();
					this.dfsSinCargar(i, marca, mapaCiudades,camAct, lista, ciudad2,tanqueAuto);
					encontro=true;
				}
				else
					i++;
			}
			
		}
		return lista;
	}

	private void dfsSinCargar(int i,boolean[] marca, Grafo<String> grafo, ListaGenerica<String> camAct,ListaGenerica<String> lista , String ciudad2,int tanqueAuto) {
		marca[i]=true; //marca la ciudad visitada
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		camAct.agregarFinal(v.dato());
		if (!v.dato().equals(ciudad2)) {
			ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
			ady.comenzar();
			int peso;
			while (!ady.fin() && (lista.esVacia()))  {
				Arista<String> arista = ady.proximo();
				int j = arista.verticeDestino().getPosicion(); 
				if (!marca[j]) {
					peso = arista.peso(); //OBTENER PESO ASI!!! PARA EVITAR BUGS!!
					if (tanqueAuto >= peso)
						this.dfsSinCargar(j, marca, grafo, camAct,lista, ciudad2,tanqueAuto-peso);
				}
			}
		}
		else if (tanqueAuto >= 0) {  //clono el camAct //ya no es necesaria la pregunta
			camAct.comenzar(); 
			while (!camAct.fin())
				lista.agregarFinal(camAct.proximo());
		}
		camAct.eliminarEn(camAct.tamanio());
		marca[i]=false;
	}
	
	
	
	
	/*
	// Retorna la lista de ciudades que forman un camino para
	llegar de ciudad1 a ciudad2 teniendo en cuenta que el auto debe cargar la menor cantidad de veces. El
	auto no se debe quedar sin combustible en medio de una ruta, además puede completar su tanque al
	llegar a cualquier ciudad. Si no existe camino retorna la lista vacía.
	 */
	public ListaGenerica<String> caminoConMenorCargaDeCombustible(String ciudad1, String ciudad2,int tanqueAuto){
		boolean[] marca = new boolean[mapaCiudades.listaDeVertices().tamanio() + 1];
		
		ListaGenerica<String> lista = new ListaEnlazadaGenerica<>();
		if (!mapaCiudades.esVacio()) {
			boolean encontro = false;
			int i = 1;
			while ((i<marca.length) && (!encontro)){
				if (mapaCiudades.listaDeVertices().elemento(i).dato().equals(ciudad1)) {

					MinCosto min= new MinCosto(Integer.MAX_VALUE);
					int capacidad = tanqueAuto;
					int cantCargas=0;
					ListaGenerica<String> camAct = new ListaEnlazadaGenerica<>();
					this.dfsMenorCarga(i, marca, mapaCiudades,camAct, lista, ciudad2,capacidad,cantCargas,tanqueAuto,min);
					encontro=true;
				}
				else
					i++;
			}
		}
		return lista;
	}

	

	private void dfsMenorCarga(int i,boolean[] marca, Grafo<String> grafo, ListaGenerica<String> camAct,ListaGenerica<String> lista , String ciudad2,int capacidad,int cantCargas,int tanqueAuto,MinCosto min) {
		marca[i]=true; //marca la ciudad visitada
		Vertice<String> v = grafo.listaDeVertices().elemento(i);
		camAct.agregarFinal(v.dato());
		if (!v.dato().equals(ciudad2)) { //si no llegue a la ciudad2
			ListaGenerica<Arista<String>> ady = grafo.listaDeAdyacentes(v);
			ady.comenzar();
			int peso;
			while (!ady.fin())  { //debo recorrer todos los caminos
				Arista<String> arista = ady.proximo();
				int j = arista.verticeDestino().getPosicion(); 
				if (!marca[j]) {
					peso = arista.peso(); //OBTENER PESO ASI!!! PARA EVITAR BUGS!!
					if (tanqueAuto >= peso) { //no necesito recargar
						this.dfsMenorCarga(j, marca, grafo, camAct,lista, ciudad2,capacidad,cantCargas,tanqueAuto-peso,min);
					}
					else if (capacidad >= peso) { //puedo ir pero necesito recargar
						this.dfsMenorCarga(j, marca, grafo, camAct,lista, ciudad2,capacidad,cantCargas+1,capacidad-peso,min); //incremento cargas y lleno tanque
					}
				}
			}
		}
		else if (cantCargas < min.getMinimo()) {  //clono el camAct
			min.setMinimo(cantCargas);
			while (!lista.esVacia()) { //elimino todos los elementos
				lista.eliminarEn(1); 
			}
			camAct.comenzar(); //faltaba en los demas codigos!!
			while (!camAct.fin())
				lista.agregarFinal(camAct.proximo());
		}
		camAct.eliminarEn(camAct.tamanio());
		marca[i]=false;
	}
}
