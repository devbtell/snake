package game_v00;

import javax.swing.JFrame;

public class Snake extends JFrame{

	private static final long serialVersionUID = 1L;

	Snake() {
		Panel grafico = new Panel();		
		getContentPane().add(grafico);		
		this.setResizable(false);		
		this.pack();		
		this.setVisible(true);		
		this.setLocationRelativeTo(null);
	}
}