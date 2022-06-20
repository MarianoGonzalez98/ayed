package tp02.ejercicio3;

public class TestProgram {

	public static void main(String[] args) {
		ColaGenerica<Integer> cola = new ColaGenerica<Integer>();
		cola.encolar(1);
		cola.encolar(2);
		cola.encolar(3);
		System.out.println(cola.esVacia());
		System.out.println(cola.tope());
		System.out.println(cola.desencolar());
		System.out.println(cola.desencolar());
		System.out.println(cola.desencolar());
		System.out.println(cola.esVacia());
		
		System.out.println("----------------------");
		
		PilaGenerica<Integer> pila= new PilaGenerica<Integer>();
		System.out.println(pila.esVacia());
		pila.apilar(1);
		pila.apilar(2);
		pila.apilar(3);
		System.out.println(pila.esVacia());
		System.out.println(pila.tope());
		System.out.println(pila.desapilar());
		System.out.println(pila.desapilar());
		System.out.println(pila.desapilar());
		System.out.println(pila.esVacia());
	}

}
