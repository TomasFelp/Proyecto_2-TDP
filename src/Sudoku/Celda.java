package Sudoku;

public class Celda {
//Attributes
	private EntidadGrafica fig;
	private Integer valor;
	private boolean celdaBloqueada;
	
//Builder
	public Celda(){
		fig=new EntidadGrafica();
		valor=null;
		celdaBloqueada=false;
	}
	
//Methods
	/*
	 * Aumenta el valor de la celda en 1
	 */
	public void actualizar(){
		
		if(celdaBloqueada==false){
			if(valor!=null && valor<fig.getCantImagenes())
				valor=valor+1;
			else
				valor=1;

			fig.sincronizarImagen(valor);
		}
		
	}
	
	/*
	 * Disminuye el valor de la celda en 1
	 */
	public void actualizar2(){

		if(celdaBloqueada==false) {
			if(valor!=null && valor>1)
				valor=valor-1;
			 else
				valor=9;
			
			fig.sincronizarImagen(valor);
		}
		
	}
	
	/*
	 * Borra el contenido de la celda
	 */
	public void actualizar3(){
		//borra el contenido de la celda
		if(celdaBloqueada==false) {
			valor=null;
			
			fig.reiniciar();
		}
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setValor(Integer v) {
		valor=v;
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return fig; 
	}
	
	public boolean estaBloqeuada() {
		return celdaBloqueada;
	}
	
	public void setBloqueada(boolean v) {
		celdaBloqueada=v;
	}
}




















