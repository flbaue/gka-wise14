package aufgabe1.gui;

import aufgabe1.Vertex;
import aufgabe1.algorithms.BreadthFirstSearch;
import aufgabe1.io.GraphIO;
import aufgabe1.utils.FileUtils;
import aufgabe1.utils.GraphUtils;
import com.alee.laf.WebLookAndFeel;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxStyleUtils;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.function.Consumer;

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
    private static final String WAY_FOUND = "Vertices: %s\nEdgeDistance: %s";
    private static final String FRAME_TITLE = "GKA-Graph-Application";
    private final GraphIO graphIO = new GraphIO();
    private final JFileChooser fileChooser = new JFileChooser();
    private Graph graph;

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

    private void initGraphXComponent() {
        StringBuilder stringBuilder = new StringBuilder();
        JGraphXAdapter jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(graph);
        mxGraphComponent graphComponent = new mxGraphComponent(jgxAdapter);
        setStandardCellStyle(jgxAdapter, GraphUtils.isDirectedGraph(graph));
        handleBfsShortestWay(jgxAdapter, graphComponent.getGraphControl(), v -> {
            stringBuilder.append(Objects.nonNull(v) ? String.format(WAY_FOUND, v.getThisAndPredecessors(), v.getDistance()) : NO_WAY_FOUND);
            JOptionPane.showMessageDialog(this, stringBuilder.toString(), MESSAGE_DIALOG_TITLE, JOptionPane.INFORMATION_MESSAGE);
            stringBuilder.delete(0, stringBuilder.length());
        });
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        layout.setRadius(450);
        layout.setMoveCircle(true);
        layout.execute(jgxAdapter.getDefaultParent());
        getContentPane().add(graphComponent);
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
                            initGraphXComponent();
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

    private void setStandardCellStyle(JGraphXAdapter jgxAdapter, boolean isDirected) {
        mxStylesheet style = jgxAdapter.getStylesheet();
        Map<String, Object> edgeStyle = new HashMap(style.getDefaultEdgeStyle());

        mxStyleUtils.setCellStyles(jgxAdapter.getModel(), jgxAdapter.getChildVertices(jgxAdapter.getDefaultParent()), mxConstants.STYLE_FILLCOLOR, "#ffffff");

        edgeStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        edgeStyle.put(mxConstants.STYLE_ENDARROW, isDirected ? mxConstants.ARROW_CLASSIC : mxConstants.NONE);
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");

        mxStylesheet stylesheet = new mxStylesheet();
        stylesheet.setDefaultEdgeStyle(edgeStyle);
        jgxAdapter.setStylesheet(stylesheet);
        repaint();
    }

    private void handleBfsShortestWay(JGraphXAdapter jgxAdapter, mxGraphComponent.mxGraphControl graphControl, Consumer<Vertex> consumer) {
        BfsPopupHandler bfsPopupHandler = new BfsPopupHandler(graphControl);
        Object[] cells = jgxAdapter.getChildVertices(jgxAdapter.getDefaultParent());
        Collection<Object> cellList = Arrays.asList(cells);
        mxStylesheet style = jgxAdapter.getStylesheet();
        Map<String, Object> vstyle = new HashMap(style.getDefaultVertexStyle());
        vstyle.put(mxConstants.STYLE_FILLCOLOR, "#889ee7");

        bfsPopupHandler.handleBfsStartStopVertexCell((v1, v2) -> {

            Vertex start = (Vertex) v1.getValue();
            Vertex stop = (Vertex) v2.getValue();
            Vertex shortest = null;
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
            }
            consumer.accept(shortest);

        });
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new WebLookAndFeel());
            JFrame mainFrame = MainFrame.newInstance();
            mainFrame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
}
