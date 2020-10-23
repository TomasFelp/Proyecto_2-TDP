package Sudoku;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Cronometro {
	
//Attributes
	private Timer tiempo;
	private int horas;
	private int min;
	private int seg;
	private boolean enEjecucion,parar=false;
	
	private JLabel decenaHora;
	private JLabel unidadHora;
	private JLabel decenaMin;
	private JLabel unidadMin;
	private JLabel decenaSeg;
	private JLabel unidadSeg;


//Builder
	public Cronometro(JLabel dh,JLabel uh,JLabel dm,JLabel um,JLabel ds,JLabel us){
		decenaHora=dh;
		unidadHora=uh;
		decenaMin=dm;
		unidadMin=um;
		decenaSeg=ds;
		unidadSeg=us;
		
		horas=min=seg=0;
		
		enEjecucion=false;
		
		tiempo=new Timer();
	}
	
//Methods
public void iniciar() {
	if(enEjecucion==false) {
		tarea t=new tarea();
		tiempo.schedule(t,1500,1000);
		enEjecucion=true;
	}
}

public void detener(){
	parar=true;
	enEjecucion=false;
}

private void actulizarLabels() {
	unidadSeg.setIcon(new ImageIcon(this.getClass().getResource("/cronometro/"+seg%10+"a.png")));
	decenaSeg.setIcon(new ImageIcon(this.getClass().getResource("/cronometro/"+seg/10+"a.png")));
	
	unidadMin.setIcon(new ImageIcon(this.getClass().getResource("/cronometro/"+min%10+"a.png")));
	decenaMin.setIcon(new ImageIcon(this.getClass().getResource("/cronometro/"+min/10+"a.png")));
	
	unidadHora.setIcon(new ImageIcon(this.getClass().getResource("/cronometro/"+horas%10+"a.png")));
	decenaHora.setIcon(new ImageIcon(this.getClass().getResource("/cronometro/"+horas/10+"a.png")));
}
	
//Inner Class
	class tarea extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(parar==true)
				cancel();
			
			seg++;
	
			if(seg==60){
				seg=0;
				min++;
				if(min==60){
					min=0;
					horas++;
					if(horas==24) {
						horas=0;
					}
				}
			}
			
			actulizarLabels();
		}
		
	}
	
}









