import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class MainFrame extends JFrame {
	// set scale

	// Create button

	static ButtonGenerate generate = new ButtonGenerate();
	
	// Create text
	static JLabel label1 = new JLabel("Вкажіть a : ");
	static JLabel label4 = new JLabel("Вкажіть b : ");
	static JLabel label2 = new JLabel("Область побудови (по осі х) з/Ідо : ");
	static JLabel label3 = new JLabel("Крок зміни x : ");

	// Create textfield
	static FieldWithDot textfield1 = new FieldWithDot();
	static FieldWithDot textfield2 = new FieldWithDot();
	static FieldWithDot textfield3 = new FieldWithDot();
	static FieldWithDot textfield4 = new FieldWithDot();
	static FieldWithDot textfield5 = new FieldWithDot();
	// set static main frame
	static MainFrame frame;

	public static void main(String args[]) {

		// frame create constructor of main class
		frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public MainFrame() {
		// constructor of frame

		setTitle("Graph");

		// get screen Size
		Toolkit kit = Toolkit.getDefaultToolkit();
		int scX = kit.getScreenSize().width;
		int scY = kit.getScreenSize().height;

		// set frame location and size
		setLocation(10 * scX / 100, 10 * scY / 100);
		setSize(600, 180);

		// set panel constructor IntefaceForUser
		InterfaceForUser panel = new InterfaceForUser();

		setLayout(new BorderLayout());
		add(panel, BorderLayout.PAGE_START);
		

		setVisible(true);
	}

	// check String for empty
	public static boolean isEmpty(FieldWithDot textfield12) {
		if (textfield12.getText().equals(""))
			return true;
		return false;
	}
}

// set only number and dot
class FieldWithDot extends JTextField {
	public FieldWithDot() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {

				char c = e.getKeyChar();
			
				if (((int) c != 45 && (int) c != 46 && !Character.isDigit(c)) && (int) c != 8) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
	}

}



// create button Generate for MainFrame
class ButtonGenerate extends JButton {
	public ButtonGenerate() {
		setText("Generate");
		addActionListener(new ButtonGenerateActionListener());
	}
}

// create FileSafeButton Generate for SafeFrame
class FrameSafeButton extends JButton {
	public FrameSafeButton() {
		setText("Safe");
		addActionListener(new FrameSafeButtonActionListener());
	}
}

// create FileSafeButtonActionListener for FileSafeButton
class FrameSafeButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// safe image
		BufferedImage image = (BufferedImage) ButtonGenerateActionListener.plot.createImage(ButtonGenerateActionListener.plot.getWidth(),
				ButtonGenerateActionListener.plot.getHeight());
		Graphics2D g = image.createGraphics();
		ButtonGenerateActionListener.plot.paint(g);
		g.dispose();
		// get Text
		String text = ButtonSafeActionListener.tfield.getText();
		try {
			ImageIO.write(image, "jpeg", new File(text + ".jpeg"));
		} catch (IOException io) {
			io.printStackTrace();

		}
		// close window
		ButtonSafeActionListener.frame.dispose();
	}
}



// create ButtonSafe for MainClass
class ButtonSafe extends JButton {
	public ButtonSafe() {
		setText("Safe");
		addActionListener(new ButtonSafeActionListener());
	}
}



// create FailButtonActionListener for ButtonSafe
class FailButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ButtonGenerateActionListener.frame.dispose();

	}
}

// create fail massage
class FailButton extends JButton {
	public FailButton() {
		setText("OK");
		addActionListener(new FailButtonActionListener());
	}
}

// create frame fail massage
class FailFrame extends JFrame {
	public FailFrame() {
		// set Tittle Location and size
		setTitle("Fail");
		Toolkit kit = Toolkit.getDefaultToolkit();
		int scX = kit.getScreenSize().width;
		int scY = kit.getScreenSize().height;
		setLocation(10 * scX / 100, 10 * scY / 100);
		setSize(400, 100);

		
		setLayout(new GridBagLayout());
		// set Text and Button of exit
		add(new JLabel("Помилка при введенні даних, спробуйте ще раз"), new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		FailButton OK = new FailButton();
		add(OK, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
	}
}

// Listener for Button Generate
class ButtonGenerateActionListener implements ActionListener {
	static FailFrame frame;
	static PlotFrame plot;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// check empty
		if (MainFrame.isEmpty(MainFrame.textfield1) || MainFrame.isEmpty(MainFrame.textfield2)
				|| MainFrame.isEmpty(MainFrame.textfield3) || MainFrame.isEmpty(MainFrame.textfield4)) {
			// create fail frame
			frame = new FailFrame();
			frame.setVisible(true);
		} else {
			// get variable
			double a = Double.parseDouble(MainFrame.textfield1.getText());
			double d0 = Double.parseDouble(MainFrame.textfield2.getText());
			double d = Double.parseDouble(MainFrame.textfield3.getText());
			double h = Double.parseDouble(MainFrame.textfield4.getText());
			if ( d < d0 || h > a / 2 || h < 0.001) {
				// create fail frame
				frame = new FailFrame();
				frame.setVisible(true);

			} else {
				// if all is OK build graphics
				 plot = new PlotFrame();
				plot.setVisible(true);
				/*MainFrame.frame.add(new CreatePlot());

				MainFrame.frame.setVisible(true);*/
			}
		}

	}
}

class PlotFrame extends JFrame{

	public PlotFrame(){
		setSize(800,800);
		 ButtonSafe safe = new ButtonSafe();

		add(safe, BorderLayout.PAGE_END);
		add(new CreatePlot());
		
	}
}
// create listeter for Safe for MainFrame
class ButtonSafeActionListener implements ActionListener {
	static JFrame frame;
	static JTextField tfield = new JTextField();

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// add new frame for safe
		frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setSize(400, 100);
		frame.setLocation(500, 250);

		// add textfield label and button
		frame.add(new JLabel("Введіть назву файла:"), new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.LINE_END, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		frame.add(tfield, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		frame.add(new JLabel(".jpeg"), new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.LINE_START,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		frame.add(new FrameSafeButton(), new GridBagConstraints(0, 1, 3, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		frame.setVisible(true);

	}
}

// create panel for variable
class InterfaceForUser extends JPanel {
	public InterfaceForUser() {

		setLayout(new GridBagLayout());

		JLabel nwe = new JLabel();
		// add image text button and label ,Locate it
		nwe.setIcon(new javax.swing.ImageIcon(getClass().getResource("Graph.png")));
		add(nwe, new GridBagConstraints(0, 0, 1, 5, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.label1, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.label4, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.label2, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.label3, new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.textfield1, new GridBagConstraints(2, 2, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.textfield5, new GridBagConstraints(2, 3, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.textfield2, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.textfield3, new GridBagConstraints(3, 4, 1, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.textfield4, new GridBagConstraints(2, 5, 2, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		add(MainFrame.generate, new GridBagConstraints(0, 6, 4, 1, 1, 1, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		
		// default construct
		MainFrame.textfield1.setText("2");
		MainFrame.textfield2.setText("-1");
		MainFrame.textfield3.setText("3");
		MainFrame.textfield4.setText("0.01");
		MainFrame.textfield5.setText("1");
	}
}

// create Plot
class CreatePlot extends JPanel {

	// set variable
	double a , b;
	double d0;
	double d;
	double h;

	public void paint(Graphics g) {
		int scale = 400;
		System.out.print(getWidth() +" "+ getHeight());

		super.paint(g);
		// getVariable
		int diam;
		a = Double.parseDouble(MainFrame.textfield1.getText());
		b = Double.parseDouble(MainFrame.textfield5.getText());
		d0 = Double.parseDouble(MainFrame.textfield2.getText());
		d = Double.parseDouble(MainFrame.textfield3.getText());
		h = Double.parseDouble(MainFrame.textfield4.getText());

		// set size
		int x0 = getWidth() / 5;
		int y0 = getHeight() / 2;

		double change0x = 1.0;
		// change scale of graphics
		//while ((x0 + (int) (h * scale) + (int) ((a - a / 10) * scale)) > x0 * 10) {
		//	scale = scale / 2;
		//	change0x *= 2;
		//}

	//add Arr for Plot

		double rn, xn, yn;
		

		//double size = (2 * Math.PI - 0.01) / h + 1;
		System.out.println();

	/*	int[] arrX = new int[(int) size];
		int[] arrY = new int[(int) size];
		int k = 0;
			for (double i = 0; i < 2 * Math.PI - 0.01; i += h) {

			rn = b + a * Math.cos(i);
			xn = (int) (rn * Math.cos(i) * scale);
			arrX[k] = (int) xn;
			yn = (int) (rn * Math.sin(i) * scale);
			arrY[k] = (int) yn;
			k++;

		}*/
		double[] arrrX={0.0375,0.075,0.15,0.3};
		double[] arrrY={0.351,0.467,1.013,1.473};
		
		int[] arrX = new int[arrrX.length];
		int[] arrY = new int[arrrX.length];
		for (int i = 0; i < arrrY.length; i++) {
			arrX[i]=(int) (arrrX[i]* 2000);
			arrY[i]=(int) (arrrY[i]* 100);
			
		}
		// point line thickness
		//((Graphics2D) g).setStroke(new BasicStroke(3));

		for (int i = 0; i < arrX.length - 1; i++) {
			g.drawLine(x0 + arrX[i], y0 - y0 / 100, x0 + arrX[i], y0 + y0 / 100);
			g.drawLine(x0 + -x0 / 30, y0 - arrY[i], x0 + x0 / 30, y0 - arrY[i]);
			g.drawString(arrrY[i] + "", x0 - x0 / 4, y0 - arrY[i] + y0 / 60);
			g.drawString(arrrX[i] + "", x0 - x0 / 10 + arrX[i], y0 + y0 / 20);
		//	g.setColor(Color.BLUE);
			//if(arrX[i]<d*scale&arrX[i]>d0*scale)
			g.drawLine(arrX[i] + x0, -arrY[i]+ y0, arrX[i + 1] + x0, -arrY[i + 1] +y0);

		}
		int i = arrX.length-1;
		g.drawLine(x0 + arrX[i], y0 - y0 / 100, x0 + arrX[i], y0 + y0 / 100);
		g.drawLine(x0 + -x0 / 30, y0 - arrY[i], x0 + x0 / 30, y0 - arrY[i]);
		g.drawString(arrrY[i] + "", x0 - x0 / 4, y0 - arrY[i] + y0 / 60);
		g.drawString(arrrX[i] + "", x0 - x0 / 10 + arrX[i], y0 + y0 / 20);
	//	g.setColor(Color.BLUE);
		//if(arrX[i]<d*scale&arrX[i]>d0*scale)
		i = arrX.length-2;
		g.drawLine(arrX[i] + x0, -arrY[i]+ y0, arrX[i + 1] + x0, -arrY[i + 1] +y0);

		System.out.println(arrX[i + 1] + x0+ " x " + (-arrY[i + 1] +y0) + "  y  " );
		System.out.println(arrX[0] + x0+ " x " + (-arrY[0] +y0) + "  y  " );
		// draw Legend
	//	g.drawLine(75,35,600,147);
		g.drawLine(231,328,756,216);
		
		g.drawLine(x0 + x0 / 2, y0 / 10, x0 * 2, y0 / 10);
		g.setColor(Color.black);
		((Graphics2D) g).setStroke(new BasicStroke(1));
		g.drawString(" Завитка Паскаля", x0 * 2, y0 / 10);

		// draw 0x 0y
		g.drawLine(x0, 0, x0, 2 * y0);
		g.drawLine(0, y0, 10 * x0, y0);
		// x
		
		//setMax
	//	Arrays.sort(arrX);
		diam = (arrX[arrX.length - 1]) / 3;

	double caunter = 0;

		for (int j = 0; x0 + j <= getWidth(); j += diam) {
			// draw line
			g.drawLine(x0 + j, y0 - y0 / 100, x0 + j, y0 + y0 / 100);
			g.drawLine(x0- j, y0 - y0 / 100, x0 - j, y0 + y0 / 100);
			// draw number
			if (caunter != 0)
			//	g.drawString(caunter + "", x0 - x0 / 10 + i, y0 + y0 / 20);
			if (caunter != 0)
			//	g.drawString(-caunter + "", x0 - x0 / 10 - i, y0 + y0 / 20);
			caunter += change0x;
		}

		// y
		caunter = 0;
		for (int j = 0;  j < arrrY.length; j += diam) {
			// draw line
			g.drawLine(x0 + -x0 / 30, y0 - j, x0 + x0 / 30, y0 - j);
			g.drawLine(x0 + -x0 / 30, y0 + j, x0 + x0 / 30, y0 + j);
			// draw number
			
			if (caunter != 0)
			//	g.drawString(-caunter + "", x0 - x0 / 4, y0 + i + y0 / 60);
			caunter += (change0x / 2);
		}

	}

}
