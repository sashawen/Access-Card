package ymss.csc.views.order;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ymss.csc.models.FoodItem;

public class ItemMenuPanel extends JPanel implements Scrollable {

	List<FoodItem> menu;

	public ItemMenuPanel(List<FoodItem> menu) {
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(0, 5, 0, 5));

		this.menu = menu;
		redraw();
	}

	private void redraw() {
		removeAll();

		Iterator<FoodItem> it = menu.iterator();
		while (it.hasNext()) {
			FoodItem item = it.next();
			addItem(item);
		}

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.SOUTH;
		gc.gridx = 0;
		gc.gridy = GridBagConstraints.RELATIVE;
		gc.weighty = 1.0;
		add(new JPanel(), gc);
		
		repaint();
		revalidate();
	}

	private void addItem(FoodItem item) {
		ItemPanel itemPanel = new ItemPanel(item);

		itemPanel.addSelectListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FoodItem item = itemPanel.getItem();
				itemSelected(item);
			}
		});

		GridBagConstraints cs = new GridBagConstraints();
		cs.gridx = 0;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 1.0;
		cs.anchor = GridBagConstraints.NORTH;
		add(itemPanel, cs);
	}

	public interface SelectionListener {
		public void itemSelected(FoodItem item);
	}

	private Set<SelectionListener> listeners = new HashSet<SelectionListener>();

	public void addSelectionListener(SelectionListener l) {
		listeners.add(l);
	}

	public void deleteSelectionListener(SelectionListener l) {
		listeners.remove(l);
	}

	private void itemSelected(FoodItem item) {
		for (SelectionListener l : listeners) {
			l.itemSelected(item);
		}
	}

	public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
       return 10;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return ((orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width) - 10;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

}
