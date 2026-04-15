package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Settings dialog for customizing board colors and size.
 */
public class SettingsDialog extends JDialog {
    private JComboBox<String> themeBox;
    private JComboBox<String> sizeBox;
    private boolean applied;

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);
        setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        centerPanel.add(new JLabel("Board Theme:"));
        themeBox = new JComboBox<>(new String[]{"Classic", "Gray", "Blue"});
        centerPanel.add(themeBox);

        centerPanel.add(new JLabel("Board Size:"));
        sizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        sizeBox.setSelectedItem("Medium");
        centerPanel.add(sizeBox);

        JPanel buttonPanel = new JPanel();
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        applyButton.addActionListener(e -> {
            applied = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            applied = false;
            setVisible(false);
        });

        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    public boolean isApplied() {
        return applied;
    }

    public String getSelectedTheme() {
        return (String) themeBox.getSelectedItem();
    }

    public String getSelectedSize() {
        return (String) sizeBox.getSelectedItem();
    }
}