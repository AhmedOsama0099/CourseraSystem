package com.oop.View;

import com.oop.Controllers.AttachedFileController.AttachedFileController;
import com.oop.Controllers.Course.CourseController;
import com.oop.Controllers.Users.AdminUserController;
import com.oop.DAO.Users.AdminUserDAO;
import com.oop.Model.Admin;
import com.oop.Model.Course;

import com.oop.Model.User;
import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import java.util.ArrayList;

public class AdminUserForm {
    JFrame frame;
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JPanel addAdminPanel;
    private JPanel addCoursePanel;
    private JButton addAdminBtn;
    private JTextField userNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JPanel addAdminInputPanel;
    private JButton addCourseBtn;
    private JTextField courseCodeField;
    private JTextField courseNameField;
    private JTable coursesTable;
    private JTextField attachCourseCodeFiled;
    private JButton addFileButton;
    private JPanel addFilleToCoursePannel;
    private JPanel addCourseInputPanel;
    private JPanel chartPannel;
    AdminUserController adminUserController;
    CourseController courseController;
    AttachedFileController attachedFileController;
    private DefaultTableModel coursesTableModel;
    int currAdminID;
    User user;
    AdminUserForm(User user){

        frame = new JFrame();
        frame.setTitle("Admin User");
        frame.setSize(800, 600);
        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setWindowAtCenter();
        setActions();
        setBackGroundColours();
        setControllers();
        this.user=user;
        this.currAdminID=adminUserController.getUserID(user);
        setCoursesTable();

        /*DefaultPieDataset pieDataset=new DefaultPieDataset();
        pieDataset.setValue("one", Integer.valueOf(10));
        pieDataset.setValue("two", Integer.valueOf(20));
        pieDataset.setValue("three", Integer.valueOf(30));
        pieDataset.setValue("four", Integer.valueOf(40));
        JFreeChart chart=ChartFactory.createPieChart("pie",pieDataset,true,true,true);
        //PiePlot3D p=(PiePlot3D)chart.getPlot();
        //p.setForegroundAlpha(TOP_ALLIGNMENRT);
        ChartFrame frame=new ChartFrame("pe chart",chart);
        frame.setVisible(true);
        frame.setSize(450,500);*/
        chartPannel.setLayout(new BorderLayout());
        chartPannel.add(getChartPanel(), BorderLayout.CENTER);
    }
    private  PieDataset createDataset( ) {
        DefaultPieDataset dataset = new DefaultPieDataset( );

        ArrayList<Pair<String,Integer>>arrayList=adminUserController.getNumberOfUsersJoinedInAllCourses();
        for(Pair pair:arrayList){
            dataset.setValue(pair.getKey().toString(),Integer.valueOf(pair.getValue().toString()));
        }

        return dataset;
    }
    private  JFreeChart createChart( PieDataset dataset ) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Joined Courses Chart",   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);

        return chart;
    }
    public  JPanel getChartPanel( ) {
        JFreeChart chart = createChart(createDataset( ) );

        return new ChartPanel( chart );
    }

    private void setCoursesTable() {
        coursesTableModel=new DefaultTableModel();
        coursesTable.setEnabled(false);
        coursesTable.setModel(coursesTableModel);
        coursesTableModel.addColumn("admin_id");
        coursesTableModel.addColumn("code");
        coursesTableModel.addColumn("name");
        setCoursesTableData();
    }

    private void setCoursesTableData() {
        coursesTableModel.setRowCount(0);
        ArrayList<Course>courses=courseController.getAllCourses();
        for(Course course:courses){
            coursesTableModel.insertRow(coursesTableModel.getRowCount(), new Object[]{ String.valueOf(course.getAdminId()), course.getCode(), course.getName()});
        }
    }

    private void setControllers() {
        adminUserController=new AdminUserController();
        courseController=new CourseController();
        attachedFileController=new AttachedFileController();
    }

    private void setBackGroundColours() {
        panelMain.setBackground(Color.DARK_GRAY);
        addAdminPanel.setBackground(Color.DARK_GRAY);
        addAdminInputPanel.setBackground(Color.DARK_GRAY);
        addCoursePanel.setBackground(Color.DARK_GRAY);
        addCourseInputPanel.setBackground(Color.DARK_GRAY);
        addFilleToCoursePannel.setBackground(Color.DARK_GRAY);
        //actionPanel.setBackground(Color.DARK_GRAY);
    }

    private void setActions() {
        addAdminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Admin admin=validData();
                if(admin!=null){
                    if(!adminUserController.insertUser(admin)){
                        JOptionPane.showMessageDialog(frame,"UserName or Email found before");
                        return;
                    }
                    JOptionPane.showMessageDialog(frame,"Added Successfully");
                }
            }
        });

        addCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Course course=validCourseData();
                if(course!=null){
                    if(courseController.insertCourse(course)){
                        JOptionPane.showMessageDialog(frame,"Added Successfully");
                        setCoursesTableData();
                        chartPannel.removeAll();
                        chartPannel.add(getChartPanel(), BorderLayout.CENTER);

                    }
                    else{
                        JOptionPane.showMessageDialog(frame,"course code found before");
                    }
                }
            }
        });

        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(attachCourseCodeFiled.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(frame,"Course code can't be empty");
                    return;
                }
                Course course=courseController.getCourseByCourseCode(attachCourseCodeFiled.getText().trim());
                if(course==null){
                    JOptionPane.showMessageDialog(frame,"Course not found");
                    return;
                }
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                //chooser.addChoosableFileFilter(new FileNameExtensionFilter("fi", "jpg", "png"));
                chooser.showOpenDialog(null);
                File []files = chooser.getSelectedFiles();
                if(files!=null){
                    if(attachedFileController.insertFilesToCourse(files,course.getCode())){
                        JOptionPane.showMessageDialog(frame,"Files added successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(frame,"something wrong!");
                    }
                }
            }
        });
    }

    private Course validCourseData() {
        String courseCode=courseCodeField.getText().trim();
        if(courseCode.isEmpty()){
            JOptionPane.showMessageDialog(frame,"course code can't be empty");
            return null;
        }
        String courseName=courseNameField.getText().trim();
        if(courseName.isEmpty()){
            JOptionPane.showMessageDialog(frame,"course name can't be empty");
            return null;
        }

        return new Course(currAdminID,courseCode,courseName);
    }

    private Admin validData() {
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
        return new Admin(userName,email,pw);
    }

    private void setWindowAtCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
