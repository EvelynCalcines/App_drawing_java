import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Figure {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Figure(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        // Implement the drawing logic here
    }

    public void setXYSize(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}

class Square extends Figure {
    public Square(int x, int y, int size) {
        super(x, y, size, size);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}

class Circle extends Figure {
    public Circle(int x, int y, int size) {
        super(x, y, size, size);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);
    }
}

class Rectangle extends Figure {
    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
}

class FigureCollection {
    private List<Figure> figures = new ArrayList<>();

    public void addFigure(Figure figure) {
        figures.add(figure);
    }

    public void addFigures(List<Figure> newFigures) {
        figures.addAll(newFigures);
    }

    public void drawFigures(Graphics g) {
        for (Figure figure : figures) {
            figure.draw(g);
        }
    }

    public void moveLeft(int pixels) {
        for (Figure figure : figures) {
            figure.setXYSize(figure.x - pixels, figure.y, figure.width, figure.height);
        }
    }

    public void enlarge(int pixels) {
        for (Figure figure : figures) {
            figure.setXYSize(figure.x, figure.y, figure.width + pixels, figure.height + pixels);
        }
    }

    public void removeFigures(List<Figure> figuresToRemove) {
        figures.removeAll(figuresToRemove);
    }
}

public class DrawingFigures {
    private static List<Figure> currentFigures = new ArrayList<>();
    private static FigureCollection mainCollection = new FigureCollection();
    private static int xCoordinate = 10; // Nueva variable para controlar la posición x de las figuras agregadas

    public class DrawingCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Dibuja las figuras en currentFigures
            for (Figure figure : currentFigures) {
                figure.draw(g);
            }
        }
    }

    public static void main(String[] args) {
        DrawingFigures drawingFigures = new DrawingFigures();

        JFrame frame = new JFrame("Drawing Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        DrawingCanvas canvas = drawingFigures.new DrawingCanvas();
        frame.add(canvas);

        JButton btnAddCircle = new JButton("Add Circle");
        JButton btnAddSquare = new JButton("Add Square");
        JButton btnAddRectangle = new JButton("Add Rectangle");
        JButton btnGroupFigures = new JButton("Group Figures");
        JButton btnMoveLeft = new JButton("Move Left");
        JButton btnEnlarge = new JButton("Enlarge");

        JTextField txtX = new JTextField(5);
        JTextField txtY = new JTextField(5);
        JTextField txtWidth = new JTextField(5);
        JTextField txtHeight = new JTextField(5);

        canvas.add(btnAddCircle);
        canvas.add(btnAddSquare);
        canvas.add(btnAddRectangle);
        canvas.add(btnGroupFigures);
        canvas.add(btnMoveLeft);
        canvas.add(btnEnlarge);
        canvas.add(new JLabel("X: "));
        canvas.add(txtX);
        canvas.add(new JLabel("Y: "));
        canvas.add(txtY);
        canvas.add(new JLabel("Width: "));
        canvas.add(txtWidth);
        canvas.add(new JLabel("Height: "));
        canvas.add(txtHeight);

        btnAddCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int y = Integer.parseInt(txtY.getText());
                    int size = Integer.parseInt(txtWidth.getText());

                    if (size <= 0) {
                        JOptionPane.showMessageDialog(null, "El tamaño debe ser un número positivo.");
                        return;
                    }

                    Figure circle = new Circle(xCoordinate, y, size);
                    currentFigures.add(circle);
                    canvas.repaint();

                    xCoordinate += size + 10; // Ajusta la posición x para la siguiente figura
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingresa valores numéricos válidos.");
                }
            }
        });

        btnAddSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int y = Integer.parseInt(txtY.getText());
                    int size = Integer.parseInt(txtWidth.getText());

                    if (size <= 0) {
                        JOptionPane.showMessageDialog(null, "El tamaño debe ser un número positivo.");
                        return;
                    }

                    Figure square = new Square(xCoordinate, y, size);
                    currentFigures.add(square);
                    canvas.repaint();

                    xCoordinate += size + 10; // Ajusta la posición x para la siguiente figura
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingresa valores numéricos válidos.");
                }
            }
        });

        btnAddRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int y = Integer.parseInt(txtY.getText());
                    int width = Integer.parseInt(txtWidth.getText());
                    int height = Integer.parseInt(txtHeight.getText());

                    if (width <= 0 || height <= 0) {
                        JOptionPane.showMessageDialog(null, "El ancho y la altura deben ser números positivos.");
                        return;
                    }

                    Figure rectangle = new Rectangle(xCoordinate, y, width, height);
                    currentFigures.add(rectangle);
                    canvas.repaint();

                    xCoordinate += width + 10; // Ajusta la posición x para la siguiente figura
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingresa valores numéricos válidos.");
                }
            }
        });

        btnGroupFigures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFigures.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay figuras para agrupar.");
                    return;
                }

                // Paso 1: Añade las figuras actuales en una colección aparte
                List<Figure> groupedFigures = new ArrayList<>();
                groupedFigures.addAll(currentFigures);

                // Paso 2: Quita las figuras de la colección principal
                currentFigures.clear();

                // Paso 3: Añade a la colección principal la colección del paso 1
                mainCollection.addFigures(groupedFigures);
                xCoordinate = 10; // Reinicia la posición x

                // Mostrar información sobre el agrupamiento
                int numGroupedFigures = groupedFigures.size();
                System.out.println("Figuras agrupadas: " + numGroupedFigures);

                for (Figure figure : groupedFigures) {
                    System.out.println("Figura agrupada: " + figure.getClass().getSimpleName());
                }

                canvas.repaint();
            }
        });

        btnMoveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pixelsToMove = 10;
                for (Figure figure : currentFigures) {
                    figure.setXYSize(figure.x - pixelsToMove, figure.y, figure.width, figure.height);
                }
                mainCollection.moveLeft(pixelsToMove);
                canvas.repaint();
            }
        });

        btnEnlarge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pixelsToEnlarge = 10;
                for (Figure figure : currentFigures) {
                    figure.setXYSize(figure.x, figure.y, figure.width + pixelsToEnlarge,
                            figure.height + pixelsToEnlarge);
                }
                mainCollection.enlarge(pixelsToEnlarge);
                canvas.repaint();
            }
        });

        frame.setVisible(true);
    }
}
