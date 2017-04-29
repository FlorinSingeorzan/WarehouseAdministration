package dao;


import connection.ConnectionFactory;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {

    Class<T> specificGenericClass;
    private final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    /**
     * Constructorul preia clasa ce il va inlocui pe T la executie
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unckecked")
    public AbstractDAO() throws ClassNotFoundException {
        this.specificGenericClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Metoda de insert:
     * Se creaza o conexiune la baza  de date
     * Pentru fiecare domeniu de pe clasa T se cheama getter-ul si prin invocarea lui se adauga in locul
     * caracterului '?' din statement si executa sitagma SQL data
     * @param t
     * @param insertStatementString
     * @return  1 in caz de succes, 0 altfel
     */
    public int insert(T t, String insertStatementString) {
        Connection dbConnection = ConnectionFactory.getConnection();
        int no = 1;
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        java.beans.PropertyDescriptor objPropertyDescriptor;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            Field[] fields = specificGenericClass.getDeclaredFields();
            for (Field f : fields) {
                try {
                    objPropertyDescriptor = new java.beans.PropertyDescriptor(f.getName(), specificGenericClass);
                    Method method = objPropertyDescriptor.getReadMethod();
                    if (!method.getReturnType().isPrimitive()) {
                        insertStatement.setString(no, (String) method.invoke(t, null));
                    } else {
                        String y = method.invoke(t, null).toString();
                        insertStatement.setInt(no, Integer.parseInt(y));
                    }
                    no++;
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            }
            insertStatement.executeUpdate();
                insertedId=1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Wrong: " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Parametrul (?) tipului formal al statement-ului de executat este inlocuit cu id pentru care se cauta datele
     * @param toFind
     * @param id
     * @return un obiect de instanta T dupa reusirea interogarii
     */
    public T findById(String toFind, int id) {
        T actual = null;
        try {
            actual = specificGenericClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet resultSet = null;
        java.beans.PropertyDescriptor objPropertyDescriptor = null;
        Field[] fields = specificGenericClass.getDeclaredFields();
        Method method;
        String toAddString;
        int toAddInt;
        Object toAdd;

        try {
            findStatement = dbConnection.prepareStatement(toFind);
            fields = specificGenericClass.getDeclaredFields();
            findStatement.setInt(1, id);
            resultSet = findStatement.executeQuery();
            resultSet.next();
            for (Field f : fields) {
                objPropertyDescriptor = new java.beans.PropertyDescriptor(f.getName(), specificGenericClass);
                method = objPropertyDescriptor.getWriteMethod();
                toAdd = resultSet.getObject(f.getName());

                if (f.getType().isPrimitive()) {
                    method.invoke(actual, (Integer) toAdd);
                } else {
                    method.invoke(actual, (String) toAdd);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:findId " + e.getMessage());
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }

        return actual;

    }

    /**
     * Metoda incearca se prelucreze baza de date printr-un statement de delete in funtie de id-ul care se doreste eliminat
     * @param deleteStatement
     * @param id
     * @return setul de executie
     */
    public int delete(String deleteStatement, int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement delStatement = null;
        int resultSet = 0;
        try {
            delStatement = dbConnection.prepareStatement(deleteStatement);
            delStatement.setInt(1, id);
            resultSet = delStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Wrong " + e.getMessage());
        } finally {
            ConnectionFactory.close(delStatement);
            ConnectionFactory.close(dbConnection);
        }
        return resultSet;
    }

    /**
     * Executa update pe baza de date aferenta obiectului T
     * Pentru fiecare field al obiectului t se obtine metoda de get, se insereaza in statement
     * si se executa.
     *
     * @param t
     * @param updateString
     * @return -1 in caz de eroare si 1 altfel
     */
    public int update(T t, String updateString) {
        Connection dbConnection = ConnectionFactory.getConnection();
        int done=-1;
        PreparedStatement updateStatement = null;
        java.beans.PropertyDescriptor objPropertyDescriptor;
        try {
            updateStatement = dbConnection.prepareStatement(updateString, Statement.RETURN_GENERATED_KEYS);
            Field[] fields = specificGenericClass.getDeclaredFields();

            int no = fields.length;
            for (Field f : fields) {
                try {
                    objPropertyDescriptor = new java.beans.PropertyDescriptor(f.getName(), specificGenericClass);
                    Method method = objPropertyDescriptor.getReadMethod();
                    if (!method.getReturnType().isPrimitive()) {
                        updateStatement.setString(no, (String) method.invoke(t, null));
                    } else {
                        String y = method.invoke(t, null).toString();
                        updateStatement.setInt(no, Integer.parseInt(y));
                    }
                    no++;
                    if (no == fields.length+1) {
                        no = 1;
                    }
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
            }
            updateStatement.executeUpdate();
            done=1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Wrong: " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return done;
    }


}




