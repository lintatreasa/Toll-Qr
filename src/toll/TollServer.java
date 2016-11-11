/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toll;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bill
 */

public class TollServer extends JFrame implements WindowListener,ActionListener,Runnable{
    private JTextArea serVArea,serVArea2;
    private ScrollPane scrol;
    Container container=getContentPane();
    DefaultTableModel model;
    
    private int SERVERPORT = 5657;
    private ServerSocket serverSocket;
    private Socket client = null;
    private boolean running = false;
    private PrintWriter mOut;
    JTable table =new JTable();
    ResultSet rs;
    String date="1234";
    private JLabel Img=new JLabel();
    private JMenuBar menuBar=new JMenuBar();
                
                JMenu mFile=new JMenu("File");
                JMenu mStatement=new JMenu("Statement");
                
                JMenuItem miniStatement=new JMenuItem("Last Entery");
                JMenuItem todayStatement=new JMenuItem("Today's Entery"); 
                JMenuItem fullStatement=new JMenuItem("List"); 
                JMenuItem exit=new JMenuItem("Exit");
                
                
    
    /**
     * @param args the command line arguments
     */
    
    public TollServer(){
         super(Constants.SERVER_NAME);
         setExtendedState(JFrame.MAXIMIZED_BOTH); 
         setResizable(true);
         setLayout(null);
         
                container.setBackground(Color.WHITE);
                final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - this.getWidth()) / 2;
                final int y = (screenSize.height - this.getHeight()) / 2;
                this.setLocation(x, y);
               // this.setResizable(false);
                serVArea=new JTextArea();
                serVArea2=new JTextArea();
                serVArea2.setSize(200, 200);
                serVArea2.setLocation(550, 0);
                serVArea.setBackground(Color.black);
                serVArea.setCaretColor(Color.red);
                serVArea.setForeground(Color.WHITE);
                scrol=new ScrollPane();
                Img.setSize(300, 300);
                Img.setLocation(520, 200);
                scrol.add(serVArea);
                scrol.setSize(300,500);
                scrol.setLocation(screenSize.width-300, 0);
            
                
                //ithokke ntha
                
                
                mStatement.add(miniStatement);
                mStatement.add(todayStatement);                
                mStatement.add(fullStatement);
                
                mFile.add(exit);               
                menuBar.add(mFile);
                menuBar.add(mStatement);
                
                String[] columnNames = {"Id Tag","Number","Date","Amount","Type","Direction"};
               
                // It creates and displays the table
                
       try {
                   ResultSet t=DB.getData();
                    model=buildTableModel(t,t.getMetaData());
            table.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TollServer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                //JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);
                
                table.addMouseListener(new MouseListener() {

                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                           
                                    }

                                    @Override
                                    public void mousePressed(MouseEvent e) {
                                        //testData();
                                        String[] cNames = {"Number","Date","Amount","Type","Direction","Done"};
                                        //:20160315113349
                                        //serVArea2.setText("Number :"+table.getValueAt(table.getSelectedRow(),0).toString()+
                                        serVArea2.setText("Date :"+dateSeperator(table.getValueAt(table.getSelectedRow(),0).toString())+
                                        "\nAmount :"+table.getValueAt(table.getSelectedRow(),1).toString()+
                                        "\nType :"+table.getValueAt(table.getSelectedRow(),2).toString()+
                                        "\nBooth :"+table.getValueAt(table.getSelectedRow(),3).toString());
                                        
                                        try {
                                               BufferedImage bufImg=ImageIO.read(new File("images/"+table.getValueAt
                                                    (table.getSelectedRow(),0).toString()+".png"));
                                                Img.setIcon(new ImageIcon(new ImageIcon(bufImg).getImage()
                                                        .getScaledInstance(300, 220, Image.SCALE_DEFAULT)));
                                                Img.repaint();
                                                //works even without repaint
                                                }
                                                    catch (IOException ex) {
                                                        try{
                                                        BufferedImage bufImg=ImageIO.read(new File("images/excep.png"));
                                                Img.setIcon(new ImageIcon(new ImageIcon(bufImg).getImage()
                                                        .getScaledInstance(300, 220, Image.SCALE_DEFAULT)));
                                                Img.repaint();}catch(Exception er){
                                                    
                                                }
                                                    System.out.println("Unable to read image file");
                                                }
                                        int a=Img.HEIGHT;
                                        int b=Img.WIDTH;
                                        
                                    }

                                    @Override
                                    public void mouseReleased(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseEntered(MouseEvent e) {

                                    }

                                    @Override
                                    public void mouseExited(MouseEvent e) {

                                    }
                                });
                
                scrollPane.setLocation(10, 0);
                scrollPane.setSize(500,screenSize.height);
                
                
                
                
                setJMenuBar(menuBar);                
                container.add(scrol);
                container.add(serVArea2);
                container.add(scrollPane);
                container.add(Img);
                serVArea.append(Constants.SERVER_INTT);
                serVArea.setEditable(false);
                
                
                Thread thread=new Thread(this);
                thread.start();
            this.addWindowListener(this);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        TollServer server=new TollServer();
    }
    

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    }

    public void testData(){
        serVArea.append("loading");
                System.out.println("loading");
                String back=date;
            
	while(true){          
            if(!DB.numbOf().equals(date.trim())){              
                date=DB.numbOf().trim();
                serVArea.append("\n"+date);
                if(!back.equals("1234")){
                    if(!back.equals(date)){
                            back=date;
                        try {
                        webCam(date);
                    } catch (IOException ex) {
                        Logger.getLogger(TollServer.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  
                    
                    }
                }
                System.out.println(date);               
               try {                    
                   ResultSet t=DB.getData();
                    model=buildTableModel(t,t.getMetaData());
                    table.setModel(model);
                    model.fireTableDataChanged();
          } catch (SQLException ex) {
          Logger.getLogger(TollServer.class.getName()).log(Level.SEVERE, null, ex);
      }  
            }          
        }  	
    }
    @Override
    public void run() {
		serVArea.append("loading");
                System.out.println("loading");
                String back=date;

	while(true){  
            back=date;
            if(!DB.numbOf().equals(date.trim())){              
                date=DB.numbOf().trim();
                serVArea.append("\n"+date);
                if(!back.equals("1234")){
                    if(!back.equals(date)){
                            back=date;
                    try {
                        webCam(date);
                    } catch (IOException ex) {
                        Logger.getLogger(TollServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                }
                System.out.println(date);               
               try {         
                   Thread.sleep(2000);
                   ResultSet t=DB.getData();
                    model=buildTableModel(t,t.getMetaData());
                    table.setModel(model);
                    model.fireTableDataChanged();
          } catch (Exception ex) {
          Logger.getLogger(TollServer.class.getName()).log(Level.SEVERE, null, ex);
      }  
            }          
        }  	
    }
        
    public  DefaultTableModel buildTableModel(ResultSet rs,ResultSetMetaData m)throws SQLException {
             
    String[] cNames = {"Date","Amount","Type","Booth"};
    Vector<String>ColumnNames = new Vector<>(Arrays.asList(cNames));
    ResultSetMetaData metaData = m;//rs.getMetaData();

    // names of columns
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }
    
    // data of the table
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }
    return new DefaultTableModel(data, ColumnNames);
}
    
    public String dateSeperator(String date) {
      String y="",m="",d="",h="",mi="",s="";  
        for(int i=0;i<4;i++){y+=date.charAt(i);}
        for(int i=4;i<6;i++){m+=date.charAt(i);}
        for(int i=6;i<8;i++){d+=date.charAt(i);}
        for(int i=8;i<10;i++){ h+=date.charAt(i);}
        for(int i=10;i<12;i++){mi+=date.charAt(i);}
        for(int i=12;i<14;i++){s+=date.charAt(i);}
        return y+" "+m+" "+d+" "+h+":"+mi+":"+s;
    }
    
    public void webCam(String imgTag) throws IOException{
                       
                Webcam cam = Webcam.getDefault();
                cam.setViewSize(WebcamResolution.VGA.getSize());
                //cam.setViewSize(new Dimension(320,240));
                cam.open();
                BufferedImage image = cam.getImage();
                ImageIO.write(image, "PNG", new File("images/"+imgTag.trim()+".png"));
                cam.close();
    }
    
}
