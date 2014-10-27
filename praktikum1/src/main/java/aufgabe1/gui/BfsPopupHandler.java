package aufgabe1.gui;

import aufgabe1.Vertex;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Created by schlegel11 on 26.10.14.
 */
class BfsPopupHandler {
    private static final String START_MENU = "Start";
    private static final String STOP_MENU = "Stop";
    private final JMenuItem start = new JMenuItem(START_MENU);
    private final JMenuItem stop = new JMenuItem(STOP_MENU);
    private final JPopupMenu popupMenu = new JPopupMenu("BFS");
    private mxCell vertexCell;

    public BfsPopupHandler(mxGraphComponent.mxGraphControl graphControl) {
        addMouseListener(graphControl);
        popupMenu.add(start);
        popupMenu.add(stop);
    }

    public void handleBfsStartStopVertexCell(BiConsumer<mxCell, mxCell> consumer) {
        addActionListener(consumer, start, stop);
    }

    private void addMouseListener(mxGraphComponent.mxGraphControl graphControl) {
        graphControl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mxGraphComponent graphComponent = ((mxGraphComponent.mxGraphControl) e.getComponent()).getGraphContainer();
                vertexCell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
                if (Objects.nonNull(vertexCell) && vertexCell.getValue() instanceof Vertex) {
                    popupMenu.show(graphComponent, e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void addActionListener(BiConsumer<mxCell, mxCell> consumer, JMenuItem... menuItems) {
        for (JMenuItem menuItem : menuItems) {
            menuItem.addActionListener(e -> {
                mxCell vertexCellStart = (mxCell) start.getClientProperty(START_MENU);
                mxCell vertexCellStop = (mxCell) stop.getClientProperty(STOP_MENU);
                switch (e.getActionCommand()) {
                    case START_MENU: {
                        start.putClientProperty(START_MENU, vertexCell);
                        if (Objects.nonNull(vertexCellStop)) {
                            consumer.accept(vertexCell, vertexCellStop);
                            start.putClientProperty(START_MENU, null);
                            stop.putClientProperty(STOP_MENU, null);
                        }
                    }
                    break;

                    case STOP_MENU: {
                        stop.putClientProperty(STOP_MENU, vertexCell);
                        if (Objects.nonNull(vertexCellStart)) {
                            consumer.accept(vertexCellStart, vertexCell);
                            start.putClientProperty(START_MENU, null);
                            stop.putClientProperty(STOP_MENU, null);
                        }
                    }
                    break;
                }
            });
        }
    }
}
