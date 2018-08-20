/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Maciek
 */
public class Settings extends JFrame {
    
  
   protected Color tempBgColor = new Color(0,0,0);
   protected Color tempFontColor = new Color(0,0,0);
   protected JTextArea preview = new JTextArea(" This it preview text");
   protected JButton applyBtn = new JButton("Apply Changes");
   protected JButton exitBtn = new JButton("    Exit    ");
    
    
    class Slider extends JSlider
    {
        Slider(int min,int max)
        {   
            super(min,max);
            setMajorTickSpacing(255);
            setPaintLabels(true);
            this.setFont(new Font(this.getFont().getName(),this.getFont().getStyle(),10));
        }
    
    }
    
    Settings(Component frame)
    {
       
       initComponents();
       setVisible(true);
       this.setLocationRelativeTo(frame);
    }

    
    void initComponents()
    {
  
        setBounds(300,300,320,360);
        setVisible(false);
        setTitle("Settings");
        setResizable(false);      
        JPanel topRootPanel =new JPanel(new GridLayout(2,0));
        JPanel slidersPanel = new JPanel();
        
       
        
       getContentPane().add(topRootPanel);
        
       
        preview.setBorder(BorderFactory.createEtchedBorder());  
        preview.setEditable(false);
        
        topRootPanel.add(slidersPanel);
        topRootPanel.add(preview);
       
        
       JLabel bgColor = new JLabel("Background");
       JLabel fontColor = new JLabel("Font");
       JLabel red = new JLabel("Red");
       JLabel green = new JLabel("Green");
       JLabel blue = new JLabel(" Blue");

       JSeparator separator = new JSeparator(); 
       separator.setOrientation(javax.swing.SwingConstants.VERTICAL);
       Slider bgRedSlider = new Slider(0,255);
       Slider bgGreenSlider = new Slider(0,255);
       Slider bgBlueSlider = new Slider(0,255);
       
       Slider fontRedSlider = new Slider(0,255);
       Slider fontGreenSlider = new Slider(0,255);
       Slider fontBlueSlider = new Slider(0,255);
       
       
        
       
        GroupLayout layout = new GroupLayout(slidersPanel);
        slidersPanel.setLayout(layout);
       
        
        /**
         * GroupLayout
         */
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(blue)
                                        .addGap(29, 29, 29)
                                        .addComponent(bgBlueSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(bgRedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(red)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(green)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bgGreenSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bgColor)
                        .addGap(28, 28, 28)))
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(fontColor))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fontRedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fontGreenSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fontBlueSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(fontColor))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bgColor)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(red)
                                .addComponent(bgRedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fontRedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bgGreenSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fontGreenSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(green, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fontBlueSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(blue)
                                    .addComponent(bgBlueSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
     
        
        buttonPanel.add(applyBtn);
        buttonPanel.add(exitBtn);
        
        add(buttonPanel,BorderLayout.SOUTH);
        

        
        bgRedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tempBgColor = new Color(((JSlider)ce.getSource()).getValue(),tempBgColor.getGreen(),tempBgColor.getBlue());     
                 preview.setBackground(tempBgColor);
                 
            }
         });
        
        
        bgGreenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tempBgColor = new Color(tempBgColor.getRed(),((JSlider)ce.getSource()).getValue(),tempBgColor.getBlue());     
                 preview.setBackground(tempBgColor);
                 
            }
         });
        
        bgBlueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tempBgColor = new Color(tempBgColor.getRed(),tempBgColor.getGreen(),((JSlider)ce.getSource()).getValue());     
                 preview.setBackground(tempBgColor);
                 
            }
         });
        
        
        fontRedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tempFontColor = new Color(((JSlider)ce.getSource()).getValue(),tempFontColor.getGreen(),tempFontColor.getBlue());     
                 preview.setForeground(tempFontColor);
                
            }
         });
        
        
        fontGreenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tempFontColor = new Color(tempFontColor.getRed(),((JSlider)ce.getSource()).getValue(),tempFontColor.getBlue());     
                 preview.setForeground(tempFontColor);
                
            }
         });
        
        fontBlueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tempFontColor = new Color(tempFontColor.getRed(),tempFontColor.getGreen(),((JSlider)ce.getSource()).getValue());     
                 preview.setForeground(tempFontColor);
               
            }
         });
    

        
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               dispose();
            }
        });

    }
    
   Color setBgColor()
   {   
            return tempBgColor;    
   }
   
   Color setFontColor()
   {   
            return tempFontColor;
   }
   
   
    void adapt(Font actualFont, Color actualBackgroundColor, Color actualForeGroundFolor)
    {
        preview.setBackground(actualBackgroundColor);
        preview.setForeground(actualForeGroundFolor);
        preview.setFont(actualFont);
    
    }
    
 
}
 

