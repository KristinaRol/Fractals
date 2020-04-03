package Mandelbrot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	Image img = new ImageIcon("Icon.png").getImage();
	FractalContainer fractalContainer = new FractalContainer();
	public static final int WIDTH = 1010;

	// needed arrays for combobox
	String[] fractalSelection = { "Mandelbrot", "Julia-Fraktal" };
	StartValue[] startValues = StartValue.values();

	// bottom panel stuff
	JComboBox<String> fractalSelectionBox = new JComboBox<String>(fractalSelection);
	JTextField iterationTextField = new JTextField(fractalContainer.mandelbrot.getIterations() + " ");
	JSlider zoomSlider = new JSlider(1, 6, 1);
	JTextField textField1 = new JTextField("0.0    ");
	JTextField textField2 = new JTextField("0.0    ");
	JToggleButton toggleButton = new JToggleButton("own constant");
	JComboBox<StartValue> startValuesBox = new JComboBox<StartValue>(startValues);
	JButton colorButton = new JButton("Inner color");
	JSlider rSlider = new JSlider(0,100,20);
	JSlider gSlider = new JSlider(0,100,40);
	JSlider bSlider = new JSlider(0,100,60);
	JSlider powerSlider = new JSlider(0,100,50);
	
	// label
	JLabel zoomLabel = new JLabel("zoom");
	JLabel startNumberLabel = new JLabel("start number c =");
	JLabel plus = new JLabel("+");
	JLabel i = new JLabel("i");
	JLabel iterationLabel = new JLabel("Iterations:");
	JLabel space = new JLabel("    ");
	JLabel r = new JLabel("r");
	JLabel g = new JLabel("g");
	JLabel b = new JLabel("b");
	JLabel scaleLabel = new JLabel("scale");

	public Frame() {
		
		// basic frame stuff
		setTitle("Fraktals");
		setIconImage(img);
		setSize(WIDTH, (int) (2.0 / 3 * WIDTH));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// combobox for fractal selection
		fractalSelectionBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String str = (String) fractalSelectionBox.getSelectedItem();
					if (str.equals("Mandelbrot")) {
						fractalContainer.mandelbrot.setSelectedFractal(Fractal.MANDELBROT);
						startValuesBox.setVisible(false);
						toggleButton.setVisible(false);
						toggleButton.setSelected(true);
						startNumberLabel.setVisible(true);
					} else if (str.equals("Julia-Fraktal")) {
						fractalContainer.mandelbrot.setSelectedFractal(Fractal.JULIA);
						startValuesBox.setVisible(true);
						toggleButton.setVisible(true);
						toggleButton.setSelected(false);
						startNumberLabel.setVisible(false);
						setJuliaConstant();
					}
				}
				fractalContainer.repaint();
			}
		});

		// button color change
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fractalContainer.innerColor = JColorChooser.showDialog(getContentPane(), "choose the background", Color.black);
				fractalContainer.repaint();
			}
		});

		// zoom slider
		zoomSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				double zoom = zoomSlider.getValue();
				fractalContainer.setZoom(zoom);
				fractalContainer.repaint();
			}
		});
		
		//iteration text field
		iterationTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String iterations = iterationTextField.getText();
				double it = Double.valueOf(iterations);
				fractalContainer.mandelbrot.setIterations( (int) it);
				fractalContainer.repaint();
			}
		});

		// numeric text field
		textField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String number1 = textField1.getText();
				double re = Double.valueOf(number1);
				fractalContainer.mandelbrot.setComplexConstantX(re);
				fractalContainer.repaint();
			}
		});
		textField2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String number2 = textField2.getText();
				double im = Double.valueOf(number2);
				fractalContainer.mandelbrot.setComplexConstantY(im);
				fractalContainer.repaint();
			}
		});

		// combobox for different start values
		startValuesBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!toggleButton.isSelected()) {
						setJuliaConstant();
					}
				}
				fractalContainer.repaint();
			}
		});

		// togglebutton to select own start value or start value from combobox
		toggleButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if (state == ItemEvent.SELECTED) {
					fractalContainer.mandelbrot.setComplexConstantX(Double.valueOf(textField1.getText()));
					fractalContainer.mandelbrot.setComplexConstantY(Double.valueOf(textField2.getText()));
				} else {
					setJuliaConstant();
				}
				fractalContainer.repaint();
			}
		});
		
		rSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int r = rSlider.getValue();
				fractalContainer.setR(r);
				fractalContainer.repaint();
			}
		});
		gSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int g = gSlider.getValue();
				fractalContainer.setG(g);
				fractalContainer.repaint();
			}
		});
		bSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int b = bSlider.getValue();
				fractalContainer.setB(b);
				fractalContainer.repaint();
			}
		});
		powerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int power = powerSlider.getValue();
				fractalContainer.setScale(power);
				fractalContainer.repaint();
			}
		});
		

		// Layout
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());

		// Borderlayout within center from main borderlayout
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		pane.add(center, BorderLayout.CENTER);
		center.add(fractalContainer, BorderLayout.CENTER);
		
		//Borderlayout in south
		JPanel southBorder = new JPanel();
		southBorder.setLayout(new BorderLayout());
		pane.add(southBorder, BorderLayout.SOUTH);
		
		// flowlayout north in borderlayout south
		JPanel upPanel = new JPanel();
		FlowLayout flowLayout1 = new FlowLayout(-1,10,5);
		upPanel.setLayout(flowLayout1);
		southBorder.add(upPanel, BorderLayout.CENTER);

		// flowlayout south in borderlayout in south
		JPanel bottomPanel = new JPanel();
		FlowLayout flowLayout2 = new FlowLayout(-1,5,5);
		bottomPanel.setLayout(flowLayout2);
		southBorder.add(bottomPanel, BorderLayout.SOUTH);

		// adding stuff to south region
		upPanel.add(fractalSelectionBox);
		upPanel.add(iterationLabel);
		upPanel.add(iterationTextField);
		upPanel.add(zoomLabel);
		upPanel.add(zoomSlider);
		upPanel.add(startNumberLabel);
		upPanel.add(toggleButton);
		toggleButton.setVisible(false);
		upPanel.add(textField1);
		upPanel.add(plus);
		upPanel.add(textField2);
		upPanel.add(i);
		upPanel.add(space);
		upPanel.add(startValuesBox);
		startValuesBox.setVisible(false);
		bottomPanel.add(colorButton);
		bottomPanel.add(r);
		bottomPanel.add(rSlider);
		bottomPanel.add(g);
		bottomPanel.add(gSlider);
		bottomPanel.add(b);
		bottomPanel.add(bSlider);
		bottomPanel.add(scaleLabel);
		bottomPanel.add(powerSlider);

		// needed so you can drag the fractals
		@SuppressWarnings("unused")
		Movement mv = new Movement(center, fractalContainer);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Frame frame = new Frame();
				frame.setVisible(true);
			}
		});
	}
	
	/**
	 * sets the complex constant in mandelbrot to selected constant
	 */
	private void setJuliaConstant() {
		fractalContainer.mandelbrot.setComplexConstantX(((StartValue) startValuesBox.getSelectedItem()).complex.re());
		fractalContainer.mandelbrot.setComplexConstantY(((StartValue) startValuesBox.getSelectedItem()).complex.im());
	}
	
	@Override
	public void setIconImage(Image image) {
		super.setIconImage(image);
	}
}
