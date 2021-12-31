package myvirtualfactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class client2 extends javax.swing.JFrame {
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton makinalarıGösterButton;
    private JButton işleriGösterButton;
    private JTextField textField3;
    private JButton işEkleButton;
    private JList list1;
    String ad,pass,clientid="";
    DataOutputStream dos;
    DataInputStream din;
    DefaultListModel dim;
    public void printResult(String msg){
        textArea1.setText(textArea1.getText() + "\n"  + msg);
    }


    public client2(){
        add(panel1);
        setSize(400,400);
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); }

         client2(String ad , Socket s)
            {
                this.ad=ad;
                this.pass=pass;
                try {
                    add(panel1);
                    setSize(500,500);
                    setTitle("Kullanıcı Girişi");
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    dim=new DefaultListModel();
                    //list1.setModel(dim);
                   // idlabel.setText(ad);
                    //din=new DataInputStream(s.getInputStream());
                    //dos=new DataOutputStream(s.getOutputStream());
                   // new Read().start();
                    printResult("Sunucuya başarılı şekilde baglanıldı.. ");
                    işleriGösterButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {

                                Socket s = new Socket("localhost", 2222);
                                DataInputStream din = new DataInputStream(s.getInputStream());
                                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                                dout.writeUTF("işgöste");
                                String i= new DataInputStream(s.getInputStream()).readUTF();


                                if (i.equals("veri zaten gönderildi!!")){
                                    JOptionPane.showMessageDialog(panel1,"\"veri zaten gönderildi!!\"");
                                }else {
                                    textArea1.setText(" ");

                                    printResult("veriler çekiliyor .. ");
                                    printResult(i);
                                }


                                //clientcoll.put(data, s);
                                //String[] msg = i.split("-");




                            }catch (Exception ex){ex.printStackTrace();}
                        }
                    });
                    işEkleButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try{
                                String işekle=textField3.getText();
                                Socket s = new Socket("localhost", 2222);
                                DataInputStream din = new DataInputStream(s.getInputStream());
                                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                                dout.writeUTF(işekle);
                                textField3.setText("");

                            } catch (Exception ex){ex.printStackTrace();}

                        }
                    });
                    makinalarıGösterButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //printResult("Sunucuya başarılı şekilde baglanıldı.. \n");

                            try {

                                Socket s = new Socket("localhost", 2222);
                                DataInputStream din = new DataInputStream(s.getInputStream());
                                DataOutputStream dout = new DataOutputStream(s.getOutputStream());

                                dout.writeUTF("göster");
                                String i= new DataInputStream(s.getInputStream()).readUTF();

                                if (i.equals("veri zaten gönderildi!!")){
                                    JOptionPane.showMessageDialog(panel1,"\"veri zaten gönderildi!!\"");
                                }else {
                                    textArea1.setText("");

                                    printResult("veriler çekiliyor .. ");
                                    printResult(i);
                                }

                                    //clientcoll.put(data, s);
                                    //String[] msg = i.split("-");




                            }catch (Exception ex){ex.printStackTrace();}
                        }
                    });




                    }catch (Exception ex){ex.printStackTrace();};

            }


       /* class Read extends Thread{
        public void run(){
            while(true){
                try {
                    String m=din.readUTF();
                    if(m.contains(":;,./"))
                    {
                        m=m.substring(6);
                        dim.clear();
                        StringTokenizer st =new StringTokenizer(m,",");
                        while(st.hasMoreTokens()){
                            String u=st.nextToken();
                            if(!ad.equals(u)){
                                dim.addElement(u);
                            }

                        }
                    }else{
                        textArea1.append(""+m+"\n");
                    }

                }catch (Exception ex){ex.printStackTrace();};
            }
        }

        }
*/

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new client2().setVisible(true);
            }
        });

    }
}
