package com.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by negrita91 on 18/05/15.
 */
public class PresentacionLista {
    private CtrlPresentacionLista ctrlPresentacionLista;
    private JPanel panel1;
    private JButton cancelarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton anadirCancionButton;
    private JButton alterarOrdenCancionButton;
    private JButton anadirButton1;
    private JPanel panelPrincipal;
    private JPanel panelAnadir;
    private JTextField nom;
    private JTextArea desc;
    private JPanel panelBorrar;
    private JList listNomListas;
    private JButton salirButton;
    private JButton borrarListaSelect;
    private JButton salirListaButton;
    private JScrollPane Scroll;
    private JComboBox comboBox1;
    private JButton editarButton;
    private JTextArea edicionDesc;
    private JTextField edicionNom;
    private JButton guardarCambiosButton;
    private JPanel panelEditar;
    private JPanel panelEditarBasico;
    private JPanel panelEditarComplejo;
    private JButton salirButton1;
    private JComboBox comboBox2;
    private JButton seleccionarButton;
    private JButton salirButton2;
    private JScrollPane ScrollActual;
    private JScrollPane ScrollFuturas;
    private JButton anadirCancionButton1;
    private JList cancionesActuales;
    private JList cancionesFuturas;
    private JPanel panelAnadirBasico;
    private JPanel panelAnadirCompleto;
    private JPanel panelAnadirCancion;
    private JButton eliminarCancionButton1;
    private JButton exportarButton;
    private JButton anadirButton;
    private JButton modificarReproducidasButton;
    private JButton anadirEliminarCancionButton;
    private JPanel panelReproducciones;
    private JButton anadirReproducionesButton;
    private JScrollPane ScrollCanciones;
    private JList canciones;
    private JTextField numeroReproduccion;
    private JButton salirButton3;
    private JButton eliminarReproducionesButton;
    private JPanel panelExportar;
    private JComboBox comboBox3;
    private JButton exportarButton1;
    private JButton salirButton4;
    private JPanel panelAlterar;
    private JPanel panelAlterarBasico;
    private JPanel panelAlterarCompleto;
    private JComboBox comboBox4;
    private JButton alterarButton;
    private JButton salirButton5;
    private JButton subirButton;
    private JButton bajarButton;
    private JList cancionesLista;
    private JScrollPane ScrollAlterar;
    private JButton verButton;
    private JPanel panelConsultar;
    private JScrollPane ScrollListas;
    private JList Listas;
    private JPanel panelConsultarBasico;
    private JPanel panelConsultarCompleto;
    private JButton verButton1;
    private JButton salirButton6;
    private JTextField textNombre;
    private JList listaCanciones;
    private JTextArea textDescripcion;
    private JComboBox comboBox5;
    private JPanel panelAnadir2;
    private JList list1;
    private JList list2;
    private JButton salirButton7;
    private JScrollPane ScrollLista1;
    private JScrollPane ScrollLista2;
    private JTextField tituloAutorTextField;
    private JButton buscarCancionButton;
    private JTextField numActRep;
    private JScrollPane scrollDesc;
    private JScrollPane scrollDescMod;
    private static JFrame frame;
    private JOptionPane jop = new JOptionPane();
    private ArrayList<String> list;
    private DefaultListModel listModel;
    private String listaActual;
    private Integer indice = -1;

    public PresentacionLista(CtrlPresentacionLista cl) {
        ctrlPresentacionLista = cl;

        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuConsultarBasico();
                cargarDatosLista(5);
            }
        });

        verButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuConsultarCompleto();
                cargarTodosLosDatos();
            }
        });

        //Añadir una lista (nom y desc) por el momento
        anadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAnadir();
            }
        });

        anadirButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirButton1ActionPerfomed(e);

            }
        });

        anadirCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirButtonActionPerfomed(e);
            }
        });

        //Modificar los campos de ua lista
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuEditarBasico();
                cargarDatosLista(1);

                //comboBox1.addActionListener(this);
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size()==1) {
                    jop.showMessageDialog(alterarButton, "No tienes ninguna lista", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    menuEditarCompleto();
                    datosEditarCompleto();
                }
            }
        });

        guardarCambiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosButtonActionPerfomed(e);
            }
        });



        //Eliminar una lista
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuBorrar();
                Scroll = new JScrollPane();
                list = ctrlPresentacionLista.getNombresListas();
                listModel = new DefaultListModel();
                for (int i = 1; i < list.size(); i++) {
                    listModel.addElement(list.get(i));
                }
                listNomListas.setModel(listModel);
                listNomListas.setLayoutOrientation(JList.VERTICAL);
                listNomListas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            }
        });

        //Añadir o eliminar canciones a una lista
        anadirEliminarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAnadirCancionBasico();
                cargarDatosLista(2);
            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size()==1) {
                    jop.showMessageDialog(alterarButton, "No tienes ninguna lista", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    listaActual = (String) comboBox2.getSelectedItem();
                    menuAnadirCancionComplejo();
                    cargarDatosCancionesLista(1);
                    cargarDatosCancionesNoLista(1);
                }
            }
        });

        anadirCancionButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirCancionButton1ActionPerfomed(e);
                cargarDatosCancionesLista(1);
                cargarDatosCancionesNoLista(1);
            }
        });
        eliminarCancionButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCancionButton1ActionPerfomed(e);
                cargarDatosCancionesLista(1);
                cargarDatosCancionesNoLista(1);
            }
        });


        anadirReproducionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducionesButtonSelectActionPerfomed(e, true);
            }
        });

        eliminarReproducionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducionesButtonSelectActionPerfomed(e, false);
            }
        });


        //para que enseñe el numero de reproducciones
        canciones.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() >= 1) {
                    String can = (String) canciones.getSelectedValue();
                    Integer num = ctrlPresentacionLista.getNumeroRepro(can);
                    String n = Integer.toString(num);
                    //un click o mas
                    numActRep.setText(n);
                }
            }
        });

        canciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                char car = (char) e.getKeyCode();
                if (car == KeyEvent.VK_DOWN || car == KeyEvent.VK_UP) {
                    if (!canciones.isSelectionEmpty()) {
                        String can = (String) canciones.getSelectedValue();
                        Integer num = ctrlPresentacionLista.getNumeroRepro(can);
                        String n = Integer.toString(num);
                        //un click o mas
                        numActRep.setText(n);
                    }
                }
            }
        });

        //EXPORTAR UNA LISTA
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuExportar();
                cargarDatosLista(3);
            }
        });

        exportarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size()==1) {
                    jop.showMessageDialog(alterarButton, "No tienes ninguna lista", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //ventana
                    JFileChooser jF1 = new JFileChooser();
                    String ruta = "";
                    try {
                        if (jF1.showSaveDialog(null) == jF1.APPROVE_OPTION) {
                            ruta = jF1.getSelectedFile().getAbsolutePath();
                            //si existe el fichero le pregunto si desea remplazarlo en caso de si lo remplazaremos con el contenido
                            //de la lista "listaActual"
                            if (new File(ruta).exists()) {
                                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(exportarButton1, "El fichero existe,deseas reemplazarlo?", "Titulo", JOptionPane.YES_NO_OPTION)) {
                                    listaActual = (String) comboBox3.getSelectedItem();
                                    ctrlPresentacionLista.exportarLista(listaActual, ruta);
                                }
                            } else {
                                listaActual = (String) comboBox3.getSelectedItem();
                                ctrlPresentacionLista.exportarLista(listaActual, ruta);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //Alterar orden canciones
        alterarOrdenCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAlterarOrdenBasico();
                cargarDatosLista(4);
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size() == 1) {
                    jop.showMessageDialog(alterarButton, "No tienes ninguna lista", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                } else {
                    menuAlterarOrdenCompleto();
                    cargarDatosCancionesLista(2);
                    if (indice != -1) cancionesLista.setSelectedIndex(indice);
                }
            }
        });

        cancionesLista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                indice = cancionesLista.getSelectedIndex();
            }
        });

        subirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarSubirBajarButtonActionPerformed(e, true);
                cargarDatosCancionesLista(2);
                if (indice!=-1) {
                    indice -=1;
                    cancionesLista.setSelectedIndex(indice);
                    cancionesLista.ensureIndexIsVisible(indice);
                }
            }
        });

        bajarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarSubirBajarButtonActionPerformed(e, false);
                cargarDatosCancionesLista(2);
                if (indice!=-1) {
                    indice +=1;
                    cancionesLista.setSelectedIndex(indice);
                    cancionesLista.ensureIndexIsVisible(indice);

                }
            }
        });


        salirListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ya no me tengo que preocupar (?)
                ctrlPresentacionLista.guardaModificacionesReproduciones();
                //esta es una prueba para comprobar que guarda la modificación de la cancion en global
                //Esto debe ser que que el la guarde.
                //Ya no me tengo que preocupar (?)
                ctrlPresentacionLista.guardaModificacionesReproducionesCanciones();
                frame.setVisible(false);
                ctrlPresentacionLista.getMenuPrincipal();
                //Para cerrar java -------- IMPORTANTE QUITAR CUANDO SE JUNTE LAS
            }
        });


        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });


        //boton que indica que quiere eliminar la lista seleccionada
        borrarListaSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarListaSelectActionPerfomed(e);
            }
        });

        //salir del menu de borrar lista
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        salirButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        salirButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        salirButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ctrlPresentacionLista.getMenuPrincipal();
            }
        });
        salirButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        salirButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        salirButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tituloAutorTextField.setText("Titulo;Autor");
                menu();
            }
        });
        salirButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        //Para que solo entre numero en el cuadrado de reproducciones
        numeroReproduccion.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                // Verificar si la tecla pulsada no es un digito
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
                    e.consume(); // ignorar el evento de teclado
                }
            }
        });
        buscarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Primero cogemos el texto
                String textoBusqueda = tituloAutorTextField.getText();
                if (textoBusqueda.equals("") || textoBusqueda.equals("Titulo;Autor")) {
                    jop.showMessageDialog(buscarCancionButton, "Por favor especifique una canción con Titulo;Autor", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!textoBusqueda.contains(";")) {
                    jop.showMessageDialog(buscarCancionButton, "Por favor especifique una canción con el siguiente formato: Titulo;Autor", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //Tiene sentido lo que ha puesto el usuario, vamos a buscarlo
                String resBusqueda = "";
                try {
                    resBusqueda = ctrlPresentacionLista.buscarCancion(textoBusqueda);
                } catch (Exception ex) {
                    jop.showMessageDialog(buscarCancionButton, "Por favor especifique una canción con el siguiente formato: Titulo;Autor", "ERROR CRITICO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (resBusqueda == null)
                    jop.showMessageDialog(buscarCancionButton, "No se ha encontrado ninguna canción con ese titulo y autor", "WARNING", JOptionPane.WARNING_MESSAGE);
                else {
                    //Visualizamos los datos
                    jop.showMessageDialog(buscarCancionButton, resBusqueda, "Resultado busqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        tituloAutorTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (tituloAutorTextField.getText().equals("Titulo;Autor")) {
                    tituloAutorTextField.setText("");
                }
            }
        });
    }

    public void cargarTodosLosDatos() {
        listaActual = (String) comboBox5.getSelectedItem();
        ArrayList<String> datosInfo = ctrlPresentacionLista.getDatosLista(listaActual);
        ArrayList<String> canciones = ctrlPresentacionLista.getNombresCanciones(listaActual);
        textNombre.setText(datosInfo.get(0));
        textDescripcion.setText(datosInfo.get(1));

        listModel = new DefaultListModel();
        for (int i = 0; i < canciones.size(); i++) {
            listModel.addElement(canciones.get(i));
        }
        listaCanciones.setModel(listModel);
        listaCanciones.setLayoutOrientation(JList.VERTICAL);
        listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }


    public void main() {
        principal();
    }

    public void mainRepro() {
        menuModificarReproduciones();
        cargarDatosCanciones(1);
    }

    public void principal(){
        frame = new JFrame("Panel Lista");
        frame.setContentPane(this.panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);//Lo ponemos en el centro de la pantalla
        frame.setVisible(true);
    }

    public void menu(){
        frame.setVisible(false);
        eliminarContenido();
        frame = new JFrame("Panel Lista");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void menuAnadir(){
        frame.setVisible(false);
        eliminarContenido();
        frame = new JFrame("Añadir una lista");
        frame.setContentPane(panelAnadir);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuBorrar(){
        frame.setVisible(false);
        eliminarContenido();
        frame = new JFrame("Borrar una lista");
        frame.setContentPane(panelBorrar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuEditarBasico(){
        frame.setVisible(false);
        frame = new JFrame("Editar una lista");
        frame.setContentPane(panelEditar);
        //frame.remove(panelEditarComplejo);
        frame.getContentPane().getComponent(1).setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuEditarCompleto(){
        //frame.setVisible(false);
        //frame = new JFrame("Editar una lista");
        //frame.setContentPane(panelEditar);
        frame.getContentPane().getComponent(1).setVisible(true);
        //frame.add(panelEditarComplejo);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);
    }

    public void menuAnadirCancionBasico(){
        frame.setVisible(false);
        frame = new JFrame("Añadir canciones a una lista");
        frame.setContentPane(panelAnadirCancion);
        //frame.remove(panelEditarComplejo);
        frame.getContentPane().getComponent(1).setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuAnadirCancionComplejo(){
        //frame.setVisible(false);
        //frame = new JFrame("Editar una lista");
        //frame.setContentPane(panelEditar);
        frame.getContentPane().getComponent(1).setVisible(true);
        //frame.add(panelEditarComplejo);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);
    }

    public void menuModificarReproduciones() {
        frame = new JFrame("Reproduciones de canciones");
        frame.setContentPane(panelReproducciones);
        frame.getContentPane().getComponent(1).setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuExportar() {
        frame.setVisible(false);
        frame = new JFrame("Exportar una lista");
        frame.setContentPane(panelExportar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuAlterarOrdenBasico(){
        frame.setVisible(false);
        frame = new JFrame("Alterar el orden de las canciones en una lista");
        frame.setContentPane(panelAlterar);
        //frame.remove(panelEditarComplejo);
        frame.getContentPane().getComponent(1).setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuConsultarBasico(){
        frame.setVisible(false);
        frame = new JFrame("Consultar información");
        frame.setContentPane(panelConsultar);
        //frame.remove(panelEditarComplejo);
        frame.getContentPane().getComponent(1).setVisible(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuConsultarCompleto() {
        frame.getContentPane().getComponent(1).setVisible(true);
    }

    public void menuAlterarOrdenCompleto(){
        frame.getContentPane().getComponent(1).setVisible(true);
    }

    private void menuAñadirCancionesDespuesDeCrearla() {
        frame.setVisible(false);
        frame = new JFrame("Añadir canciones a la lista");
        frame.setContentPane(panelAnadir2);
        //frame.remove(panelEditarComplejo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    private void anadirButton1ActionPerfomed(ActionEvent e){
        if (nom.getText().isEmpty()) jop.showMessageDialog(this.anadirButton1, "El nombre de la lista no puede ser vacia", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        else {
            if (ctrlPresentacionLista.anadirLista(nom.getText(), desc.getText())) {
                if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(anadirButton1,"Lista creada\n¿Deseas añadir canciones a la lista?","Acción terminada",JOptionPane.YES_NO_OPTION)) {
                    listaActual = nom.getText();
                    menuAñadirCancionesDespuesDeCrearla();
                    cargarDatosCancionesNoLista(2);
                    cargarDatosCancionesLista(3);
                }
                else {
                    menu();
                }
            } else {
                jop.showMessageDialog(this.anadirButton1, "La lista " + nom.getText() + " ya existe", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                menuEditarCompleto();
                //jop.showInputDialog("Lista no añadida");
                //jop.showConfirmDialog(this, "Lista no añadida");

            }
        }

    }

    private void anadirButtonActionPerfomed(ActionEvent e){
        if (list1.isSelectionEmpty()) {
            jop.showMessageDialog(this.anadirButton, "Selecciona una canción", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        }
        else {
            String IdCancion = (String) list1.getSelectedValue();
            ctrlPresentacionLista.anadirCancionALista(listaActual, IdCancion);
            cargarDatosCancionesNoLista(2);
            cargarDatosCancionesLista(3);
        }

    }

    private void borrarListaSelectActionPerfomed(ActionEvent e){
        if (listNomListas.isSelectionEmpty())
            jop.showMessageDialog(this.borrarListaSelect, "Selecciona una lista", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        else {
            if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(borrarListaSelect, "Estas seguro que deseas eliminar la(s) lista(s)?", "Lista", JOptionPane.YES_NO_OPTION)) {
                List<String> selectedValuesList = listNomListas.getSelectedValuesList();
                for (String noml : selectedValuesList) {
                    ctrlPresentacionLista.eliminarLista(noml);
                    listModel.remove(listNomListas.getSelectedIndex());
                }
                jop.showMessageDialog(this.anadirButton1, "Lista(s) eliminada(s)", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);

            }
        }

    }

    private void guardarCambiosButtonActionPerfomed(ActionEvent e){
        String nomlNuevo = edicionNom.getText();
        String descNuevo = edicionDesc.getText();
        if(nomlNuevo.isEmpty()) jop.showMessageDialog(this.guardarCambiosButton, "La lista no puede tener su nombre vacio", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        else {
            Boolean b = ctrlPresentacionLista.modificarLista(listaActual, nomlNuevo, descNuevo);
            if (b)jop.showMessageDialog(this.guardarCambiosButton, "Lista modificada", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);
            else jop.showMessageDialog(this.guardarCambiosButton, "No se ha podido modificar la lista", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        }
        menuEditarBasico();
        cargarDatosLista(1);
    }

    private void anadirCancionButton1ActionPerfomed(ActionEvent e){
        if (cancionesFuturas.isSelectionEmpty()) jop.showMessageDialog(this.anadirCancionButton1, "Selecciona una canción", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        else {
            List<String> selectedValuesList = cancionesFuturas.getSelectedValuesList();
            for (String IdCancion : selectedValuesList) {
                ctrlPresentacionLista.anadirCancionALista(listaActual, IdCancion);
            }

        }

    }

    private void eliminarCancionButton1ActionPerfomed(ActionEvent e){
        if (cancionesActuales.isSelectionEmpty()) jop.showMessageDialog(this.eliminarCancionButton1, "Selecciona una canción", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        else {
            List<String> selectedValuesList = cancionesActuales.getSelectedValuesList();
            for (String IdCancion : selectedValuesList) {
                ctrlPresentacionLista.eliminarCancionDeLista(listaActual, IdCancion);
            }
        }
    }

    private void reproducionesButtonSelectActionPerfomed(ActionEvent e, Boolean aumentar){
        if (numeroReproduccion.getText().isEmpty()){
            jop.showMessageDialog(this.anadirReproducionesButton, "Ingresa un numero", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        }
        else {
            //ha introduccido un numero pero no se ha seleccionado cancion
            if (canciones.isSelectionEmpty()) jop.showMessageDialog(this.anadirReproducionesButton, "Selecciona una cancion", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
            //se ha seleccionado cancion y se ha puesto numero
            else {
                Integer numero = Integer.parseInt(numeroReproduccion.getText());
                String cancion = (String) canciones.getSelectedValue();
                Boolean b = ctrlPresentacionLista.modificarNumeroReproduciones(cancion, numero, aumentar);
                if (b) {
                    jop.showMessageDialog(this.anadirReproducionesButton, "Modificado numero reproducidas", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);
                        if (!(canciones.isSelectionEmpty())) {
                            String can = (String) canciones.getSelectedValue();
                            Integer num = ctrlPresentacionLista.getNumeroRepro(can);
                            String n = Integer.toString(num);
                            numActRep.setText(n);
                        }
                }
                else
                    jop.showMessageDialog(this.anadirReproducionesButton, "No puedes quitar reproducciones porque no has reproducidas tantas", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
            }
            numeroReproduccion.setText(null);
        }
    }

    public void alterarSubirBajarButtonActionPerformed(ActionEvent e, Boolean subir){
        if(cancionesLista.isSelectionEmpty()) jop.showMessageDialog(this.subirButton, "Selecciona una canción", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
        else {
            String IdCancion = (String) cancionesLista.getSelectedValue();
            Boolean b = ctrlPresentacionLista.modificarIndiceCancion(listaActual, IdCancion, subir);
            if (!b) {
                jop.showMessageDialog(this.subirButton, "No se puede mover la canción", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
            }
            else {
                cargarDatosCancionesLista(2);
            }
        }
    }

    private void eliminarContenido(){
        nom.setText(null);
        desc.setText(null);
    }

    private void datosEditarCompleto(){
        listaActual = (String) comboBox1.getSelectedItem();
        ArrayList<String> datos = ctrlPresentacionLista.getDatosLista(listaActual);
        edicionNom.setText(datos.get(0));
        edicionDesc.setText(datos.get(1));
    }

    private void cargarDatosLista(Integer accion) {
        list = ctrlPresentacionLista.getNombresListas();
        if (accion==1) {
            comboBox1.removeAllItems();
            for (int i = 1; i < list.size(); i++) {
                comboBox1.addItem(list.get(i));
            }

            //comboBox1.setSelectedIndex(0);
        }
        else if (accion == 2){
            comboBox2.removeAllItems();
            for (int i = 1; i < list.size(); i++) {
                comboBox2.addItem(list.get(i));
            }

            //comboBox2.setSelectedIndex(0);
        }
        else if (accion == 3){
            comboBox3.removeAllItems();
            for (int i = 1; i < list.size(); i++) {
                comboBox3.addItem(list.get(i));
            }

            //comboBox3.setSelectedIndex(0);
        }
        else if(accion == 4){
            comboBox4.removeAllItems();
            for (int i = 1; i < list.size(); i++) {
                comboBox4.addItem(list.get(i));
            }

            //comboBox4.setSelectedIndex(0);
        }
        else if(accion == 5){
            comboBox5.removeAllItems();
            for (int i = 0; i < list.size(); i++) {
                comboBox5.addItem(list.get(i));
            }
        }
    }

    private void cargarDatosCancionesLista(Integer accion){
        if (accion == 1) {
            listaActual = (String) comboBox2.getSelectedItem();
            ScrollActual = new JScrollPane();
            list = ctrlPresentacionLista.getNombresCanciones(listaActual);
            listModel = new DefaultListModel();
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            cancionesActuales.setModel(listModel);
            cancionesActuales.setLayoutOrientation(JList.VERTICAL);
            cancionesActuales.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        else if (accion == 2){
            listaActual = (String) comboBox4.getSelectedItem();
            ScrollAlterar = new JScrollPane();
            list = ctrlPresentacionLista.getNombresCanciones(listaActual);
            listModel = new DefaultListModel();
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            cancionesLista.setModel(listModel);
            cancionesLista.setLayoutOrientation(JList.VERTICAL);
            cancionesLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        else if (accion == 3){
            //listaActual = (String) comboBox4.getSelectedItem();
            ScrollLista2 = new JScrollPane();
            list = ctrlPresentacionLista.getNombresCanciones(listaActual);
            listModel = new DefaultListModel();
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            list2.setModel(listModel);
            list2.setLayoutOrientation(JList.VERTICAL);
            list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    private void cargarDatosCancionesNoLista(Integer accion){
        if (accion == 1) {
            ScrollActual = new JScrollPane();
            list = ctrlPresentacionLista.getNombresNoCanciones(listaActual);
            listModel = new DefaultListModel();
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            cancionesFuturas.setModel(listModel);
            cancionesFuturas.setLayoutOrientation(JList.VERTICAL);
            cancionesFuturas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        else if (accion == 2){
            ScrollLista1 = new JScrollPane();
            list = ctrlPresentacionLista.getNombresNoCanciones(listaActual);
            listModel = new DefaultListModel();
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            list1.setModel(listModel);
            list1.setLayoutOrientation(JList.VERTICAL);
            list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    private void cargarDatosCanciones(Integer accion){
        list = ctrlPresentacionLista.getTodosNombresCanciones();
        if (accion==1) {
            ScrollCanciones = new JScrollPane();

            listModel = new DefaultListModel();
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
            }
            canciones.setModel(listModel);
            canciones.setLayoutOrientation(JList.VERTICAL);
            canciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

}
