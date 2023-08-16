import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FogaoSimulador {

    private static EstadoBoca[] bocas = new EstadoBoca[6];
    private static boolean ignicaoAtivada = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Automato Fogão");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(815, 900);
        frame.setLayout(new BorderLayout());

        JPanel bocasPanel = new JPanel(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder("Boca " + (i + 1)));
            panel.setLayout(new BorderLayout());

            JButton ativarButton = new JButton(new ImageIcon("icone_ativar.png"));
            ativarButton.setContentAreaFilled(false);
            ativarButton.setBorderPainted(false);
            JButton desativarButton = new JButton(new ImageIcon("icone_desativar.png"));
            desativarButton.setContentAreaFilled(false);
            desativarButton.setBorderPainted(false);
            ativarButton.addActionListener(new AtivarActionListener(i));
            desativarButton.addActionListener(new DesativarActionListener(i));

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(ativarButton);
            buttonPanel.add(desativarButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);


            JButton bocaButton = new JButton(new ImageIcon("boca_desligada.png"));
            bocaButton.setBorderPainted(false);
            bocaButton.setContentAreaFilled(false);
            panel.add(bocaButton, BorderLayout.CENTER);

            bocas[i] = new EstadoBoca(bocaButton);
            panel.setBorder(BorderFactory.createTitledBorder("Boca " + (i + 1)));
            bocasPanel.add(panel);
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(bocasPanel, BorderLayout.NORTH);

        JButton ignicaoButton = new JButton(new ImageIcon("icone_ignicao.png"));
        ignicaoButton.setPreferredSize(new Dimension(50, 50));
        ignicaoButton.addActionListener(new IgnicaoActionListener());
        centerPanel.add(ignicaoButton, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static class EstadoBoca extends JLabel {
        private boolean emFuncionamento;
        private boolean acesa;
        private JButton bocaButton;

        public EstadoBoca(JButton bocaButton) {
            this.bocaButton = bocaButton;
            setPreferredSize(new Dimension(100, 100));
            setHorizontalAlignment(SwingConstants.CENTER);
            setDesligada();
        }

        public void setDesligada() {
            emFuncionamento = false;
            acesa = false;
            bocaButton.setIcon(new ImageIcon("boca_desligada.png"));
        }

        public void setEmFuncionamento() {
            emFuncionamento = true;
            bocaButton.setIcon(new ImageIcon("boca_ativa.png"));
        }

        public void acender() {
            if (emFuncionamento) {
                acesa = true;
                bocaButton.setIcon(new ImageIcon("boca_acesa.gif"));
            }
        }

        public void apagar() {
            acesa = false;
            emFuncionamento = false;
            bocaButton.setIcon(new ImageIcon("boca_desligada.png"));
        }
    }

    private static class AtivarActionListener implements ActionListener {
        private int bocaIndex;

        public AtivarActionListener(int index) {
            this.bocaIndex = index;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!bocas[bocaIndex].emFuncionamento) {
                bocas[bocaIndex].setEmFuncionamento();
            }
        }
    }
    private static class DesativarActionListener implements ActionListener {
        private int bocaIndex;

        public DesativarActionListener(int index) {
            this.bocaIndex = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (bocas[bocaIndex].emFuncionamento) {
                bocas[bocaIndex].apagar();
            }
        }
    }

    private static class IgnicaoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!ignicaoAtivada) {
                ignicaoAtivada = true;
                for (int i=0; i < 6; i++){if(!bocas[i].acesa){bocas[i].bocaButton.setIcon(new ImageIcon("boca_ignicao.gif"));}}
                    JOptionPane.showMessageDialog(null, "A ignição ativada.");
                for (int i=0; i < 6; i++){bocas[i].bocaButton.setIcon(new ImageIcon("boca_desligada.png"));}
                for (int i = 0; i < 6; i++){bocas[i].acender();}
                ignicaoAtivada = false;
            }
        }
    }
}
