package GuiProject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class gui {

    private JButton Login;
    private JPanel panel1;
    private JTextField username;
    private JTextField password;

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




        }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Successful");
        frame.setContentPane(new gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }


}