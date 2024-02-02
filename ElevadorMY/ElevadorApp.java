package ElevadorMY;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ElevadorApp extends JFrame {

    private static final int NUMERO_ANDARES = 6;

    private Elevador elevador1;
    private Elevador elevador2;
    private JTextArea statsTextArea;

    public ElevadorApp() {
        super("Simulador de Elevadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        elevador1 = new Elevador();
        elevador2 = new Elevador();

        JButton chamarElevadorButton = new JButton("Chamar Elevador");
        JButton entrarElevadorButton = new JButton("Mudar o andar");
        JButton sairElevadorButton = new JButton("Sair do Elevador");
        JButton statsButton = new JButton("Estatísticas");

        statsTextArea = new JTextArea(10, 40);
        statsTextArea.setEditable(false);

        chamarElevadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chamarElevador();
            }
        });

        entrarElevadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrarElevador();
            }
        });

        sairElevadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sairElevador();
            }
        });

        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirEstatisticas();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(chamarElevadorButton);
        panel.add(entrarElevadorButton);
        panel.add(sairElevadorButton);

        JPanel statsPanel = new JPanel();
        statsPanel.add(statsButton);

        add(panel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(new JScrollPane(statsTextArea), BorderLayout.SOUTH);
    }

    private void chamarElevador() {
        int andarDesejado = Integer.parseInt(JOptionPane.showInputDialog("Digite o andar desejado até 6:"));
        if (andarDesejado < 1 || andarDesejado > NUMERO_ANDARES) {
            JOptionPane.showMessageDialog(this, "Andar inválido. Digite um andar entre 1 e " + NUMERO_ANDARES + ".");
            return;
        }

        Elevador elevadorMaisProximo = calcularElevadorMaisProximo(andarDesejado);
        elevadorMaisProximo.chamarEEntrar(andarDesejado);

        updateStats();
    }

    private void entrarElevador() {
        // Verifica se há pessoas no elevador antes de permitir entrar
        if (!elevador1.isPessoaDentro() && !elevador2.isPessoaDentro()) {
            JOptionPane.showMessageDialog(this, "Não há elevador chamado ou já há pessoas dentro.");
            return;
        }

        int andarDesejado = Integer.parseInt(JOptionPane.showInputDialog("Digite o andar desejado até 6:"));
        if (andarDesejado < 1 || andarDesejado > NUMERO_ANDARES) {
            JOptionPane.showMessageDialog(this, "Andar inválido. Digite um andar entre 1 e " + NUMERO_ANDARES + ".");
            return;
        }

        Elevador elevadorMaisProximo = calcularElevadorMaisProximo(andarDesejado);
        elevadorMaisProximo.entrar(andarDesejado);

        updateStats();
    }

    private void sairElevador() {
        // Implementação semelhante ao código anterior
    }

    private void updateStats() {
        statsTextArea.setText("Elevador 1 - Pessoas: " + elevador1.getPessoas() +
                ", Andar Atual: " + elevador1.getAndarAtual() + "\n" +
                "Elevador 2 - Pessoas: " + elevador2.getPessoas() +
                ", Andar Atual: " + elevador2.getAndarAtual());
    }

    private void exibirEstatisticas() {
        JOptionPane.showMessageDialog(this, "Estatísticas:\n" +
                "Elevador 1 - Pessoas: " + elevador1.getPessoas() +
                ", Andar Atual: " + elevador1.getAndarAtual() + "\n" +
                "Elevador 2 - Pessoas: " + elevador2.getPessoas() +
                ", Andar Atual: " + elevador2.getAndarAtual());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ElevadorApp().setVisible(true);
            }
        });
    }

    private Elevador calcularElevadorMaisProximo(int andarDesejado) {
        int distanciaElevador1 = Math.abs(elevador1.getAndarAtual() - andarDesejado);
        int distanciaElevador2 = Math.abs(elevador2.getAndarAtual() - andarDesejado);

        return (distanciaElevador1 <= distanciaElevador2) ? elevador1 : elevador2;
    }

    private class Elevador {
        private int andarAtual;
        private int pessoas;

        public Elevador() {
            this.andarAtual = new Random().nextInt(NUMERO_ANDARES);
            this.pessoas = 0;
        }

        public void chamarEEntrar(int andarDesejado) {
            chamar(andarDesejado);
            entrar(andarDesejado);
        }

        public void chamar(int andarDesejado) {
            if (!isElevadorMovendo()) {
                andarAtual = andarDesejado;
                JOptionPane.showMessageDialog(ElevadorApp.this, "Elevador chamado para o andar " + andarDesejado);
            }
        }

        public void entrar(int andarDesejado) {
            pessoas++;
            andarAtual = andarDesejado;
        }

        public void sair() {
            if (pessoas > 0) {
                pessoas--;
            }
        }

        public int getAndarAtual() {
            return andarAtual;
        }

        public int getPessoas() {
            return pessoas;
        }

        public boolean isElevadorMovendo() {
            return false; // Implemente a lógica real se o elevador estiver se movendo
        }

        public boolean isPessoaDentro() {
            return pessoas > 0;
        }
    }
}
