package org.projmedia.ui;

import org.projmedia.domain.Dimensions;
import org.projmedia.controller.MediaController;

import javax.swing.*;
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
