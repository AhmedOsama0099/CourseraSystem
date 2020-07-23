package com.oop.View;

import com.oop.Controllers.Users.NormalUserController;
import com.oop.DAO.Users.AdminUserDAO;
import com.oop.DAO.Users.NormalUserDAO;
import com.oop.Model.NormalUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class SignUpNormalUser {
    private JButton registerButton;
    private JTextField userNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JPanel panelMain;
    private JPanel inputPanel;
    JFrame frame;
    JFrame signInFrame;
    NormalUserController normalUserController;
    SignUpNormalUser(JFrame signInFrame){
        frame=new JFrame();
        frame.setTitle("SignIn");
        frame.setSize(500, 300);
        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackGroundColours();
        this.signInFrame=signInFrame;
        normalUserController=new NormalUserController();
        setWindowAtCenter();
        setActionListener();
        onClosing();

    }
    private void setBackGroundColours() {
        panelMain.setBackground(Color.DARK_GRAY);
        inputPanel.setBackground(Color.DARK_GRAY);
    }
    private void onClosing() {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                signInFrame.setVisible(true);
            }
        });
    }

    private void setWindowAtCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void setActionListener() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                NormalUser normalUser=validData();
                if(normalUser!=null){
                    if(!normalUserController.insertUser(normalUser)){
                        JOptionPane.showMessageDialog(frame,"UserName or Email found before");
                        return;
                    }
                    JOptionPane.showMessageDialog(frame,"Registered Successfully");
                    signInFrame.setVisible(true);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            }
        });
    }

    private NormalUser validData() {
        String userName=userNameField.getText().trim();
        if(userName.isEmpty()){
            JOptionPane.showMessageDialog(frame,"User Name can't be empty");
            return null;
        }
        String email=emailField.getText().trim();
        if(email.isEmpty()){
            JOptionPane.showMessageDialog(frame,"Email can't be empty");
            return null;
        }
        String pw=String.valueOf(passwordField.getPassword()).trim();
        if(pw.isEmpty()){
            JOptionPane.showMessageDialog(frame,"Password can't be empty");
            return null;
        }
        String confirmPw=String.valueOf(confirmPasswordField.getPassword());
        if(confirmPw.isEmpty()){
            JOptionPane.showMessageDialog(frame,"Confirm password can't be empty");
            return null;
        }

        if(!pw.equals(confirmPw)){
            JOptionPane.showMessageDialog(frame,"not match password");
            return null;
        }
        return new NormalUser(userName,email,pw);
    }
}
