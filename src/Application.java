

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.awt.Toolkit;

public class Application {

	private JFrame frame;
	private JPanel panel_1;
	private GridBagLayout gbl_panel_1;
    private JButton encryptBtn;
    private GridBagConstraints gbc_encryptBtn;
    private JTextField dataTxt;
    private JTextField keyTxt;
    private JButton decryptBtn;
    private JRadioButton is_DES;
    private  JRadioButton is_AES;
    private  JRadioButton is_OTP;
    private JTextArea result;
    private static final String SALT = "ThisIsSalt";

    private static final String algo = "DESede";
    private KeySpec keyspec;
    private SecretKeyFactory skf;
    private static Cipher cipher;
    private byte[] Tkeybytes;
    private String TencryptionAlgo, encryptedDate, decryptedDate, tsk;
    private SecretKey Tsecretkey;
	private static OTP otp;
	private String cipherText;
	private String plainText;
	private static AES aes;
	private static TripleDES tripleDes;
	public Application() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ok\\Desktop\\TedySolomon\\3rd year 2nd semister\\Programming\\JAVA\\Documents\\CNS_enc\\src\\image2.jpg"));
		panel_1=new JPanel();
		gbl_panel_1 = new GridBagLayout();
	    dataTxt=new JTextField();
	    is_DES = new JRadioButton("3DES");
	    is_AES = new JRadioButton(" AES");
	    keyTxt=new JTextField();
	    is_OTP = new JRadioButton(" OTP");
	    result=new JTextArea();
		Handler handler=new Handler();
		frame.setForeground(Color.WHITE);
		frame.setTitle("Data Encryption");
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gbl_panel_1.columnWidths = new int[] {340, 0, 0};
		gbl_panel_1.rowHeights = new int[] {30, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		panel_1.setBackground(Color.GRAY);
		frame.getContentPane().add(panel_1,BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);

		dataTxt.setColumns(30);
		GridBagConstraints gbc_dataTxt = new GridBagConstraints();
		gbc_dataTxt.insets = new Insets(0, 0, 5, 5);
		gbc_dataTxt.gridx = 1;
		gbc_dataTxt.gridy = 1;
		panel_1.add(dataTxt, gbc_dataTxt);
			
		
		GridBagConstraints gbc_is_AES = new GridBagConstraints();
		gbc_is_AES.insets = new Insets(0, 0, 5, 0);
		gbc_is_AES.gridx = 5;
		gbc_is_AES.gridy = 1;
		panel_1.add(is_AES, gbc_is_AES);
		
		
		GridBagConstraints gbc_is_DES = new GridBagConstraints();
		gbc_is_DES.insets = new Insets(0, 0, 5, 0);
		gbc_is_DES.gridx = 5;
		gbc_is_DES.gridy = 2;
		panel_1.add(is_DES, gbc_is_DES);
		
		JLabel lblNewLabel_1 = new JLabel("Key");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
	
		keyTxt.setColumns(30);
		GridBagConstraints gbc_keyTxt = new GridBagConstraints();
		gbc_keyTxt.insets = new Insets(0, 0, 5, 5);
		gbc_keyTxt.gridx = 1;
		gbc_keyTxt.gridy = 3;
		panel_1.add(keyTxt, gbc_keyTxt);
		GridBagConstraints gbc_is_OTP = new GridBagConstraints();
		gbc_is_OTP.insets = new Insets(0, 0, 5, 0);
		gbc_is_OTP.gridx = 5;
		gbc_is_OTP.gridy = 3;
		panel_1.add(is_OTP, gbc_is_OTP);
		
		JLabel lblNewLabel_2 = new JLabel("Result");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 5;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		result.setRows(5);
		result.setColumns(30);
		GridBagConstraints gbc_result = new GridBagConstraints();
		gbc_result.insets = new Insets(0, 0, 5, 5);
		gbc_result.gridx = 1;
		gbc_result.gridy = 5;
		panel_1.add(result, gbc_result);
		decryptBtn = new JButton("Decrypt Message");
		
	    decryptBtn.addActionListener(handler);
	    decryptBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
	    decryptBtn.setBounds(100,100,100,50);
	    GridBagConstraints gbc_decryptBtn = new GridBagConstraints();
	    gbc_decryptBtn.anchor = GridBagConstraints.NORTHEAST;
	    gbc_decryptBtn.insets = new Insets(0, 0, 0, 5);
	    gbc_decryptBtn.gridx = 0;
	    gbc_decryptBtn.gridy = 15;
	    panel_1.add(decryptBtn, gbc_decryptBtn);
	    encryptBtn = new JButton("Encrypt Message");
	    gbc_encryptBtn = new GridBagConstraints();
	    gbc_encryptBtn.insets = new Insets(0, 0, 0, 5);
	    
	    encryptBtn.setForeground(Color.BLACK);
	    encryptBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
	    
	    gbc_encryptBtn.anchor = GridBagConstraints.NORTHWEST;
	    gbc_encryptBtn.gridx = 3;
	    gbc_encryptBtn.gridy = 15;
	    encryptBtn.addActionListener(handler);
	    panel_1.add(encryptBtn, gbc_encryptBtn);
		frame.setVisible(true);
	}

    
    void onActionEncrypt() throws Exception {
        if (keyTxt.getText().trim().isEmpty() && dataTxt.getText().trim().isEmpty()) {
            System.out.println("Didnt work");
        } else if (keyTxt.getText().trim().isEmpty()) {
            System.out.println("key is empty");
        } else if (dataTxt.getText().trim().isEmpty()) {
            System.out.println("Data is empty");
        } else {
            if (is_AES.isSelected()) {
                result.setText(aes.encrypt(dataTxt.getText(), keyTxt.getText()));
            } else if (is_DES.isSelected()) {
                if (keyTxt.getText().length() <16) {
                    System.out.println("Wrong Key size");
                } else {
                    result.setText(tripleDes.encrypt(dataTxt.getText(), keyTxt.getText()));
                }
            }

            else if(is_OTP.isSelected()) {
            	    
            		result.setText(otp.encrypt(dataTxt.getText(),keyTxt.getText()));
            }
            else {
                System.out.println("Please select an encryption type");
            }
        }
    }
    void onActionDecrypt() throws Exception{
        if (keyTxt.getText().trim().isEmpty() && dataTxt.getText().trim().isEmpty()) {
            System.out.println("Didnt work");
        } else if (keyTxt.getText().trim().isEmpty()) {
            System.out.println("key is empty");
        } else if (dataTxt.getText().trim().isEmpty()) {
            System.out.println("Data is empty");
        } else {
            if (is_AES.isSelected()) {
                result.setText(aes.decrypt(dataTxt.getText(), keyTxt.getText()));
            } else if (is_DES.isSelected()) {
                if (keyTxt.getText().length() < 16) {
                    System.out.println("Wrong Key size");
                } else {
                    result.setText(tripleDes.decrypt(dataTxt.getText(), keyTxt.getText()));
                }
            } 
            
            else if(is_OTP.isSelected()) {
            	
            		result.setText(otp.decrypt(dataTxt.getText(),keyTxt.getText()));
            	
            }
            else {
                System.out.println("Please select an encryption type");
            }
        }
    }
	
    public class Handler implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e) {
    		try {
    			if(e.getSource()==decryptBtn) {
        			onActionDecrypt();
        		}
        		else if(e.getSource()==encryptBtn) {
        			onActionEncrypt();
        		}
    		}
    		catch(Exception err) {
    			System.out.print(err);
    		}
    	}
    }
    
  
}
