package com.Vista;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Alex on 19/05/2015.
 * @author Alejandro Quibus
 */
public class PresentacionConjuntosAgrupaciones {
    /**DIMENSIONES PARA TIPO DE VISTA*/
    private Dimension dimSelec = new Dimension(400, 350);
    private Dimension dimVisor = new Dimension(1050,550);
    private Dimension dimPopUp = new Dimension(425, 180);
    private Dimension dimGenerar = new Dimension(1250, 250);
    private String nombreConjunto;
    private ArrayList<ArrayList<String>> agrupaciones;
    private DefaultListModel<String> listadoAgrupaciones;
    private DefaultListModel<String> cancionesAgrupacion;

    //VARIABLES PARA LA INTERFAZ

    //Elementos de la interfaz
    private JPanel t;
    private JList listCan;
    private JList listConjuntos;
    private JButton cargarConjuntoButton;
    private JButton borrarConjuntoButton;
    private JPanel panelSeleccionar;
    private JPanel panelVisualizar;
    private JTextField textNombreC;
    private JTextField textMetodoC;
    private JTextField textParC;
    private JTextField textDescC;
    private JTextField textOrigC;
    private JCheckBox modificadoCheckBox;
    private JButton eliminarAgrupacionButton;
    private JButton fusionarAgrupacionesButton;
    private JButton exportarPlayListButton;
    private JButton exportarFileButton;
    private JButton reGenerarButton;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton cambiarOrdenCancionButton;
    private JButton BORRARCANCIONButton;
    private JButton BUSCARCANCIONButton;
    private JTextField tituloAutorTextField;
    private JPanel popUpExportarPlayList;
    private JTextField textListaN;
    private JTextField textListaD;
    private JButton guardarButton1;
    private JButton cancelarButton1;
    private JButton autogenerarButton;
    private JPanel autogenerar;
    private JComboBox comboOrigenD;
    private JTextField textFAutor;
    private JTextField textFAlbum;
    private JTextField textFGenero;
    private JTextField textFNRep;
    private JTextField textFFechaIns;
    private JTextField textFNMAX;
    private JComboBox comboAAutor;
    private JComboBox comboAAlbum;
    private JComboBox comboAGenero;
    private JComboBox comboARep;
    private JComboBox comboAAno;
    private JComboBox comboAlgoritmo;
    private JButton cancelarButtonAlgoritmo;
    private JButton generarButtonAlgoritmo;
    private JSlider slider1;
    private JLabel porcentaje;
    private JPanel popUpSelectLista;
    private JPanel visorJtree;
    private JTree treeConjuntos;
    private JButton salirButton;
    private JComboBox comboListas;
    private JButton seleccionarButton;
    private JButton cancelarButton3;
    private JButton CAMBIARCANCIONDEAGRUPACIONButton;
    CtrlPresentacionConjuntoAgrupaciones controlador;
    private JOptionPane jop = new JOptionPane();
    private static JFrame frame;

    private String listaSeleccionada;

    public PresentacionConjuntosAgrupaciones(final CtrlPresentacionConjuntoAgrupaciones cl) {
        //Estamos en el caso que entramos directos a seleccionar, por tanto no queremos ver la visualización
        panelVisualizar.setVisible(false);
        //Ocultamos los popUP
        popUpExportarPlayList.setVisible(false);
        //Ocultamos generar
        autogenerar.setVisible(false);
        popUpSelectLista.setVisible(false);
        //Creamos su controlador
        controlador = cl;
        //Vamos a inicializar toda esta mierda
        //1)Obtengamos el listado de conjuntos
        cargarListaConjunto();

        cargarConjuntoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Preparamos la siguiente vista para verlo y se cargan los datos
                Object prueba = listConjuntos.getSelectedValue();
                if (prueba != null) {
                    String seleccionado = listConjuntos.getSelectedValue().toString();
                    if (!seleccionado.isEmpty()) {
                        visualizarConjuntoGuardado(seleccionado);
                    }
                } else moststarMensaje("ERROR AL CARGAR", "SELECIONE UN CONJUNTO PLEASE");

            }
        });


        borrarConjuntoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Borramos el conjunto y actualizamos
                borrarConjunto();
            }
        });



        //PARA BORRAR CONJUNTOS {made by IDI RULES}
        listConjuntos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    borrarConjunto();
                }
                super.keyPressed(e);
            }
        });
        slider1.addComponentListener(new ComponentAdapter() {
        });
        autogenerarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGenerar();
            }
        });
        cancelarButtonAlgoritmo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volveraSelector();
            }
        });
        generarButtonAlgoritmo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activarAutogeneracion();
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Deseas guardar los cambios antes de salir?", "AVISO", JOptionPane.YES_NO_OPTION)) {
                    volveraSelector();
                }
                else{
                    if(textNombreC.getText().equals("")) moststarMensaje("ERROR", "Introduzca un nombre para guardar la agrupación");
                    else {
                        if(controlador.guardarAgrupacion(textNombreC.getText(), textDescC.getText())) {
                            volveraSelector();
                        }
                        else{
                            moststarMensaje("ERROR","Ya existe una agrupación guardada con ese nombre, por favor eliminela o elija otro nombre");
                        }
                    }
                }
            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Guardamos el conjunto
                if(textNombreC.getText().equals("")) moststarMensaje("Introduzca un nombre", "Para guardar la agrupación");
                else {
                    if(!controlador.guardarAgrupacion(textNombreC.getText(), textDescC.getText())){
                        moststarMensaje("ERROR","Ya existe una agrupación guardada con ese nombre, por favor eliminela o elija otro nombre");
                    }
                }
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cerrar();
            }
        });
        exportarPlayListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Mostramos la view con la lista
                TreePath clicado = treeConjuntos.getSelectionPath();
                if(clicado == null || clicado.getPathCount() != 2){
                    //En el caso de no clicar una agrupación autogenerada
                    moststarMensaje("ERROR", "Selecciona una agrupación por favor");
                    return; //ABORTAMOS
                }
                panelVisualizar.setVisible(false);
                popUpExportarPlayList.setVisible(true);
                frame.setMinimumSize(dimPopUp);
                frame.setSize(dimPopUp);
                frame.setLocationRelativeTo(null);
                frame.pack();
            }
        });
        guardarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Pulsamos guardar lista
                //Vamos a ver si se ha pulsado una agrupación
                TreePath clicado = treeConjuntos.getSelectionPath();
                if(clicado == null || clicado.getPathCount() != 2){
                    moststarMensaje("ERROR", "Selecciona una agrupación por favor");
                    cancelarPoPExportar();
                    return; //ABORTAMOS
                }
                if(textListaN.getText().equals("")){
                    moststarMensaje("ERROR", "Introduzca un nombre para la lista");
                    return;
                }
                String elemento = obtenerNagrupacion(clicado);
                if(!controlador.exportarALista(Integer.valueOf(elemento),textListaN.getText(),textListaD.getText())) moststarMensaje("ERROR", "Ya tienes una lista con nombre: " + textListaN.getText());
                else{
                    //Se ha exportado la lista por tanto ahora cerramos el POPup
                    moststarMensaje("Información","Lista: "+textListaN.getText()+" exportada correctamente");
                    cancelarPoPExportar();
                }
            }
        });
        cancelarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarPoPExportar();
            }
        });
        comboOrigenD.addContainerListener(new ContainerAdapter() {
        });
        comboOrigenD.addComponentListener(new ComponentAdapter() {
        });
        comboOrigenD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboOrigenD.getSelectedIndex() == 2) {
                    //Hemos pulsado la opción de seleccionar una lista
                    seleccionarLista();
                }
            }
        });
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaSeleccionada = comboListas.getSelectedItem().toString();
                comboOrigenD.addItem(listaSeleccionada);
                comboOrigenD.setSelectedIndex(comboOrigenD.getItemCount()-1);
                cancelarPoPLista();
            }
        });
        cancelarButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Cancelamos la selección de una playlist propia
                cancelarPoPLista();
                comboOrigenD.setSelectedIndex(0);
            }
        });
        exportarFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Han pulsado para exportar una agrupación o conjunto de agrupaciones a un fichero
                //Primero voy a comprobar que han:
                TreePath clicado = treeConjuntos.getSelectionPath();
                if(clicado == null){
                    //No han pulsado ningun elemento del jtree por tanto error y cancelar
                    moststarMensaje("ERROR", "Por favor seleccione la raiz conjunto (para exportar todo el conjunto) o bien una agrupación, gracias!");
                    return;
                }
                if(clicado.getPathCount() > 2){
                    //Han clicado una canción por tanto error
                    moststarMensaje("ERROR", "Por favor seleccione la raiz conjunto (para exportar todo el conjunto) o bien una agrupación, gracias!");
                    return;
                }
                JFileChooser elegir = new JFileChooser();
                String ruta = null;
                if(clicado.getPathCount() == 1 || clicado.getPathCount() == 2){
                    //Obtenemos la ruta donde se quiere guardar
                    try{
                        if(elegir.showSaveDialog(null) == elegir.APPROVE_OPTION){
                            ruta = elegir.getSelectedFile().getAbsolutePath();
                            //Miramos a ver que pasa con el fichero
                            if(new File(ruta).exists()){
                                //Si existe preguntamos si queremos reemplazarlo
                                if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null, "El fichero existe,deseas reemplazarlo?", "Titulo", JOptionPane.YES_NO_OPTION)) {
                                    return;
                                }
                            }
                            //Entonces o bien no existe o aceptan reescribir vamos a guardarlo
                            if(clicado.getPathCount() == 1){
                                //Han clicado el conjunto por tanto exportamos el conjunto
                                //Vamos a coger los datos que hay actualmente para  que sean los vigentes al exportar
                                ArrayList<String> datos = new ArrayList<String>();
                                datos.add(textNombreC.getText());
                                datos.add(textDescC.getText());
                                controlador.exportarArchivoConjunto(ruta,datos);
                            }
                            else if(clicado.getPathCount() == 2){
                                //Han clicado una agrupación y la exportamos
                                //Obtenemos la agrupación clicada
                                String elemento = obtenerNagrupacion(clicado);
                                controlador.exportarArchivoAgrupacion(elemento,ruta);
                            }
                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        eliminarAgrupacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Primero comprobamos que ha pulsado una agrupación
                TreePath clicado = treeConjuntos.getSelectionPath();
                if(clicado == null || clicado.getPathCount() != 2){
                    //No han pulsado ningun elemento del jtree o has pulsado el conjunto o una canción por tanto error y cancelar
                    moststarMensaje("ERROR", "Por favor seleccione una agrupación a borrar");
                    return;
                }
                else {
                    //Has pulsado una agrupación por tanto vamos a coger el numero de esa agrupacion y a borrarla
                    if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Deseas borrar el conjunto seleccionado?", "Titulo", JOptionPane.YES_NO_OPTION)) {
                        return;
                    }
                    String elemento = obtenerNagrupacion(clicado);
                    controlador.borrarAgrupacion(elemento);
                    //Se ha borrado y actualizamos lo que ve el usuario
                    actualizarTree();
                }
            }
        });
        fusionarAgrupacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Desean fucionar dos agrupaciones guardadas
                TreePath[] clicados = treeConjuntos.getSelectionPaths();
                if(clicados.length != 2) {
                    moststarMensaje("ERROR", "Seleccione únicamente dos Agrupaciones");
                    return;
                }
                if(clicados[0].getPathCount() != 2 || clicados[1].getPathCount() != 2){
                    moststarMensaje("ERROR", "Seleccione únicamente dos Agrupaciones, no canciones o el conjunto (gracias por su cooperación)");
                    return;
                }
                //Bien tenemos seleccionadas dos agrupaciones por tanto procedemos a fusionarlas
                //Vamos a obtener el numero de cada una
                String a1 = obtenerNagrupacion(clicados[0]);
                String a2 = obtenerNagrupacion(clicados[1]);
                controlador.fusionarAgrupaciones(a1, a2);
                actualizarTree();
            }
        });
        cambiarOrdenCancionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] clicados = treeConjuntos.getSelectionPaths();
                if(clicados.length != 2) {
                    moststarMensaje("ERROR", "Seleccione únicamente dos canciones");
                    return;
                }
                if(clicados[0].getPathCount() != 3 || clicados[1].getPathCount() != 3 || !clicados[0].getParentPath().equals(clicados[1].getParentPath()) ){
                    moststarMensaje("ERROR", "Seleccione únicamente dos canciones,dentro del mismo conjunto");
                    return;
                }
                String numA = obtenerNagrupacion(clicados[0]);
                //Bien tenemos seleccionadas dos agrupaciones por tanto procedemos a fusionarlas
                //Vamos a obtener el titulo;autor de cada una
                String aux1 = clicados[0].getLastPathComponent().toString();
                int post = aux1.indexOf(',');
                int post2 = aux1.indexOf(',', post + 1);
                String c1 = aux1.substring(0,post) +";" +aux1.substring(post+2,post2);
                //Vamos a pasar los IDs de las canciones
                aux1 = clicados[1].getLastPathComponent().toString();
                post = aux1.indexOf(',');
                post2 = aux1.indexOf(',', post + 1);
                String c2 = aux1.substring(0,post) +";" +aux1.substring(post+2,post2);
                controlador.intercambiarOrdenCanciones(numA, c1, c2);
                actualizarTree();
            }
        });
        BORRARCANCIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Primero comprobamos que ha pulsado una canción
                TreePath clicado = treeConjuntos.getSelectionPath();
                if(clicado == null || clicado.getPathCount() != 3){
                    //No han pulsado ningun elemento del jtree o has pulsado el conjunto o una canción por tanto error y cancelar
                    moststarMensaje("ERROR", "Por favor seleccione una única canción a borrar");
                    return;
                }
                else {
                    //Has pulsado una agrupación por tanto vamos a coger el numero de esa agrupacion y a borrarla
                    if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Deseas borrar la canción seleccionada?", "Titulo", JOptionPane.YES_NO_OPTION)) {
                        return;
                    }
                    String elemento = obtenerNagrupacion(clicado);
                    String aux1 = clicado.getLastPathComponent().toString();
                    int post = aux1.indexOf(',');
                    int post2 = aux1.indexOf(',', post + 1);
                    String c1 = aux1.substring(0,post) +";" +aux1.substring(post+2,post2);
                    controlador.borrarCancion(elemento, c1);
                    //Se ha borrado y actualizamos lo que ve el usuario
                    actualizarTree();
                }
            }
        });
        BUSCARCANCIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Primero cogemos el texto
                String textoBusqueda = tituloAutorTextField.getText();
                if (textoBusqueda.equals("") || textoBusqueda.equals("Titulo;Autor")) {
                    moststarMensaje("ERROR", "Por favor especifique una canción con Titulo;Autor");
                    return;
                }
                if (!textoBusqueda.contains(";")) {
                    moststarMensaje("ERROR", "Por favor especifique una canción con el siguiente formato: Titulo;Autor");
                    return;
                }
                //Tiene sentido lo que ha puesto el usuario, vamos a buscarlo
                String resBusqueda = "";
                try {
                    resBusqueda = controlador.buscarCancion(textoBusqueda);
                } catch (Exception ex) {
                    moststarMensaje("ERROR CRITICO", "Por favor especifique una canción con el siguiente formato: Titulo;Autor");
                    return;
                }
                if (resBusqueda == null)
                    moststarMensaje("WARNING", "No se ha encontrado ninguna canción con esos parámetros");
                else {
                    //Visualizamos los datos
                    moststarMensaje("Resultado busqueda", resBusqueda);

                }
            }
        });
        tituloAutorTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tituloAutorTextField.getText().equals("Titulo;Autor")) {
                    tituloAutorTextField.setText("");
                }
            }
        });
        reGenerarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Deseas volver a generar el conjunto, los datos no guardados se perderán?", "CUIDADO", JOptionPane.YES_NO_OPTION)) {
                    return;
                }
                panelVisualizar.setVisible(false);
                showGenerar();
                //Volvemos a regenerar el conjunto actual
                //Campo 0 el originel de los daots
                switch (textOrigC.getText()) {
                    case "All":
                        comboOrigenD.setSelectedIndex(0);
                        break;
                    case "Mis canciones":
                        comboOrigenD.setSelectedIndex(1);
                        break;
                    default:
                        if(comboOrigenD.getItemCount() > 3) {
                            boolean b = false;
                            for(int i = 2;i<comboOrigenD.getItemCount() &&!b;i++){
                                if(comboOrigenD.getItemAt(i).equals(textOrigC.getText())) {
                                    comboOrigenD.setSelectedIndex(i);
                                    b=true;
                                }
                            }
                            if(!b) comboOrigenD.setSelectedIndex(0);
                        }
                        else comboOrigenD.setSelectedIndex(0);
                        break;
                }
                //Campo 1 el algoritmo
                switch (textMetodoC.getText()) {
                    case "Clique":
                        comboAlgoritmo.setSelectedIndex(0);
                        break;
                    case "Louvain":
                        comboAlgoritmo.setSelectedIndex(2);
                        break;
                    case "Newmann":
                        comboAlgoritmo.setSelectedIndex(1);
                        break;
                }
                ArrayList<Integer> res = controlador.getParametrosActual();
                comboAAutor.setSelectedIndex(res.get(0));
                comboAAlbum.setSelectedIndex(res.get(1));
                comboAGenero.setSelectedIndex(res.get(2));
                comboARep.setSelectedIndex(res.get(3));
                comboAAno.setSelectedIndex(res.get(4));
            }
        });


        textFAutor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!textFAutor.getText().equals("")) {
                    //Si lo han modificado bloqueamos que se pueda elegir ese parámetro en el filtrado
                    comboAAutor.setSelectedIndex(0);
                    comboAAutor.setEnabled(false);
                } else {
                    comboAAutor.setEnabled(true);
                }
            }
        });
        textFAlbum.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(!textFAlbum.getText().equals("")){
                    //Si lo han modificado bloqueamos que se pueda elegir ese parámetro en el filtrado
                    comboAAlbum.setSelectedIndex(0);
                    comboAAlbum.setEnabled(false);
                }
                else{
                    //Lo reactivamos
                    comboAAlbum.setEnabled(true);
                }
            }
        });
        textFGenero.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!textFGenero.getText().equals("")) {
                    //Si lo han modificado bloqueamos que se pueda elegir ese parámetro en el filtrado
                    comboAGenero.setSelectedIndex(0);
                    comboAGenero.setEnabled(false);
                } else {
                    //Lo reactivamos
                    comboAGenero.setEnabled(true);
                }
            }
        });

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Integer valor = slider1.getValue();
                porcentaje.setText(valor.toString() + "%");
            }
        });
        CAMBIARCANCIONDEAGRUPACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Queremos cambiar el orden de la canción por tanto vamos a obtener los datos
                String agrupacion = jop.showInputDialog("Por favor especifique el número de la agrupación a la cual deseas mover la canción seleccionada");
                if(agrupacion == null ||agrupacion.equals("")) return;
                //Primero comprobamos que ha pulsado una canción
                TreePath clicado = treeConjuntos.getSelectionPath();
                if(clicado == null || clicado.getPathCount() != 3){
                    //No han pulsado ningun elemento del jtree o has pulsado el conjunto o una canción por tanto error y cancelar
                    moststarMensaje("ERROR", "Por favor seleccione una única canción a cambiar de Agrupación");
                    return;
                }
                else {
                    //Has pulsado una agrupación por tanto vamos a coger el numero de esa agrupacion y a borrarla
                    String elemento = obtenerNagrupacion(clicado);
                    String aux1 = clicado.getLastPathComponent().toString();
                    int post = aux1.indexOf(',');
                    int post2 = aux1.indexOf(',', post + 1);
                    String c1 = aux1.substring(0,post) +";" +aux1.substring(post+2,post2);
                    if(controlador.cambiarCancionAgrupacion(elemento, agrupacion, c1))actualizarTree();
                    else{
                        moststarMensaje("ERROR","Error al mover la canción ¿Has indicado un número de agrupación correcto?");
                    }
                }

            }
        });
    }

    private String obtenerNagrupacion(TreePath path){
        String num;
        if(path.getPathCount() == 3){
            //Han clicado una canción
            String elemento = path.getParentPath().getLastPathComponent().toString();
            String[] aux = elemento.split(" ");
            num = aux[1];
        }
        else{
            //Han clicado una agrupacion
            String elemento = path.getLastPathComponent().toString();
            String[] aux = elemento.split(" ");
            num = aux[1];
        }
        return num;
    }

    private void actualizarConjuntoMostrado() {
        //VAMOS A INICIALIZAR LA VISTA
        ArrayList<String> datos = controlador.getDatosConjuntoActual();
        textNombreC.setText(datos.get(0));
        textMetodoC.setText(datos.get(1));
        mostrarParametros(datos.get(2));
        modificadoCheckBox.setSelected(datos.get(3).equals("true"));
        textOrigC.setText(datos.get(4));
        textDescC.setText(datos.get(5));

        //Vamos a cargar las agrupaciones
        agrupaciones = controlador.getAgrupacionesConjunto();
        //JTREE
        MutableTreeNode top = new DefaultMutableTreeNode("Conjunto Generado",true);
        MutableTreeNode agrupacion = null;
        int j = 0;
        for(ArrayList<String> ag : agrupaciones){
            agrupacion = new DefaultMutableTreeNode("Agrupación " + j,true);
            DefaultMutableTreeNode can = null;
            int x =0;
            for(String cd :ag) {
                can = new DefaultMutableTreeNode(cd, false);
                agrupacion.insert(can, x);
                x++;
            }
            top.insert(agrupacion, j);
            j++;
        }

        DefaultTreeModel modelo = new DefaultTreeModel(top);
        treeConjuntos.setModel(modelo);
    }
    private void actualizarTree() {
        //VAMOS A INICIALIZAR LA VISTA
        ArrayList<String> datos = controlador.getDatosConjuntoActual();
        modificadoCheckBox.setSelected(datos.get(3).equals("true"));
        //Vamos a cargar las agrupaciones
        agrupaciones = controlador.getAgrupacionesConjunto();
        //JTREE
        MutableTreeNode top = new DefaultMutableTreeNode("Conjunto Generado",true);
        MutableTreeNode agrupacion = null;
        int j = 0;
        for(ArrayList<String> ag : agrupaciones){
            agrupacion = new DefaultMutableTreeNode("Agrupación " + j,true);
            DefaultMutableTreeNode can = null;
            int x =0;
            for(String cd :ag) {
                can = new DefaultMutableTreeNode(cd, false);
                agrupacion.insert(can, x);
                x++;
            }
            top.insert(agrupacion, j);
            j++;
        }

        DefaultTreeModel modelo = new DefaultTreeModel(top);
        treeConjuntos.setModel(modelo);
    }



    private void cancelarPoPLista() {
        popUpSelectLista.setVisible(false);
        autogenerar.setVisible(true);
        frame.setSize(dimGenerar);
        frame.setMinimumSize(dimGenerar);
        frame.pack();
    }


    private void cancelarPoPExportar() {
        textListaD.setText("");
        textListaN.setText("");
        popUpExportarPlayList.setVisible(false);
        panelVisualizar.setVisible(true);
        frame.setMinimumSize(dimVisor);
        frame.setSize(dimVisor);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }


    public void main() {
        frame = new JFrame("PresentacionConjuntosAgrupaciones");
        frame.setContentPane(this.t);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Para que cierre la ventana actual
        frame.pack();
        //Seleccionamos el tamaño que queremos
        frame.setMinimumSize(dimSelec);
        frame.setLocationRelativeTo(null);//Lo ponemos en el centro de la pantalla
        frame.setVisible(true);
    }

    private void cargarListaConjunto(){
        ArrayList<String> listado = controlador.getConjuntosGuardados();
        DefaultListModel<String> aux = new DefaultListModel<>();
        for(String a : listado) aux.addElement(a);
        listConjuntos.setModel(aux); //Cargamos los datos
        listConjuntos.setLayoutOrientation(JList.VERTICAL);
        listConjuntos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void borrarConjunto(){
        Object prueba = listConjuntos.getSelectedValue();
        if(prueba != null ) {
            if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(null, "¿Estás seguro que desas borrar el conjunto?", "Confirma", JOptionPane.YES_NO_OPTION)) {
                return;
            }
            String seleccionado = listConjuntos.getSelectedValue().toString();
            if (!seleccionado.isEmpty()) {
                controlador.borrarConjunto(seleccionado);
                actualizarVista();
            }
        }
        else moststarMensaje("ERROR AL BORRAR", "SELECIONE UN CONJUNTO PLEASE");
    }

    private void actualizarVista(){
        ArrayList<String> listado = controlador.getConjuntosGuardados();
        DefaultListModel<String> aux = new DefaultListModel<String>();
        for(String a : listado) aux.addElement(a);
        listConjuntos.setModel(aux); //Cargamos los datos
    }

    private void moststarMensaje(String mensaje, String titulo){
        jop.showMessageDialog(this.frame, titulo, mensaje, JOptionPane.INFORMATION_MESSAGE);
    }

    private void volveraSelector(){
        tituloAutorTextField.setText("Titulo;Autor");
        actualizarVista();
        panelVisualizar.setVisible(false);
        autogenerar.setVisible(false);
        panelSeleccionar.setVisible(true);
        frame.setMinimumSize(dimSelec);
        frame.setSize(dimSelec);
        frame.setLocationRelativeTo(null); // PARA QUE NOS LO MUESTRE JUSTO EN EL CENTRO
        //frame.pack(); adapta el nuevo contenido
        frame.pack();
    }

    private void visualizarConjuntoGuardado(String nombre){
        frame.setMinimumSize(dimVisor);
        frame.setSize(dimVisor);
        frame.setLocationRelativeTo(null); // PARA QUE NOS LO MUESTRE JUSTO EN EL CENTRO
        //frame.pack(); adapta el nuevo contenido
        panelVisualizar.setVisible(true);
        panelSeleccionar.setVisible(false);

        //VAMOS A INICIALIZAR LA VISTA
        ArrayList<String> datos = controlador.getDatosConjunto(nombre);
        textNombreC.setText(datos.get(0));
        textMetodoC.setText(datos.get(1));
        mostrarParametros(datos.get(2));
        modificadoCheckBox.setSelected(datos.get(3).equals("true"));
        textOrigC.setText(datos.get(4));
        textDescC.setText(datos.get(5));

        //Vamos a cargar las agrupaciones
        agrupaciones = controlador.getAgrupacionesConjunto();
        //Para las listas
        if(listConjuntos.isVisible()) {
            listadoAgrupaciones = new DefaultListModel<>();
            for (int i = 0; i < agrupaciones.size(); i++) {
                listadoAgrupaciones.addElement("Agrupación " + i);
            }
            listConjuntos.setLayoutOrientation(JList.VERTICAL);
            listConjuntos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        MutableTreeNode top = new DefaultMutableTreeNode("Conjunto Generado",true);
        MutableTreeNode agrupacion = null;
        int j = 0;
        for(ArrayList<String> ag : agrupaciones){
            agrupacion = new DefaultMutableTreeNode("Agrupación " + j,true);
            DefaultMutableTreeNode can = null;
            int x =0;
            for(String cd :ag) {
                can = new DefaultMutableTreeNode(cd, false);
                agrupacion.insert(can, x);
                x++;
            }
            top.insert(agrupacion, j);
            j++;
        }

        DefaultTreeModel modelo = new DefaultTreeModel(top);
        treeConjuntos.setModel(modelo);

    }


    private void mostrarParametros(String elementos){
        String resultado = "";
        String[] aux = elementos.split(";");
        if(!aux[0].equals("0")) resultado = resultado +"Autor (" +aux[0] +"), " ;
        if(!aux[1].equals("0")) resultado = resultado +"Álbum (" +aux[1] +"), ";
        if(!aux[2].equals("0")) resultado = resultado +"Género (" +aux[2] +"), ";
        if(!aux[3].equals("0")) resultado = resultado +"Reproducciones Comunes (" +aux[3] +"), ";
        if(!aux[4].equals("0")) resultado = resultado +"Año publicación (" +aux[4] +"), ";
        if(resultado.length()>3) textParC.setText(resultado.substring(0, resultado.length() - 2)); //Mostramos y quitamos la coma y el espacio
        else textParC.setText(resultado);
    }

    private void mostrarCancionesAgrupacion(int n){
        if(cancionesAgrupacion != null) cancionesAgrupacion.removeAllElements();
        else {
            cancionesAgrupacion = new DefaultListModel<>();
            listCan.setModel(cancionesAgrupacion);
        }
        for(String info : agrupaciones.get(n)){
            cancionesAgrupacion.addElement(info);
        }


    }

    private void showGenerar(){
        panelSeleccionar.setVisible(false);
        autogenerar.setVisible(true);
        frame.setSize(dimGenerar);
        frame.setMinimumSize(dimGenerar);
        frame.setLocationRelativeTo(null);
        frame.pack();
        //INICIALIZAMOS VALORES
        inicializarCombosAlgoritmo();
    }

    private void inicializarCombosAlgoritmo(){
        if(comboOrigenD.getItemCount()==0) {
            comboOrigenD.addItem("Todas las canciones del sistema");
            comboOrigenD.addItem("Mis canciones asociadas");
            comboOrigenD.addItem("Lista de reproducción propia");
            comboAlgoritmo.addItem("Clique");
            comboAlgoritmo.addItem("Newmann");
            comboAlgoritmo.addItem("Louvain");
            comboAAutor.addItem(0);
            comboAAutor.addItem(1);
            comboAAutor.addItem(2);
            comboAAutor.addItem(3);
            comboAAutor.addItem(4);
            comboAAlbum.addItem(0);
            comboAAlbum.addItem(1);
            comboAAlbum.addItem(2);
            comboAAlbum.addItem(3);
            comboAAlbum.addItem(4);
            comboAGenero.addItem(0);
            comboAGenero.addItem(1);
            comboAGenero.addItem(2);
            comboAGenero.addItem(3);
            comboAGenero.addItem(4);
            comboARep.addItem(0);
            comboARep.addItem(1);
            comboARep.addItem(2);
            comboARep.addItem(3);
            comboARep.addItem(4);
            comboAAno.addItem(0);
            comboAAno.addItem(1);
            comboAAno.addItem(2);
            comboAAno.addItem(3);
            comboAAno.addItem(4);
        }
    }

    private void hideGenerar(){
        autogenerar.setVisible(false);
        frame.setLocationRelativeTo(null);
    }

    private void activarAutogeneracion(){
        //VAMOS A RECOPILAR
        //Obtengamos los datos elementales
        ArrayList<String> datos = new ArrayList<>();
        //Campo 0 el originel de los daots
        switch (comboOrigenD.getSelectedIndex()){
            case 0:
                datos.add("All");
                break;
            case 1:
                datos.add("Mis canciones");
                break;
            case 2:
                //Caso de error imposible
                return;
            default:
                datos.add(comboOrigenD.getSelectedItem().toString());
                break;
        }
        //Campo 1 el algoritmo
        datos.add(comboAlgoritmo.getSelectedItem().toString());
        //Campo 2 como de estricto será el algoritmo
        datos.add(String.valueOf(slider1.getValue()));

        //Obtengamos los datos para el filtrado
        ArrayList<String> parFil = new ArrayList<>();
        if(textFAutor.getText().equals("")) parFil.add("-1");
        else parFil.add(textFAutor.getText());
        if(textFAlbum.getText().equals("")) parFil.add("-1");
        else parFil.add(textFAlbum.getText());
        if(textFGenero.getText().equals("")) parFil.add("-1");
        else parFil.add(textFGenero.getText());
        if(textFNRep.getText().equals("")) parFil.add("-1");
        else parFil.add(textFNRep.getText());
        if(textFFechaIns.getText().equals("")) parFil.add("-1");
        else parFil.add(textFFechaIns.getText());
        if(textFNMAX.getText().equals("")) parFil.add("-1");
        else parFil.add(textFNMAX.getText());

        //Obtengamos los datos para el algoritmo
        ArrayList<Integer> parAlgo = new ArrayList<>();
        parAlgo.add(comboAAutor.getSelectedIndex());
        parAlgo.add(comboAAlbum.getSelectedIndex());
        parAlgo.add(comboAGenero.getSelectedIndex());
        parAlgo.add(comboARep.getSelectedIndex());
        parAlgo.add(comboAAno.getSelectedIndex());

        if(controlador.generarConjunto(datos,parFil,parAlgo)){
            //Se ha realizado los conjuntos por tanto lo mostramos
            hideGenerar();
            visualizarConjuntoGenerado();
        }
        else moststarMensaje("ERROR AL GENERAR LAS AGRUPACIONES", "¿Quizás has sido muy estricto con los criterios de filtrado?\n¿Si has seleccionado una lista tienes canciones en esa lista? \n¿Si has seleccionado mis canciones has asociado canciones?");
    }

    private void seleccionarLista() {
        //Cargamos las listas del usuario (quitando mis canciones)
        popUpSelectLista.setVisible(true);
        autogenerar.setVisible(false);
        frame.setSize(dimPopUp);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(dimPopUp);
        frame.pack();
        comboListas.removeAllItems();
        ArrayList<String> listasU = controlador.getListas();
        listasU.remove(0);
        //Rellenamos el combobox
       // comboListas.removeAll();
        if(listasU.size() == 0) {
            moststarMensaje("AVISO", "El usuario no tiene ninguna lista de reproducción ajena a Mis canciones");
            //cancelarPoPLista();
            comboOrigenD.setSelectedIndex(0);
            return;
        }
        for(String s : listasU) comboListas.addItem(s);
        //Ya el botón será el encargado de devolver el string
    }

    private void cancelarPoPSeleccionar(){

    }

    private void visualizarConjuntoGenerado(){
        frame.setSize(dimVisor);
        frame.setMinimumSize(dimVisor);
        frame.setLocationRelativeTo(null); // PARA QUE NOS LO MUESTRE JUSTO EN EL CENTRO
        //frame.pack(); adapta el nuevo contenido
        panelVisualizar.setVisible(true);
        panelSeleccionar.setVisible(false);

        //VAMOS A INICIALIZAR LA VISTA
        ArrayList<String> datos = controlador.getDatosConjuntoActual();
        textNombreC.setText(datos.get(0));
        textMetodoC.setText(datos.get(1));
        mostrarParametros(datos.get(2));
        modificadoCheckBox.setSelected(datos.get(3).equals("true"));
        textOrigC.setText(datos.get(4));
        textDescC.setText(datos.get(5));

        //Vamos a cargar las agrupaciones
        agrupaciones = controlador.getAgrupacionesConjunto();
        //JTREE
        MutableTreeNode top = new DefaultMutableTreeNode("Conjunto Generado",true);
        MutableTreeNode agrupacion = null;
        int j = 0;
        for(ArrayList<String> ag : agrupaciones){
            agrupacion = new DefaultMutableTreeNode("Agrupación " + j,true);
            DefaultMutableTreeNode can = null;
            int x =0;
            for(String cd :ag) {
                can = new DefaultMutableTreeNode(cd, false);
                agrupacion.insert(can, x);
                x++;
            }
            top.insert(agrupacion, j);
            j++;
        }

        DefaultTreeModel modelo = new DefaultTreeModel(top);
        treeConjuntos.setModel(modelo);
    }

    public void close() {
        frame.dispose();
    }
}
