/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Maciek
 */
public class SearchAndReplace extends Notepad
{

    SearchAndReplace(Component frame)
    {
    
       initComponents();
       searchFrame.setVisible(true);
       searchFrame.setLocationRelativeTo(frame);
       textArea=super.textArea;
    }
    
        JFrame searchFrame =new JFrame();
        JButton cancelSearchBtn = new JButton("Cancel");
        JLabel replaceLabel = new JLabel("Replace with: ");
        JButton replaceSearchBtn = new JButton("Replace");
        JTextField replaceField = new JTextField(10);
        JButton replaceAllBtn = new JButton("Replace All");
        JButton searchBtn = new JButton("Find");
        JLabel searchLabel= new JLabel("Find: ");
        JTextField findField=new JTextField(10);
    
    void initComponents()
    {
    
        searchFrame.setAlwaysOnTop(true);
       
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
    
    int selectionStart=-1;
    int selectionEnd=0;
    
    boolean findText(String textArea)
    {
            String textToFind=findField.getText();
            System.out.println("Text area "+textArea);
            System.out.println("text to find " + textToFind);
            
            selectionStart=textArea.indexOf(textToFind, selectionStart+1);
            System.out.println(selectionStart);
            if (selectionStart == -1)
            {                     
              selectionStart = textArea.indexOf(textToFind);            
            }
            if (selectionStart >= 0)
            { 
                selectionEnd=selectionStart+textToFind.length();
                return true;
            }

            return false;
            
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
            
            System.out.println("TEKS AREA+ "+ textArea.getText());
            
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
    
}
