package presentation;




import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clasa foloseste parametrul generic T, care impreuna cu strategia denumita "reflexion"
 * opereaza asupra tabelelor din fereastra grafica
 * @param <T>
 */
public class GenericData<T> {


    private Class<T> genericType;


    /**
     * Constructorul incearca sa obtina tipul clasei cu care va fi inlocuit T in timpul executiei
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unckecked")
    protected GenericData() throws ClassNotFoundException {
        this.genericType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Pentru fiecare field din clasa T se preia metoda de get si folosind model.addRow
     * se insereaza in JTabel
     * @param t
     * @param model
     */
    public void insertToTable(T t, DefaultTableModel model) {
        int no;
        java.beans.PropertyDescriptor objPropertyDescriptor;
        Field[] fields = genericType.getDeclaredFields();
        Object[] rows=new Object[fields.length];
        for (no=0;no<fields.length;no++) {
            try {
                objPropertyDescriptor = new java.beans.PropertyDescriptor(fields[no].getName(), genericType);
                Method method = objPropertyDescriptor.getReadMethod();
                String addedString = method.invoke(t, null).toString();
                rows[no] = addedString;
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        model.addRow(rows);
    }

    /**
     * Clasa parcurhe multime (1, Integer.MAX_VALUE) si verifica daca id corespunzator celui care se doreste eliminat
     * este gasit, fiind eliminat prin removeRow
     * @param toDelete
     * @param model
     */
  public void deleteFromTable(int toDelete,DefaultTableModel model) {
      int i;
      for (i = 0; i < Integer.MAX_VALUE; i++) {
          try {
              if (Integer.parseInt(model.getValueAt(i, 0).toString()) == toDelete) {
                  model.removeRow(i);
                  break;
              }
          } catch (Exception ignored) {

          }
      }
  }
}
