/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toll;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author bill
 */
public class User extends JFrame implements WindowListener,ActionListener{
    Container container=getContentPane();
    private JLabel lblName=new JLabel("a");
    private JLabel lblAcc=new JLabel("b");
    private JLabel lblAmt=new JLabel("c");
    private JButton btnQrcode=new JButton("Make QR Tag");
    ArrayList arrayList;
    JFileChooser chooser=new JFileChooser();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new User("user1","1234");
    }
    public User(String usrname,String pasword){
        super("User");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
         setResizable(true);
                setLayout(null);  
                container.setBackground(Color.WHITE);
                final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - this.getWidth()) / 2;
                final int y = (screenSize.height - this.getHeight()) / 2;
                this.setLocation(x, y);
                lblName.setSize(100, 30);
                lblAcc.setSize(200, 30);
                lblAmt.setSize(200, 30);
                btnQrcode.setSize(200, 30);
                
                lblName.setLocation(300, 200);
                lblAcc.setLocation(300, 250);
                lblAmt.setLocation(300, 300);
                btnQrcode.setLocation(600, 300);
                container.add(lblName);
                container.add(lblAcc);
                container.add(lblAmt);
                container.add(btnQrcode);
                arrayList=DB.userData(usrname, pasword);
                lblName.setText("Name  : "+arrayList.get(0).toString());
                lblAcc.setText("Acc no : "+arrayList.get(1).toString());
                lblAmt.setText("Amount : "+arrayList.get(2).toString());
                btnQrcode.addActionListener(this);
                this.addWindowListener(this);
                this.setVisible(true);
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
        if(e.getSource()==btnQrcode){
            chooser.setDialogTitle("Specify a file to save");   
          int returnVal = chooser.showSaveDialog(this);
          
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println(file.getName());
            System.out.println(file.getPath());
             System.out.println("Save as file: " + file.getAbsolutePath()); 
             if(file.getName().endsWith(".png")){
                 try{
                    String filePath = file.getAbsolutePath();
                    String charset = "UTF-8"; // or "ISO-8859-1"
                    Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                    createQRCode("tom", filePath, charset, hintMap, 200, 200);
                    DB.addTag("108", "tom");
                 }catch(Exception f){
                     
                 }
             }else{
                 try{
                    String filePath = file.getAbsolutePath()+".png";
                    String charset = "UTF-8"; // or "ISO-8859-1"
                    Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                    createQRCode("tom", filePath, charset, hintMap, 200, 200);
                    DB.addTag("108", "tom");
                 }catch(Exception f){
                     
                 }
             }
              
             
             
        } else {
          
        }
            
        }
    }
     public static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
                        BitMatrix matrix = new MultiFormatWriter().encode(
                        new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
                        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                        .lastIndexOf('.') + 1), new File(filePath));
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
                        throws FileNotFoundException, IOException, NotFoundException {
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                        new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
                        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,hintMap);
                        return qrCodeResult.getText();
	}
        
        
        
        
}
