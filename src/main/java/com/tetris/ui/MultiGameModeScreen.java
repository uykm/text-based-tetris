package com.tetris.ui;

import com.tetris.component.Button;
import com.tetris.logic.DualTetrisController;
import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.tetris.component.ScreenSize.setWidthHeight;

public class MultiGameModeScreen extends JFrame implements ActionListener {

    private JButton btnNormal, btnItem, btnTime;
    private JButton btnMenu;

    private SettingController settingController = new SettingController();

    private final int btnSize;

    public MultiGameModeScreen() {

        setTitle("Tetris - GameMode");
        String screenSize = settingController.getScreenSize("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(390, 420, this);
                btnSize = 100;
                break;
            case "big":
                setWidthHeight(910, 940, this);
                btnSize = 200;
                break;
            default:
                setWidthHeight(650, 680, this);
                btnSize = 170;
                break;
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        btnNormal = Button.createLogoBtnUp("Normal", "normal", this, screenSize, "/image/play_logo.png");
        btnNormal.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnItem = Button.createLogoBtnUp("Item", "item", this, screenSize, "/image/mario.png");
        btnItem.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnTime = Button.createLogoBtnUp("Time", "time", this, screenSize, "/image/time.png");
        btnTime.setPreferredSize((new Dimension(btnSize, btnSize)));

        btnNormal.addKeyListener(new MyKeyListener());
        btnItem.addKeyListener(new MyKeyListener());
        btnTime.addKeyListener(new MyKeyListener());

        topPanel.add(btnNormal);
        topPanel.add(btnItem);
        topPanel.add(btnTime);

        add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnMenu = Button.createLogoBtn("menu", this, "/image/backLogo.png");
        btnMenu.setPreferredSize((new Dimension(60, 32)));
        btnMenu.addKeyListener(new MyKeyListener());

        bottomPanel.add(btnMenu);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        setVisible(false);
        switch (command) {
            case "normal" -> {
                new DifficultyScreen(false, true, false);
            }
            case "item" -> {
                new DifficultyScreen(true, true, false);
            }
            case "time" -> {
                new DifficultyScreen(false, true, true);
            }
            case "menu" -> {
                new MainMenuScreen();
            }
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnNormal.isFocusOwner()) {
            new DifficultyScreen(false, true, false);
        } else if (btnItem.isFocusOwner()) {
            new DifficultyScreen(true, true, false);
        } else if (btnTime.isFocusOwner()) {
            new DifficultyScreen(false, true, true);
        } else if (btnMenu.isFocusOwner()) {
            new MainMenuScreen();
        }
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT) {
                focusRightButton();
            } else if (keyCode == KeyEvent.VK_LEFT) {
                focusLeftButton();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                focusDownButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusUpButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                moveScreen();
            }
        }
    }

    private void focusRightButton() {
        if (btnNormal.isFocusOwner()) {
            btnItem.requestFocusInWindow();
        } else if (btnItem.isFocusOwner()) {
            btnTime.requestFocusInWindow();
        }
    }

    private void focusLeftButton() {
        if (btnTime.isFocusOwner()) {
            btnItem.requestFocusInWindow();
        } else if (btnItem.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        }
    }

    private void focusUpButton() {
        if (btnMenu.isFocusOwner()) {
            btnItem.requestFocusInWindow();
        }
    }

    private void focusDownButton() {
        if (btnNormal.isFocusOwner() || btnItem.isFocusOwner() || btnTime.isFocusOwner()) {
            btnMenu.requestFocusInWindow();
        }
    }

    // 테스트 코드를 위한 GETTER
    public JButton getBtnNormal() {
        return btnNormal;
    }
    public JButton getBtnItem() {
        return btnItem;
    }
    public JButton getBtnTime() {
        return btnTime;
    }
    public JButton getBtnMenu() {
        return btnMenu;
    }
}
