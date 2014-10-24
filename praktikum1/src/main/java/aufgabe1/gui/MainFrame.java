package aufgabe1.gui;

import aufgabe1.io.GraphIO;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by schlegel11 on 24.10.14.
 */
public class MainFrame extends JFrame {
    private static final String MENU_FILE = "File";
    private static final String MENU_ITEM_LOAD = "Load";
    private static final String MENU_ITEM_SAVE = "Save";
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
    private final GraphIO graphIO = new GraphIO();
    private final JFileChooser fileChooser = new JFileChooser();

    private MainFrame() throws HeadlessException {
        init();
    }

    public static JFrame newInstance() {
        return new MainFrame();
    }

    private void init() {

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU_FILE);
        JMenuItem loadItem = new JMenuItem(MENU_ITEM_LOAD);
        JMenuItem saveItem = new JMenuItem(MENU_ITEM_SAVE);

        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(loadItem);
        menu.add(saveItem);

        addActionListener(loadItem);
        addActionListener(saveItem);

        setSize(DEFAULT_SIZE);
    }


    private void addActionListener(JMenuItem menuItem) {
        menuItem.addActionListener(e -> {
            switch (e.getActionCommand()) {
                case (MENU_ITEM_LOAD): {
                    System.out.println(MENU_ITEM_LOAD);
                    int returnVal = fileChooser.showOpenDialog(this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();

                        ListenableGraph graph = ListenableGraphFactory.createFromGraph(graphIO.readGraphFromFile(file));
                        JGraphXAdapter jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(graph);
                        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);
                        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
                        getContentPane().add(graphComponent);
                        layout.execute(jgxAdapter.getDefaultParent());

                    } else {
                        System.out.println("Canceled");
                    }

                }
                break;
                case (MENU_ITEM_SAVE): {
                    System.out.println(MENU_ITEM_SAVE);
                }
                break;
            }
            
            validate();
        });
    }

    public static void main(String[] args) {

        JFrame mainFrame = MainFrame.newInstance();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
