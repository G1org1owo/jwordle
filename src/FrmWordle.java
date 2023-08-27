import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FrmWordle extends JFrame implements KeyListener {
    JLabel[][] guesses = new JLabel[6][5];
    Color[][] colors = new Color[6][5];
    Wordle wordle;
    private JPanel pnlCenter;
    private int guessCount = 0;
    private int charIndex = 0;

    private static final Color BACKGROUND = new Color(0x121213);
    private static final Color FOREGROUND = Color.WHITE;
    private static final Color CHAR_BACKGROUND_NEUTRAL = BACKGROUND;
    public static final Color CHAR_BACKGROUND_EXACT = new Color(0x538d4e);
    public static final Color CHAR_BACKGROUND_PRESENT = new Color(0xb59f3b);
    private static final Color CHAR_BACKGROUND_WRONG = Color.DARK_GRAY;
    private static final Color CHAR_BORDER_NEUTRAL = new Color(0x3a3a3c);
    private static final Color CHAR_BORDER_SELECTED = new Color(0x565758);
    private boolean gameEnded = false;

    private FrmWordle() {
        this.wordle = new Wordle();

        this.setTitle("Wordle");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.initUI();
        this.populate();

        this.pack();
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    private void initUI() {
        pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBackground(BACKGROUND);
        pnlCenter.setFocusable(true);
        pnlCenter.addKeyListener(this);

        for(int i=0; i<6; i++) {
            for (int j = 0; j < 5; j++) {
                JLabel lblChar = new JLabel("");
                lblChar.setFont(new Font("Monospace", Font.BOLD, 32));
                lblChar.setForeground(FOREGROUND);
                lblChar.setHorizontalAlignment(SwingConstants.CENTER);
                guesses[i][j] = lblChar;
                colors[i][j] = CHAR_BACKGROUND_NEUTRAL;
            }
        }

        this.add(pnlCenter, BorderLayout.CENTER);
    }

    private void populate() {
        pnlCenter.removeAll();

        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                JPanel pnlChar = new JPanel(new BorderLayout());
                pnlChar.add(guesses[i][j], BorderLayout.CENTER);
                pnlChar.setPreferredSize(new Dimension(100, 100));
                pnlChar.setBackground(colors[i][j]);
                if(i >= guessCount){
                    pnlChar.setBorder(BorderFactory.createLineBorder(
                            (j < charIndex && i == guessCount)?
                                CHAR_BORDER_SELECTED :
                                CHAR_BORDER_NEUTRAL,
                            2));
                }
                pnlCenter.add(pnlChar, makeGridBagConstraints(j, i));
            }
        }

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FrmWordle::new);
    }

    public static GridBagConstraints makeGridBagConstraints(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = x;
        gbc.gridy = y;

        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        return gbc;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(gameEnded) return;

        switch(e.getKeyChar()){
            case '\b' -> {
                if(charIndex > 0) guesses[guessCount][--charIndex].setText("");
            }
            case '\n' -> {
                if(charIndex == 5 && wordle.isValidWord(this.getCurrentWord())) tryGuess();
            }
            default -> {
                if(charIndex < 5) guesses[guessCount][charIndex++].setText((e.getKeyChar() + "").toUpperCase());
            }
        }

        populate();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void tryGuess() {
        String word = getCurrentWord();

        WordleChar[] answer = wordle.checkWord(word);
        for(int i=0; i<5; i++){
            colors[guessCount][i] = answer[i].isExact() ?
                    CHAR_BACKGROUND_EXACT : answer[i].isPresent()?
                    CHAR_BACKGROUND_PRESENT :
                    CHAR_BACKGROUND_WRONG;
        }

        guessCount++;
        charIndex = 0;
        populate();

        if(wordle.isAnswerRight(answer)) gameWon();
        else if(guessCount == 6) gameLost();

    }

    private String getCurrentWord() {
        StringBuilder word = new StringBuilder();
        for(int i=0; i<5; i++){
            word.append(guesses[guessCount][i].getText());
        }
        return word.toString();
    }

    private void gameWon() {
        gameEnded = true;
        WordleOptionPane.showWinMessageDialog(this, "You won!");
    }
    private void gameLost() {
        gameEnded = true;
        WordleOptionPane.showLossMessageDialog(
                this,
                "You lost! The correct word was " + wordle.getSolution() + ""
        );
    }
}
