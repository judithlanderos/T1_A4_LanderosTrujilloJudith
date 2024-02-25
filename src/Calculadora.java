import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//Los numeros ingresados al seleccionar el operador no se muestran pero si realiza 
// los eventos correctamente

class VentanaPrincipal extends JFrame {

    private JTextField muestra;

    private double primerNumero = 0;
    private String operacionPendiente = "";
    private boolean nuevaOperacion = true;

    public VentanaPrincipal() {
        getContentPane().setLayout(new GridLayout(6, 1)); //filas,columna
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 500);
        setLocationRelativeTo(null);
        setTitle("Calculadora");
        setVisible(true);

        JLabel txtEstandar = new JLabel("ESTANDAR");
        add(txtEstandar);

        JPanel panelResul = new JPanel();
        panelResul.setLayout(new FlowLayout());
        muestra = new JTextField(20);
        panelResul.add(muestra);
        add(panelResul);

        JPanel panelM = new JPanel();
        panelM.setLayout(new FlowLayout());
        String[] letras = {"M", "MR", "M+", "M-", "MS", "M*"};
        for (String letra : letras) {
            JButton btnLetra = new JButton(letra);
            panelM.add(btnLetra);
        }
        add(panelM);

        JPanel panelOpe = new JPanel();
        panelOpe.setLayout(new FlowLayout());
        String[] funciones = {"%", "Raiz", "x^2", "1/x"};
        for (String funcion : funciones) {
            JButton btnFuncion = new JButton(funcion);
            btnFuncion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String textoBoton = e.getActionCommand();
                    switch (textoBoton) {
                        case "%":
                            calcularPorcentaje();
                            break;
                        case "Raiz":
                            calcularRaizCuadrada();
                            break;
                        case "x^2":
                            calcularCuadrado();
                            break;
                        case "1/x":
                            calcularInversa();
                            break;
                    }
                }
            });
            panelOpe.add(btnFuncion);
        }
        add(panelOpe);

        JPanel panelOperaciones = new JPanel();
        panelOperaciones.setLayout(new FlowLayout());
        String[] operaciones = {"CE", "C", "Borrar", "/", "*", "-", "+"};
        for (String operacion : operaciones) {
            JButton btnOperacion = new JButton(operacion);
            btnOperacion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String textoBoton = e.getActionCommand();
                    switch (textoBoton) {
                        case "CE":
                            muestra.setText("");
                            break;
                        case "C":
                            muestra.setText("");
                            primerNumero = 0;
                            operacionPendiente = "";
                            break;
                        case "Borrar":
                            String textoActual = muestra.getText();
                            if (!textoActual.isEmpty()) {
                                muestra.setText(textoActual.substring(0, textoActual.length() - 1));
                            }
                            break;
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                            if (!nuevaOperacion) {
                                calcularResultado();
                            } else {
                                primerNumero = Double.parseDouble(muestra.getText());
                                nuevaOperacion = false;
                            }
                            operacionPendiente = textoBoton;
                            muestra.setText("");
                            break;
                        case "=":
                            calcularResultado();
                            operacionPendiente = "";
                            nuevaOperacion = true;
                            break;
                    }
                }
            });
            panelOperaciones.add(btnOperacion);
        }
        add(panelOperaciones);

        JPanel btnNum = new JPanel();
        btnNum.setLayout(new GridLayout(4, 4));
        String[][] numeros = {
            {"7", "8", "9", ""},
            {"4", "5", "6", ""},
            {"1", "2", "3", ""},
            {"", "0", ".", "="}
        };
        for (String[] fila : numeros) {
            for (String boton : fila) {
                JButton btnNumeroOperacion = new JButton(boton);
                if (!boton.isEmpty()) {
                    btnNumeroOperacion.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String textoBoton = e.getActionCommand();
                            if ("=".equals(textoBoton)) {
                                calcularResultado();
                                operacionPendiente = "";
                                nuevaOperacion = true;
                            } else {
                                muestra.setText(muestra.getText() + textoBoton);
                            }
                        }
                    });
                }
                btnNum.add(btnNumeroOperacion);
            }
        }
        add(btnNum);
    }

    private void calcularResultado() {
        double segundoNumero = Double.parseDouble(muestra.getText());
        double resultado = 0;
        switch (operacionPendiente) {
            case "+":
                resultado = primerNumero + segundoNumero;
                break;
            case "-":
                resultado = primerNumero - segundoNumero;
                break;
            case "*":
                resultado = primerNumero * segundoNumero;
                break;
            case "/":
                if (segundoNumero != 0) {
                    resultado = primerNumero / segundoNumero;
                } else {
                    JOptionPane.showMessageDialog(this, "Error: División por cero.");
                }
                break;
        }
        muestra.setText(String.valueOf(resultado));
    }

    private void calcularPorcentaje() {
        double porcentaje = Double.parseDouble(muestra.getText()) / 100;
        muestra.setText(String.valueOf(porcentaje));
    }

    private void calcularRaizCuadrada() {
        double numero = Double.parseDouble(muestra.getText());
        if (numero >= 0) {
            double resultado = Math.sqrt(numero);
            muestra.setText(String.valueOf(resultado));
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se puede calcular la raíz cuadrada de un número negativo.");
        }
    }

    private void calcularCuadrado() {
        double numero = Double.parseDouble(muestra.getText());
        double resultado = Math.pow(numero, 2);
        muestra.setText(String.valueOf(resultado));
    }

    private void calcularInversa() {
        double numero = Double.parseDouble(muestra.getText());
        if (numero != 0) {
            double inversa = 1 / numero;
            muestra.setText(String.valueOf(inversa));
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se puede calcular la inversa de cero.");
        }
    }
}

public class Calculadora {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal();
            }
        });
    }
}
