package com.tetris.ui;

import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import static com.tetris.component.ScreenSize.setWidthHeight;

public class KeySettingScreen extends JFrame {
    private JLabel[] labels = new JLabel[6];
    private JTextField[] textFields = new JTextField[6];
    private SettingController settingController = new SettingController();
    private int[] keyCodes;
    private final String[] keyShape;

    private int focusedIndex = 0;
    private int fontSize;
    private int fieldWidth;
    private int textFieldLength;
    private int textFieldHeight;
    private String player;
    private boolean isDualMode;

    public KeySettingScreen(String player, boolean isDualMode) {

        // 누구의 키세팅 화면인지 선언.
        this.isDualMode = isDualMode;
        this.player = player;

        // 저장된 키세팅 불러오기
        keyShape = settingController.getKeyShape(player);
        keyCodes = settingController.getKeyCodes(player);

        setTitle("Key Setting");
        String screenSize = settingController.getScreenSize("screenSize", "medium");
        switch (screenSize) {
            case "small":
                setWidthHeight(390, 420, this);
                fontSize = 15;
                break;
            case "big":
                setWidthHeight(910, 940, this);
                fontSize = 25;
                break;
            default:
                setWidthHeight(650, 680, this);
                fontSize = 20;
                break;
        }

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel keySettingPanel = new JPanel();
        keySettingPanel.setLayout(new BoxLayout(keySettingPanel, BoxLayout.Y_AXIS));

        // Initialize labels and text fields
        for (int i = 0; i < textFields.length; i++) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            labels[i] = new JLabel();
            labels[i].setFont(new Font(labels[i].getFont().getName(), Font.BOLD, fontSize));

            if (i == 0) {
                labels[i].setText("Change Shape");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 1) {
                labels[i].setText("Left Key");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 2) {
                labels[i].setText("Right Key");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 3) {
                labels[i].setText("Down Key");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else if (i == 4) {
                labels[i].setText("Go down at once");
                textFields[i] = new JTextField(keyShape[i], 5);
            } else {
                textFields[i] = new JTextField("Back");
            }

            // textFields[i].setSize(20, 20);
            textFields[i].setEditable(false);
            textFields[i].setBackground(Color.WHITE);
            textFields[i].setHorizontalAlignment(JTextField.CENTER);

            // 마우스 클릭 이벤트 추가
            textFields[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1 && !e.isConsumed()) {
                        e.consume();
                        // 포커스 인덱스 업데이트 및 입력 모드 진입
                        focusedIndex = Arrays.asList(textFields).indexOf(e.getSource());
                        updateFocus();
                        enterInputMode(player);
                        KeySettingScreen.this.requestFocusInWindow();
                    }
                }
            });

            panel.add(labels[i]);
            panel.add(textFields[i]);

            keySettingPanel.add(panel);
            repaint();
        }

        add(Box.createVerticalStrut(20));

        updateFocus();

        // 키 바인딩 설정
        setFocusable(true);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upAction");
        getRootPane().getActionMap().put("upAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusedIndex = (focusedIndex - 1 + textFields.length) % textFields.length;
                updateFocus();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        getRootPane().getActionMap().put("downAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                focusedIndex = (focusedIndex + 1) % textFields.length;
                updateFocus();
            }
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enterAction");
        getRootPane().getActionMap().put("enterAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterInputMode(player);
            }
        });

        add(keySettingPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateFocus() {
        for (int i = 0; i < textFields.length; i++) {
            if (i == focusedIndex) {
                textFields[i].setBackground(Color.YELLOW);
            } else {
                textFields[i].setBackground(Color.WHITE);
            }
        }
    }

    private void enterInputMode(String player) {

        if (focusedIndex == textFields.length - 1) { // If "Back" text field is focused
            setVisible(false);
            new SettingScreen();
            return; // Exit the method early
        }

        JDialog inputDialog = new JDialog(this, "Input Key", true);
        inputDialog.setLayout(new FlowLayout());
        inputDialog.setSize(300, 100);

        JLabel instructionLabel = new JLabel("Press any key...");
        inputDialog.add(instructionLabel);

        inputDialog.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                String keyString = KeyEvent.getKeyText(keyCode);

                // Disallowing certain keys
                if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER ||
                        keyCode == KeyEvent.VK_WINDOWS || keyCode == KeyEvent.VK_CONTROL ||
                        keyCode == KeyEvent.VK_META || keyCode == KeyEvent.VK_ALT) {
                    JOptionPane.showMessageDialog(KeySettingScreen.this, "This key is not allowed to be assigned.", "Invalid Key", JOptionPane.WARNING_MESSAGE);
                    inputDialog.dispose();
                    return;
                }

                if (isDualMode) {

                    // 다른 플레이어가 해당 키를 사용하는지 확인
                    if (settingController.isKeyAssigned(keyCode, player)) {
                        JOptionPane.showMessageDialog(KeySettingScreen.this, "This key is already assigned to another player.", "Invalid Key", JOptionPane.WARNING_MESSAGE);
                        inputDialog.dispose();
                        return;
                    }
                }

                boolean keyAssigned = false;
                for (JTextField textField : textFields) {
                    if (textField.getText().equals(keyString)) {
                        keyAssigned = true;
                        break;
                    }
                }
                if (!keyAssigned) {
                    textFields[focusedIndex].setText(keyString);
                    keyCodes[focusedIndex] = keyCode;
                    keyShape[focusedIndex] = keyString;
                    settingController.setKeyCodes(keyCodes, player);
                    settingController.setKeyShape(keyShape, player);
                } else {
                    JOptionPane.showMessageDialog(KeySettingScreen.this, "This key is already assigned to another section.", "Key Already Assigned", JOptionPane.WARNING_MESSAGE);
                }
                inputDialog.dispose();
            }
        });

        inputDialog.setVisible(true);
    }
}
