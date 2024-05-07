package com.tetris.ui;

import com.tetris.component.Button;
import com.tetris.logic.GameController;
import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static com.tetris.component.ScreenSize.setWidthHeight;

public class GameModeScreen extends JFrame implements ActionListener {

    private JButton btnNormal, btnItem, btnTime;
    private JButton btnMenu;

    private SettingController settingController = new SettingController();

    private final int btnSize;

    public GameModeScreen() {

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
        btnNormal = Button.createLogoBtnUp("Normal", "normal", this, screenSize, "src/main/java/com/tetris/image/play_logo.png");
        btnNormal.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnItem = Button.createLogoBtnUp("Item", "item", this, screenSize, "src/main/java/com/tetris/image/mario.png");
        btnItem.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnTime = Button.createLogoBtnUp("Time", "time", this, screenSize, "src/main/java/com/tetris/image/time.png");
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

        btnMenu = Button.createLogoBtn("menu", this, "src/main/java/com/tetris/image/backLogo.png");
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
                //
            }
            case "item" -> {
                //
            }
            case "time" -> {
                //
            }
            case "menu" -> {
                //
            }
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnNormal.isFocusOwner()) {
        } else if (btnItem.isFocusOwner()) {
            //
        } else if (btnTime.isFocusOwner()) {
            //
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
}