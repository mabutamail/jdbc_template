import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramStart {

    private final static String URL =
            "jdbc:mariadb://mysql.dragon660.myjino.ru:3306/dragon660_javadb";
    private final static String USER = "032670002_javadb";
    private final static String PASS = "javadb";

    public static void main(String[] args) {

        //Загружаем драйвер, предпочтительно использовать DataSource!
        //устаревший способ:
        //Driver drv = new org.mariadb.jdbc.Driver();
        //DriverManager.registerDriver(drv);
        try {
            //Загружаем драйвер с помощью Class.forName
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Драйвер подключен");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка! Драйвер не найден");
            e.printStackTrace();
        }

        try {
            //Создаём соединение
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Соединение установлено");

            //Для использования SQL запросов существуют 3 типа объектов:
            //1.Statement: используется для простых случаев без параметров
            Statement statement = connection.createStatement();

            System.out.println("Вставить запись (create-insert)");
            statement.executeUpdate("INSERT INTO paper(name) values('name gloss')");
            System.out.println("Прочесть записи (read-select)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paper");
            System.out.println("Обновить запись (update-update)");
            statement.executeUpdate("UPDATE paper SET name = 'admin' where id = 4");

            //Вывод результата
            //resultSet это указатель на первую строку с выборки, чтобы вывести данные мы
            //будем использовать метод next(), с помощью которого переходим к следующему элементу
            System.out.println("Выводим statement");
            while (resultSet.next()) {
                System.out.println("Номер в выборке #" + resultSet.getRow()
                        + "\t Номер в базе #" + resultSet.getInt("id")
                        + "\t" + resultSet.getString("name")
                        + "\t" + resultSet.getInt("weight"));
            }

            connection.close();
            System.out.println("Соединение закрыто");

        } catch (SQLException e) {
            System.out.println("Ошибка соединения или SQL");
            e.printStackTrace();
        }

//        finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                    System.out.println("Соединение закрыто");
//                } catch (SQLException ex) {
//                    Logger.getLogger(ProgramStart.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }

        System.out.println("jdbc template");
    }
}