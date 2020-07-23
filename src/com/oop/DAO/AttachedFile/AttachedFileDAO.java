package com.oop.DAO.AttachedFile;

import com.oop.SQLiteConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AttachedFileDAO {
    public boolean insertFilesToCourse(File[]files,String courseCode){
        for(File f:files){
            copyFile(f);
            String sqlQuery = "Insert Into attached_files(course_code,full_path) values (?,?)";
            try {
                Connection connection = SQLiteConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, courseCode);
                preparedStatement.setString(2, "AttachedFiles/"+f.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }

    private void copyFile(File file) {
        File source = new File(file.getAbsolutePath());
        File dest = new File("AttachedFiles/"+file.getName());

        try {
            Files.copy(source.toPath(), dest.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
