package Sudoku;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EntidadGrafica {
//Attributes
	private ImageIcon figura;
	private String[] imagenes;
	private String imagenNula;
	private JLabel etiquetaContenedora;

//Builder
	public EntidadGrafica(){
		figura=new ImageIcon();
		imagenes=new String[]{"/imagenes/1.png","/imagenes/2.png","/imagenes/3.png","/imagenes/4.png","/imagenes/5.png","/imagenes/6.png","/imagenes/7.png","/imagenes/8.png","/imagenes/9.png"};
		imagenNula="/imagenes/null.png";
		etiquetaContenedora=null;
	}
	
//Methods
	/*
	 * Cambia la figura que contiene el objeto segun el número que recibe como parametro.
	 */
	public void sincronizarImagen(Integer valor) {
		
	etiquetaContenedora.setBackground(Color.WHITE);
		ImageIcon nuevaImagen;
		
		if(valor!=null)
			nuevaImagen=new ImageIcon(this.getClass().getResource(imagenes[valor-1]));
		 else
			nuevaImagen=new ImageIcon(this.getClass().getResource(imagenNula));
		
		figura.setImage(nuevaImagen.getImage());
	}
	
	public ImageIcon getImageIcon() {
		return figura;
	}
	
	public void setLabel(JLabel nuevaLabel) {
		etiquetaContenedora=nuevaLabel;
	}
	
	public JLabel getLabel() {
		return etiquetaContenedora;
	}
	
	public int getCantImagenes() {
		return imagenes.length;
	}
	
	public void reiniciar(){
		etiquetaContenedora.setBackground(Color.WHITE);
		ImageIcon nuevaImagen=new ImageIcon(this.getClass().getResource(imagenNula));
		figura.setImage(nuevaImagen.getImage());
	}
}

































