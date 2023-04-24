import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelayedDocumentListener implements DocumentListener{
    private ActionListener actionListener;
    private String text;
    private Timer timer;

    public DelayedDocumentListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        timer = new Timer(1000, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
                                            actionListener.actionPerformed(evt);
                                        }
                                    });
        timer.setRepeats(false);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        doCheck(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        doCheck(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        doCheck(e);
    }

    protected void doCheck(DocumentEvent e) {
        try {
            Document doc = e.getDocument();
            text = doc.getText(0, doc.getLength());
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        timer.restart();
    }

}