package Sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUISudoku extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sudoku juego;
	private Cronometro cronometro;
	private JPanel panelTablero;
	private JPanel panelSuperior;
	private JPanel panelSuperiorDerecho;
	private JPanel panelSuperiorCentral;
	private JButton botonRevisar;
	private JButton botonLimpiar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISudoku frame = new GUISudoku();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUISudoku() {
		Elemento<Boolean> sudoku_de_archivo_valido= new Elemento<Boolean>(false);
		juego=new Sudoku(sudoku_de_archivo_valido);

		if(sudoku_de_archivo_valido.getElem()==true)
			armarInterfaz();
		 else {
			 JOptionPane.showMessageDialog(null,"Error:\nEl archivo encargado de inicializar el juego no contiene una solucion valida","Error", JOptionPane.ERROR_MESSAGE);
			 System.exit(0);
		 }
		
		
	}
	
	private void armarInterfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.lightGray);
		setContentPane(contentPane);
		
		armarPanelesSuperiores();
		
		armarBotones();
		
		armarCronometro();
		
		armarTablero();
		
	}
	
	private void reDimensionar(JLabel label, ImageIcon figura) {
		Image image = figura.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			figura.setImage(newimg);
			label.repaint();
		}
	}
	
	
	private void armarBotones() {
		botonLimpiar=new JButton("limpiar celdas");
		botonLimpiar.setBackground(Color.ORANGE);
		botonLimpiar.addActionListener(new oyeneBotonLimpiarCeldads());
		panelSuperiorCentral.add(botonLimpiar);
		
		botonRevisar=new JButton("revisar");
		botonRevisar.setBackground(Color.GREEN);
		botonRevisar.addActionListener(new oyeneteBotonRevisar());
		panelSuperiorCentral.add(botonRevisar);
	}
	
	private void armarPanelesSuperiores(){
		
		panelSuperior=new JPanel();
		panelSuperior.setLayout(new GridLayout(1,3));
		panelSuperior.setBackground(Color.lightGray);
		
		contentPane.add(panelSuperior,BorderLayout.NORTH);

		JLabel informacion=new JLabel("ayuda: click aqui");
		informacion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				 if (e.getButton()==MouseEvent.BUTTON1)
					 JOptionPane.showMessageDialog(null,"Codigo de colores en celdas: \n\nRojo claro: falta completar. \nRojo oscuro: número repetido en el panel.\nNaranja: número repetdido en la fila.\nVioleta:número repetido en la columna.\nGris: celda bloqueada.\n\nControles:\n\nclick izquierdo: avanza un número.\nclick derecho: vulve un número.\nclick central: reinicia la celda.");
			}
		});
		
		panelSuperior.add(informacion);
		
		panelSuperiorCentral=new JPanel();
		panelSuperiorCentral.setBackground(Color.lightGray);
		panelSuperiorCentral.setLayout(new FlowLayout());
		panelSuperior.add(panelSuperiorCentral);
		
		panelSuperiorDerecho=new JPanel();
		panelSuperiorDerecho.setBackground(Color.lightGray);
		panelSuperiorDerecho.setLayout(new FlowLayout());
		panelSuperior.add(panelSuperiorDerecho);
	}
	
	private void armarCronometro() {
	
		JLabel lDhoras,lUhoras,lDmi,lUmi,lDseg,lUseg;
		JLabel cronometroTitulo=new JLabel("Tiempo:");
		JLabel puntosSeparacion1,puntosSeparacion2;
		int w=17,h=25;
		
		
		puntosSeparacion1=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/puntos.png")));
		puntosSeparacion1.setPreferredSize(new Dimension(w-10,h));
		
		puntosSeparacion2=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/puntos.png")));
		puntosSeparacion2.setPreferredSize(new Dimension(w-10,h));
		
		lDhoras=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/0a.png")));
		lDhoras.setPreferredSize(new Dimension(w,h));
		lUhoras=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/0a.png")));
		lUhoras.setPreferredSize(new Dimension(w,h));
		lDmi=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/0a.png")));
		lDmi.setPreferredSize(new Dimension(w,h));
		lUmi=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/0a.png")));
		lUmi.setPreferredSize(new Dimension(w,h));
		lDseg=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/0a.png")));
		lDseg.setPreferredSize(new Dimension(w,h));
		lUseg=new JLabel(new ImageIcon(this.getClass().getResource("/cronometro/0a.png")));
		lUseg.setPreferredSize(new Dimension(w,h));
		
		panelSuperiorDerecho.add(cronometroTitulo);
		panelSuperiorDerecho.add(lDhoras);
		panelSuperiorDerecho.add(lUhoras);
		panelSuperiorDerecho.add(puntosSeparacion1);
		panelSuperiorDerecho.add(lDmi);
		panelSuperiorDerecho.add(lUmi);
		panelSuperiorDerecho.add(puntosSeparacion2);
		panelSuperiorDerecho.add(lDseg);
		panelSuperiorDerecho.add(lUseg);
		
		cronometro= new Cronometro(lDhoras,lUhoras,lDmi,lUmi,lDseg,lUseg);
		
		cronometro.iniciar();
	}
	
	private void armarTablero() {
		panelTablero = new JPanel();
		panelTablero.setBackground(Color.DARK_GRAY);
		contentPane.add(panelTablero, BorderLayout.CENTER);
		panelTablero.setLayout(new GridLayout(3, 3,-1,-1));
		JPanel [] panelBloque=new JPanel[9];
		int panelActual=0;
		int cantBloques=juego.getCantBloques();
		int cantCeldas=juego.getBloque(0,0).getCantCeldas();
		
		for(int bloqueI=0;bloqueI<cantBloques;bloqueI++) {
			for(int bloqueJ=0;bloqueJ<cantBloques;bloqueJ++) {
				panelBloque[panelActual]=new JPanel();
				panelBloque[panelActual].setLayout(new GridLayout(3, 3,-1,0));
				panelBloque[panelActual].setBorder(BorderFactory.createLineBorder(Color.black,3));
				panelTablero.add(panelBloque[panelActual]);
				
				Bloque bloqueActual;
				bloqueActual=juego.getBloque(bloqueI, bloqueJ);
				
				for(int celdaI=0;celdaI<cantCeldas;celdaI++){
					for(int celdaJ=0;celdaJ<cantCeldas;celdaJ++){
						Celda celdaActual;
						ImageIcon figura;
						celdaActual=bloqueActual.getCelda(celdaI,celdaJ);
						figura=celdaActual.getEntidadGrafica().getImageIcon();
						
						JLabel label=new JLabel();
						label.setOpaque(true);
						label.setBorder(BorderFactory.createLineBorder(Color.blue));
					
						panelBloque[panelActual].add(label);
						celdaActual.getEntidadGrafica().setLabel(label);
						
						celdaActual.getEntidadGrafica().sincronizarImagen(celdaActual.getValor());
						if(celdaActual.estaBloqeuada()) {
							label.setBackground(new Color(200,200,200));
						}
						
						label.addComponentListener(new ComponentAdapter() {
							@Override
							public void componentResized(ComponentEvent e) {
								reDimensionar(label, figura);
								label.setIcon(figura);
							}
							
						});
						
						label.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								 if (e.getButton()==MouseEvent.BUTTON1){
								        //Se presiono el boton izquierdo
									 juego.accionar(celdaActual);
									 reDimensionar(label,figura);
								 }else
								    if(e.getButton()==MouseEvent.BUTTON3){
								        //Se presiono el boton derecho
										juego.accionar2(celdaActual);
										reDimensionar(label,figura);
								    }else
									    if(e.getButton()==MouseEvent.BUTTON2){
									        //Se presiono la rueda
											juego.accionar3(celdaActual);
											reDimensionar(label,figura);
									    }
							}
						});
					}
				}
				panelActual++;
			}
		}
	}
	
/*
 * -------------------------------------------------------Clases internas------------------------------------------------------------------------
 */
	
	private class oyeneteBotonRevisar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			/*
			 * Chequea si el jugador gano la partida, si no es asi marca las celdas mal completadas.
			 */
			boolean partidaGanada=juego.revisar();
			
			if(partidaGanada==true) {
				for(int i=0;i<juego.getCantBloques();i++) {
					for(int j=0;j<juego.getCantBloques();j++) {
						juego.getBloque(i, j).pintarBloque(Color.green);
					}
				}
				cronometro.detener();
				JOptionPane.showMessageDialog(null,"Has ganado la partida!!!");
			}
		}
		
	}
	
	private class oyeneBotonLimpiarCeldads implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			/*
			 * borra el contenido de todas las celdas que modifico el jugador
			 */
			Bloque bloqueActual;
			
			for(int bloqueI=0;bloqueI<juego.getCantBloques();bloqueI++) {
				for(int bloqueJ=0;bloqueJ<juego.getCantBloques();bloqueJ++) {
					bloqueActual=juego.getBloque(bloqueI, bloqueJ);
					for(int celdaI=0;celdaI<bloqueActual.getCantCeldas();celdaI++) {
						for(int celdaj=0;celdaj<bloqueActual.getCantCeldas();celdaj++) {
							juego.accionar3(bloqueActual.getCelda(celdaI,celdaj));
							bloqueActual.getCelda(celdaI,celdaj).getEntidadGrafica().getLabel().repaint();
						}
					}
				}
			}
			
		}
	}
	
	
}























