package org.projmedia.ui;

import org.projmedia.domain.Dimensions;
import org.projmedia.controller.MediaController;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    public AppFrame() {
        // Frame
        setSize(Dimensions.FRAME_WIDTH, Dimensions.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("The Frame");

        JPanel leftPanel = new SelectListPanel();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        leftPanel.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, Dimensions.FRAME_HEIGHT));

        JPanel topRightPanel = new VideoPlayPanel();
        topRightPanel.setPreferredSize(new Dimension(Dimensions.RIGHT_PANEL_WIDTH, Dimensions.TOP_RIGHT_PANEL_HEIGHT));
        topRightPanel.setBackground(Color.GREEN);

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setPreferredSize(new Dimension(Dimensions.RIGHT_PANEL_WIDTH, Dimensions.BOTTOM_RIGHT_PANEL_HEIGHT));

        rightPanel.add(topRightPanel);
        rightPanel.add(bottomRightPanel);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Bottom Right
        JButton playButton = new JButton("PLAY");
        JButton pauseButton = new JButton("PAUSE");
        JButton stopButton = new JButton("STOP");

        playButton.addActionListener(e -> MediaController.getInstance().play());
        pauseButton.addActionListener(e -> MediaController.getInstance().pause());
        stopButton.addActionListener(e -> MediaController.getInstance().stop());

        playButton.setPreferredSize(new Dimension(100, 50));
        pauseButton.setPreferredSize(new Dimension(100, 50));
        stopButton.setPreferredSize(new Dimension(100, 50));
        bottomRightPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        bottomRightPanel.add(playButton, gbc);
        gbc.gridx = 1;
        bottomRightPanel.add(pauseButton, gbc);
        gbc.gridx = 2;
        bottomRightPanel.add(stopButton, gbc);

        setVisible(true);
    }
}