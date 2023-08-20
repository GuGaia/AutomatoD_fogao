import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FogaoSimulador {

    // Essa classe é usada para representar o estado de uma boca do fogão. Cada boca tem três possíveis estados: desligada, em funcionamento e acesa.
    private static EstadoBoca[] bocas = new EstadoBoca[6];
    // Indica se a ignição está ativada
    private static boolean ignicaoAtivada = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }
    //criação e configuração da interface
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Automato Fogão");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(815, 900);
        frame.setLayout(new BorderLayout());

        JPanel bocasPanel = new JPanel(new GridLayout(2, 3));
        // Criando e configurando as bocas do fogão
        for (int i = 0; i < 6; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder("Boca " + (i + 1)));
            panel.setLayout(new BorderLayout());

            JButton ativarButton = new JButton(new ImageIcon("icone_ativar.png"));
            // Configurando botões de ativação e desativação
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

            // Criando e configurando o estado da boca
            bocas[i] = new EstadoBoca(bocaButton);
            panel.setBorder(BorderFactory.createTitledBorder("Boca " + (i + 1)));
            bocasPanel.add(panel);
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(bocasPanel, BorderLayout.NORTH);

        // Configurando botão de ignição
        JButton ignicaoButton = new JButton(new ImageIcon("icone_ignicao.png"));
        ignicaoButton.setPreferredSize(new Dimension(50, 50));
        ignicaoButton.addActionListener(new IgnicaoActionListener());
        centerPanel.add(ignicaoButton, BorderLayout.CENTER);

        // Adicionando painel de bocas e botão de ignição ao centro do frame
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
     
    // Classe que representa o estado de uma boca do fogão
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

        //Funções de transição, responsáveis por alterar os estados das boca sempre que são chamadas pela ativação do botão
        public void setDesligada() {
            emFuncionamento = false;
            acesa = false;
            bocaButton.setIcon(new ImageIcon("boca_desligada.png"));//A mudança das imagens durante o funcionamento indica visualmente a mudança de estado
        }

        public void setEmFuncionamento() {
            emFuncionamento = true;
            bocaButton.setIcon(new ImageIcon("boca_ativa.png")); //A mudança das imagens durante o funcionamento indica visualmente a mudança de estado
        }

        public void acender() {
            if (emFuncionamento) {
                acesa = true;
                bocaButton.setIcon(new ImageIcon("boca_acesa.gif")); //A mudança das imagens durante o funcionamento indica visualmente a mudança de estado
            }
        }

        public void apagar() {
            acesa = false;
            emFuncionamento = false;
            bocaButton.setIcon(new ImageIcon("boca_desligada.png")); //A mudança das imagens durante o funcionamento indica visualmente a mudança de estado
        }
    }

    // Classe responsável por conectar os botões com os metodos de mudança de estado
    private static class AtivarActionListener implements ActionListener {
        private int bocaIndex;

        public AtivarActionListener(int index) {
            this.bocaIndex = index;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!bocas[bocaIndex].emFuncionamento) {
                // Mudança de estado: Boca é ativada
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
                // Mudança de estado: Boca é desativada
                bocas[bocaIndex].apagar();
            }
        }
    }

    // classe para acender as bocas que estão no estado ativada, essa classe coloca as bocas selecionadas no estado acesa
    private static class IgnicaoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!ignicaoAtivada) {
                ignicaoAtivada = true;
                for (int i=0; i < 6; i++){if(!bocas[i].acesa){bocas[i].bocaButton.setIcon(new ImageIcon("boca_ignicao.gif"));}}
                    JOptionPane.showMessageDialog(null, "A ignição ativada.");
                for (int i=0; i < 6; i++){bocas[i].bocaButton.setIcon(new ImageIcon("boca_desligada.png"));}
                // Mudança de estado: Boca está acesa
                for (int i = 0; i < 6; i++){bocas[i].acender();}
                ignicaoAtivada = false;
            }
        }
    }
}
