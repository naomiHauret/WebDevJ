

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {
  public static void main(String[] args) throws Exception {
    JEditorPane editor = new JEditorPane("text/html",
        "<H1>A!</H1><P><FONT COLOR=blue>blue</FONT></P>");
    editor.setEditable(false);
    JScrollPane pane = new JScrollPane(editor);
    JFrame f = new JFrame("HTML Demo");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(pane);
    f.setSize(800, 600);
    f.setVisible(true);
  }
}