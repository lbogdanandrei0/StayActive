package com.lbogdanandrei.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

class MainWindow extends JFrame implements ActionListener, ItemListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int delay = 10000;
    Timer control;
    Robot robot;
    Random random = new Random();

    JPanel info = new JPanel();
    JLabel coords = new JLabel("");
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");

    JComboBox<String> delayBox = new JComboBox<String>();

    public MainWindow()
    {
        super();
        try {
            robot = new Robot();
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error!-Exiting", "Robot error", JOptionPane.OK_OPTION);
        }

        this.setLayout(new BorderLayout());
        info.setBackground(Color.BLUE);
        coords.setText("X & Y");
        info.add(coords);
        this.add(info, BorderLayout.NORTH);

        start.addActionListener(this);
        stop.addActionListener(this);
        this.add(start, BorderLayout.WEST);
        this.add(stop, BorderLayout.EAST);

        for(int d=1; d<=20; d++)
        {
            delayBox.addItem(d+ "");
        }
        delayBox.setSelectedIndex(9);
        delayBox.addItemListener(this);
        delayBox.setToolTipText("Delay (in seconds)");
        this.add(delayBox, BorderLayout.CENTER);

        this.setSize(300, 120);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
        control = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Tick");
                int newWidth = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().width);
                int newHeight = random.nextInt(Toolkit.getDefaultToolkit().getScreenSize().height);
                robot.mouseMove(newWidth, newHeight);
                coords.setText(newWidth + " & " + newHeight);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(start))
        {
              this.setTitle("StayActive - On");
              control.start();
        }
        if(e.getSource().equals(stop))
        {
            this.setTitle("StayActive - Off");
            control.stop();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED)
        {
            delay = Integer.parseInt(e.getItem().toString()) * 1000;
            control.setDelay(delay);
        }
    }
}

public class Main{

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setTitle("StayActive - Off");
    }
}
