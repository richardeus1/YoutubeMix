package com.Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Natali Balon
 * @author Andreu Conesa
 */
public class PresentacionUsuarios {
    CtrlPresentacionUsuarios ctrlPresentacionUsuarios;
    private static JFrame frame;
    private JButton modificarButton;
    private JButton cambiarButton;
    private JButton exportarButton;
    private JPanel panelPrincipal;
    private JButton importarButton;
    private JButton eliminarButton;
    private JButton cerrarSesionButton;
    private JButton atrasButton;
    private JPanel panelModificar;
    private JButton guardarDatosButton;
    private JTextField textNom;
    private JTextField textApellido;
    private JTextField textFecha;
    private JTextField textGenero;
    private JTextField textCorreo;
    private JButton atrasButton1;
    private JPanel panelContrasenya;
    private JPasswordField password1;
    private JButton guardarNuevaContrasenyaButton;
    private JButton cancelarButton;
    private JPasswordField password2;
    private JOptionPane jop = new JOptionPane();


    public PresentacionUsuarios(CtrlPresentacionUsuarios cl) {
        ctrlPresentacionUsuarios = cl;

        //MODIFICAR ATRIBUTOS
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuModificar();
                cargarInformacion();
            }
        });

        guardarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(guardarDatosButton,"Estas seguro que quieres guardar los cambios","Guardar",JOptionPane.YES_NO_OPTION)) {
                    ctrlPresentacionUsuarios.setInformacion(textNom.getText(), textApellido.getText(), textFecha.getText(), textGenero.getText(), textCorreo.getText());
                    jop.showMessageDialog(guardarDatosButton, "Los datos han sido modificados", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        atrasButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });


        //CAMBIAR CONTRASENYA
        cambiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPasswordField pf = new JPasswordField();
                int okCxl = JOptionPane.showConfirmDialog(null, pf, "Entrar contraseña actual", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (okCxl == JOptionPane.OK_OPTION) {
                    String password = new String(pf.getPassword());
                    String contrasenyaActual = ctrlPresentacionUsuarios.getContrasenya();
                    if (password.equals(contrasenyaActual)) {
                        menuCambiarContrasenya();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Contraseña incorrecta");
                    }
                }
            }
        });

        guardarNuevaContrasenyaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] arrayC = password1.getPassword();
                String pass = new String(arrayC);

                char[] arrayC2 = password2.getPassword();
                String pass2 = new String(arrayC2);
                if (pass.equals(pass2)){
                    ctrlPresentacionUsuarios.setContrasenya(pass);
                    jop.showMessageDialog(guardarNuevaContrasenyaButton, "Contraseña modificada", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);
                    password1.setText(null);
                    password2.setText(null);
                    menu();
                } else {
                    jop.showMessageDialog(guardarNuevaContrasenyaButton, "Las contraseñas no coinciden", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                    password1.setText(null);
                    password2.setText(null);
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ax = JOptionPane.showConfirmDialog(null, "¿Estás seguro que desea eliminar tu cuenta?","Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if(ax == JOptionPane.YES_OPTION) {
                    frame.setVisible(false);
                    Boolean b = ctrlPresentacionUsuarios.removeTodosLasRelaciones();
                    if (b) {
                        jop.showMessageDialog(eliminarButton, "Eliminado correctamente el usuario", "Acción terminada", JOptionPane.INFORMATION_MESSAGE);
                        ctrlPresentacionUsuarios.guardarDatosGenerales();
                        ctrlPresentacionUsuarios.getMenuInicioSesion();
                    }
                    else {
                        jop.showMessageDialog(eliminarButton, "No es posible eliminar el usuario Administrador", "Acción no terminada", JOptionPane.ERROR_MESSAGE);
                        frame.setVisible(true);
                    }
                }
                //si dice que no se queda donde esta en Menu()
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ctrlPresentacionUsuarios.guardarTodosLosDatos();
                ctrlPresentacionUsuarios.getMenuInicioSesion();
            }
        });


        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CtrlPresentacionUsuarios.guardaModificacionesUsuarios();
                frame.setVisible(false);
                ctrlPresentacionUsuarios.getMenuPrincipal();
                //Para cerrar java -------- IMPORTANTE QUITAR CUANDO SE JUNTE LAS
            }
        });

    }

    public void main() {
        principal();
    }

    public void principal(){
        frame = new JFrame("Panel usuario");
        frame.setContentPane(this.panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);//Lo ponemos en el centro de la pantalla
        frame.setVisible(true);
    }

    public void menuModificar() {
        frame.setVisible(false);
        frame = new JFrame("Panel modificar información");
        frame.setContentPane(panelModificar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);//Lo ponemos en el centro de la pantalla
        frame.setVisible(true);
    }

    public void cargarInformacion(){
        ArrayList<String> infor = ctrlPresentacionUsuarios.getInformacion();
        textNom.setText(infor.get(0));
        textApellido.setText(infor.get(1));
        textFecha.setText(infor.get(2));
        textGenero.setText(infor.get(3));
        textCorreo.setText(infor.get(4));
    }

    public void menu(){
        frame.setVisible(false);
        frame = new JFrame("Panel usuario");
        frame.setContentPane(panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuCambiarContrasenya(){
        frame.setVisible(false);
        frame = new JFrame("Panel modificar contraseña");
        frame.setContentPane(panelContrasenya);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
