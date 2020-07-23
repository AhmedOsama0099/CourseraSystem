package com.oop.View;

import com.oop.Controllers.Course.CourseController;
import com.oop.Controllers.Users.NormalUserController;
import com.oop.Model.Course;
import com.oop.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class NormalUserForm {
    JFrame frame;
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JPanel joinCoursePanel;
    private JTextField joinCourseCodeFileld;
    private JButton joinCourseButton;
    private JTable coursesTable;
    private JTextField showFilesCourseCodeField;
    private JButton showFilesButton;
    private JTable joionedCoursesTable;
    private JTable filesForJoinedCourseTable;
    private JTextField courseCodeFiledForRatingField;
    private JComboBox ratecCmboBox;
    private JButton rateButton;
    private JTable joinedCoursesTableForRating;
    private NormalUserController normalUserController;
    private CourseController courseController;
    private DefaultTableModel coursesTableModel;
    private DefaultTableModel joinedCoursesTableModel;
    private DefaultTableModel filesForJoinedCoursesTableModel;
    int currUserID;
    User user;
    ArrayList<Course> allCourses;
    ArrayList<Course> joinedCourses;

    NormalUserForm(User user) {
        frame = new JFrame();
        frame.setTitle("Normal User");
        frame.setSize(800, 600);
        frame.add(panelMain);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setWindowAtCenter();
        setFilesTable();

        normalUserController = new NormalUserController();
        courseController = new CourseController();
        this.user = user;
        currUserID = normalUserController.getUserID(user);
        joinedCourses = courseController.getAllJoinedCourses(currUserID);
        allCourses = courseController.getAllCourses();
        setCoursesTable();
        setActions();
        setCourseRateTale();
        ratecCmboBox.addItem("Good");
        ratecCmboBox.addItem("Bad");
        for (Course course : joinedCourses) System.out.println(course.getName());

    }

    private void setCourseRateTale() {
        joinedCoursesTableForRating.setModel(joinedCoursesTableModel);
        joinedCoursesTableForRating.setEnabled(false);
    }

    private void setFilesTable() {
        filesForJoinedCoursesTableModel = new DefaultTableModel();
        filesForJoinedCourseTable.setEnabled(false);
        filesForJoinedCourseTable.setModel(filesForJoinedCoursesTableModel);
        filesForJoinedCourseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = filesForJoinedCourseTable.rowAtPoint(e.getPoint());
                int col = filesForJoinedCourseTable.columnAtPoint(e.getPoint());
                String fileName = filesForJoinedCoursesTableModel.getValueAt(row, col).toString();
                try {
                    File file = new File("AttachedFiles/" + fileName);
                    if (!Desktop.isDesktopSupported()) {
                        System.out.println("not supported");
                        return;
                    }
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists())
                        desktop.open(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        filesForJoinedCoursesTableModel.addColumn("File Name");
    }


    private void setWindowAtCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void setCoursesTable() {
        coursesTableModel = new DefaultTableModel();
        coursesTable.setEnabled(false);
        coursesTable.setModel(coursesTableModel);
        coursesTableModel.addColumn("admin_id");
        coursesTableModel.addColumn("course_code");
        coursesTableModel.addColumn("course_name");
        setCoursesTableData();

        joinedCoursesTableModel = new DefaultTableModel();
        joionedCoursesTable.setEnabled(false);
        joionedCoursesTable.setModel(joinedCoursesTableModel);
        joinedCoursesTableModel.addColumn("admin_id");
        joinedCoursesTableModel.addColumn("course_code");
        joinedCoursesTableModel.addColumn("course_name");
        setJoinedCoursesTableData();
    }

    private void setJoinedCoursesTableData() {
        joinedCoursesTableModel.setRowCount(0);
        ArrayList<Course> courses = courseController.getAllJoinedCourses(currUserID);
        for (Course course : courses) {
            joinedCoursesTableModel.insertRow(joinedCoursesTableModel.getRowCount(), new Object[]{String.valueOf(course.getAdminId()), course.getCode(), course.getName()});
        }
    }
    /*private void setJoinedCoursesRatingTableData() {

        joinedCoursesTableForRatingModel.setRowCount(0);
        ArrayList<Course> courses = courseController.getAllJoinedCourses(currUserID);
        System.out.println(courses);
        for (Course course : courses) {
            System.out.println("asdasdasdasd");
            joinedCoursesTableForRatingModel.insertRow(joinedCoursesTableForRatingModel.getRowCount(), new Object[]{String.valueOf(course.getAdminId()), course.getCode(), course.getName()});
        }
    }*/

    private void setCoursesTableData() {
        /*coursesTableModel.setRowCount(0);
        joinedCourses = courseController.getAllJoinedCourses(currUserID);
        for (Course course : joinedCourses) {
            coursesTableModel.insertRow(coursesTableModel.getRowCount(), new Object[]{String.valueOf(course.getAdminId()), course.getCode(), course.getName()});
        }*/
        coursesTableModel.setRowCount(0);
        for (Course course : allCourses) {
            if (!checkJoinedCourse(course.getCode())) {
                coursesTableModel.insertRow(coursesTableModel.getRowCount(), new Object[]{String.valueOf(course.getAdminId()), course.getCode(), course.getName()});
            }
        }
    }

    private void setActions() {
        joinCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseCode = joinCourseCodeFileld.getText().trim();
                if (courseCode.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "course code can't be empty");
                } else {
                    if (courseController.joinCourse(courseCode, currUserID)) {
                        JOptionPane.showMessageDialog(frame, "joined successfully");
                        joinedCourses.add(courseController.getCourseByCourseCode(courseCode));
                        setJoinedCoursesTableData();
                        //setJoinedCoursesRatingTableData();
                    } else {
                        JOptionPane.showMessageDialog(frame, "this course joined before");
                    }
                }
            }
        });
        showFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseCode = showFilesCourseCodeField.getText().trim();
                if (courseCode.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Course code can't be empty");
                    return;
                }
                if (checkJoinedCourse(courseCode)) {
                    filesForJoinedCoursesTableModel.setRowCount(0);
                    ArrayList<String> files = courseController.getCourseFiles(courseCode);
                    for (String file : files) {
                        filesForJoinedCoursesTableModel.insertRow(filesForJoinedCoursesTableModel.getRowCount(), new Object[]{file});
                    }
                } else {
                    if(checkCourse(courseCode))
                        JOptionPane.showMessageDialog(frame, "Course not joined");
                    else
                        JOptionPane.showMessageDialog(frame, "Course not found");
                }
            }
        });

        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String courseCode = courseCodeFiledForRatingField.getText().trim();
                if (courseCode.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "course code can't be empty");
                    return;
                }
                if (checkJoinedCourse(courseCode)) {
                    String rate = ratecCmboBox.getSelectedItem().toString();
                    if (courseController.updateRate(currUserID, courseCode, rate)) {
                        JOptionPane.showMessageDialog(frame, "rate done");

                    } else {
                        JOptionPane.showMessageDialog(frame, "failed rating");
                    }
                }
                else{
                    if(checkCourse(courseCode))
                        JOptionPane.showMessageDialog(frame, "You didn't join the course yet");
                    else
                        JOptionPane.showMessageDialog(frame, "Course not found");
                }


            }
        });
    }

    boolean checkJoinedCourse(String courseCode) {
        for (Course course : joinedCourses) {
            if (course.getCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }
    boolean checkCourse(String courseCode) {
        for (Course course : allCourses) {
            if (course.getCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }
}

