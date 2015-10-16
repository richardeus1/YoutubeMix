package com.Vista;

import com.dominio.ConjuntoAgrupaciones;
import com.dominio.CtrlDominio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

/**
 * Created by Alex on 21/5/15.
 */
public class inicioPrograma {
    //Cosas variable
    CtrlVistas controlador;

    //PARA DEBUGG: Si es true no se pide por usuario si no que coge el genérico que está en CtrlVistas
    private final Boolean modoDebugg = false;

    //Cosas interfaz
    private Dimension dimLog = new Dimension(450,250);
    private Dimension dimMenu = new Dimension(300,200);

    static JFrame frame;
    private JPanel panel1;
    private JButton cancionesButton;
    private JButton listasButton;
    private JButton agrupacionesButton;
    private JPanel inicio;
    private JPanel menu;
    private JTextField textUsuario;
    private JPasswordField textPass;
    private JButton crearUsuarioButton;
    private JButton iniciarSesionButton;
    private JPanel registro;
    private JButton cancelarButton;
    private JButton registrarseButton;
    private JTextField textAlias;
    private JPasswordField textPassword;
    private JPasswordField textPassword2;
    private JTextField textNombre;
    private JTextField textApellido;
    private JTextField textNacimiento;
    private JTextField textGenero;
    private JTextField textCorreo;
    private JButton editarPerfilButton;
    private JButton reproducirButton;

    public inicioPrograma(CtrlVistas vistas){
        //Inicializamos
        this.controlador = vistas;
        //Ocultamos lo que no queramos
        if(!modoDebugg) {
            inicio.setVisible(true);
            menu.setVisible(false); //Mostramos el loggin solo
            registro.setVisible(false);
        }
        else {
            inicio.setVisible(false);
            registro.setVisible(false);
        }

        //Clase del dominio necesaria

        //Listener botones
        agrupacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PresentacionConjuntosAgrupaciones nA = new PresentacionConjuntosAgrupaciones();
                //nA.main(null);
                controlador.cargarVistaAgrupaciones();
            }
        });
        listasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cargarVistaListas();

            }
        });

        cancionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cargarVistaCanciones();
            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] arrayC = textPass.getPassword();
                String pass = new String(arrayC);
                //De serie iniciaremos sesión con el usuario que tenga en la variable dentro de la clase hasta que no implementen
                Boolean b = controlador.iniciarSesion(textUsuario.getText(), pass);
                if (b) {
                    controlador.usuarioCorrecto(textUsuario.getText());
                    cargarMenu();
                    eliminarContenidoInicial();
                } else {
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(iniciarSesionButton, "Los datos son incorrectos", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        reproducirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cargarVistaListasRepro();
            }
        });

        crearUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //borramos la información que hemos escrito en usuario y contraseña de iniciar sesion
                eliminarContenidoInicial();
                MenuRegistro();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarContenidoRegistro();
                MenuInicio();
            }
        });

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //De serie iniciaremos sesión con el usuario que tenga en la variable dentro de la clase hasta que no implementen
                if (textAlias.getText().isEmpty()) {
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(registrarseButton, "El alias no puede ser vacio", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    char[] arrayC = textPassword.getPassword();
                    String pass = new String(arrayC);

                    char[] arrayC2 = textPassword2.getPassword();
                    String pass2 = new String(arrayC2);
                    if(pass.equals(pass2)) {
                        Boolean b = controlador.addUsuario(textAlias.getText(), pass, textNombre.getText(), textApellido.getText(), textNacimiento.getText(), textGenero.getText(), textCorreo.getText());
                        if (b) {
                            JOptionPane jop = new JOptionPane();
                            jop.showMessageDialog(registrarseButton, "Usuario creado", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);
                            eliminarContenidoRegistro();
                            MenuIniciarSesion();
                        } else {
                            JOptionPane jop = new JOptionPane();
                            jop.showMessageDialog(registrarseButton, "Usuario no creado", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane jop = new JOptionPane();
                        jop.showMessageDialog(registrarseButton, "Las contraseñas no coinciden", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        editarPerfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.cargarVistaUsuarios();
            }
        });
    }

    private void eliminarContenidoRegistro() {
        textAlias.setText(null);
        textPassword.setText(null);
        textPassword2.setText(null);
        textNombre.setText(null);
        textApellido.setText(null);
        textNacimiento.setText(null);
        textGenero.setText(null);
        textCorreo.setText(null);
    }

    private void eliminarContenidoInicial() {
        textUsuario.setText(null);
        textPass.setText(null);
    }


    public void cargarMenu(){
        inicio.setVisible(false);
        menu.setVisible(true); //Mostramos el loggin solo
        //Actualizamos el frame
        frame.setSize(dimMenu);
        frame.setMinimumSize(dimMenu);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    public void MenuRegistro(){
        inicio.setVisible(false);
        registro.setVisible(true); //Mostramos el registro solo
        //Actualizamos el frame
        frame.setSize(dimMenu);
        frame.setMinimumSize(dimMenu);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    public void MenuInicio(){
        registro.setVisible(false); //OCultamos el registro solo
        inicio.setVisible(true);
        //Actualizamos el frame
        frame.setSize(dimMenu);
        frame.setMinimumSize(dimMenu);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    public void MenuIniciarSesion(){
        frame.setVisible(false);
        frame = new JFrame("inicioPrograma");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().getComponent(0).setVisible(false);
        frame.getContentPane().getComponent(1).setVisible(true);
        frame.getContentPane().getComponent(2).setVisible(false);
        frame.pack();
        frame.setMinimumSize(dimLog);
        frame.setSize(dimLog);
        frame.setLocationRelativeTo(null);//Lo ponemos en el centro de la pantalla
        frame.setVisible(true);
    }

    public void main() {
        //Ocultamos lo que no necesitmas
        frame = new JFrame("YouTube Mix");
        frame.setContentPane(this.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(dimLog);
        frame.setSize(dimLog);
        frame.setLocationRelativeTo(null);//Lo ponemos en el centro de la pantalla
        frame.setVisible(true);
    }

    /**
     * Oculta la vista actual
     */
    public void ocultar(){
        frame.setVisible(false);
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}
