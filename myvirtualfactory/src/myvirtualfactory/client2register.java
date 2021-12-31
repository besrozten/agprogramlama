package myvirtualfactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class client2register extends  javax.swing.JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton girişButton;
    private String ad,pass;
    public  void  setAd(String ad) { this.ad=ad; }
    public String getAd() { return ad; }
    public  void  setPass(String pass) { this.pass=pass; }
    public String getPass() { return pass; }


    public client2register() {

            try {
                add(panel1);
                setSize(400, 400);
                setTitle("giriş");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



            }catch(Exception e){ e.printStackTrace();}

        girişButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                     ad =textField1.getText();
                    pass =textField1.getText();

                    Socket s1=new Socket("localhost",2222);
                    DataInputStream din=new DataInputStream(s1.getInputStream());
                    DataOutputStream dout=new DataOutputStream(s1.getOutputStream());

                    dout.writeUTF(getAd()+"-"+getPass());


                    String i= new DataInputStream(s1.getInputStream()).readUTF();
                    if (i.equals("You are already Registered...!!")){
                        JOptionPane.showMessageDialog(panel1,"\"You are already Registered...!!\"");
                    }
                    else if(i.equals("login"))
                    {JOptionPane.showMessageDialog(panel1,"\"Login...!!\"");
                    //panel1.setVisible(false);
                        //dout.writeUTF("login");
                        new client2(ad,s1).setVisible(true);

                    }else {

                        JOptionPane.showMessageDialog(panel1,"\"Login hatalı...!!\"");}

                }catch (Exception ex){ex.printStackTrace();}

            }
        });
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new client2register().setVisible(true);
            }
        });

    }
}
