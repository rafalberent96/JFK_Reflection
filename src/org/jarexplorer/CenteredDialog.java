package org.jarexplorer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 *   Subclasses of this class will be displayed in the center of the screen.   The dialog will be disposed if
 * 'Escape' button is closed.
 */
public class CenteredDialog extends JDialog
{
	private static final long serialVersionUID = -226807543561221645L;

	/**
     *  Constructor
     * @param   owner  owner frame
     * @param   title  title text - will be displayed on the title bar
     * @param   modal  true if modal, false otherwise
     */
    public CenteredDialog(Frame owner, String title, boolean modal)
    {
        super(owner, title, modal);
        Action escape = new AbstractAction()
        {
			private static final long serialVersionUID = -127522750497187952L;

			public void actionPerformed(java.awt.event.ActionEvent arg1)
            {
                dispose();
            }
        };
        ((JComponent)getContentPane()).registerKeyboardAction(escape, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ((JComponent)getContentPane()).registerKeyboardAction(escape, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_FOCUSED);
    }

    /**
     *  Constructor
     * @param   owner  owner dialog
     * @param   title  title text - will be displayed on the title bar
     * @param   modal  true if modal, false otherwise
     */
    public CenteredDialog(Dialog owner, String title, boolean modal)
    {
        super(owner, title, modal);
        Action escape = new AbstractAction()
        {
			private static final long serialVersionUID = -2729876259930736280L;

			public void actionPerformed(java.awt.event.ActionEvent arg1)
            {
                dispose();
            }
        };
        ((JComponent)getContentPane()).registerKeyboardAction(escape, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ((JComponent)getContentPane()).registerKeyboardAction(escape, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_FOCUSED);
    }


    /**
     *  Overrides Component.setVisible(). Contains code for centering.
     * @param   arg1  true to display , false to hide
     */
    public void setVisible(boolean arg1)
    {
        GUIUtil.centerWindow(this);
        super.setVisible(arg1);
    }
}
