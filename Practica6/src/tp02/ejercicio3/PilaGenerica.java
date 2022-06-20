package tp02.ejercicio3;
import tp02.ejercicio2.*;
public class PilaGenerica<T> {
	ListaGenerica<T> datos;
	
	public PilaGenerica() {
		datos= new ListaEnlazadaGenerica<T>();
	}
	
	public void apilar(T dato) {
		datos.agregarInicio(dato);
	}
	public T desapilar() {
		T dato= datos.elemento(1);
		datos.eliminarEn(1);
		return dato;
	}
	public T tope() {
		return datos.elemento(1);
	}
	
	public boolean esVacia() {
		return datos.esVacia();
	}
}
