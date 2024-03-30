package ui;

import logic.GameController;
import logic.SettingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static component.Button.*;
import static component.ScreenSize.setWidthHeight;
import static java.lang.System.exit;

public class MainMenuScreen extends JFrame implements ActionListener {

    JButton btnPlay;
    JButton btnSetting;
    JButton btnRanking;
    JButton btnExit;
    SettingController settingController = new SettingController();

    public MainMenuScreen() {
        setTitle("Tetris");
        String screenSize = settingController.getSetting("screenSize", "small");
        switch (screenSize) {
            case "small":
                setWidthHeight(400, 550, this);
                break;
            case "big":
                setWidthHeight(800, 950, this);
                break;
            default:
                setWidthHeight(600, 750, this);
                break;
        }
        setLocationRelativeTo(null); // Centered window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 레이아웃 설정
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 상단에 Tetris 제목 레이블 추가
        JLabel titleLabel = new JLabel("Tetris", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel, BorderLayout.NORTH);
        add(Box.createVerticalStrut(20));

        // 중앙 패널에 Play 버튼 추가
        JPanel centerPanel = new JPanel();
        add(Box.createVerticalStrut(10));
        btnPlay = createLogoBtnNext("Play", "play", this, "src/image/play_logo.png");
        btnPlay.setPreferredSize(new Dimension(300, 100));
        btnPlay.setFont(new Font("Serif", Font.BOLD, 20));
        btnPlay.setFocusable(true);
        centerPanel.add(btnPlay);
        add(centerPanel, BorderLayout.CENTER);

        // 하단 패널에 Setting, Ranking, Exit 버튼 추가
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout()); // 버튼들이 나란하게 배치되도록 FlowLayout 사용

        btnSetting = createLogoBtnUp("Setting", "setting", this, "src/image/setting_logo.png");
        btnSetting.setPreferredSize((new Dimension(100, 100)));
        btnSetting.setFocusable(true);
        bottomPanel.add(btnSetting);

        btnRanking = createLogoBtnUp("Ranking", "ranking", this, "src/image/medal.png");
        btnRanking.setPreferredSize((new Dimension(100, 100)));
        btnRanking.setFocusable(true);
        bottomPanel.add(btnRanking);

        btnExit = createLogoBtnUp("Exit", "exit", this, "src/image/exit_logo.png");
        btnExit.setPreferredSize((new Dimension(100, 100)));
        btnExit.setFocusable(true);
        bottomPanel.add(btnExit);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Set initial focus to the "Game Start" button after the GUI is fully initialized
        btnPlay.requestFocusInWindow();

        // Attach a key listener to each button
        btnPlay.addKeyListener(new MyKeyListener());
        btnSetting.addKeyListener(new MyKeyListener());
        btnRanking.addKeyListener(new MyKeyListener());
        btnExit.addKeyListener(new MyKeyListener());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("play")) {
            setVisible(false);
            new GameController();
            setVisible(false);
        } else if (command.equals("setting")) {
            new SettingScreen();
            setVisible(false);
        } else if (command.equals("ranking")) {
            new ScoreboardScreen();
            setVisible(false);
        } else if (command.equals("exit")) {
            System.exit(0);
        }
    }

    // Key listener class
    private class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_DOWN) {
                focusDownButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusUpButton();
            } else if (keyCode == KeyEvent.VK_LEFT) {
                focusLeftButton();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                focusRightButton();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                moveScreen();
            }

            System.out.println("KeyPressed");
        }
    }

    // 엔터 키
    private void moveScreen() {
        setVisible(false);
        if (btnPlay.isFocusOwner()) {
            new GameController();
        } else if (btnSetting.isFocusOwner()) {
            new SettingScreen();
        } else if (btnRanking.isFocusOwner()) {
            new ScoreboardScreen();
        }
        else if (btnExit.isFocusOwner()) {
            exit(0);
        }
    }

    // 위 방향키
    private void focusUpButton() {
        if (btnSetting.isFocusOwner()) {
            btnPlay.requestFocusInWindow();
        } else if (btnRanking.isFocusOwner()) {
            btnPlay.requestFocusInWindow();
        } else if (btnExit.isFocusOwner()) {
            btnPlay.requestFocusInWindow();
        }
    }

    // 아래 방향키
    private void focusDownButton() {
        if (btnPlay.isFocusOwner()) {
            btnRanking.requestFocusInWindow();
        }
    }

    // 왼쪽 방향키
    private void focusLeftButton() {
        if (btnExit.isFocusOwner()) {
            btnRanking.requestFocusInWindow();
        } else if (btnRanking.isFocusOwner()) {
            btnSetting.requestFocusInWindow();
        }
    }

    // 오른쪽 방향키
    private void focusRightButton() {
        if (btnSetting.isFocusOwner()) {
            btnRanking.requestFocusInWindow();
        } else if (btnRanking.isFocusOwner()) {
            btnExit.requestFocusInWindow();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainMenuScreen::new);
    }
}

