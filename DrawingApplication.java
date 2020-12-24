
package DrawingApplication;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawingApplication extends JPanel implements MouseListener, MouseMotionListener {
	static ArrayList<Drawable> itemsDrawn;
	public JButton clear, undo;
	private JLabel mousePos;
	public DrawingApplication.DrawPanel draw = new DrawPanel();
	public DrawingApplication.ControlPanel cp = new ControlPanel(draw);
        

	public DrawingApplication() {

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		JPanel control = new JPanel();
                
                
		mousePos = new JLabel("( , )");
		bottom.add(mousePos, BorderLayout.WEST);
		bottom.setVisible(true);
		clear = new JButton("Clear");
		undo = new JButton("Undo");
		start = end = null;
                
                
		control.add(undo);
		control.add(clear);
		control.add(cp);
		panel.add(control, BorderLayout.NORTH);
                
                
		draw.setLayout(new GridLayout());
		draw.setVisible(true);
		draw.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(0, 0, 20, 30)));
		draw.setBackground(Color.WHITE);
                
                
		panel.add(draw, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.add(bottom, BorderLayout.SOUTH);
		add(panel, BorderLayout.PAGE_START);
		addMouseListener((MouseListener) this);
		addMouseMotionListener((MouseMotionListener) this);

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (clear == ae.getSource()) {
					itemsDrawn.clear();
					repaint();
				}
			}
		});

		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (itemsDrawn.size() != 0) {
					itemsDrawn.remove(itemsDrawn.size() - 1);
					repaint();
				}
			}
		});
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Java 2D Drawings");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                final DrawingApplication drawing = new DrawingApplication();
		final JLabel coordinates = new JLabel("Mouse Coordinates");
		coordinates.setForeground(Color.BLUE);
		frame.add(coordinates, BorderLayout.SOUTH);
		frame.setLayout(new BorderLayout());
		frame.add(drawing, BorderLayout.NORTH);


                frame.pack();

                frame.setLocationRelativeTo(null);

                frame.setVisible(true);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public class State {

		private final Color foreground, background;
		private final boolean gradient, filled, dashed;
		private final int lineWidth, dashLength;

		public State(Color foreground, Color background, boolean gradient, boolean filled, boolean dashed,
				int lineWidth, int dashLength) {
			this.foreground = foreground;
			this.background = background;
			this.gradient = gradient;
			this.filled = filled;
			this.dashed = dashed;
			this.lineWidth = lineWidth;
			this.dashLength = dashLength;
		}

		public Color getForeground() {
			return foreground;
		}

		public Color getBackground() {
			return background;
		}

		public boolean isGradient() {
			return gradient;
		}

		public boolean isFilled() {
			return filled;
		}

		public boolean isDashed() {
			return dashed;
		}

		public int getLineWidth() {
			return lineWidth;
		}

		public int getDashLength() {
			return dashLength;
		}
	}

	public class ControlPanel extends JPanel {

		public final JComboBox shapes;
		private final JButton foreground, background;
		private final JCheckBox gradient, filled, dashed;
		private final JTextField lineWidth, dashLength;
		private final JLabel width, dash;
		private JPanel panel;
		private DrawPanel drawPanel;
                
                
                

		public ControlPanel(DrawPanel panel) {
			shapes = new JComboBox<>(new String[] { "Rectangle", "Oval", "Line" });
			foreground = new JButton("1st Color");
			foreground.setBackground(Color.BLACK);
			foreground.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					
                                    
					foreground.setBackground(JColorChooser.showDialog(null, "Pick your color", Color.BLACK));
				}
			});

			background = new JButton("2nd Color");
			background.setBackground(Color.WHITE);
			background.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					
					background.setBackground(JColorChooser.showDialog(null, "Pick your color", Color.BLACK));
				}
			});

			gradient = new JCheckBox("Use Gradient");
			filled = new JCheckBox("Filled");
			dashed = new JCheckBox("Dashed");
			dashLength = new JTextField("10");
			lineWidth = new JTextField("2");
			dash = new JLabel("Dash Length:");
			width = new JLabel("Line Width:");
                        
                        
			JPanel newpanel = new JPanel();
			newpanel.add(foreground);
			newpanel.add(background);
			newpanel.add(filled);
			setLayout(new FlowLayout());
                        
                        
                        
                        
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 1;
			add(new JLabel("Shape: "));
			add(shapes, gbc);
			add(newpanel, gbc);
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.NORTH;
			add(gradient, gbc);
			add(dashed, gbc);
			add(dash);
			add(dashLength);
			add(width);
			add(lineWidth);
                        
                        
                        
			this.drawPanel = panel;
			MouseHandler mouseHandler = new MouseHandler();
			drawPanel.addMouseListener(mouseHandler);
			drawPanel.addMouseMotionListener(mouseHandler);
		}

		public int getDash() {
			String length = dashLength.getText();
			int dash = Integer.parseInt(length);
			return dash;
		}

		public int getLine() {
			String width = lineWidth.getText();
			int line = Integer.parseInt(width);
			return line;
		}

		protected Drawable createDrawable() {
			Drawable drawable = null;
			State state = new State(foreground.getBackground(), background.getBackground(), gradient.isSelected(),
					filled.isSelected(), dashed.isSelected(), getLine(), getDash());
			String selected = (String) shapes.getSelectedItem();
			if ("rectangle".equalsIgnoreCase(selected)) {
				drawable = new MyRectangle(state);
			} else if ("oval".equalsIgnoreCase(selected)) {
				drawable = new MyOval(state);
			} else if ("Line".equalsIgnoreCase(selected)) {
				drawable = new MyLine(state);
			}
			return drawable;
		}

		public class MouseHandler extends MouseAdapter {
			private Drawable drawable;
			private Point clickPoint;

			public void mousePressed(MouseEvent e) {
				drawable = createDrawable();
				drawable.setLocation(e.getPoint());
				drawPanel.addDrawable(drawable);
				clickPoint = e.getPoint();
				String position = "(" + e.getX() + "," + e.getY() + ")";
				mousePos.setText(position);
			}

			public void mouseDragged(MouseEvent e) {
				Point drag = e.getPoint();
				Point /* start */
				start = clickPoint;

				int maxX = Math.max(drag.x, start.x);
				int maxY = Math.max(drag.y, start.y);
				int minX = Math.min(drag.x, start.x);
				int minY = Math.min(drag.y, start.y);
				int width = maxX - minX;
				int height = maxY - minY;

				if (shapes.getSelectedItem().equals("Line")) {
					drawable.setLocation(start);
					drawable.setSize(new Dimension(drag.x - start.x, drag.y - start.y));
				} else {
					drawable.setLocation(new Point(minX, minY));
					drawable.setSize(new Dimension(width, height));
				}

				String position = "(" + start.x + "," + start.y + ") - (" + drag.x + "," + drag.y + ")";

				mousePos.setText(position);
				drawPanel.repaint();
			}
			
			public void mouseMoved(MouseEvent e) {
				String position = "(" + e.getPoint().x + "," + e.getPoint().y + ")";
				mousePos.setText(position);
			}
		}
	}

	public interface Drawable {
		public void paint(JComponent parent, Graphics2D g2d);

		public void setLocation(Point location);

		public void setSize(Dimension size);

		public State getState();

		public Rectangle getBounds();
	}

	public abstract class AbstractDrawable implements Drawable {
		private Rectangle limit;
		private State state;

		public AbstractDrawable(State state) {
			limit = new Rectangle();
			this.state = state;
		}

		@Override
		public State getState() {
			return state;
		}

		public abstract Shape getShape();

		@Override
		public void setLocation(Point location) {
			limit.setLocation(location);
		}

		@Override
		public void setSize(Dimension size) {
			limit.setSize(size);
		}

		@Override
		public Rectangle getBounds() {
			return limit;
		}

		@Override
		public void paint(JComponent parent, Graphics2D g2d) {
			Shape shape = getShape();
			State state = getState();
			Rectangle limit = getBounds();
			final BasicStroke dashed = new BasicStroke(state.lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
					new float[] { state.dashLength }, 0);
			final BasicStroke solid = new BasicStroke(state.lineWidth);

			g2d.setPaint(state.getForeground());
			g2d.setStroke(solid);

			if (state.isGradient() && limit.width > 0 && limit.height > 0) {
				Point2D startPoint = new Point2D.Double(limit.x, limit.y);
				Point2D endPoint = new Point2D.Double(limit.x + limit.width, limit.y + limit.height);
				LinearGradientPaint gp = new LinearGradientPaint(startPoint, endPoint, new float[] { 0f, 1f },
						new Color[] { state.getForeground(), state.getBackground() });
				g2d.setPaint(gp);
			}

			if (state.isFilled()) {
				g2d.fill(shape);
			}

			if (state.isDashed()) {
				g2d.setStroke(dashed);
			}

			g2d.draw(shape);
		}
	}

	public class MyRectangle extends AbstractDrawable {
		public MyRectangle(State state) {
			super(state);
		}
		@Override
		public Shape getShape() {
			return getBounds();
		}
	}

	public class MyOval extends AbstractDrawable {
		public MyOval(State state) {
			super(state);
		}
		@Override
		public Shape getShape() {
			Rectangle limit = getBounds();
			return new Ellipse2D.Float(limit.x, limit.y, limit.width, limit.height);
		}
	}

	public class MyLine extends AbstractDrawable {
		public MyLine(State state) {
			super(state);
		}
		@Override
		public Shape getShape() {
			Rectangle limit = getBounds();
			return new Line2D.Float(limit.x, limit.y, limit.x + limit.width, limit.y + limit.height);
		}
	}

	public class DrawPanel extends JPanel {
		public DrawPanel() {
			itemsDrawn = new ArrayList<>();
		}
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(500, 500);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			for (Drawable d : itemsDrawn) {
				d.paint(this, g2d);
			}
			g2d.dispose();
		}

		public void addDrawable(Drawable drawable) {
			itemsDrawn.add(drawable);
			repaint();
		}
	}

	Point start, end;

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}
}