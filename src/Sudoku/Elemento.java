package Sudoku;
/*
 * Esta clase se implementa con el fin de simular pasaje de parametros por referencia.
 */
public class Elemento<E>{
	private E elem;
	
	public Elemento(E e) {
		elem=e;
	}
	
	public void setElem(E e) {
		elem=e;
	}
	
	public E getElem() {
		return elem;
	}

}
