/*
*   Copyright 2022. Eduardo Programador
*   www.eduardoprogramador.com
*   consultoria@eduardoprogramador.com
*
*   All Rights Reserved
* */

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Layout extends JFrame implements CastOperations {
    private Tasks tasks;
    private JLabel jLabelStatus;
    private String linkIn;

    public Layout() {
        super("JavaTube v1.0");

        tasks = new Tasks();

        SwingUtilities.invokeLater(() ->{

            JLabel jLabelTitle = new JLabel("JavaTube");
            jLabelTitle.setFont(new Font("calibri",Font.BOLD,30));
            jLabelTitle.setBounds(150,20,200,30);

            JLabel jLabelPref = new JLabel("Download Type:");
            jLabelPref.setFont(new Font("calibri",Font.PLAIN,17));
            jLabelPref.setBounds(70,70,200,30);

            JComboBox jComboBox = new JComboBox();
            jComboBox.addItem("Mp4");
            jComboBox.addItem("Mp3");
            jComboBox.setBounds(190,70,100,30);

            JLabel jLabelLink = new JLabel("Link of Youtube video:");
            jLabelLink.setFont(new Font("calibri",Font.PLAIN,17));
            jLabelLink.setBounds(130,110,300,30);

            JTextField fieldLink = new JTextField();
            fieldLink.setBounds(50,140,300,30);

            JButton jButtonPaste = new JButton("Paste");
            jButtonPaste.setBounds(80,180,100,30);

            jLabelStatus = new JLabel("[*] Status");
            jLabelStatus.setVisible(false);
            jLabelStatus.setBounds(170,215,100,20);

            JButton jButtonStart = new JButton("Download");
            jButtonStart.setBounds(220,180,100,30);

            JLabel jLabelCopyright = new JLabel("Copyright 2022. Eduardo Programador");
            jLabelCopyright.setFont(new Font("calibri",Font.PLAIN,15));
            jLabelCopyright.setBounds(80,250,250,20);

            String pathImg = "https://eduardoprogramador.com/img/icos/javatube_logo.jpg";
            String pathBack = "https://eduardoprogramador.com/img/icos/javatube_logo.jpg";
            Toolkit toolkit = Toolkit.getDefaultToolkit();

            JLabel jLabelBackground = null;

            try {
                Image image = toolkit.createImage(new URL(pathImg));
                Image imageB = toolkit.createImage(new URL(pathBack));
                setIconImage(image);

                jLabelBackground = new JLabel(new ImageIcon(imageB));
                jLabelBackground.setBounds(0,0,500,250);

            } catch (Exception ex){}

            Container container = getContentPane();
            container.setLayout(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(0,0,400,300);
            setResizable(false);
            setLocationRelativeTo(null);

            container.add(jLabelTitle);
            container.add(jLabelPref);
            container.add(jComboBox);
            container.add(jLabelLink);
            container.add(fieldLink);
            container.add(jButtonPaste);
            container.add(jLabelStatus);
            container.add(jButtonStart);
            container.add(jLabelCopyright);

            if(jLabelBackground != null)
                container.add(jLabelBackground);

            setVisible(true);

            jButtonStart.addActionListener(e -> {

                String format = (jComboBox.getSelectedIndex() == 0) ? "mp4" : "mp3";
                linkIn = fieldLink.getText();

                jLabelStatus.setText("[*] Wait");
                jLabelStatus.setVisible(true);

                tasks.setDefaults(linkIn,format);
                tasks.setOnCastListener(this);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tasks.processRequest();
                    }
                }).start();

            });

            jButtonPaste.addActionListener(e -> {

                String pasted = null;
                if((pasted= tasks.pasteDate()) != null) {
                    fieldLink.setText(pasted);
                }
            });

        });
    }

    @Override
    public void onDone() {
        jLabelStatus.setText("[*] Done");
    }

    @Override
    public void onFailed() {
        jLabelStatus.setText("[*] Failed");
    }
}
