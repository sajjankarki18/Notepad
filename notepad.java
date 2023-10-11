import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class notepad extends JFrame implements ActionListener {
    JTextArea textarea;
    JScrollPane scrollPane;
    JMenuBar menubar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem newWindowItem;
    JMenu editMenu;
    JMenuItem cutItem;
    JMenuItem copyItem;
    JMenuItem pasteItem;
    JMenuItem selectAllItem;
    JMenu viewMenu;
    JMenuItem zoomInMenu;
    JMenuItem zoomOutMenu;
    JPanel panel;
    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton colorButton;
    JComboBox fontBox;

    notepad(){
        ImageIcon img = new ImageIcon("NP.jpeg");

        this.setSize(1200,600);
        this.setTitle("NotePad");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setIconImage(img.getImage());

        textarea = new JTextArea();
        textarea.setPreferredSize(new Dimension(500,500));
        textarea.setLineWrap(true);
        textarea.setFont(new Font("SansSerif", Font.PLAIN, 20));

        scrollPane = new JScrollPane(textarea);
        scrollPane.setPreferredSize(new Dimension(500,500));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        menubar = new JMenuBar();

        fileMenu = new JMenu();
        fileMenu.setText("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setText("<html><u>F</u>ile</html>");

        editMenu = new JMenu();
        editMenu.setText("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.setText("<html><u>E</u>dit</html>");

        viewMenu = new JMenu();
        viewMenu.setText("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.setText("<html><u>V</u>iew</html>");

        openItem = new JMenuItem();
        openItem.setText("Open");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.addActionListener(this);

        saveItem = new JMenuItem();
        saveItem.setText("Save");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.addActionListener(this);

        exitItem = new JMenuItem();
        exitItem.setText("Exit");
        exitItem.setMnemonic(KeyEvent.VK_E);
        exitItem.addActionListener(this);

        newWindowItem = new JMenuItem();
        newWindowItem.setText("New Window");
        newWindowItem.addActionListener(this);
        newWindowItem.setMnemonic(KeyEvent.VK_N);

        cutItem = new JMenuItem();
        cutItem.setText("Cut");
        cutItem.setMnemonic(KeyEvent.VK_X);
        cutItem.addActionListener(this);

        copyItem = new JMenuItem();
        copyItem.setText("Copy");
        copyItem.setMnemonic(KeyEvent.VK_C);
        copyItem.addActionListener(this);

        pasteItem = new JMenuItem();
        pasteItem.setText("Paste");
        pasteItem.setMnemonic(KeyEvent.VK_V);
        pasteItem.addActionListener(this);

        selectAllItem = new JMenuItem();
        selectAllItem.setText("Select All");
        selectAllItem.addActionListener(this);
        selectAllItem.setMnemonic(KeyEvent.VK_S);

        zoomInMenu = new JMenuItem();
        zoomInMenu.setText("Zoom in");
        zoomInMenu.addActionListener(this);

        zoomOutMenu = new JMenuItem();
        zoomOutMenu.setText("Zoom out");
        zoomOutMenu.addActionListener(this);

        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(viewMenu);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        fileMenu.add(newWindowItem);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(selectAllItem);

        viewMenu.add(zoomInMenu);
        viewMenu.add(zoomOutMenu);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(500,45));
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));

        fontLabel = new JLabel();
        fontLabel.setText("Font");
        fontLabel.setFont(new Font("Monospaced", Font.BOLD, 15));

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.setPreferredSize(new Dimension(75,35));
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textarea.setFont(new Font(textarea.getFont().getFamily(),Font.PLAIN, (Integer) fontSizeSpinner.getValue()));
            }
        });

        colorButton = new JButton();
        colorButton.setText("Color");
        colorButton.setFocusable(false);
        colorButton.addActionListener(this);

        String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);

        panel.add(fontLabel);
        panel.add(fontSizeSpinner);
        panel.add(colorButton);
        panel.add(fontBox);

        this.add(scrollPane);
        this.setJMenuBar(menubar);
        this.add(panel, BorderLayout.NORTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==colorButton){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a Color", Color.BLACK);
            textarea.setForeground(color);
        }

        if(e.getSource()==fontBox){
            textarea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textarea.getFont().getSize()));
        }

        if(e.getSource()==openItem){
            JFileChooser filechooser = new JFileChooser();

            int response = filechooser.showOpenDialog(null);

            if(response==JFileChooser.APPROVE_OPTION){
                File file = new File(filechooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try{
                    fileIn = new Scanner(file);
                    if(file.isFile()){
                        while (fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textarea.append(line);
                        }
                    }
                }catch(Exception ex){
                    System.out.println(ex);
                }
                finally {
                    fileIn.close();
                }
            }
        }
        if(e.getSource()==saveItem){
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showSaveDialog(null);

            if(response==JFileChooser.APPROVE_OPTION){
                File file = file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter fileOut = null;

                try{
                    fileOut = new PrintWriter(file);
                    fileOut.println(textarea.getText());
                }catch (Exception r){
                    System.out.println(r);
                }
                finally {
                    fileOut.close();
                }
            }
        }
        if(e.getSource()==newWindowItem){
            new newWindow();
        }
        if(e.getSource()==exitItem){
            System.exit(0);
        }
        //Mechanisms for Cut, Copy and Paste
        if (e.getSource()==selectAllItem) {
            textarea.selectAll();
        } else if (e.getSource()==copyItem) {
            textarea.copy();
        }else if(e.getSource()==pasteItem){
            textarea.paste();
        } else if ((e.getSource()==cutItem)) {
            textarea.cut();
        }

        //For zoom in and zoom out mechanisms
        if(e.getSource()==zoomInMenu){
            zoomIn();
        }else if(e.getSource()==zoomOutMenu){
            zoomOut();
        }
    }
    public void zoomIn(){
        Font currFont = textarea.getFont();
        float newSize = currFont.getSize()+2;
        textarea.setFont(currFont.deriveFont(newSize));
    }
    public void zoomOut(){
        Font currFont = textarea.getFont();
        float newSize = currFont.getSize()-2;
        textarea.setFont(currFont.deriveFont(newSize));
    }
}
