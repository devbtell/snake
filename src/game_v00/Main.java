package game_v00;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Main extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton botonStart;
	private JPanel panel;
	private JLabel lblManzana;
	private JLabel lblCulebra;
	private JLabel lblSnakeGame;

	// Lanzamiento de la aplicación:
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Creación de la ventana:
	public Main() {
		
		// Panel principal
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(413, 375));
		contentPane.setLayout(null);
		
		// Panel secundario
		panel = new JPanel();
		panel.setBounds(5, 0, 469, 440);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 20, true));
		panel.setBackground(new Color(200, 247, 157));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		// Botón para empezar
		botonStart = new JButton("");
		botonStart.setBorder(new LineBorder(new Color(0, 0, 0), 4, true));
		botonStart.setIcon(new ImageIcon("pic/pic_v00/start.png"));
		botonStart.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 42));
		botonStart.setBackground(new Color(0, 255, 128));
		botonStart.setBounds(170, 302, 137, 86);
		panel.add(botonStart);
		botonStart.addActionListener(this);
		
		// Imagen de la serpiente
		lblCulebra = new JLabel("");
		lblCulebra.setIcon(new ImageIcon("pic/pic_v00/snake.png"));
		lblCulebra.setHorizontalAlignment(SwingConstants.CENTER);
		lblCulebra.setBounds(182, 145, 143, 132);
		panel.add(lblCulebra);
		
		// Imagen de la manzana
		lblManzana = new JLabel("");
		lblManzana.setIcon(new ImageIcon("pic/pic_v00/apple.png"));
		lblManzana.setBounds(136, 103, 103, 113);
		panel.add(lblManzana);
		
		// Titulo del juego
		lblSnakeGame = new JLabel("Snake Game");
		lblSnakeGame.setForeground(Color.BLACK);
		lblSnakeGame.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 48));
		lblSnakeGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblSnakeGame.setBounds(62, 38, 340, 86);
		panel.add(lblSnakeGame);
		
		// Para que la ventana aparezca en medio de la pantalla
		this.setLocationRelativeTo(null);
		// Para que el tamaño de la ventana no se pueda modificar
		this.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Interacción con el bóton
		Object o = e.getSource();
		if ( o.equals(botonStart)) {
			// Ocultara la portada 
			this.setVisible(false);
			// Iniciara una nueva partida
			new Snake();
		}
	}

}