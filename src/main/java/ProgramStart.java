import java.sql.*;

public class ProgramStart {
    private final static String URL =
            "jdbc:mariadb://mysql.dragon660.myjino.ru:3306/dragon660_javadb";
    private final static String USER = "032670002_javadb";
    private final static String PASS = "javadb";

    public static void main(String[] args) {
        Connection connection = null;
        //Загружаем драйвер, предпочтительно использовать DataSource!
        //устаревший способ:
        //Driver drv = new org.mariadb.jdbc.Driver();
        //DriverManager.registerDriver(drv);
        try {
            //Загружаем драйвер с помощью Class.forName
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("драйвер подключен");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка! драйвер не найден");
            e.printStackTrace();
        }

        try {
            //Создаём соединение
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("соединение установлено");

            //Для использования SQL запросов существуют 3 типа объектов:
            //1.Statement: используется для простых случаев без параметров
            Statement statement = connection.createStatement();
            System.out.println("вставить запись (create-insert)");
            statement.executeUpdate("INSERT INTO paper(name) values('name gloss')");
            System.out.println("прочесть записи (read-select)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM paper");
            System.out.println("обновить запись (update-update)");
            statement.executeUpdate("UPDATE paper SET name = 'admin' where id = 1");
            System.out.println("Обновить запись (update-update)");
            statement.executeUpdate("UPDATE paper SET name = 'admin' where id = 4");

            //Вывод результата
            //resultSet это указатель на первую строку с выборки, чтобы вывести данные мы
            //будем использовать метод next(), с помощью которого переходим к следующему элементу
            System.out.println("выводим statement");
            while (resultSet.next()) {
                System.out.println("номер в выборке #" + resultSet.getRow()
                        + "\t номер в базе #" + resultSet.getInt("id")
                        + "\t" + resultSet.getString("name")
                        + "\t" + resultSet.getInt("weight"));
            }
        } catch (SQLException e) {
            System.out.println("ошибка соединения или SQL");
            e.printStackTrace();
        }
        finally {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("соединение закрыто");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("jdbc template");
    }
}