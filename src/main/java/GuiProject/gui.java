package GuiProject;
import net.proteanit.sql.DbUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class gui extends javax.swing.JFrame {

    private JButton Login;
    private JPanel panel1;
    JTextField studentid;
    JTextField studentname;
    private JButton saveButton;
    private JTable table1;
    private JList list1;
    private JButton addStudentButton;
    private JButton updateButton;
    private JTextField course;
    private JTextField mark1;
    private JTextField mark2;
    private JButton addList;
    private JButton addtable;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;


    public gui() {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = studentid.getText();
                String name = studentname.getText();
                String major = course.getText();
                String marks1 = mark1.getText();
                String marks2 = mark2.getText();
                String host = "localhost";
                String port = "5432";
                String db_name = "student";
                String username = "postgres";
                String password = "admin";


                try {
                    Class.forName("org.postgresql.Driver");
                    Connection conn = DriverManager.getConnection(
                            "jdbc:postgresql://" + host + ":" + port + "/" + db_name + "", "" + username + "", "" + password + "");

                    if (conn != null) {
                        String sql = "insert into student values (' " + id + "',' " + name + "', ' " + major + "',' " + marks1 + "')";
                        Statement statement = conn.createStatement();
                        int x = statement.executeUpdate(sql);
                        if (x == 0) {
                            JOptionPane.showMessageDialog(Login, "User Exists");
                        } else {
                            JOptionPane.showMessageDialog(Login, "Login Successful");
                        }
                        conn.close();


                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


            }


        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject obj = new JSONObject();
                JSONArray jrr = new JSONArray();
                obj.put("Username", studentid.getText());
                obj.put("Password", studentname.getText());
                jrr.add(obj);
                JOptionPane.showMessageDialog(null, obj);
                try {
                    FileWriter file = new FileWriter("student.json");
                    file.write(jrr.toJSONString());
                    file.close();
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Error occurred");
                }
                JOptionPane.showMessageDialog(null, "Data Saved");



            }

        });
        addList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection conn = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/student)", "" + studentid + "", "" + studentname + "");
                    String sql = "select * from student";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    table1.setModel(DbUtils.resultSetToTableModel(rs));


                    while (rs.next()) {

                        String student_id = rs.getString("id");
                        String student_name = rs.getString("name");
                        String major = rs.getString("major");
                        String marks1 = rs.getString("marks1");
                        String marks2 = rs.getString("marks2");



                        String tbData[] = {student_id, student_name, major, marks1, marks2};
                        DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();

                        tblModel.addRow(tbData);
                    }

                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String sql = "UPDATE student SET name=?,major=?, marks1=?, marks2=? WHERE id=?";
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/student", "root", "");
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, studentid.getText());
                    pst.setString(2, studentname.getText());
                    pst.setString(3, course.getText());
                    pst.setString(4, mark1.getText());
                    pst.setString(5, mark2.getText());
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "updated successfully");

                } catch (SQLException | HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }


            }


        });
    }



        public static void main (String[]args){
            JFrame frame = new JFrame("Successful");
            frame.setContentPane(new gui().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        }


}
