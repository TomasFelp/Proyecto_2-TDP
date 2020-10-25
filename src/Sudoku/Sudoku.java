package Sudoku;

import java.awt.Color;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

/*
 * Modela la logica del juego
 */
public class Sudoku {
//Attributes
	private Bloque[][] bloques;
	int cantBloques;
	
//Builder
	public Sudoku(Elemento<Boolean> sudokuDeArchivoValido){
		cantBloques=3;
		bloques=new Bloque[cantBloques][cantBloques];
		
		int num;
		String renglon;
		Bloque bloqueActual;
		Celda celdaActual;
		int posRenglon=0;
		int columna=0;
		boolean estructuraDeArchivoValida=true;
		Random rnd=new Random();
		
		
		try {
		
			//Creo el tablero en blanco;
			for(int bloqueI=0;bloqueI<cantBloques;bloqueI++) {
				for(int bloqueJ=0;bloqueJ<cantBloques;bloqueJ++) {
					bloques[bloqueI][bloqueJ]=new Bloque();
				}
			}
	
			InputStream in = getClass().getResourceAsStream("/Archivos/sudoku"+rnd.nextInt(10)+".txt");
			Scanner scn = new Scanner(in);

			for(int fila=0;fila<9 && estructuraDeArchivoValida==true;fila++){//recorro filas del archivo
				renglon=scn.nextLine();
				posRenglon=0;
				columna=0;
				while(posRenglon<17 && estructuraDeArchivoValida==true) {//recorro la linea del archivo e inserto los valores en las celdas
					num=Character.getNumericValue(renglon.charAt(posRenglon));
					
					bloqueActual=bloques[fila/3][columna/3];
					celdaActual=bloqueActual.getCelda(fila%3,columna%3);
					celdaActual.setValor(num);
					
					columna++;
					posRenglon+=2;
					
					if(posRenglon<17)
						if(renglon.charAt(posRenglon-1)!=' ')//chequeo que todos los elementos esten separados por un espacio.
							estructuraDeArchivoValida=false;
					
					if(num>9 || num<0)//chequeo que todos los elementos del archivo sean validos
						estructuraDeArchivoValida=false;
				}
				if(renglon.length()-1!=16)//Chequeo que no haya más de 9 elementos por fila.
					estructuraDeArchivoValida=false;
				
			}
			if(scn.hasNext()) //Chequeo que el archivo no tenga más filas.
				estructuraDeArchivoValida=false;
			
			if(estructuraDeArchivoValida==true)
				//verifico que la solucion del archivo con el cual se inicializo sea valida
				sudokuDeArchivoValido.setElem(revisarInicializacion());
			 else
				 sudokuDeArchivoValido.setElem(false);
			
			//Elimino aleatoriamente el contenido de las celdas.
			if(sudokuDeArchivoValido.getElem()==true)
				for(int fila=0;fila<cantBloques;fila++) {
					for(int colum=0;colum<cantBloques;colum++) {
						bloques[fila][colum].eliminarValoresAleatoriamente();
					}
				}
		
		}catch(Exception e){
			System.out.println("Error en el manejo del archivo");
		}
	}
	
	
//methods
	public Bloque getBloque(int i,int j) {
		return bloques[i][j];
	}
	
	public int getCantBloques() {
		return cantBloques;
	}
	
	public int getCeldasXBloque() {
		return bloques[0][0].getCantCeldas();
	}
	
	public void accionar(Celda c) {
		c.actualizar();
	}
	
	public void accionar2(Celda c) {
		c.actualizar2();
	}

	public void accionar3(Celda c) {
		c.actualizar3();
	}
	
	/*
	 * Recorre las celdas hasta encontrar alguna en conflicto.
	 * Retorna verdadero si y solo si no hay celdas en conflicto.
	 */
	private boolean revisarInicializacion() {
		
		Bloque bloqueActual;
		boolean resp=true;
		
		//Chequeo que no haya conflicto en ningun bloque
		for(int filaBloque=0;filaBloque<3 && resp==true;filaBloque++) {
			for(int columnaBloque=0;columnaBloque<3 && resp==true;columnaBloque++) {
				bloqueActual=bloques[filaBloque][columnaBloque];
				resp=bloqueActual.bloqueValidoNoPintar();
			}
		}
		
		//chequeo que las filas y columnas sean validas
		for(int i=0;i<9 && resp==true;i++) {
			resp=filaValidaNoPintar(i);
			if(resp==true)
				resp=columnaValidaNoPintar(i);
		}
		
		
		return resp;
	}
	
	/*
	 * Recorre las celdas marcando todas las que esten en conflicto.
	 * Retorna verdadero si y solo si no hay celdas en conflicto.
	 */
	public boolean revisar(){
		
		Bloque bloqueActual;
		boolean resp=true,esValido;
		
		//Chequeo que no haya conflicto entre los bloques
		for(int filaBloque=0;filaBloque<3;filaBloque++) {
			for(int columnaBloque=0;columnaBloque<3;columnaBloque++) {
				bloqueActual=bloques[filaBloque][columnaBloque];
				esValido=bloqueActual.bloqueValido();
				if(esValido==false)
					resp=false;
			}
		}
		
		//chequeo que las filas y columnas sean validas
		for(int i=0;i<9;i++) {
			esValido=filaValida(i);
			if(esValido==false)
				resp=false;
			esValido=columnaValida(i);
			if(esValido==false)
				resp=false;
		}
		
		return resp;
	}
	
	/*
	 * Pinta todas las celdas de verde.
	 */
	public void partidaGanada(){
	
		Bloque bloqueActual;
			for(int filaBloque=0;filaBloque<3;filaBloque++) {
				for(int columnaBloque=0;columnaBloque<3;columnaBloque++) {
					bloqueActual=bloques[filaBloque][columnaBloque];
					bloqueActual.pintarBloque(Color.GREEN);
				}
		   }
	}
	
	/*
	 * Recorre la fila pintando todas las celdas en conflicto. 
	 * Retorna verdadero si y solo si no hay celdas en conflicto.
	 */
	private boolean filaValida(int i) {

		HashMap<Integer,Celda> pertenece=new HashMap<Integer, Celda>(9);
		boolean resp=true;
		Bloque bloqueActual;
		Celda celdaActual,celdaRepetida;
		int filaBloque;
		Integer valorActual;
		
		//Selecciono el primer bloque por el que pasa la fila.
		filaBloque=i/3;
		bloqueActual=bloques[filaBloque][0];
		
		for(int columnaBloque=0;columnaBloque<3;columnaBloque++) {
			
			bloqueActual=bloques[filaBloque][columnaBloque];
			
			for(int columnaCelda=0;columnaCelda<3;columnaCelda++) {
				celdaActual=bloqueActual.getCelda(i%3,columnaCelda);
                valorActual=celdaActual.getValor();
                if(valorActual!=null) {
                	celdaRepetida=pertenece.get(valorActual);
                	if(celdaRepetida!=null) {//pinto las celdas repetidas en la columna
                			if(celdaActual.estaBloqeuada()==false)
                				celdaActual.getEntidadGrafica().getLabel().setBackground(new Color(255,120,0));
                			if(celdaRepetida.estaBloqeuada()==false)
                				celdaRepetida.getEntidadGrafica().getLabel().setBackground(new Color(255,120,0));
                		resp=false;
                	}else {
                		pertenece.put(valorActual,celdaActual);
                	}
                }
			}
		}
		
		
		return resp;
	}
	
	/*
	 * Recorre la columna pintando todas las celdas en conflicto.
	 * Retorna verdadero si y solo si no hay celdas en conflicto. 
	 */
	private boolean columnaValida(int i) {
		
		HashMap<Integer,Celda> pertenece=new HashMap<Integer, Celda>(9);
		boolean resp=true;
		Bloque bloqueActual;
		Celda celdaActual,celdaRepetida;
		int columnaBloque;
		Integer valorActual;
		
		//Selecciono el primer bloque por el que pasa la columna.
		columnaBloque=i/3;
		bloqueActual=bloques[0][columnaBloque];
		
		for(int filaBloque=0;filaBloque<3;filaBloque++) {
			
			bloqueActual=bloques[filaBloque][columnaBloque];
			
			for(int filaCelda=0;filaCelda<3;filaCelda++) {
				celdaActual=bloqueActual.getCelda(filaCelda,i%3);
                valorActual=celdaActual.getValor();
                if(valorActual!=null) {
                	celdaRepetida=pertenece.get(valorActual);
                	if(celdaRepetida!=null) {//pinto las celdas repetidas en la columna
                			if(celdaActual.estaBloqeuada()==false)
                				celdaActual.getEntidadGrafica().getLabel().setBackground(new Color(255,0,120));
                			if(celdaRepetida.estaBloqeuada()==false)
                				celdaRepetida.getEntidadGrafica().getLabel().setBackground(new Color(255,0,120));
                		resp=false;
                	}else {
                		pertenece.put(valorActual,celdaActual);
                	}
                }
			}
		}
		
		
		return resp;
	}
	
	/*
	 * Recorre la fila hasta encontrar una celda en conflicto (Metodo empleado en la inicializacion).
	 * Retorna verdadero si y solo si no hay celdas en conflicto.
	 */
	private boolean filaValidaNoPintar(int i) {
		
		HashMap<Integer,Celda> pertenece=new HashMap<Integer, Celda>(9);
		boolean resp=true;
		Bloque bloqueActual;
		Celda celdaActual,celdaRepetida;
		int filaBloque;
		Integer valorActual;
		
		//Selecciono el primer bloque por el que pasa la fila.
		filaBloque=i/3;
		bloqueActual=bloques[filaBloque][0];
		
		for(int columnaBloque=0;columnaBloque<3 && resp==true;columnaBloque++) {
			
			bloqueActual=bloques[filaBloque][columnaBloque];
			
			for(int columnaCelda=0;columnaCelda<3 && resp==true;columnaCelda++) {
				celdaActual=bloqueActual.getCelda(i%3,columnaCelda);
                valorActual=celdaActual.getValor();
                if(valorActual!=null) {
                	celdaRepetida=pertenece.get(valorActual);
                	if(celdaRepetida!=null) {
                		resp=false;
                	}else {
                		pertenece.put(valorActual,celdaActual);
                	}
                }
			}
		}
		
		
		return resp;
	}
	
	/*
	 * Recorre la columna hasta encontrar una celda en conflicto (Metodo empleado en la inicializacion).
	 * Retorna verdadero si y solo si no hay celdas en conflicto.
	 */
	private boolean columnaValidaNoPintar(int i) {
		
		HashMap<Integer,Celda> pertenece=new HashMap<Integer, Celda>(9);
		boolean resp=true;
		Bloque bloqueActual;
		Celda celdaActual,celdaRepetida;
		int columnaBloque;
		Integer valorActual;
		
		//Selecciono el primer bloque por el que pasa la columna.
		columnaBloque=i/3;
		bloqueActual=bloques[0][columnaBloque];
		
		for(int filaBloque=0;filaBloque<3 && resp==true;filaBloque++) {
			
			bloqueActual=bloques[filaBloque][columnaBloque];
			
			for(int filaCelda=0;filaCelda<3 && resp==true;filaCelda++) {
				celdaActual=bloqueActual.getCelda(filaCelda,i%3);
                valorActual=celdaActual.getValor();
                if(valorActual!=null) {
                	celdaRepetida=pertenece.get(valorActual);
                	if(celdaRepetida!=null) {
                		resp=false;
                	}else {
                		pertenece.put(valorActual,celdaActual);
                	}
                }
			}
		}
		
		
		return resp;
	}
}





















