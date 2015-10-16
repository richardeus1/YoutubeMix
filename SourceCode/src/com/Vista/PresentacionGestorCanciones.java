package com.Vista;

import com.dominio.Cancion;

import javax.swing.*;
import javax.swing.text.StringContent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Andreu Conesa
 */
public class PresentacionGestorCanciones {
    private CtrlPresentacionGestorCanciones ctrlPresentacionGestorCanciones ;
    private JPanel panel1;
    private JPanel JPanelGestorCanciones;
    private JPanel JPanelAnadirCancionManual;
    private JPanel JPanelAnadirCancionArchivo;
    private JPanel JPanelModificarCancionManualA;
    private JPanel JPanelModificarCancionManualB;
    private JPanel JPanelModificarCancionArchivo;
    private JPanel JPanelBorrarCancionManual;
    private JPanel JPanelBorrarCancionArchivo;
    private JPanel JPanelVerMisCanciones;
    private JPanel JPanelVerTodasCanciones;
    private JButton manualmenteButton;
    private JButton porArchivoButton;
    private JButton manualmenteButton1;
    private JButton porArchivoButton1;
    private JButton manualmenteButton2;
    private JButton porArchivoButton2;
    private JButton misCancionesButton;
    private JButton todasLasCancionesButton;
    private JButton salirButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JFormattedTextField textField5;
    private JFormattedTextField textField6;
    private JButton anadirButton;
    private JButton salirButton1;
    private JTextField textField7;
    private JButton seleccionarArchivoButton;
    private JButton anadirPorArchivoButton;
    private JButton salirButton2;
    private JList list1;
    private JList list2;
    private JButton modificarButton;
    private JButton salirButton3;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JFormattedTextField textField12;
    private JButton salirButton4;
    private JButton modificarButton1;
    private JTextField textField14;
    private JButton seleccionarArchivoButton1;
    private JButton modificarButton2;
    private JButton salirButton9;
    private JList list3;
    private JButton borrarButton;
    private JButton salirButton5;
    private JTextField textField15;
    private JButton seleccionarArchivoButton2;
    private JButton borrarButton1;
    private JButton salirButton6;
    private JList list4;
    private JButton salirButton7;
    private JList list5;
    private JButton salirButton8;
    private JScrollPane JScrollAnadir;
    private JScrollPane JScrollModificar;
    private JScrollPane JScrollBorrar;
    private JScrollPane JScrollMisCanciones;
    private JScrollPane JScrollTodasCanciones;
    private JButton cargarButton;
    private JButton buscarCancionButton;
    private JTextField textField13;
    private DefaultListModel listModel;
    private JOptionPane jop = new JOptionPane();
    private ArrayList<String> list;
    private static JFrame frame;

    public PresentacionGestorCanciones(CtrlPresentacionGestorCanciones ct) {
        ctrlPresentacionGestorCanciones = ct;
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ctrlPresentacionGestorCanciones.getMenuPrincipal();
            }
        });
        manualmenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCanciones();
                setJPanelAnadirCancionManual();
            }
        });
        salirButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        porArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setJPanelAnadirCancionArchivo();
            }
        });
        salirButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });
        manualmenteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCancionesModificar();
                setJPanelModificarCancionManualA();
            }

        });
        salirButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list2.getSelectedValue() != null) {
                    String aux = list2.getSelectedValue().toString();
                    String id = aux.replace(" - ",";");
                    ArrayList<String> ca = ctrlPresentacionGestorCanciones.buscarCancion(id);
                    setJPanelModificarCancionManualB(ca.get(0), ca.get(1), ca.get(2), ca.get(3), ca.get(4));
                }else{
                    jop.showMessageDialog(frame,"Debe seleccionar una cancion.","Alerta",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        salirButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCancionesModificar();
                setJPanelModificarCancionManualA();
            }
        });

        porArchivoButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setJPanelModificarCancionArchivo();
            }
        });

        salirButton9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        manualmenteButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCancionesBorrar();
                setJPanelBorrarCancionManual();
            }
        });

        salirButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        porArchivoButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setJPanelBorrarCancionArchivo();
            }
        });

        salirButton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        misCancionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosMisCanciones();
                setJPanelVerMisCanciones();
            }
        });

        salirButton7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        todasLasCancionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosTodasCanciones();
                setJPanelVerTodasCanciones();
            }
        });

        salirButton8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        anadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String hoy = formatter.format(date);

                if(textField1.getText().trim().equals("") || textField2.getText().trim().equals("") || textField3.getText().trim().equals("") || textField4.getText().trim().equals("") || textField5.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(frame, "Faltan datos. Asegurese de rellenar todos los campos.", "Alerta", JOptionPane.WARNING_MESSAGE);
                }else{
                    int n = JOptionPane.showConfirmDialog(frame,"Seguro que quiere anyadir la cancion?",
                            "Confirmacion", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        String creador = ctrlPresentacionGestorCanciones.getCtrlDominio().getUsuarioActual();
                        Integer nrep;
                        if (textField6.getText().trim().equals("")) nrep = 0;
                        else nrep = Integer.parseInt(textField6.getText());
                        Boolean b = ctrlPresentacionGestorCanciones.anadirCancion(textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText(), nrep, hoy, creador, Integer.parseInt(textField5.getText()));
                        if (b){
                            JOptionPane.showMessageDialog(frame, "La cancion se ha anyadido correctamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                            cargarDatosCanciones();
                            frame.pack();
                            list1.ensureIndexIsVisible(list1.getModel().getSize() - 1);
                            list1.setSelectedIndex(list1.getModel().getSize()-1);
                        }
                        else {
                            JOptionPane.showMessageDialog(frame, "La cancion ya existia.\nAhora puede encontrarla en su lista Mis canciones.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                            list1.ensureIndexIsVisible(0);
                        }
                        textField1.setText(null);
                        textField2.setText(null);
                        textField3.setText(null);
                        textField4.setText(null);
                        textField5.setText(null);
                        textField6.setText("0");
                    }
                }
            }
        });


        modificarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField10.getText().trim().equals("") || textField11.getText().trim().equals("") || textField12.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(frame,"Faltan datos. Asegurese de rellenar todos los campos.","Alerta",JOptionPane.WARNING_MESSAGE);
                }else{
                    int n = JOptionPane.showConfirmDialog(frame, "Seguro que quiere modificar la cancion?",
                            "Confirmacion", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        Boolean b = ctrlPresentacionGestorCanciones.modificarCancion(textField8.getText(), textField9.getText(), textField10.getText(), textField11.getText(), Integer.parseInt(textField12.getText()));
                        if (b) JOptionPane.showMessageDialog(frame,"La cancion se ha modificado correctamente.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                        else JOptionPane.showMessageDialog(frame, "Lo sentimos.\nLa cancion no se ha podido modificar.", "Alerta", JOptionPane.ERROR_MESSAGE);
                        cargarDatosCancionesModificar();
                        setJPanelModificarCancionManualA();
                    }
                }
            }
        });
        textField5.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
                    e.consume();
                }
            }
        });


        textField6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();

                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
                    e.consume();
                }
            }
        });


        textField12.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
                    e.consume();
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIx = list3.getSelectedIndices();
                Boolean t = true;
                if (selectedIx.length > 0) {
                    int n = JOptionPane.showConfirmDialog(frame, "Seguro que quiere eliminar las canciones?", "Confirmacion", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        for (int i = 0; i < selectedIx.length; ++i) {
                            String aux = String.valueOf(list3.getModel().getElementAt(selectedIx[i]));
                            String id = aux.replace(" - ", ";");
                            String[] s = id.split(";");
                            Boolean b = ctrlPresentacionGestorCanciones.borrarCancion(s[0]+";"+s[1]);
                            if (!b) t = false;
                        }
                        if (!t) JOptionPane.showMessageDialog(frame, "Una o varias canciones no se han podido eliminar.", "Alerta", JOptionPane.ERROR_MESSAGE);
                        else JOptionPane.showMessageDialog(frame, "Las canciones se han borrado correctamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                        cargarDatosCancionesBorrar();
                    }
                }else{
                    JOptionPane.showMessageDialog(frame,"Debe seleccionar una cancion.","Alerta",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        seleccionarArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Cada linea del archivo .txt debe contener:\ntitulo;autor;album;genero;reproducciones;anyo",
                        "Informacion",JOptionPane.INFORMATION_MESSAGE);
                JFileChooser fc = new JFileChooser();
                int r = fc.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    textField7.setText(file.getAbsolutePath());
                }else{
                    textField7.setText(null);
                }
            }
        });

        anadirPorArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField7.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(frame,"Debe seleccionar un archivo.","Alerta",JOptionPane.WARNING_MESSAGE);
                }else{
                    int n = JOptionPane.showConfirmDialog(frame, "Esta operacion puede tardar unos minutos.\nSeguro que quiere anyadir las canciones?","Confirmacion", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        Boolean b = ctrlPresentacionGestorCanciones.anadirCancionesDesdeArchivo(textField7.getText());
                        if (!b) JOptionPane.showMessageDialog(frame,"Una o varias canciones ya existian.\nAhora puede encontrarlas en su lista Mis canciones.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                        else JOptionPane.showMessageDialog(frame,"Las canciones se han anadido correctamente.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    }
                    textField7.setText(null);
                }
            }
        });

        seleccionarArchivoButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Cada linea del archivo .txt debe contener:\ntitulo;autor;album;genero;anyo\nDonde album, genero y anyo seran los nuevos atributos de la cancion con ese titulo y ese autor.",
                        "Informacion",JOptionPane.INFORMATION_MESSAGE);
                JFileChooser fc = new JFileChooser();
                int r = fc.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    textField14.setText(file.getAbsolutePath());
                }else{
                    textField14.setText(null);
                }
            }
        });


        modificarButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField14.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(frame,"Debe seleccionar un archivo.","Alerta",JOptionPane.WARNING_MESSAGE);
                }else{
                    int n = JOptionPane.showConfirmDialog(frame, "Esta operacion puede tardar unos minutos.\nSeguro que quiere modificar las canciones?","Confirmacion", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        Boolean b = ctrlPresentacionGestorCanciones.modificarCancionesDesdeArchivo(textField14.getText());
                        if (!b) JOptionPane.showMessageDialog(frame,"Una o varias canciones no se han podido modificar.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                        else JOptionPane.showMessageDialog(frame,"Las canciones se han modificado correctamente.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    }
                    textField14.setText(null);
                }
            }
        });

        seleccionarArchivoButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"Cada linea del archivo .txt debe contener:\ntitulo;autor de las canciones que desee borrar.\n",
                        "Informacion",JOptionPane.INFORMATION_MESSAGE);
                JFileChooser fc = new JFileChooser();
                int r = fc.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    textField15.setText(file.getAbsolutePath());
                }else{
                    textField15.setText(null);
                }
            }
        });


        borrarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField15.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(frame,"Debe seleccionar un archivo.","Alerta",JOptionPane.WARNING_MESSAGE);
                }else{
                    int n = JOptionPane.showConfirmDialog(frame, "Esta operacion puede tardar unos minutos.\nSeguro que quiere eliminar las canciones?", "Confirmacion", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        Boolean b = ctrlPresentacionGestorCanciones.borrarCancionesDesdeArchivo(textField15.getText());
                        if (!b) JOptionPane.showMessageDialog(frame,"Una o varias canciones no se han podido eliminar.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                        else JOptionPane.showMessageDialog(frame,"Las canciones se han borrado correctamente.","Informacion",JOptionPane.INFORMATION_MESSAGE);
                    }
                    textField15.setText(null);
                }
            }
        });

        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedValue() != null) {
                    String aux = list1.getSelectedValue().toString();
                    String id = aux.replace(" - ",";");
                    ArrayList<String> ca = ctrlPresentacionGestorCanciones.buscarCancion(id);
                    textField1.setText(ca.get(0));
                    textField2.setText(ca.get(1));
                    textField3.setText(ca.get(2));
                    textField4.setText(ca.get(3));
                    textField5.setText(ca.get(4));
                    textField6.setText(ca.get(5));
                }else{
                    JOptionPane.showMessageDialog(frame,"Debe seleccionar una cancion.","Alerta",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        buscarCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String regex = "(.+);(.+)";
                String id = textField13.getText();
                Boolean res = id.matches(regex);
                String[] partesDelString = id.split(";");
                if (res && partesDelString.length == 2) {
                    ArrayList<String> ca = ctrlPresentacionGestorCanciones.buscarCancion(id);
                    if (ca.size() == 0){
                        JOptionPane.showMessageDialog(frame, "La cancion no existe.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                        textField13.setText(null);
                    }else {
                        JOptionPane.showMessageDialog(frame, "La cancion tiene los siguientes datos:\n" + ca.get(0) + " - " + ca.get(1) + " - " + ca.get(2) + " - " + ca.get(3) + " - " + ca.get(4) + " - " + ca.get(5),
                                "Informacion", JOptionPane.INFORMATION_MESSAGE);
                        textField13.setText(null);
                    }
                }else {
                    JOptionPane.showMessageDialog(frame, "Debe especificar titulo;autor", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void main() {
        inicio();
    }

    public void inicio(){
        frame = new JFrame("Gestor de canciones");
        frame.setContentPane(this.JPanelGestorCanciones);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menu(){
        frame.setVisible(false);
        frame = new JFrame("Gestor de canciones");
        frame.setContentPane(JPanelGestorCanciones);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setJPanelAnadirCancionManual(){
        frame.setVisible(false);
        textField1.setText(null);
        textField2.setText(null);
        textField3.setText(null);
        textField4.setText(null);
        textField5.setText(null);
        textField6.setText("0");
        list1.ensureIndexIsVisible(0);
        frame = new JFrame("Anyadir cancion manualmente");
        frame.setContentPane(JPanelAnadirCancionManual);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setJPanelAnadirCancionArchivo(){
        frame.setVisible(false);
        textField7.setText(null);
        frame = new JFrame("Anyadir canciones por archivo");
        frame.setContentPane(JPanelAnadirCancionArchivo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setJPanelModificarCancionManualA(){
        frame.setVisible(false);
        list2.ensureIndexIsVisible(0);
        frame = new JFrame("Modificar cancion");
        frame.setContentPane(JPanelModificarCancionManualA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setJPanelModificarCancionManualB(String s, String s1, String s2, String s3, String s4) {
        frame.setVisible(false);
        textField8.setText(null);
        textField9.setText(null);
        textField10.setText(null);
        textField11.setText(null);
        textField12.setText(null);
        frame = new JFrame("Modificar cancion");
        frame.setContentPane(JPanelModificarCancionManualB);
        textField8.setText(s);
        textField9.setText(s1);
        textField10.setText(s2);
        textField11.setText(s3);
        textField12.setText(s4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setJPanelModificarCancionArchivo(){
        frame.setVisible(false);
        textField14.setText(null);
        frame = new JFrame("Modificar canciones por archivo");
        frame.setContentPane(JPanelModificarCancionArchivo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setJPanelBorrarCancionManual(){
        frame.setVisible(false);
        list3.ensureIndexIsVisible(0);
        list3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        frame = new JFrame("Borrar cancion manualmente");
        frame.setContentPane(JPanelBorrarCancionManual);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setJPanelBorrarCancionArchivo(){
        frame.setVisible(false);
        textField15.setText(null);
        frame = new JFrame("Borrar canciones por archivo");
        frame.setContentPane(JPanelBorrarCancionArchivo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setJPanelVerMisCanciones(){
        frame.setVisible(false);
        list4.ensureIndexIsVisible(0);
        frame = new JFrame("Mis canciones");
        frame.setContentPane(JPanelVerMisCanciones);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setJPanelVerTodasCanciones(){
        frame.setVisible(false);
        list5.ensureIndexIsVisible(0);
        textField13.setText(null);
        frame = new JFrame("Canciones del sistema");
        frame.setContentPane(JPanelVerTodasCanciones);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void cargarDatosCanciones(){
        list = ctrlPresentacionGestorCanciones.getNombreCanciones();
        JScrollAnadir = new JScrollPane();
        listModel = new DefaultListModel();
        for (int i = 0; i < list.size(); ++i){
            String aux = list.get(i).replaceAll(";"," - ");
            listModel.addElement(aux);
        }
        list1.setModel(listModel);
        list1.setLayoutOrientation(JList.VERTICAL);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarDatosCancionesModificar(){
        ArrayList<String> aux = ctrlPresentacionGestorCanciones.getCancionesAdmin();
        JScrollModificar = new JScrollPane();
        listModel = new DefaultListModel();
        for (int i = 0; i < aux.size(); ++i){
            listModel.addElement(aux.get(i));
        }
        list2.setModel(listModel);
        list2.setLayoutOrientation(JList.VERTICAL);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarDatosCancionesBorrar(){
        ArrayList<String> aux = ctrlPresentacionGestorCanciones.getCancionesAdmin();
        JScrollBorrar = new JScrollPane();
        listModel = new DefaultListModel();
        for (int i = 0; i < aux.size(); ++i){
            listModel.addElement(aux.get(i));
        }
        list3.setModel(listModel);
        list3.setLayoutOrientation(JList.VERTICAL);
        list3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void cargarDatosMisCanciones(){
        ArrayList<String> aux = ctrlPresentacionGestorCanciones.getCancionesCreador();
        JScrollMisCanciones = new JScrollPane();
        listModel = new DefaultListModel();
        for (int i = 0; i < aux.size(); ++i){
            listModel.addElement(aux.get(i));
        }
        list4.setModel(listModel);
        list4.setLayoutOrientation(JList.VERTICAL);
        list4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarDatosTodasCanciones(){
        ArrayList<String> aux = ctrlPresentacionGestorCanciones.getCanciones();
        JScrollTodasCanciones = new JScrollPane();
        listModel = new DefaultListModel();
        for (int i = 0; i < aux.size(); ++i){
            listModel.addElement(aux.get(i));
        }
        list5.setModel(listModel);
        list5.setLayoutOrientation(JList.VERTICAL);
        list5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
