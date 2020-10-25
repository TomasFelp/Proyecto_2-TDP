package Sudoku;

import java.util.Random;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/*
 * Modela un panel de celdas del sudoku.
 */
public class Bloque {

//attributes
	private Celda[][] celdas;
	private int cantCeldas;
	private Map<Integer,Celda> pertenece;
	
//builder
	public Bloque(){
		cantCeldas=3;
		celdas=new Celda[cantCeldas][cantCeldas];
		
		for(int i=0;i<cantCeldas;i++) {
			for(int j=0;j<cantCeldas;j++) {
				celdas[i][j]=new Celda();
			}
		}
		
	}
	
	
//methods
	public Celda getCelda(int fila,int columna) {
		return celdas[fila][columna];
	}
	
	public int getCantCeldas() {
		return cantCeldas;
	}
	
	/*
	 * Recorre las celdas del bloque pintando todas las que se encuentren en conflicto.
	 * retorna verdadero si no hay celdas en conflicto
	 */
	public boolean bloqueValido(){
		
		pertenece=new HashMap<Integer,Celda>(9);
		boolean resp=true;
		boolean esValido=true;
		Integer valorActual;
		
		for(int fila=0;fila<3;fila++) {
			for(int columna=0;columna<3;columna++) {
				valorActual=celdas[fila][columna].getValor();
				if(valorActual!=null) {
					if(pertenece.get(valorActual)!=null) {//Identifico las celdas en conflicto en el bloque
						esValido=false;
						if(celdas[fila][columna].estaBloqeuada()==false)
							celdas[fila][columna].getEntidadGrafica().getLabel().setBackground(Color.RED);
						if(pertenece.get(valorActual).estaBloqeuada()==false)
							pertenece.get(valorActual).getEntidadGrafica().getLabel().setBackground(Color.RED);
					}else {//se restablece el color de las celdas validas
						pertenece.put(valorActual,celdas[fila][columna]);
						if(celdas[fila][columna].estaBloqeuada()==false)
							celdas[fila][columna].getEntidadGrafica().getLabel().setBackground(Color.WHITE);
					}
				}else {//pinto las celdas con valor nulo
					esValido=false;
					if(celdas[fila][columna].estaBloqeuada()==false)
						celdas[fila][columna].getEntidadGrafica().getLabel().setBackground(new Color(255,150,150));
				}
			}
		}
		resp=esValido;
		return resp;
	}
	
	/*
	 * Recorre las celdas del bloque hasta encontrar una en conflicto (Metodo empleado en la inicializacion)
	 * retorna verdadero si no hay celdas en conflicto
	 */
	public boolean bloqueValidoNoPintar(){
	
		pertenece=new HashMap<Integer,Celda>(cantCeldas*cantCeldas);
		boolean resp=true;
		boolean esValido=true;
		Integer valorActual;
		
		for(int fila=0;fila<cantCeldas && esValido==true;fila++) {
			for(int columna=0;columna<cantCeldas && esValido==true;columna++) {
				valorActual=celdas[fila][columna].getValor();
				if(valorActual!=null) {
					if(pertenece.get(valorActual)!=null) {
						esValido=false;
					}else {
						pertenece.put(valorActual,celdas[fila][columna]);
					}
				}else {
					esValido=false;
				}
			}
		}
		resp=esValido;
		return resp;
	}
	
	/*
	 * Pinta todas las celdas del bloque con el color que recibe
	 */
	public void pintarBloque(Color c) {
		
		Celda celdaActual;
		for(int fila=0;fila<3;fila++) {
			for(int columna=0;columna<3;columna++) {
				celdaActual=celdas[fila][columna];
				if(celdaActual.estaBloqeuada()==false)
					celdaActual.getEntidadGrafica().getLabel().setBackground(c);
			}
		}
	}
	
	/*
	 * Elimina aleatoriamente el contenido de unas celdas y bloquea el de otras para inicializar el juego.
	 */
	public void eliminarValoresAleatoriamente() {
		Random rnd=new Random();
		int eliminados=0;
		
		if(rnd.nextInt(2)==0)//Elijo el sentido del recorrido al azar para mejorar la distribucion de eliminaciones.
			for(int fila=0;fila<cantCeldas;fila++) {
				for(int columna=0;columna<cantCeldas;columna++) {
					if(rnd.nextInt(3)!=1 && eliminados<7) {
						celdas[fila][columna].setValor(null);
						eliminados++;
					}else {
						 celdas[fila][columna].setBloqueada(true);
					 }
				}
			}
		else {
			for(int fila=cantCeldas-1;fila>=0;fila--) {
				for(int columna=cantCeldas-1;columna>=0;columna--) {
					if(rnd.nextInt(3)!=1 && eliminados<7) {
						celdas[fila][columna].setValor(null);
						eliminados++;
					}else {
						 celdas[fila][columna].setBloqueada(true);
					 }
				}
			}
		}
	}
	
}



























