package graphics;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JDialog {
    private final JSlider mrowiskaSlider;
    private final JSlider mrowkiSlider;
    private final JSlider przedmiotySlider;
    private boolean started = false;

    public int getLiczbaMrowisk() {
        return mrowiskaSlider.getValue();
    }
    //Zawraca w milisekundach
    public int getMrowkiInterval() {
        return mrowkiSlider.getValue() * 1000;
    }

    public int getPrzedmiotyInterval() {
        return przedmiotySlider.getValue() * 100;
    }

    public boolean isStarted() {
        return started;
    }

    public StartMenu(JFrame parent) {
        super(parent, "Ustawienia Symulacji", true);
        setLayout(new GridLayout(5, 1, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        mrowiskaSlider = new JSlider(2, 10, 3);
        mrowkiSlider = new JSlider(1, 10, 4);
        przedmiotySlider = new JSlider(0, 50, 15);

        mrowiskaSlider.setMajorTickSpacing(1);
        mrowkiSlider.setMajorTickSpacing(1);
        przedmiotySlider.setMajorTickSpacing(5);

        mrowiskaSlider.setPaintTicks(true);
        mrowkiSlider.setPaintTicks(true);
        przedmiotySlider.setPaintTicks(true);

        mrowiskaSlider.setPaintLabels(true);
        mrowkiSlider.setPaintLabels(true);
        przedmiotySlider.setPaintLabels(true);

        add(new JLabel("Liczba mrowisk:"));
        add(mrowiskaSlider);
        add(new JLabel("Czas tworzenia mrówek (s):"));
        add(mrowkiSlider);
        add(new JLabel("Czas pojawiania się przedmiotów (0,1s):"));
        add(przedmiotySlider);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            started = true;
            dispose();
        });
        add(startButton);
    }
}

