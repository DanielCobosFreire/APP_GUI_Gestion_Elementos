import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Aplicación GUI para gestión de elementos
 * Permite agregar elementos a una lista, mostrarlos y limpiar la lista
 * 
 * @author Daniel_Cobos_Freire
 * @version 1.0
 */
public class GestionElementosApp extends JFrame {
    // Componentes de la interfaz
    private JLabel tituloLabel;
    private JLabel instruccionLabel;
    private JTextField entradaTextField;
    private JButton agregarButton;
    private JButton limpiarButton;
    private JList<String> elementosList;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;
    private JLabel statusLabel;

    /**
     * Constructor que inicializa la interfaz gráfica
     */
    public GestionElementosApp() {
        // Configuración de la ventana principal
        setTitle("Gestión de Elementos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Centrar la ventana
        
        // Inicializar componentes
        inicializarComponentes();
        
        // Configurar el layout y agregar componentes
        configurarLayout();
        
        // Hacer visible la ventana
        setVisible(true);
    }

    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Etiquetas
        tituloLabel = new JLabel("Gestión de Elementos", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        instruccionLabel = new JLabel("Ingrese un elemento:");
        
        // Campo de texto
        entradaTextField = new JTextField(20);
        
        // Botones
        agregarButton = new JButton("Agregar");
        limpiarButton = new JButton("Limpiar Todo");
        
        // Modelo y lista para mostrar elementos
        listModel = new DefaultListModel<>();
        elementosList = new JList<>(listModel);
        elementosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Panel de desplazamiento para la lista
        scrollPane = new JScrollPane(elementosList);
        
        // Etiqueta de estado
        statusLabel = new JLabel("Lista vacía. Agregue elementos.", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GRAY);
        
        // Configurar manejadores de eventos
        configurarEventos();
    }

    /**
     * Configura el layout y añade los componentes a la ventana
     */
    private void configurarLayout() {
        // Panel principal con BorderLayout
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior para el título
        JPanel panelTitulo = new JPanel();
        panelTitulo.add(tituloLabel);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central para la lista
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior para entrada de datos y botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        
        // Panel para entrada
        JPanel panelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelEntrada.add(instruccionLabel);
        panelEntrada.add(entradaTextField);
        panelEntrada.add(agregarButton);
        
        // Panel para botones adicionales
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(limpiarButton);
        
        // Panel para estado
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelEstado.add(statusLabel);
        
        // Agregar subpaneles al panel inferior
        panelInferior.add(panelEntrada);
        panelInferior.add(panelBotones);
        panelInferior.add(panelEstado);
        
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Configura los manejadores de eventos para los componentes
     */
    private void configurarEventos() {
        // Evento para el botón Agregar
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarElemento();
            }
        });
        
        // Evento para el botón Limpiar
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarLista();
            }
        });
        
        // Evento para la lista (selección de elemento)
        elementosList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int indiceSeleccionado = elementosList.getSelectedIndex();
                    if (indiceSeleccionado != -1) {
                        statusLabel.setText("Elemento seleccionado: " + 
                                (indiceSeleccionado + 1) + " de " + listModel.size());
                    }
                }
            }
        });
        
        // Evento para el campo de texto (Enter para agregar)
        entradaTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarElemento();
            }
        });
    }

    /**
     * Agrega un elemento a la lista después de validar que no esté vacío
     */
    private void agregarElemento() {
        String texto = entradaTextField.getText().trim();
        
        // Validar que el texto no esté vacío
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese un elemento válido.", 
                    "Entrada vacía", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Agregar el elemento al modelo de lista
        listModel.addElement(texto);
        
        // Limpiar el campo de texto y poner el foco en él
        entradaTextField.setText("");
        entradaTextField.requestFocus();
        
        // Actualizar el estado
        actualizarEstado();
    }

    /**
     * Limpia toda la lista de elementos
     */
    private void limpiarLista() {
        // Confirmar antes de limpiar
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar todos los elementos?",
                "Confirmar limpieza",
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            listModel.clear();
            actualizarEstado();
        }
    }

    /**
     * Actualiza la etiqueta de estado con la cantidad de elementos
     */
    private void actualizarEstado() {
        int cantidad = listModel.size();
        if (cantidad == 0) {
            statusLabel.setText("Lista vacía. Agregue elementos.");
        } else {
            statusLabel.setText("Total de elementos: " + cantidad);
        }
    }

    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GestionElementosApp();
            }
        });
    }
}
