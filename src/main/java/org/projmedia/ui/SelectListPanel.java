package org.projmedia.ui;

import javafx.scene.media.MediaPlayer;
import org.projmedia.domain.Dimensions;
import org.projmedia.controller.MediaController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectListPanel extends JPanel {

    public SelectListPanel() {
        super();
        initSelectList();
    }

    public SelectListPanel(LayoutManager layout) {
        super(layout);
        initSelectList();
    }

    public void initSelectList() {
        JList<String> selectList = MediaController.getInstance().getSelectList();

        selectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                MediaPlayer mediaPlayer = MediaController.getInstance().getMediaPlayer();
                if (!selectList.getValueIsAdjusting() && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                    int index = selectList.getSelectedIndex();
                    if (index >= 0) {
                        MediaController.getInstance().setToTime(index);
                    }
                }
            }
        });

        selectList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectList.setValueIsAdjusting(true);
                int index = selectList.locationToIndex(e.getPoint());
                MediaController.getInstance().setUserSelect(index);
                selectList.setValueIsAdjusting(false);
            }
        });

        JScrollPane scrollPane = new JScrollPane(selectList);
        scrollPane.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, Dimensions.FRAME_HEIGHT));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
    }
}
