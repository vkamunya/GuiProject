package GuiProject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.FileWriter;



public class gui extends javax.swing.JFrame{

    private JButton Login;
    private JPanel panel1;
     JTextField username;
     JTextField password;
    private JButton saveButton;

    public gui() {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name= username.getText();
                String pass= password.getText();

                    Connection connection = null;
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
                    JOptionPane.showMessageDialog(null,"Error occured");
                }
                JOptionPane.showMessageDialog(null,"Data Saved");



            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Successful");
        frame.setContentPane(new gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

            }

        }
