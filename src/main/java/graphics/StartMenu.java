package graphics;

import javax.swing.*;
import java.awt.*;

public class StartMenu extends JDialog {
    private final JSlider mrowiskaSlider;
    private final JSlider mrowkiSlider;
    private final JSlider przedmiotySlider;
    private boolean started = false;
    private final JSlider czasSlider;
    public boolean choice = false;
    private boolean mode = false;


    public int getCzasInterval() { return czasSlider.getValue() * 1000; }

    public boolean getChoice() { return choice; }

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
        setLayout(new GridLayout(9, 1, 10, 10));
        setSize(600, 500);
        setLocationRelativeTo(parent);





        mrowiskaSlider = new JSlider(2, 10, 3);
        mrowkiSlider = new JSlider(1, 10, 4);
        przedmiotySlider = new JSlider(0, 50, 15);
        czasSlider = new JSlider(15, 180, 60);


        mrowiskaSlider.setMajorTickSpacing(1);
        mrowkiSlider.setMajorTickSpacing(1);
        przedmiotySlider.setMajorTickSpacing(5);
        mrowiskaSlider.setPaintTicks(true);
        mrowkiSlider.setPaintTicks(true);
        przedmiotySlider.setPaintTicks(true);
        mrowiskaSlider.setPaintLabels(true);
        mrowkiSlider.setPaintLabels(true);
        przedmiotySlider.setPaintLabels(true);
        czasSlider.setMajorTickSpacing(15);
        czasSlider.setPaintTicks(true);
        czasSlider.setPaintLabels(true);


        add(new JLabel("Czas trwania symulacji: (s)"));
        add(new JLabel("Liczba mrowisk:"));
        add(mrowiskaSlider);
        add(new JLabel("Czas tworzenia mrówek (s):"));
        add(mrowkiSlider);
        add(new JLabel("Czas pojawiania się przedmiotów (0,1s):"));
        add(przedmiotySlider);

        czasSlider.setEnabled(false);
        add(czasSlider);
        add(new JLabel("Wybierz tryb gry:"));



        JButton LewoButton = new JButton("Battle Royale");
        JButton PrawoButton = new JButton("Timer");
        JButton StartButton = new JButton("Start");
        StartButton.setEnabled(false);

        LewoButton.addActionListener(e -> {
            choice = false;
            mode = true;
            StartButton.setEnabled(true);
        });

        PrawoButton.addActionListener(e -> {
            choice = true;
            mode = true;
            StartButton.setEnabled(true);
            czasSlider.setEnabled(true);

        });

        add(LewoButton);
        add(PrawoButton);

        StartButton.setEnabled(false); // domyślnie zablokowany

        StartButton.addActionListener(e -> {
            if (mode){
                started = true;
                dispose();
            }
        });




        add(StartButton);


    }


}