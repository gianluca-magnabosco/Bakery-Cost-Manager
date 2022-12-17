package biscoitagem;

import java.io.Serializable;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

public class TableComboModel<E> extends AbstractListModel<E> implements MutableComboBoxModel<E>, Serializable {
    private List<E> values = null;
    private E selected = null;
    
    public TableComboModel(List<E> values) {
        this.values = values;
    }

    @Override
    public int getSize() {
        return values.size();
    }

    @Override
    public E getElementAt(int index) {
        return values.get(index);
    }

    @Override
    public void addElement(E item) {
        values.add(item);
    }

    @Override
    public void removeElement(Object obj) {
        values.remove(obj);
    }

    @Override
    public void insertElementAt(E item, int index) {
        values.add(index, item);
    }

    @Override
    public void removeElementAt(int index) {
        values.remove(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selected = this.values.get(0);
    }

    @Override
    public Object getSelectedItem() {
        return this.selected;
    }    
}
