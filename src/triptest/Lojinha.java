package triptest;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Lojinha extends JFrame implements Runnable {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Lojinha frame = new Lojinha();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */

	private ActionListener acaoBotoes;

	int b = 0;
	JPanel jpan;
	JButton[] botao;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public void criarbotao() {

		botao = new JButton[20];
		int posX = 10;
		int posY = 10;
		int fila1 = 1;
		int fila2 = 11;

		for (int i = 0; i < 20; i++) {
			String v = Integer.toString(i);
			botao[i] = new JButton(v);
			this.jpan.add(botao[i]);
			botao[i].setBounds(posX, posY, 50, 20);
			botao[i].setBackground(Color.GREEN);
			botao[i].addActionListener(acaoBotoes);
			posX = posX + 45;

			if (i == fila1 && i < 9) {
				posX = 10;
				posY = posY + 30;
				fila1 = fila1 + 2;

			}

			if (i == 9) {
				posX = 150;
				posY = 10;
			}

			if (i == fila2) {
				posX = 150;
				posY = posY + 30;
				fila2 = fila2 + 2;
			}

			colocaFunc(botao[i]);

		}
	}

	private void colocaFunc(JButton jb) {

		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jb.setBackground(Color.ORANGE);

				Confirmar_Compra frame = new Confirmar_Compra();
				frame.setVisible(true);

			}
		});
	}

	ObjectInputStream input; // comunicação
	ObjectOutputStream output; // bidirecional

	public Lojinha(Socket socketCliente) throws IOException {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		input = new ObjectInputStream(socketCliente.getInputStream());
		output = new ObjectOutputStream(socketCliente.getOutputStream());

		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		jpan = new JPanel();
		jpan.setBackground(Color.LIGHT_GRAY);
		jpan.setBounds(10, 37, 260, 166);
		contentPane.add(jpan);
		jpan.setLayout(null);

		JButton btnNewButton = new JButton("Comprar");
		btnNewButton.setBounds(337, 455, 116, 39);
		contentPane.add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(425, 63, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(581, 63, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(380, 66, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("SobreNome");
		lblNewLabel_1.setBounds(521, 66, 55, 14);
		contentPane.add(lblNewLabel_1);

		textField_2 = new JTextField();
		textField_2.setBounds(425, 102, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("RG");
		lblNewLabel_2.setBounds(392, 105, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Escolha um assento");
		lblNewLabel_3.setBounds(80, 12, 190, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Digite as informa\u00E7\u00F5es da passagem");
		lblNewLabel_4.setBounds(425, 12, 278, 14);
		contentPane.add(lblNewLabel_4);

		textField_3 = new JTextField();
		textField_3.setBounds(581, 102, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("CPF");
		lblNewLabel_5.setBounds(538, 105, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		

		//criarbotao();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		criarbotao();
		while (true) {
			try {
				
				String c = "FON";
				output.writeObject(c);
				
				Object lido = input.readObject();
				
				if (lido instanceof Seat[]) {
					Seat[] seat = (Seat[]) lido;
					
					for (int i = 0; i < seat.length; i++) {
						if (seat[i].isFree()) {
							System.out.println(i + " ta livre");
						}
						else if(seat[i].isPurchased()) {
							
							System.out.println(i + " foi comprado");
						}
					}

					for (int i = 0; i < seat.length; i++) {
						if (seat[i].isFree()) {
							botao[i].setBackground(Color.GREEN);
							repaint();
							revalidate();
							
						} 
						
						else if (seat[i].isPurchased()) {
							botao[i].setBackground(Color.RED);
							repaint();
							revalidate();
						}
						
						else if(seat[i].isInProcess()) {
							botao[i].setBackground(Color.ORANGE);
							repaint();
							revalidate();
						}
						
					}
					
				}
			} 
			catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// break;
			}

		}
	}
}
