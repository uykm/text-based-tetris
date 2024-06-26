package com.tetris.ui;

import com.tetris.component.Button;
import com.tetris.logic.DualTetrisController;
import com.tetris.logic.GameController;
import com.tetris.logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import static com.tetris.component.ScreenSize.setWidthHeight;

public class DifficultyScreen extends JFrame implements ActionListener {

    JButton btnEasy;
    JButton btnNormal;
    JButton btnHard;
    JButton btnMenu;
    private boolean isItem;
    private boolean isDualMode;
    private boolean isTimeAttack;
    private SettingController settingController = new SettingController();

    private final int btnSize;

    // 싱글 모드
    public DifficultyScreen(boolean isItem) {
        this(isItem, false, false);
    }

    // 멀티 모드
    public DifficultyScreen(boolean isItem, boolean isDualMode, boolean isTimeAttack) {

        // 모드 설정 불러오기
        this.isItem = isItem;
        this.isDualMode = isDualMode;
        this.isTimeAttack = isTimeAttack;

        setTitle("Tetris - Difficulty");
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
        btnEasy = Button.createLogoBtnUp("Easy", "easy", this, screenSize, "/image/easy_logo.png");
        btnEasy.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnNormal = Button.createLogoBtnUp("Normal", "normal", this, screenSize, "/image/normal_logo.png");
        btnNormal.setPreferredSize((new Dimension(btnSize, btnSize)));
        btnHard = Button.createLogoBtnUp("Hard", "hard", this, screenSize, "/image/hard_logo.png");
        btnHard.setPreferredSize((new Dimension(btnSize, btnSize)));

        btnEasy.addKeyListener(new MyKeyListener());
        btnNormal.addKeyListener(new MyKeyListener());
        btnHard.addKeyListener(new MyKeyListener());

        topPanel.add(btnEasy);
        topPanel.add(btnNormal);
        topPanel.add(btnHard);

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
            case "easy":
            case "normal":
            case "hard":
                settingController.saveSettings("difficulty", command.equals("easy") ? "0" : command.equals("normal") ? "1" : "2");
                if (isDualMode) {
                    new DualTetrisController(isItem, isTimeAttack);
                } else {
                    new GameController(isItem);
                }
                break;
            case "menu":
                new MainMenuScreen();
                break;
        }
    }

    private void moveScreen() {
        setVisible(false);
        Map<JButton, String> buttonDifficultyMap = Map.of(
                btnEasy, "0",
                btnNormal, "1",
                btnHard, "2"
        );

        for (Map.Entry<JButton, String> entry : buttonDifficultyMap.entrySet()) {
            if (entry.getKey().isFocusOwner()) {
                settingController.saveSettings("difficulty", entry.getValue());
                if (isDualMode) {
                    new DualTetrisController(isItem, isTimeAttack);
                } else {
                    new GameController(isItem);
                }
                return;
            }
        }

        if (btnMenu.isFocusOwner()) {
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
        if (btnEasy.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        } else if (btnNormal.isFocusOwner()) {
            btnHard.requestFocusInWindow();
        }
    }

    private void focusLeftButton() {
        if (btnHard.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        } else if (btnNormal.isFocusOwner()) {
            btnEasy.requestFocusInWindow();
        }
    }

    private void focusUpButton() {
        if (btnMenu.isFocusOwner()) {
            btnNormal.requestFocusInWindow();
        }
    }

    private void focusDownButton() {
        if (btnEasy.isFocusOwner() || btnNormal.isFocusOwner() || btnHard.isFocusOwner()) {
            btnMenu.requestFocusInWindow();
        }
    }
}
