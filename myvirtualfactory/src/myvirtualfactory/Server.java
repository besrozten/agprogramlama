package myvirtualfactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends  javax.swing.JFrame{
    private JPanel panel1;
    private JButton işleriAtaButton;
    private JButton işATAButton;
    private JTextArea textArea2;
    private JTextArea textArea1;
    private JButton sistemDurumuButton;
    private JTextArea textArea3;
    private JTextField textField1;
    private JButton işEmriAtaButton;
    public int emptymakina=0;
    public String[] makina=new String[10];
    public String[] is=new String[10];
    public ServerSocket ss,ss1;
    public HashMap clientcoll= new HashMap();
    public Scanner input;
    public String temp;
    public String temp1;
    public int islercount=0;
    private static Message myMachineList;
    private static Message myMachineList2;
    public void printResult(String msg){
        textArea2.setText(textArea2.getText() + "\n"  + msg);
    }
    public void printWorks(String[] msg){
        for (int i=0;i<msg.length;i++)
            if(msg[i]!=null)
        textArea2.setText(textArea2.getText() +"\n"  + msg[i]+ " işi alındı");
    }

    public void printMakine(){
        temp="";
        for (int i=0;i<makina.length;i++){
            if(makina[i]!=null)
            temp+="\n"+makina[i];}
    }
    public void printIs(){

        temp1="";
        for (int i=0;i<is.length;i++){
            if(is[i]!=null)
                temp1+="\n"+is[i];}
    }
    public void printEmptyMakine(){

        //textArea2.setText("");

        for (int i=0;i<emptymakina;i++){
            if(makina[i]!=null)
            textArea2.setText(textArea2.getText() +"\n "+(i+1)+"->"+makina[i]+" MAKİNA BOŞ"); }

    }


    public Server(){
        try {

            add(panel1);
            setSize(600, 600);
            setTitle("Server");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ss=new ServerSocket(2089);
            ss1=new ServerSocket(2222);
            this.textArea2.setText("Server Başlatıldı \n");
            myMachineList = new Message();

            new ClientAccept().start();
            new ClientAccept2().start();

           // new ClientWork().start();
        }catch(Exception e){ e.printStackTrace();}

        işATAButton.addActionListener(new ActionListener() {/*iş makinası listeleme*/
            @Override
            public void actionPerformed(ActionEvent e) {
                printEmptyMakine();


            }
        });
        işleriAtaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                new ClientWork().start();
                }catch(Exception ex){ ex.printStackTrace();}

            }
        });

        sistemDurumuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea1.setText("");
                printResult("Sistem durumu");
                //printMakine();
               //printEmptyMakine();
                printWorks(is);
            }
        });
    }
    class ClientWork extends Thread{
        public void run(){
            while(true){
            try {
                String b;
                String isAta =textField1.getText();
                Socket s = ss.accept();

                String data=new DataInputStream(s.getInputStream()).readUTF();
                DataOutputStream dout1= new DataOutputStream(s.getOutputStream());

                DataInputStream din= new DataInputStream(s.getInputStream());

                    String[] msg = data.split("-");
                    //clientcoll.put(data, s);
                if(data!=null && data!=""){

                    if (msg[2].contains(isAta)){
                     dout1.writeUTF("atandı");
                     printResult(msg[1]+"-> atandı");
                     emptymakina--;
                        textField1.setText("");

                }
                 /*if (msg[4].contains("EMPTY")) {
                     //dout1.writeUTF("atandı");
                     printResult(msg[1] + "-> atamaya hazır");
                     makina[emptymakina]=msg[2];
                     emptymakina++;
                     dout1.writeUTF("hazır");
                     textField1.setText("");
                 }*/
                 else { printResult("ATAMADA HATA"); }

                s.close();
                    dout1.writeUTF("");

            }}catch (Exception ex){ex.printStackTrace();}


        }}
    }
    class ClientAccept extends Thread{
        public void run(){
            while(true) {
                try {
                    myVirtualFactory.isEmptyMakine = false;
                    Socket s = ss.accept();


                    String data = new DataInputStream(s.getInputStream()).readUTF();
                    if (clientcoll.containsKey(data)) {
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("You are already Registered...!!");
                    }
                    else {
                        clientcoll.put(data, s);
                        String[] msg = data.split("-");


                            textArea1.append(" " + data + " JOİN \n");
                            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                            dout.writeUTF("login");


                            if (msg[4].compareTo("EMPTY") == 0) {

                                myVirtualFactory.isEmptyMakine = true;
                                printResult("makine " + msg[1] + " suan boşta ");
                                myMachineList.add(msg[2]);
                                makina[emptymakina] = msg[2];
                                emptymakina++;
                                //DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                dout.writeUTF("");

                        }



                    }
                    s.close();



                } catch (Exception e) {
                    e.printStackTrace();
                }
              /* try {

                    Socket s1 = ss1.accept();
                    String data1 = new DataInputStream(s1.getInputStream()).readUTF();
                    DataOutputStream dout = new DataOutputStream(s1.getOutputStream());
                    if(data1.compareTo("göster") == 0){
                        printResult("makineler kullanıcıya gönderildi ");
                        for (int i=0; i<emptymakina;i++){
                            dout.writeUTF(makina[i]);}}
                }catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }

    }

    class ClientAccept2 extends Thread{

        public void run(){

            while(true) {

                try {
                    Socket s = ss1.accept();
                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                    String data = new DataInputStream(s.getInputStream()).readUTF();


                        //dout.writeUTF("veri zaten gönderildi!!");
                    if (clientcoll.containsKey(data)) {

                        dout.writeUTF("veri zaten gönderildi!!");
                    }if (data.contains("göster")) {
                        printResult(" boştaki makinalar gönderiliyor ");
                        DataOutputStream dout1 = new DataOutputStream(s.getOutputStream());

                        printMakine();
                        dout1.writeUTF(temp);
                    }
                    else if (data.contains("işgöste")) {
                        printResult(" boştaki işler gönderiliyor ");
                        DataOutputStream dout1 = new DataOutputStream(s.getOutputStream());

                        printIs();
                        dout1.writeUTF(temp1);
                    }
                    else if(data.contains("CNC")||data.contains("DÖKÜM")||data.contains("KAPLAMA")||data.contains("KILIF")){
                        String isler=data;

                        is[islercount]=isler;
                        islercount++;

                    }
                    else {

                        clientcoll.put(data, s);
                        String[] msg = data.split("-");
                        textArea1.append(" Uygulamacı " + data + " JOİN \n");
                        //DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("login");

                    }


                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    class MsgRead extends Thread{
        Socket s;
        String ID;
            MsgRead(Socket s,String ID){
                this.s=s;
                this.ID=ID;
            }
            public void run(){
                try {
                   /* while(!clientcoll.isEmpty() ){
                        String data= new DataInputStream(s.getInputStream()).readUTF();
                        if (data.equals("mkşngğo89755KLD")){
                            clientcoll.remove(ID);
                            textArea3.append(ID+"removed: \n");
                            new PrepareClientList().start();
                            Set k= clientcoll.keySet();
                            Iterator itr=k.iterator();
                            while(itr.hasNext()) {
                                String key=(String)itr.next();
                                if(!key.equalsIgnoreCase(ID)){
                                    try{
                                        new DataOutputStream(((Socket)clientcoll.get(key)).getOutputStream()).writeUTF("");
                                    }catch (Exception e){
                                        clientcoll.remove(key);
                                        textArea3.append(key+"removed!");
                                        new PrepareClientList().start();

                                    }
                                }
                                else if(data.contains("#@@@43256864@@@")){
                                    data=data.substring(20);
                                    StringTokenizer st=new StringTokenizer(data,":");
                                    String id=st.nextToken();
                                    data=st.nextToken();
                                    try{
                                        new DataOutputStream(((Socket)clientcoll.get(key)).getOutputStream()).writeUTF("&&ID"+data);
                                    }catch (Exception e){
                                        clientcoll.remove(key);
                                        textArea3.append(key+"removed!");
                                        new PrepareClientList().start();

                                    }
                                }

                            }
                        }

                        }*/


                    }catch (Exception e){e.printStackTrace();}
                }
            }

    class PrepareClientList extends Thread{
         public void run(){
             try {
                 String ids="";
                 Set k=clientcoll.keySet();
                 Iterator itr=k.iterator();
                 while (itr.hasNext()){
                     String key=(String)itr.next();
                     ids+=key+",";
                 }
                 if (ids.length()!=0){
                     ids=ids.substring(0,ids.length()-1);
                 }
                 itr=k.iterator();
                 while(itr.hasNext()){
                     String key=(String) itr.next();
                     try{
                         new DataOutputStream(((Socket)clientcoll.get(key)).getOutputStream()).writeUTF("::../"+ids);
                     }catch (Exception e){
                         clientcoll.remove(key);
                         textArea3.append(key+"removed!");


                     }

                 }

             }catch (Exception e){e.printStackTrace();}

         }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Server().setVisible(true);
            }
        });

    }
}
