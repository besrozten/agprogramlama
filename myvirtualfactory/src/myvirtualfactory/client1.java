package myvirtualfactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class client1 extends  javax.swing.JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton JOİNButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextArea textArea1;
    private JButton CHECKButton;
    private JLabel idlabel;
    String ad,pass,clientid="";
    DataOutputStream dos;
    DataInputStream din;
    DefaultListModel dim;
    static int sayac = 0;
    static Timer myTimer = new Timer();
    static TimerTask gorev1;

    public void printResult(String msg){
        textArea1.setText(textArea1.getText() + "\n"  + msg);
    }


    public client1(){
        add(panel1);
        setSize(400,400);
        setTitle("İş makinasi Client");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JOİNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String mid =textField1.getText();
                    String mAd =textField2.getText();
                    String mTur =comboBox1.getSelectedItem().toString();
                    String mHız =comboBox2.getSelectedItem().toString();
                    String mDurum =comboBox3.getSelectedItem().toString();



                    Socket s=new Socket("localhost",2089);
                    DataInputStream din=new DataInputStream(s.getInputStream());
                    DataOutputStream dout=new DataOutputStream(s.getOutputStream());

                    dout.writeUTF(mid+"-"+mAd+"-"+mTur+"-"+mHız+"-"+mDurum);

                    String i= new DataInputStream(s.getInputStream()).readUTF();

                     if (i.equals("You are already Registered...!!")){
                        JOptionPane.showMessageDialog(panel1,"\"You are already Registered...!!\"");
                    }
                    else {
                        textArea1.setText("");
                        printResult("Sunucuya başarılı şekilde baglanıldı.. \n");
                        printResult("iş emri bekleniyor.. \n");
                    }
                    s.close();

                }catch (Exception ex){ex.printStackTrace();}
            }
        });

        CHECKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String mid =textField1.getText();
                    String mAd =textField2.getText();
                    String mTur =comboBox1.getSelectedItem().toString();
                    String mHız =comboBox2.getSelectedItem().toString();
                    String mDurum =comboBox3.getSelectedItem().toString();

                    int val=Integer.parseInt(mHız);


                    Socket s=new Socket("localhost",2089);
                    DataInputStream din=new DataInputStream(s.getInputStream());
                    DataOutputStream dout=new DataOutputStream(s.getOutputStream());

                    dout.writeUTF(mid+"-"+mAd+"-"+mTur+"-"+mHız+"-"+mDurum);
                    mDurum="BUSY";

                    String i= new DataInputStream(s.getInputStream()).readUTF();

                    if(i.equals("atandı")) {
                        printResult("atama gerçekleştirildi\n");
                        JOptionPane.showMessageDialog(panel1, "\"atama gerçekleştirildi\"");

                        JOİNButton.setEnabled(false);
                        CHECKButton.setEnabled(false);
                        comboBox3.setSelectedIndex(1);
                        comboBox3.setEnabled(false);
                        //dout.writeUTF(mid+"-"+mAd+"-"+mTur+"-"+mHız+"-"+mDurum);
                        TimerTask task = new TimerTask() {
                            public void run() {
                               try{
                                Socket s=new Socket("localhost",2089);
                                DataInputStream din=new DataInputStream(s.getInputStream());
                                DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                   dout.writeUTF(mid+"-"+mAd+"-"+mTur+"-"+mHız+"-"+"EMPTY "+"AGAİN");
                                JOİNButton.setEnabled(true);
                                CHECKButton.setEnabled(true);
                                comboBox3.setEnabled(true);
                                comboBox3.setSelectedIndex(0);}catch (Exception ex){ex.printStackTrace();};

                            }
                        };
                        Timer timer = new Timer("Timer");

                        long delay =val* 1000L;
                        timer.schedule(task, delay);
                         dout.writeUTF(mid+"-"+mAd+"-"+mTur+"-"+mHız+"-"+mDurum);
                        // This method will be executed once the timer is over
                    }

                    //String K= new DataInputStream(s.getInputStream()).readUTF();
                     if(i.equals("hazır")){
                         mDurum="EMPTY";

                    }
                    else{

                        printResult("atama hata\n");

                          }

                            /*else{

                                 // printResult("atama gerçekleşmedi\n");
                                  // DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                  dout.writeUTF("") ;
                              }*/

                    //notifyAll();
                    s.close();
                }catch (Exception ex){ex.printStackTrace();}
            }
        });
    }
    client1(String ad, Socket s){
        this.ad=ad;
        this.pass=pass;
        try {
            dim=new DefaultListModel();
            //list1.setModel(dim);
            // idlabel.setText(ad);
            din=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
            new client1.Read().start();
        }catch (Exception ex){ex.printStackTrace();};
    }

    class Read extends Thread{
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
                    }

                   else{
                        textArea1.append(""+m+"\n");
                    }

                }catch (Exception ex){ex.printStackTrace();};
            }
        }

    }












    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new client1().setVisible(true);
            }
        });

    }
}
