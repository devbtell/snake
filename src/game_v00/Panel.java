package game_v00;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int ancho = 500;
	private int alto = 500;
	// Tamaño de los cuadrados (pixeles)
	private int cuadrado = 20;
	// Cant. Cuadrados ( dimensiones / n.cuadrados )
	private int cuadrados = (ancho * alto) / (cuadrado * cuadrado);
	// Tamaño inicial de la serpiente
	private int tamaño = 5;
	// Direccion inicial de la serpiente
	private String direccion = "abajo";
	// Puntuación (manzanas ingeridas)
	private int numManzanas;
	// Coodenadas de la manzana
	private int manzanaX;
	private int manzanaY;
	private boolean jugando = false; 

	/*
	 * 2 arrays para el cuerpo de la serpiente:
	 * Uno para la coordenada X (ancho), y otro para la coordenada Y (alto).
	 */
	private int x[] = new int[cuadrados];
	private int y[] = new int[cuadrados];

	private Random random;	// Para generar un numero random
	private Timer timer;	// Temporizador (velocidad de la serpiente)

	public Panel() {
		this.setPreferredSize(new Dimension(ancho, alto));
		this.setBackground(Color.black);
		this.setFocusable(true);
		// Sistema de control del teclado
		this.addKeyListener(new MyKeyAdapter());
		jugar();
	}

	/*
	 * Mediante el metodo graphics ( si 'jugando' está en true ), muestra la comida,
	 * la cabeza, la cola y el score. En caso contrario, se llama al metodo 'fin',
	 * que muestra el fin de la partida.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		dibujar(g);
	}

	public void dibujar(Graphics g) {
		if (jugando) {
			// Manzanas
			g.setColor(new Color(210, 115, 90));
			g.fillOval(manzanaX, manzanaY, cuadrado, cuadrado);

			// Cabeza de la serpiente
			g.setColor(Color.white);
			g.fillRect(x[0], y[0], cuadrado, cuadrado);

			// Cuerpo de la serpiente
			for (int i = 1; i < tamaño; i++) {
				g.setColor(new Color(108, 202, 37));
				g.fillRect(x[i], y[i], cuadrado, cuadrado);
			}
			
			// Puntuación en pantalla
			g.setColor(Color.white);
			g.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
			getFontMetrics(g.getFont());
			g.drawString("Puntuacion: " + numManzanas, 180, 25);

		} else {
			fin(g);
		}
	}

	/*
	 * Cuando 'jugando' está en false:
	 * se ejecuta el metodo graphics de nuevo 
	 * para dibujar en pantalla el "game over"
	 * y la puntuación total de la partida.
	 */
	public void fin(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 50));
		g.drawString("¡Has Perdido!", 85, 250);
		
		g.setColor(Color.white);
		g.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
		g.drawString("Puntuación final: " + numManzanas, 150, 25);
	}

	// Creamos las coordenadas de la manzana con 'random'
	public void añadir_manzana() {
		// Creamos un nuevo objeto 'random' para evitar el error de null
		random = new Random();
		manzanaX = random.nextInt((int) (ancho / cuadrado)) * cuadrado;
		manzanaY = random.nextInt((int) (alto / cuadrado)) * cuadrado;
	}

	/*
	 * Si las coordenadas de la cabeza coinciden con la manzana generada,
	 * se suma 1 al tamaño de la serpiente y al score,
	 * y se genera otra random con el metodo 'añadir_manzana'.
	 */
	public void comer() {
		if (x[0] == manzanaX && y[0] == manzanaY) {
			tamaño++;
			numManzanas++;
			añadir_manzana();
		}
	}

	/*
	 * La partida comienza cambiando la variable 'jugando' a true
	 * y ejecuta el metodo de 'añadir_manzana' para generar una aleatoria;
	 * seguido de un 'timer' que indica la velocidad de la serpiente.
	 */
	public void jugar() {
		añadir_manzana();
		jugando = true;
		timer = new Timer(100, this);
		timer.start();
	}

	public void moverse() {
		// La cola de la serpiente sigue a la cabeza y coge las coordenadas anteriores
		for (int i = tamaño; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		// Movimiento de la cabeza según la tecla pulsada
		if (direccion.equals("izquierda")) {
			x[0] = x[0] - cuadrado;
		} else if (direccion.equals("derecha")) {
			x[0] = x[0] + cuadrado;
		} else if (direccion.equals("arriba")) {
			y[0] = y[0] - cuadrado;
		} else {
			y[0] = y[0] + cuadrado;
		}
	}

	/*
	 * Para saber si la serpiente se choca o no:
	 * Se verifica que la cabeza "x[0]" y "y[0]" coincide con alguna cordenada del cuerpo, 
	 * o también si la cabeza coincide con las cordenadas de las paredes.
	 */
	public void comprobar() {
		for (int i = tamaño; i > 0; i--) {
			if (x[0] == x[i] && y[0] == y[i]) {
				jugando = false;
			}
		}
		if (x[0] < 0 || x[0] >= ancho || y[0] < 0 || y[0] >= alto) {
			jugando = false;
		}
		if (!jugando) {
			timer.stop();
		}
	}

	/*
	 * Con el siguiente metodo hacemos mover la serpiente con las teclas
	 * "RIGHT", "LEFT", "UP" y "DOWN". La direccion va a tener constantemente un valor 
	 * ('derecha', 'izquierda', 'arriba' o 'abajo' ) y dependiendo de la tecla pulsada,
	 * esta variable cambiará de valor a alguno de los otros 2 valores, o seguirá con el mismo valor.
	 */
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (!direccion.equals("derecha")) {
					direccion = "izquierda";
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!direccion.equals("izquierda")) {
					direccion = "derecha";
				}
				break;
			case KeyEvent.VK_UP:
				if (!direccion.equals("abajo")) {
					direccion = "arriba";
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!direccion.equals("arriba")) {
					direccion = "abajo";
				}
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		e.getSource();
		if (jugando) {
			moverse();
			comer();
			comprobar();
		}
		repaint();
	}
	
}