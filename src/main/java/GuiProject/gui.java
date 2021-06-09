package GuiProject;
import net.proteanit.sql.DbUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class gui extends javax.swing.JFrame{

    private JButton Login;
    private JPanel panel1;
     JTextField username;
     JTextField password;
    private JButton saveButton;
    private JTable table1;
    PreparedStatement pst = null;
    ResultSet rs = null;



    public gui() {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name= username.getText();
                String pass= password.getText();
                String host = "localhost";
                String port = "5432";
                String db_name = "test";
                String username = "postgres";
                String password = "admin";



                    try {
                        Class.forName("org.postgresql.Driver");
                        Connection conn = DriverManager.getConnection(
                                "jdbc:postgresql://"+host+":"+port+"/"+db_name+"",""+username+"",""+password+"");

                        if (conn != null) {
                            String sql = "insert into test values (' "+name+"',' "+pass+"')";
                            Statement statement= conn.createStatement();
                            int x= statement.executeUpdate(sql);
                            if (x==0){
                                JOptionPane.showMessageDialog(Login,"User Exists");
                            }
                            else {
                                JOptionPane.showMessageDialog(Login,"Login Successful");
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
                obj.put("Username",username.getText());
                obj.put("Password",password.getText());
                jrr.add(obj);
                JOptionPane.showMessageDialog(null,obj);
                try {
                    FileWriter file = new FileWriter("Userdata.json");
                    file.write(jrr.toJSONString());
                    file.close();
                }
                catch (Exception e2){
                    JOptionPane.showMessageDialog(null,"Error occurred");
                }
                JOptionPane.showMessageDialog(null,"Data Saved");

                showTableData();

            }

        });
    }
    public void showTableData(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/test)",""+username+"",""+password+"");
            String sql = "select * from test";
            pst = conn.prepareStatement(sql);
            rs=pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            conn.close();
        }

catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Successful");
        frame.setContentPane(new gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

            }

        }
