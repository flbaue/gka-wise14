package aufgabe1.gui;

import aufgabe1.Vertex;
import aufgabe1.algorithms.BreadthFirstSearch;
import aufgabe1.io.GraphIO;
import aufgabe1.utils.FileUtils;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by schlegel11 on 24.10.14.
 */
public class MainFrame extends JFrame {
    private static final String MENU_FILE = "File";
    private static final String MENU_ITEM_LOAD = "Load";
    private static final String MENU_ITEM_SAVE = "Save";
    private static final String FILE_DESCRIPTION = "GraphFiles";
    private static final String FILE_SUFFIX = "txt";
    private static final String NULL_GRAPH = "No Graph is loaded.";
    private static final String MESSAGE_DIALOG_TITLE = "Info";
    private static final String NO_WAY_FOUND = "No way found.";
    private static final String WAY_FOUND = "Vertices: %s EdgeDistance: %s";
    private static final String FRAME_TITLE = "GKA-Graph-Application";
    private final GraphIO graphIO = new GraphIO();
    private final JFileChooser fileChooser = new JFileChooser();
    private ListenableGraph graph;

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

        fileChooser.setFileFilter(new FileNameExtensionFilter(FILE_DESCRIPTION, FILE_SUFFIX));
        setTitle(FRAME_TITLE);

        addActionListener(loadItem);
        addActionListener(saveItem);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }


    private void addActionListener(JMenuItem menuItem) {
        menuItem.addActionListener(e -> {
            try {
                switch (e.getActionCommand()) {
                    case MENU_ITEM_LOAD: {
                        System.out.println(MENU_ITEM_LOAD);

                        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                            File file = fileChooser.getSelectedFile();
                            graph = ListenableGraphFactory.createFromGraph(graphIO.readGraphFromFile(file));

                            JGraphXAdapter jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(graph);
                            mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);

                            setStandardCellStyle(jgxAdapter);
                            handleBfsShortestWay(jgxAdapter, graphComponent.getGraphControl());

                            mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
                            layout.setRadius(400);
                            layout.setMoveCircle(true);
                            layout.execute(jgxAdapter.getDefaultParent());
                            getContentPane().add(graphComponent);

                        }
                    }
                    break;
                    case MENU_ITEM_SAVE: {
                        System.out.println(MENU_ITEM_SAVE);
                        Objects.requireNonNull(graph, NULL_GRAPH);
                        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                            File file = fileChooser.getSelectedFile();
                            graphIO.saveGraphAsFile(graph, FileUtils.setFileSuffix(file, FILE_SUFFIX));
                        }
                    }
                    break;
                }
                validate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), MESSAGE_DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void setStandardCellStyle(JGraphXAdapter jgxAdapter) {
        mxStylesheet style = jgxAdapter.getStylesheet();
        Map<String, Object> vstyle = new HashMap(style.getDefaultVertexStyle());
        vstyle.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
        for (Object elem : jgxAdapter.getChildVertices(jgxAdapter.getDefaultParent())) {
            mxCell cell = (mxCell) elem;
            jgxAdapter.getView().getState(cell).setStyle(vstyle);
            repaint();
        }
    }

    private void handleBfsShortestWay(JGraphXAdapter jgxAdapter, mxGraphComponent.mxGraphControl graphControl) {

        StringBuilder stringBuilder = new StringBuilder();
        BfsPopupHandler bfsPopupHandler = new BfsPopupHandler(graphControl);
        Object[] cells = jgxAdapter.getChildVertices(jgxAdapter.getDefaultParent());
        Collection<Object> cellList = Arrays.asList(cells);
        mxStylesheet style = jgxAdapter.getStylesheet();
        Map<String, Object> vstyle = new HashMap(style.getDefaultVertexStyle());
        vstyle.put(mxConstants.STYLE_FILLCOLOR, "#000000");

        bfsPopupHandler.handleBfsStartStopVertexCell((v1, v2) -> {
            Vertex start = (Vertex) v1.getValue();
            Vertex stop = (Vertex) v2.getValue();
            Vertex shortest = null;
            setStandardCellStyle(jgxAdapter);
            BreadthFirstSearch iterator = new BreadthFirstSearch(graph, start);

            for (Vertex vertex : iterator) {
                if (vertex.equals(stop)) {
                    shortest = vertex;
                    break;
                }
            }
            if (Objects.nonNull(shortest)) {
                for (Vertex vertex : shortest.getThisAndPredecessors()) {
                    mxCell cell = (mxCell) cellList.stream().filter(c -> ((mxCell) c).getValue().equals(vertex)).findFirst().get();
                    jgxAdapter.getView().getState(cell).setStyle(vstyle);
                    repaint();
                }
                stringBuilder.append(String.format(WAY_FOUND, shortest.getThisAndPredecessors().stream().map(Object::toString).distinct()
                        .collect(Collectors.joining("<-")), shortest.getDistance()));
            } else {
                stringBuilder.append(NO_WAY_FOUND);
            }
            JOptionPane.showMessageDialog(this, stringBuilder.toString(), MESSAGE_DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
        });

    }

    public static void main(String[] args) {

        JFrame mainFrame = MainFrame.newInstance();
        mainFrame.setVisible(true);

    }
}
