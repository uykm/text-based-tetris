package ui;

import logic.SettingController;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static component.Button.createBtn;
import static component.Button.createLogoBtnUp;
import static component.ScreenSize.setWidthHeight;

public class DifficultyScreen extends JFrame implements ActionListener {

    JButton btnEasy, btnNormal, btnHard;
    JButton btnGeneral, btnItem;
    JButton btnMenu;
    SettingController settingController = new SettingController();

    public DifficultyScreen() {
        setTitle("Tetris - Difficulty");
        String screenSize = settingController.getScreenSize("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(390, 420, this);
                break;
            case "big":
                setWidthHeight(910, 940, this);
                break;
            default:
                setWidthHeight(650, 680, this);
                break;
        }
        setLocationRelativeTo(null); // Centered window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 레이아웃 설정
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 버튼
        JPanel topPanel = new JPanel();
        btnEasy = createLogoBtnUp("Easy", "easy", this, "src/image/easy_logo.png");
        btnEasy.setPreferredSize((new Dimension(100, 100)));
        btnNormal = createLogoBtnUp("Normal", "normal", this, "src/image/normal_logo.png");
        btnNormal.setPreferredSize((new Dimension(100, 100)));
        btnHard = createLogoBtnUp("Hard", "hard", this, "src/image/hard_logo.png");
        btnHard.setPreferredSize((new Dimension(100, 100)));

        btnEasy.addKeyListener(new MyKeyListener());
        btnNormal.addKeyListener(new MyKeyListener());
        btnHard.addKeyListener(new MyKeyListener());

        topPanel.add(btnEasy);
        topPanel.add(btnNormal);
        topPanel.add(btnHard);

        add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        btnMenu = createBtn("Menu", "menu", this);
        btnMenu.addKeyListener(new MyKeyListener());

        bottomPanel.add(btnMenu);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("easy")) {
            settingController.saveSettings("difficulty", "0");
        } else if (command.equals("normal")) {
            settingController.saveSettings("difficulty", "1");
        } else if (command.equals("hard")) {
            settingController.saveSettings("difficulty", "2");
        } else if (command.equals("menu")) {
            // 게임 모드 선택 화면으로 돌아가기
        }
    }

    private void moveScreen() {
        setVisible(false);
        if (btnEasy.isFocusOwner()) {

        } else if (btnNormal.isFocusOwner()) {

        } else if (btnHard.isFocusOwner()) {

        } else if (btnMenu.isFocusOwner()) {
            // 게임 모드 선택 화면으로 돌아가기
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(DifficultyScreen::new);
    }
}