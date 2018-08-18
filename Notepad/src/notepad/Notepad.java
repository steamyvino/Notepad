/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author Maciek
 */
public class Notepad {

   private JFrame mainNotepadFrame = new JFrame();
   private JFrame aboutFrame = new JFrame();
   private JFrame searchFrame = new JFrame();
   

   private JPanel menuPanel = new JPanel();
   Action actionSave = new ActionSave("Save","Save your file","ctrl S",new ImageIcon("resources/save.png"));
 
   boolean isUnsaved=false;
   String textProgression="";
   private JMenuBar menuBar = new JMenuBar();
   private JToolBar editPanel = new JToolBar();
   

   public JTextArea textArea =new JTextArea("");
   private JScrollPane textPane = new JScrollPane(textArea);
   
   private JPanel statusBar = new JPanel();
   private JLabel statusLabel = new JLabel("status panel  ");
   
   
   private JComboBox fontSizeList = new JComboBox();
   private JComboBox fontNameList = new JComboBox();
   
   private UndoManager undo = new UndoManager();
   private Document doc = textArea.getDocument();
   
   private UndoAction undoAction = new UndoAction("Undo","Undo your operation","ctrl Z",new ImageIcon("resources/undo.png"));
   private RedoAction redoAction = new RedoAction("Redo","Redo your operation","ctrl Y",new ImageIcon("resources/redo.png"));
   private ActionDelete deleteAction = new ActionDelete("Delete","Delete");
   private ActionSelectAll selectAll = new ActionSelectAll("Select All","ctrl A");
   private ActionCopyCut cutAction = new ActionCopyCut("Cut","ctrl X",new ImageIcon("resources/cut.png"));
   private ActionCopyCut copyAction = new ActionCopyCut("Copy","ctrl C",new ImageIcon("resources/copy.png"));
   private ActionPaste pasteAction = new ActionPaste("Paste","ctrl V",new ImageIcon("resources/paste.png"));
   
   
   private JCheckBox lineWrap = new JCheckBox("Word Wrap");
  
   private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
   private  File config = new File("config.txt");
   private String defaultFontName;
   private int defaultFontSize;
   private File currentFile;
   private boolean fileExist=false;
   
   private JFileChooser fileChooser = new JFileChooser();
   
   
   
   private Color defaultBgColor;
   private Color defaultFontColor;
   
   /**
    * Find and Replace Frame
    */
        JButton cancelSearchBtn = new JButton("Cancel");
        JLabel replaceLabel = new JLabel("Replace with: ");
        JButton replaceSearchBtn = new JButton("Replace");
        JTextField replaceField = new JTextField(10);
        JButton replaceAllBtn = new JButton("Replace All");
        JButton searchBtn = new JButton("Find");
        JLabel searchLabel= new JLabel("Find: ");
        JTextField findField=new JTextField(10);
        
        JTextField findFieldToolbar = new JTextField(10);
  
    Notepad()
    {
        
        checkConfig();
        getConfig();
        initComponents();
        initMenuBar();
        initTextArea();
        initToolbar();
        initRightClickMenu();
        initAboutFrame();
       
    }
    
    Notepad(boolean init)
    {

    }
    
    
    
    public Font getActualFont()
    {
        return textArea.getFont();
    }
    
    void initComponents()
    {

        
       
        mainNotepadFrame.setBounds(300,300,800,600);
        mainNotepadFrame.setDefaultCloseOperation(3);
        mainNotepadFrame.setTitle("Untitled - Notepad");
       
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.setResizable(false);
        
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.setResizable(false);
       
        mainNotepadFrame.addWindowListener(new WindowAdapter() {
        
            
        public void windowClosing(WindowEvent we) 
        {     
            saveConfig();
            closingNotepad();
            
        }
        
        
        });
        
        
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter);

      
       
        
        

        
    }
    
    void checkConfig()
    {
          System.out.println("checking config..."+config.exists()); 
       try {         
           if(!config.exists())
           {   
               System.out.println("CREATING CONFIG FILE...");
               config.createNewFile();
               BufferedWriter configWriter = new BufferedWriter(new FileWriter(config)); 
               configWriter.write("Arial");
               configWriter.newLine();
               configWriter.write("14");
               configWriter.newLine();
               configWriter.write("255");
               configWriter.newLine();
               configWriter.write("255");
               configWriter.newLine();
               configWriter.write("255");
               configWriter.newLine();
               configWriter.write("0");
               configWriter.newLine();
               configWriter.write("0");
               configWriter.newLine();
               configWriter.write("0");
               configWriter.newLine();
               configWriter.close();    
           }
          
           BufferedReader configReader = new BufferedReader(new FileReader(config));
           defaultFontName = configReader.readLine();
           defaultFontSize = Integer.parseInt(configReader.readLine());
                    
       } catch (IOException ex) {
           System.out.println(ex.getMessage());
       }
    }
    
    void getConfig()
    {
    
       try {
           BufferedReader configReader = new BufferedReader(new FileReader(config));
           
           defaultFontName = configReader.readLine();
           defaultFontSize = Integer.parseInt(configReader.readLine());
           defaultBgColor = new Color(Integer.parseInt(configReader.readLine()),Integer.parseInt(configReader.readLine()),Integer.parseInt(configReader.readLine()));
           defaultFontColor = new Color(Integer.parseInt(configReader.readLine()),Integer.parseInt(configReader.readLine()),Integer.parseInt(configReader.readLine()));
       } catch (FileNotFoundException ex) {
           System.out.println(ex.getMessage());
       }catch (IOException ex) {
           System.out.println(ex.getMessage());
       }
    
    }
    
    void saveConfig()
    {
      
        System.out.println("TEST");
              
       try {
           BufferedWriter configWriter = new BufferedWriter(new FileWriter(config));
           configWriter.write(fontNameList.getSelectedItem().toString());
           configWriter.newLine();   
           configWriter.write(fontSizeList.getSelectedItem().toString());
           configWriter.newLine();  
           configWriter.write(Integer.toString(textArea.getBackground().getRed()));
           configWriter.newLine();
           configWriter.write(Integer.toString(textArea.getBackground().getGreen()));
           configWriter.newLine();
           configWriter.write(Integer.toString(textArea.getBackground().getBlue()));
           configWriter.newLine();
           configWriter.write(Integer.toString(textArea.getForeground().getRed()));
           configWriter.newLine();
           configWriter.write(Integer.toString(textArea.getForeground().getGreen()));
           configWriter.newLine();
           configWriter.write(Integer.toString(textArea.getForeground().getBlue()));
           configWriter.close();
       } catch (IOException ex) {
           Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
     
       }
    
    
    }
    
    
    void saveAsFile()
    {
           int confirm=0;
           int result = fileChooser.showSaveDialog(mainNotepadFrame);
           System.out.println(fileChooser.getSelectedFile());
           currentFile = fileChooser.getSelectedFile();
           
           
           if(currentFile.exists()) 
           {  
               if(result==0)
               {
                confirm = JOptionPane.showConfirmDialog(fileChooser,"File "+currentFile.getName()+" already exist. Do you want to overwrite it?","Confirm Save As", 0);
                if(confirm==1);
                    saveFile();
                    fileExist=true;
               }
           }  
           else
           {
               try 
               {
                    System.out.println(fileChooser.getFileFilter());
                    currentFile.createNewFile();
                    saveFile();
                    fileExist=true;
               } 
               catch (IOException ex)
               {
                    System.out.println(ex.getMessage());
               }
           }
           
           mainNotepadFrame.setTitle(currentFile.getName()+" Notepad");
    }
    
    /**
     * test save file method
     */
    void saveFile()
    {
       try {
           
           BufferedWriter textWriter = new BufferedWriter(new FileWriter(currentFile));
           textArea.write(textWriter);
          // textWriter.write(textArea.getText());
           textWriter.close();
       } catch (IOException ex) {
           Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    
    }
    
    
    
    
    void initSearchFrame()
    {
     
        searchFrame.setAlwaysOnTop(true);
        searchBtn.addActionListener(new FindBtnHandler());
        replaceSearchBtn.addActionListener(new ReplaceBtnHandler());
        replaceAllBtn.addActionListener(new ReplaceAllBtnHandler());
        
        GroupLayout layout = new javax.swing.GroupLayout(searchFrame.getContentPane());
        searchFrame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(replaceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(replaceField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(replaceSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(replaceAllBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(searchLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(replaceSearchBtn)
                    .addComponent(replaceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(replaceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(replaceAllBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelSearchBtn)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        searchFrame.pack();
   
        
       cancelSearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               searchFrame.dispose();
            }
        });
       
       
        
    
    }
    
    void initAboutFrame()
    {
        
        aboutFrame.setSize(300,300);
        ImageIcon logo = new ImageIcon("resources/logobig.png");
        JLabel title = new JLabel("Notepad",logo,0);
        title.setFont((new Font("Arial Black",Font.PLAIN,30)));
        JLabel author = new JLabel(" Created by Vino");
        JLabel credits = new JLabel("<html><div> Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> "
                + "from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com"
                + "</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative "
                + "Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div></html>");
        JButton closeButton = new JButton("   Ok   ");
        aboutFrame.getContentPane().setLayout(new GridLayout(4,0));
        aboutFrame.getContentPane().add(title);
        aboutFrame.getContentPane().add(author);
        aboutFrame.getContentPane().add(credits);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               aboutFrame.dispose();
            }
        });
        
        aboutFrame.getContentPane().add(buttonPanel);
        
    
    }
    
    
    
    void openFile(File file)
    {
        currentFile=file;
        fileExist=true;
        mainNotepadFrame.setTitle(currentFile.getName()+" Notepad");
       
       
       try {
           BufferedReader readw = new BufferedReader(new FileReader(file));
           textArea.read(readw, null);
      
       } catch (FileNotFoundException ex) {
           System.out.println(ex.getMessage());
       } catch (IOException ex) {
           System.out.println(ex.getMessage());
       
       }
    }
    
    void initMenuBar()
    {
     
        
        
        mainNotepadFrame.getContentPane().add(menuPanel,BorderLayout.NORTH);
        menuPanel.setLayout(new GridLayout(2,0));
        menuPanel.add(menuBar);
        
        /**
         * Menu
         */
        JMenu menuFile = menuBar.add(new JMenu(" File "));
        JMenuItem menuItemNew = menuFile.add("New");
        JMenuItem menuItemOpen = menuFile.add("Open");
        menuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              fileChooser.showOpenDialog(mainNotepadFrame);
              openFile(fileChooser.getSelectedFile());
              
            }
        });
        
        menuFile.add(actionSave);
        JMenuItem menuItemSaveAs = menuFile.add("SaveAs");
        menuItemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
             saveAsFile();
            }
        });
        menuFile.addSeparator();
      
        JMenuItem menuItemExit = menuFile.add("Exit");
        
        
        JMenu menuEdit = menuBar.add(new JMenu(" Edit "));
        menuEdit.add(undoAction);
        undoAction.setEnabled(false);
        menuEdit.add(redoAction);
        redoAction.setEnabled(false);
        menuEdit.addSeparator();
        menuEdit.add(cutAction);
        cutAction.setEnabled(false);
        menuEdit.add(copyAction);
        copyAction.setEnabled(false);
        menuEdit.add(pasteAction);
        menuEdit.add(deleteAction);
        deleteAction.setEnabled(false);
        menuEdit.addSeparator();
        JMenuItem menuItemReplace = menuEdit.add("Find/Replace");
        menuItemReplace.setIcon(new ImageIcon("resources/find.png"));
        menuItemReplace.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
        menuItemReplace.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
           
            initSearchFrame();
          
            searchFrame.setLocationRelativeTo(mainNotepadFrame);
            searchFrame.setVisible(true);
           
        }
    });
        menuEdit.add(selectAll);
        

        Settings settings = new Settings();
        settings.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent we) 
        {     
            textArea.setBackground(settings.colorBackground);
            textArea.setForeground(settings.colorFont);
            
        }
});
        
        
        JMenu menuTools = menuBar.add(new JMenu(" Tools "));
     
        JMenuItem menItemSettings = menuTools.add("Settings");
        menItemSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              settings.setVisible(true);
              settings.preview.setFont(textArea.getFont());
               
            }
        });
        
        JMenu menuHelp = menuBar.add(new JMenu(" Help "));
        JMenuItem menuItemAbout = menuHelp.add("About");
        menuItemAbout.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            
           
            aboutFrame.setLocationRelativeTo(mainNotepadFrame);
            aboutFrame.setVisible(true);
            
        }
    });
        
        JMenuItem menuItemGitHub = menuHelp.add("GitHub");
        
        
        
        /**
         * Menu Bar behaviors
         */
        
        menuItemExit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
           System.out.println("exiting");
            closingNotepad();
           
        }
    });
        
    
    }
    
    void closingNotepad()
    {
    
        if(!isUnsaved)
           {
              System.exit(0);
           }
           else 
           {
              int saveChoice = JOptionPane.showConfirmDialog(mainNotepadFrame,new String("Do you want to save changes?"));
                if(saveChoice==0)
                {
                   checkIfFileExist();
                   System.exit(0);
                }
                else if(saveChoice==1)
                    System.exit(0);
           }
    
    }
    
  
    
    void initToolbar()
    {
        
        editPanel.setBackground(new Color(238, 238, 238));
        
        /**
         * ToolBar
         */
        menuPanel.add(editPanel);
        menuPanel.setBorder(BorderFactory.createCompoundBorder());
     
        JButton btnSave = new JButton(actionSave);
        btnSave.setText("");
        btnSave.setBorderPainted(false);
        btnSave.setMaximumSize(new Dimension(16,16));
        editPanel.addSeparator();
        editPanel.add(btnSave);
        editPanel.addSeparator();
        editPanel.add(new btnFontStyle(new ImageIcon("resources/bold.png"),Font.BOLD));
        editPanel.add(new btnFontStyle(new ImageIcon("resources/Italic.png"),Font.ITALIC));
       
        
        
        fontSizeList.setBackground(new Color(238, 238, 238));
        fontSizeList.setMinimumSize(new Dimension(40,20));
        fontSizeList.setMaximumSize(new Dimension(40,20));
        
        fontNameList.setBackground(new Color(238, 238, 238));
        fontNameList.setMinimumSize(new Dimension(170,20));
        fontNameList.setMaximumSize(new Dimension(170,20));
        
        editPanel.addSeparator();
       
       
        
        editPanel.add(fontSizeList);
        int[] fontSize ={8,9,10,11,12,14,16,18,20,22,26,28,38,48,72};
        for (int size : fontSize) {
                   fontSizeList.addItem(size);
               }
       
        fontSizeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int fontSize= (int)((JComboBox)ae.getSource()).getSelectedItem();
                textArea.setFont(new Font(textArea.getFont().getName(),textArea.getFont().getStyle(),fontSize));
                
            }
        });
          
        editPanel.addSeparator();
       
       String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
       for (String name : fontNames) {
           fontNameList.addItem(name);
       }
       
       fontNameList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               
               String fontName = (String)((JComboBox)ae.getSource()).getSelectedItem();
               textArea.setFont(new Font(fontName,textArea.getFont().getStyle(),textArea.getFont().getSize()));
               System.out.println("font :"+textArea.getFont().getFontName());
               System.out.println("font :"+textArea.getFont().getStyle());
            }
        });
        
        fontSizeList.setSelectedItem(defaultFontSize);
        fontNameList.setSelectedItem(defaultFontName);
        editPanel.add(fontNameList);
        editPanel.addSeparator();
        editPanel.add(lineWrap);
        lineWrap.setFocusPainted(false);
        lineWrap.setFont(new Font("Dialog",Font.BOLD,11));
        lineWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                   textArea.setLineWrap(((JCheckBox)ae.getSource()).isSelected());
            }
        });
        
        editPanel.addSeparator();
        System.out.println(lineWrap.getFont().toString());
        searchLabel.setFont(new Font("Dialog",Font.BOLD,12));
        System.out.println(searchLabel.getFont().toString());
       
        JLabel searchLabelToolbar = new JLabel("Find: ");
        
        JButton searchBtnToolbar = new JButton("Find");
        editPanel.add(searchLabelToolbar);
       
        editPanel.add(findFieldToolbar);
          

        editPanel.add(searchBtnToolbar);
        searchBtnToolbar.addActionListener(new FindBtnHandler());
        editPanel.addSeparator();
        
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setLayout(new BorderLayout());
        statusBar.add(statusLabel,BorderLayout.LINE_END);
        mainNotepadFrame.getContentPane().add(statusBar,BorderLayout.SOUTH);
 
    }

    void initTextArea()
    {
       
        
        doc.addUndoableEditListener(new MyUndoableEditListener());
        
      
                
        Font defaultFont = new Font("Calibri",Font.PLAIN,14);
        
        textArea.setFont(defaultFont);
        textArea.setBackground(defaultBgColor);
        textArea.setForeground(defaultFontColor);

        mainNotepadFrame.getContentPane().add(textPane,BorderLayout.CENTER);
      
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
               if(textArea.getSelectedText()!=null)
               {
                    deleteAction.setEnabled(true);
                    cutAction.setEnabled(true);
                    copyAction.setEnabled(true);
               }
               else
               {
                   deleteAction.setEnabled(false);
                   cutAction.setEnabled(false);
                   copyAction.setEnabled(false);
               }
            }
        });
        textArea.addKeyListener(new KeyAdapter() 
        {
            public void keyTyped(KeyEvent ke) 
            {
              
                
                
                if (!(textArea.getText()+ke.getKeyChar()).equals(textProgression)&&isUnsaved==false&&isAscii(ke.getKeyChar())&&!ke.isControlDown())
                {                
                 textProgression=textArea.getText();
                 actionSave.setEnabled(isUnsaved=true);
                  }
               
            }
            
            public boolean isAscii(char chr)
            {
            
               for(int i=0;i<255;i++)
                   if(chr==i)
                       return true;
               return false;
            
            
            }
        });

        
    }
    
     class MyUndoableEditListener implements UndoableEditListener 
     { 
        public void undoableEditHappened(UndoableEditEvent e) 
        {
        //Remember the edit and update the menus
        undo.addEdit(e.getEdit());
        undoAction.updateUndoState();
        redoAction.updateRedoState();
        }
      
    }
    
     
    
        private class ActionSave extends AbstractAction
        {
            
            public ActionSave(String name, String description, String acceleratorKey,Icon icon)
            {
                this.putValue(Action.NAME, name);
                this.putValue(Action.SHORT_DESCRIPTION, description);
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));
                this.putValue(Action.SMALL_ICON, icon);
            }
            
            
            @Override
            public void actionPerformed(ActionEvent ae)
            {
              
               checkIfFileExist();
                  
            }
            
           
        }      
        
        void checkIfFileExist()
        {
             if(!fileExist)
                {
                   actionSave.setEnabled(isUnsaved=false);
                   saveAsFile(); 
                   
                   
                }
                else
                {
                    saveFile();
                    actionSave.setEnabled(isUnsaved=false);
                    System.out.println("Saving");
                    System.out.println(isUnsaved);
                }
        
        }
        
         class UndoAction extends AbstractAction
        {
            
            public UndoAction(String name, String description, String acceleratorKey,Icon icon)
            {
                this.putValue(Action.NAME, name);
                this.putValue(Action.SHORT_DESCRIPTION, description);
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));
                this.putValue(Action.SMALL_ICON, icon);
            }
            
            
            @Override
            public void actionPerformed(ActionEvent ae)
            {

               try 
                {
                    
                    undo.undo();
                } catch (CannotUndoException ex) {
                    System.out.println("Unable to undo: " + ex);
                    ex.printStackTrace();
                }
                updateUndoState();
                redoAction.updateRedoState();
            }   
                public void updateUndoState()
                {
                    if (undo.canUndo())
                    {
                      setEnabled(true);
                      //putValue(Action.NAME, undo.getUndoPresentationName());   <-- adittion
                    }
                    else
                    {
                      setEnabled(false);
                    //  putValue(Action.NAME, "Undo");
                    }
                }

        }
    
        
        class RedoAction extends AbstractAction
        {
            
            public RedoAction(String name, String description, String acceleratorKey,Icon icon)
            {
                this.putValue(Action.NAME, name);
                this.putValue(Action.SHORT_DESCRIPTION, description);
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));
                this.putValue(Action.SMALL_ICON, icon);
            }
            
         
            
            @Override
            public void actionPerformed(ActionEvent ae)
            {

                try {
                    undo.redo();
                } catch (CannotRedoException ex) {
                    System.out.println("Unable to redo: " + ex);
                    ex.printStackTrace();
                }
                updateRedoState();
                undoAction.updateUndoState();    
                  
            }
            
            public void updateRedoState()
            {
                if (undo.canRedo())
                {
                    setEnabled(true);
                }
                else
                    setEnabled(false);
            
            }
            
        }

        private class ActionSelectAll extends AbstractAction
        {
            
            public ActionSelectAll(String name,String acceleratorKey)
            {
                this.putValue(Action.NAME, name);         
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));      
            }
            
            
            @Override
            public void actionPerformed(ActionEvent ae)
            {
               
                textArea.selectAll();
                  
            }
            
           
        }
        
        
        private class ActionDelete extends AbstractAction
        {
            
            public ActionDelete(String name,String acceleratorKey)
            {
                this.putValue(Action.NAME, name);         
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));      
            }
            
            
            @Override
            public void actionPerformed(ActionEvent ae)
            {

               textArea.replaceSelection("");
               this.setEnabled(false);
                  
            }
            
           
        }
        
        private class ActionCopyCut extends AbstractAction
        {
            
            public ActionCopyCut(String name,String acceleratorKey, Icon icon)
            {
                this.putValue(Action.NAME, name);         
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));     
                this.putValue(Action.SMALL_ICON, icon);
            }
                      
            @Override
            public void actionPerformed(ActionEvent ae)
            {
               
               StringSelection newSelection = new StringSelection(textArea.getSelectedText());
               clipboard.setContents(newSelection,null); 
               this.setEnabled(false);
               if(this.getValue(NAME).equals("Cut"))
                   textArea.replaceSelection("");
                              
            }
 
        }
        
        private class ActionPaste extends AbstractAction
        {
            
            public ActionPaste(String name,String acceleratorKey, Icon icon)
            {
                this.putValue(Action.NAME, name);         
                this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey));     
                this.putValue(Action.SMALL_ICON, icon);
            }
                      
            @Override
            public void actionPerformed(ActionEvent ae)
            {
               
                textArea.paste();
                              
            }
 
        }
             
        class btnFontStyle extends JButton
        {
            int fontStyle;
            boolean isStyleSet=false;
            btnFontStyle thisBtn = this;
            
            btnFontStyle(Icon icon, int fontStyle)
            {               
                this.setMaximumSize(new Dimension(20,20));
                this.setFocusPainted(false);
                this.fontStyle=fontStyle;
                this.setIcon(icon);
                thisBtn.setBorderPainted(isStyleSet);
                this.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        thisBtn.setBackground(Color.LIGHT_GRAY);
                        int activeTextStyle = textArea.getFont().getStyle();
                        thisBtn.setBorderPainted(isStyleSet);
                        if(isStyleSet)
                        {     
                             textArea.requestFocus();
                             textArea.setFont(new Font(textArea.getFont().getName(),activeTextStyle^fontStyle,textArea.getFont().getSize()));
                             thisBtn.setBackground(null);
                             thisBtn.setBorderPainted(isStyleSet=false);
                             System.out.println("zaznaczamy");
                             System.out.println("font name :"+textArea.getFont().getFontName());
                           System.out.println("font style :"+textArea.getFont().getStyle());
                            
                        }
                        else
                        {
                            textArea.requestFocus();
                            textArea.setFont(new Font(textArea.getFont().getName(),activeTextStyle^fontStyle,textArea.getFont().getSize()));
                            thisBtn.setBorderPainted(isStyleSet=true);
                            System.out.println("odznaczamy");
                            System.out.println("font name :"+textArea.getFont().getFontName());
                            System.out.println("font style :"+textArea.getFont().getStyle());
                        }
                        
                      
                        
                    }
                });
            }
            
            
            
            
        
        }

  
        void initRightClickMenu()
        {

            JPopupMenu rightClickMenu = new JPopupMenu();
            rightClickMenu.add(undoAction);
            rightClickMenu.add(redoAction);
            rightClickMenu.add(selectAll);
            rightClickMenu.add(cutAction);
            rightClickMenu.add(copyAction);
            rightClickMenu.add(pasteAction);
            rightClickMenu.add(deleteAction);
            
            
            textArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if(me.isPopupTrigger())
                        rightClickMenu.show(textArea, me.getX(),me.getY());
                    if(me.getButton()==MouseEvent.BUTTON1)
                        rightClickMenu.setVisible(false);
                }
            
            });      
            
          
       
            
            
            
        }
        
      class FindBtnHandler implements ActionListener
    {
       private int selectionStart=0;
       
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(!searchFrame.isVisible())
            {
            
                findField.setText(findFieldToolbar.getText());
                
            }
           
            String textToFind=findField.getText();
            
            selectionStart=textArea.getText().indexOf(textToFind, selectionStart+textToFind.length());
            System.out.println(textArea.getText().indexOf(textToFind, selectionStart+textToFind.length()));
            
           if (selectionStart == -1)         
               
              selectionStart = textArea.getText().indexOf(textToFind);
           
           if (selectionStart >= 0)
           { 
                textArea.requestFocus();   
                textArea.select(selectionStart,selectionStart+textToFind.length());          
           }
            
        }

    }
    
    class ReplaceBtnHandler implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {   searchFrame.requestFocus();
            if(textArea.getSelectedText()!=null)
            textArea.replaceSelection(replaceField.getText());
        }
       

    }
    
    
    class ReplaceAllBtnHandler implements ActionListener
    {
        
        private int selectionStart=0;

        @Override
        public void actionPerformed(ActionEvent e) {
            String textToReplace=findField.getText();
            String replacementText=replaceField.getText();
            
            if(!textToReplace.isEmpty())
            {
                    System.out.println("niepusty");
                while(textArea.getText().contains(textToReplace))
                {
                    selectionStart=textArea.getText().indexOf(textToReplace, selectionStart+textToReplace.length());                 
                    if(selectionStart==-1)
                    {
                        selectionStart=0;    
                    }
                    textArea.select(selectionStart,selectionStart+textToReplace.length());
                    System.out.println(selectionStart);
                    System.out.println(textArea.getSelectedText());
                    textArea.replaceSelection(replacementText);
                    textArea.select(0, 0);
                }   
            }
        }
       

    }    
        
  
    public static void main(String[] args) {
      
        new Notepad().mainNotepadFrame.setVisible(true);
        
    }
    
}