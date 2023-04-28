package org.projmedia.ui;

import org.projmedia.domain.Dimensions;
import org.projmedia.controller.MediaController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

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
        JList<String> selectList = new JList<>(MediaController.getInstance().getIndexTextList().toArray(new String[0]));

        selectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = selectList.getSelectedIndex();
                    MediaController.getInstance().setToTime(index);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(selectList);
        scrollPane.setPreferredSize(new Dimension(Dimensions.LEFT_PANEL_WIDTH, Dimensions.FRAME_HEIGHT));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
    }
}
