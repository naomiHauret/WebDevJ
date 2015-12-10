import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GetDeletedTab
{
  private static JTabbedPane tabbedPane = new JTabbedPane();
  private static String[] workDays =
  {
    "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
  };
  private static int currentIndex = -1;
  private static int previousIndex = -1;
  
  private static void createAndShowUI()
  {
    for (String day : workDays)
    {
      tabbedPane.addTab(day, new JPanel());
    }
    currentIndex = tabbedPane.getSelectedIndex();
    
    tabbedPane.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent e)
      {
        int index = tabbedPane.getSelectedIndex();
        if (index != currentIndex)
        {
          previousIndex = currentIndex;
          currentIndex = index;
          if (previousIndex != -1)
          {
            String currTabTitle = tabbedPane.getTitleAt(currentIndex);
            String prevTabTitle = tabbedPane.getTitleAt(previousIndex);
            System.out.println("Previous Tab Title was: " + prevTabTitle );
            System.out.println("Current Tab Title is: " + currTabTitle);
            System.out.println();
          }
        }
        
      }
    });
    
    JFrame frame = new JFrame("GetDeletedTab");
    JPanel cPane = (JPanel)frame.getContentPane();
    cPane.setPreferredSize(new Dimension(600, 400));
    cPane.add(tabbedPane);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowUI();
      }
    });
  }
}