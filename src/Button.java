import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource.MatteBorderUIResource;
import javax.swing.text.Document;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionEvent;


public class Button extends JButton{
    //ATTRIB
    private ImageIcon icone;
    private ActionListener swapToListListener;
    private ActionListener swapToMyClubListener;
    private ActionListener swapToSettingsListener;
    //CONSTR
    // public Button(String imgPath) { -> constr avec image
    //     this.icone = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT));
    //     this.setIcon(this.icone);
    // }
    public Button(String label) {
        super(label);
        init();
    }
    public Button(ImageIcon icone) {
        this.icone = icone;
        init();
    }
    public Button() {
        init();
    }
    //METH

    private void init(){
        this.setContentAreaFilled(false);
        // this.setBorderPainted(false);
    }

    public void addSwapToListListener()
    {

        this.swapToListListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                swapToList();
            }
        };
        this.addActionListener(swapToListListener);
    }

    public void addSwapToMyClubListener()
    {
        this.swapToMyClubListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                swapToMyClub();
            }
        };
        this.addActionListener(swapToMyClubListener);
    }

    public void addSwapToSettingsListener()
    {
        this.swapToSettingsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                swapToSettings();
            }
        };
        this.addActionListener(swapToSettingsListener);
    }

    public void addModClubWindowListener()
    {
        this.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                View.getInstance().openModClubWindow();
            }
        }
        );
    }
    public void addNewClubWindowListener()
    {
        this.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                View.getInstance().openNewClubWindow();
            }
        }
        );
    }

    public void removeSwapToListListener(){
        this.removeActionListener(this.swapToListListener);
    }
    public void removeSwapToMyClubListener(){
        this.removeActionListener(this.swapToMyClubListener);
    }
    public void removeSwapToSettingsListener(){
        this.removeActionListener(this.swapToSettingsListener);
    }



    public void swapToList(){
        View.getInstance().setListVisible();
    }
    public void swapToMyClub(){
        View.getInstance().setMyClubVisible();

    }
    public void swapToSettings(){
        View.getInstance().setSettingsVisible();

    }


    //fonction add 
    // private void init(String wavPath){ 
    //     File wav = new File(wavPath);
    //     AudioInputStream stream;
    //     try {
    //         stream = AudioSystem.getAudioInputStream(wav);
    //         Clip clip = AudioSystem.getClip();
    //         clip.open(stream);
    //         ActionListener listener = new ActionListener() {
    //             public void actionPerformed(ActionEvent event) {
    //                 clip.stop();
    //                 clip.setMicrosecondPosition(0);
    //                 clip.start();
    //             }
    //         };
    //         this.addActionListener(listener);
    //     } catch (UnsupportedAudioFileException | IOException e) {
    //         System.out.println("Error, couldn't get audioinputstream");
    //         e.printStackTrace();
    //     } catch (LineUnavailableException e) {
    //         System.out.println("Error, couldn't open clip");
    //         e.printStackTrace();
    //     }
    // }

    //Getset

    public ImageIcon getIcon(){
        return this.icone;
    }
    public void setIcon(ImageIcon icone){
        this.icone = icone;
    }

}