package org.jarexplorer;

import jfk.prism.ICallable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * This is a panel with a list box, which shows result of a search. It shows fully qualified paths to resources,
 * including inside the jar files.
 */
public class SearchResultsPanel extends JPanel
{
	private static final long serialVersionUID = 4810102451562305873L;
	private JList<Object> resultList = new JList<Object>();
    private DefaultListModel<Object> model;
    private TitledBorder border;
    ICallable call;
    private JButton exploreB, cleanB;

    /**
     * Constructor
     */
    public SearchResultsPanel()
    {
        this.resultList.setModel(model = new DefaultListModel<Object>());
        setLayout(new BorderLayout());
        setBorder(border = new TitledBorder("Search Results"));
        add(new JScrollPane(resultList), BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        southPanel.add(exploreB = new JButton("Explore"));
        exploreB.setEnabled(false);
        southPanel.add(cleanB = new JButton("Clean"));
        cleanB.setEnabled(false);
        add(southPanel, BorderLayout.SOUTH);
        addActionListeners();
    }

    /**
     * Wires listeners
     */
    private void addActionListeners()
    {
        //Construct popup menu
        final JPopupMenu menu = new JPopupMenu();
        JMenuItem copyMI = new JMenuItem("Copy");
        copyMI.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StringSelection stringSelection = new StringSelection(resultList.getSelectedValue().toString());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
            }
        });
        menu.add(copyMI);

        //this will popup a menu if there is something selected
        resultList.addMouseListener(new MouseAdapter()
        {

            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    exploreSelected();
                }
            }

            public void mousePressed(MouseEvent e)
            {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e)
            {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {

                    if (resultList.getSelectedValue() != null)
                    {
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        cleanB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                model.removeAllElements();
                SearchResultsPanel.this.repaint();
                border.setTitle("");
                cleanB.setEnabled(false);
            }
        });

        resultList.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (resultList.getSelectedValue() == null)
                {
                    exploreB.setEnabled(false);
                }
                else if(!resultList.getSelectedValue().toString().endsWith("/"))
                {
                    exploreB.setEnabled(true);
                }
                else
                {
                    exploreB.setEnabled(false);
                }
            }
        });

        resultList.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
               if(e.getKeyChar() == '\n')
                {
                    exploreSelected();
                }
            }
        });

        exploreB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                exploreSelected();
            }
        });
    }

    /**
     * will pop a corresponding dialog for a resource.
     */
    private void exploreSelected()
    {
        try
        {
            Class<?> c = (Class<?>) resultList.getSelectedValue();

            jfk.prism.Description description = c.getAnnotation(jfk.prism.Description.class);
            System.out.println(description.description());
            ICallable callable = (ICallable) c.newInstance();

            Prism cl =new Prism(callable, description, c.getName());

            JFrame frame1 = new JFrame();
            frame1.setTitle(cl.getName());
            frame1.setLayout(new GridLayout(5,1));
            call=cl.getCallable();
            JTextArea desc = new JTextArea();
            desc.setColumns(25);
            desc.setText(cl.getDescription().description());
            JTextField x1=new JTextField();
            x1.setText("x1");
            JTextField x2=new JTextField();
            x2.setText("x2");
            JTextField wynik=new JTextField();
            wynik.setText("wynik");
            JButton exe = new JButton();
            exe.setText("Execute");
            exe.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    wynik.setText(call.Call(new String[]{x1.getText(),x2.getText()}));
                }
            });
            frame1.add(desc);
            frame1.add(x1);
            frame1.add(x2);
            frame1.add(wynik);
            frame1.add(exe);
            frame1.setLocationRelativeTo(null);
            frame1.pack();
            frame1.setVisible(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            GUIUtil.messageBoxWithDetails("Blad", ex, javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }


    /**
     * Sets results for this search panel.
     *
     * @param title   - title of the results .
     * @param results - strings are expected.
     */
    public void setResults(String title, ArrayList<?> results)
    {
        model.removeAllElements();
        border.setTitle(title);

        for (int i = 0; i < results.size(); i++)
        {
            Object o = results.get(i);
            model.addElement(o);
        }
        cleanB.setEnabled(true);
        repaint();
    }

    public void clean()
    {
        model = new DefaultListModel<Object>();
        resultList.setModel(model);
    }
}
