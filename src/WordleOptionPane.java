import javax.swing.*;
import java.awt.*;

public class WordleOptionPane {
    private static final Color BACKGROUND = new Color(0x121213);

    private static void showMessageDialog(Component parent, String title, String message) {
        JDialog dlgMessage = new JDialog();
        dlgMessage.setModal(true);
        dlgMessage.setTitle(title);

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(BACKGROUND);

        JLabel lblMessage = new JLabel(message);
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setFont(new Font("Monospace", Font.BOLD, 20));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        pnlCenter.add(lblMessage);

        dlgMessage.add(pnlCenter, BorderLayout.CENTER);

        dlgMessage.setSize(400, 200);
        dlgMessage.setLocationRelativeTo(parent);

        dlgMessage.setVisible(true);
    }

    public static void showWinMessageDialog(Component parent, String message) {
        showMessageDialog(parent, "You won!", message);
    }

    public static void showLossMessageDialog(Component parent, String message) {
        showMessageDialog(parent, "You lost!", message);
    }
}
