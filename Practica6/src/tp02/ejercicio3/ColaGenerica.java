package tp02.ejercicio3;
import tp02.ejercicio2.*;
public class ColaGenerica<T> {
	private ListaGenerica<T> datos;
	
	public ColaGenerica(){
		datos=new ListaEnlazadaGenerica<T>();
	}
	public void encolar(T dato) {
		datos.agregarFinal(dato);
	}
	
	public T desencolar() {
		T dato=datos.elemento(1); 
		datos.eliminarEn(1);
		return dato;
	}
	public T tope() {
		return datos.elemento(1);
	}
	public boolean esVacia()
	{
		return datos.esVacia();
	}
}
