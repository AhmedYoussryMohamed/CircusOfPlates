package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.Controller;

public class FirstWindow extends JFrame {

	// private ImageIcon ic = new ImageIcon( System.getenv("ahmed") +
	// "index.jpg" );
	private JPanel contentPane;
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public FirstWindow() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, d.width, d.height - 45);

		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel background = new JLabel( new ImageIcon(System.getenv("ahmed") + "index.jpg"));
		background.setLayout( new BorderLayout() );
		contentPane.add(background);

		ImageIcon start = new ImageIcon(System.getenv("ahmed") + "start.jpg");
		JButton btnStart = new JButton(new ImageIcon(
				(start.getImage()).getScaledInstance(400, 100,
						java.awt.Image.SCALE_SMOOTH)));
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							dispose();
							Gui frame = new Gui();
							frame.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});

			}

		});
		btnStart.setBounds(140, 200, 400, 100);
		contentPane.add(btnStart);

		ImageIcon load = new ImageIcon(System.getenv("ahmed") + "load.jpg");
		JButton btnLoad = new JButton(new ImageIcon(
				(load.getImage()).getScaledInstance(400, 100,
						java.awt.Image.SCALE_SMOOTH)));
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller control = Controller.getInstance();
				try {
					dispose();
					String input = getInput();
					if (input != null) {
						control.loadFile(input);
					}

				} catch (ClassNotFoundException | IOException e1) {
					System.out.println("There is no file to load.");
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								dispose();
								Gui frame = new Gui();
								frame.setVisible(true);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});
				}// end catch.
				dispose();
			}// end action performed.

		});

		btnLoad.setBounds(140, 300, 400, 100);
		contentPane.add(btnLoad);

		ImageIcon exit = new ImageIcon(System.getenv("ahmed") + "exit.jpg");
		JButton btnExit = new JButton(new ImageIcon(
				(exit.getImage()).getScaledInstance(400, 100,
						java.awt.Image.SCALE_SMOOTH)));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});
		btnExit.setBounds(140, 400, 400, 100);
		contentPane.add(btnExit);
		this.setVisible(true);
	}// end Const.

	public String getInput() {
		String input = JOptionPane.showInputDialog(this,
				"Enter The name of the file:", "File Name",
				JOptionPane.QUESTION_MESSAGE);
		return input;
	}// end method

	public void paint(Graphics g) {
		 super.paint(g);
		 g.setColor(Color.WHITE);
		 g.fillRect(0, 0, d.width, 235);
		 g.fillRect(0, 0, 148, d.height);
		 g.fillRect(0, 535, 600, d.height);
		Image bg = new ImageIcon(System.getenv("ahmed") + "index.jpg")
				.getImage();
		g.drawImage(bg, 550, 230, d.width - 550, d.height - 300, null);

	}

}// end class.
