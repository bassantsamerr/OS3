import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

class SchedulingGUI
{
    ArrayList<Process> Processes = new ArrayList<Process>();
    static JFrame frame = new JFrame("CPU Schedulers Simulator");
    static GridBagConstraints gbc = new GridBagConstraints();
    static JPanel panal = new JPanel();

    public SchedulingGUI(ArrayList<Process> P) {
        // TODO Auto-generated constructor stub
        Processes = P;

        panal.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 5, 5, 5);

        JLabel ProcessName;
        for (int i = 0; i < Processes.size(); i++) {
            ProcessName = new JLabel("ahmed");
            gbc.gridx = 0;
            gbc.gridy = i;
            panal.add(ProcessName, gbc);
            frame.setVisible(true);
        }

        //panal.setBackground(new Color(81, 77, 77));
        panal.setBackground(new Color(255, 255, 255));
        JScrollPane panelPane = new JScrollPane(panal);
        panelPane.setBackground(new Color(255, 255, 255));
        frame.setBackground(new Color(255, 255, 255));
        frame.add(panelPane);
        frame.pack();
        //frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

// row = current time + 1
// col = process index in the array
// c = color of the process , or waiting time(white button -> c = new color(255,255,255))
// will add 1 button