package com.oop.View;

import com.oop.Controllers.Users.AdminUserController;
import com.oop.Controllers.Users.LoginUserController;
import com.oop.Model.Admin;
import com.oop.Model.NormalUser;
import com.oop.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class SignIn {
    private JTextField userName;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel panelMain;
    private JPanel actionPanel;
    private JPanel inputPanel;
    JFrame frame;
    LoginUserController loginUserController;

    public SignIn() {
        frame = new JFrame();
        frame.setTitle("SignIn");
        frame.setSize(500, 300);
        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginUserController = new LoginUserController();
        setBackGroundColours();
        setWindowAtCenter();
        actionListener();
    }

    private void setBackGroundColours() {
        panelMain.setBackground(Color.DARK_GRAY);
        inputPanel.setBackground(Color.DARK_GRAY);
        actionPanel.setBackground(Color.DARK_GRAY);
    }

    private void setWindowAtCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void actionListener() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginUser();

            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SignUpNormalUser signUpNormalUser = new SignUpNormalUser(frame);
                signUpNormalUser.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }

    private void loginUser() {
        if (validData()) {
            User user = loginUserController.login(new User(userName.getText().trim(), "", String.valueOf(password.getPassword()).trim()));
            if (user == null) {
                JOptionPane.showMessageDialog(frame, "invalid UserName or Password");
            } else {
                if (user instanceof Admin) {
                    AdminUserForm adminUserForm=new AdminUserForm(user);
                    adminUserForm.frame.setVisible(true);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

                } else if (user instanceof NormalUser) {
                    NormalUserForm normalUserForm=new NormalUserForm(user);
                    normalUserForm.frame.setVisible(true);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            }
        }
    }

    private boolean validData() {
        if (userName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "UserName can't be empty");
            return false;
        }
        if (String.valueOf(password.getPassword()).trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Password can't be empty");
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SignIn signIn = new SignIn();
                signIn.frame.setVisible(true);
                //signIn.setVisible(true);
            }
        });
    }
}
