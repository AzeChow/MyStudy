package com.bestway.dzsc.client.innermerge;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
public class Chat extends Frame
{
        List lst=new List(6);
        TextField tfIP=new TextField(15);
        TextField tfData=new TextField(60);
        DatagramSocket ds=null;
        public Chat()
        {
                try
                {
                    ds=new DatagramSocket(3000);
                }
                catch(Exception e)
                {
                       e.printStackTrace();
                }
                this.add(lst,"Center");
                Panel p=new Panel();
                this.add(p,"South");
                p.setLayout(new BorderLayout());
                p.add(tfIP,"West");
                p.add(tfData,"East");
                new Thread(new Runnable()
                {
                    public void run()
                    {
                            byte buf[]=new byte[1024];
                            DatagramPacket dp=new DatagramPacket(buf,1024);
                            while(true)
                            {
                                   try
                                   {
                                       ds.receive(dp);
                                       lst.add(new String(/*dp.getData()*/buf,0,dp.getLength())+
                                       "  from "+dp.getAddress().getHostAddress()+":"+dp.getPort(),0);
                                   }
                                   catch(Exception e)
                                   {
                                         if(!ds.isClosed())
                                         {
                                             e.printStackTrace();
                                         }
                                   }
                            }
                    }
               }).start();
               tfData.addActionListener(new ActionListener()
               {
                       public void actionPerformed(ActionEvent e)
                       {
                               byte [] buf;
                               buf=tfData.getText().getBytes();
                               try
                               {
                                   DatagramPacket dp=new DatagramPacket(buf,buf.length,
                                   InetAddress.getByName(tfIP.getText()),3000);
                                   ds.send(dp);
                               }
                               catch(Exception ex)
                               {
                                     //ex.printStackTrace();
                               }
                               tfData.setText("");
                        }
                       });
                       addWindowListener(new WindowAdapter()
                       {
                               public void windowClosing(WindowEvent e)
                               {
                                       ds.close();
                                       //dispose();
                                       System.exit(0);
                               }
               });
    }
    public static void main(String args[])
    {
            System.out.println("String Chat....");
            Chat mainFrame=new Chat();
            mainFrame.setSize(300,400);
            mainFrame.setTitle("Chat");
            mainFrame.setVisible(true);
            mainFrame.setResizable(false);
    }
}
