import java.time.LocalDateTime;

public class OrderTG {
    //поля
    private Long id;
    private LocalDateTime date;
    private Client client;
    private Long price;
    private String comment;

    //пустой конструктор
    public OrderTG() {
    }
}