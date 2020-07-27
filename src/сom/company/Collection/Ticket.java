package сom.company.Collection;

import сom.company.Networking.Client;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

/**
 * От данного класса создаются все Ticket`ы
 */

public class Ticket implements Comparable<Ticket>, Serializable {
    private static final long serialVersionUID = 30L;
    public long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    public String name; //Поле не может быть null, Строка не может быть пустой
    public Coordinates coordinates; //Поле не может быть null
    public Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    public Float price; //Поле не может быть null, Значение поля должно быть больше 0
    public String comment; //Строка не может быть пустой, Поле не может быть null
    public Boolean refundable; //Поле может быть null
    public TicketType type; //Поле не может быть null
    public Event event; //Поле не может быть null
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket( String name, Coordinates coordinates, Float price, String comment, Boolean refundable, TicketType type, Event event, Date creationDate,User user){
        this.name=name;
        this.coordinates=coordinates;
        if(price<=0){
            System.out.println(" Товарищ,постойте у вас обнаружено значение цены меньше 0");
            System.out.println("Высставляю минимальное значение");
            System.out.println("Вот только теперь");
            this.price=0.1F;
        }else
            this.price=price;
        this.comment=comment;
        this.refundable=refundable;
        this.type=type;
        this.event=event;
        this.creationDate= creationDate;

    }
    public Ticket(){}

    public Ticket(Event event) {
        this.event=event;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Event getEvent() {
        return event;
    }

    public TicketType getType() {
        return type;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public Float getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getComment() {
        return comment;
    }
    public static Date StringToDate(String string){
        try {
            Date date=new SimpleDateFormat("dd MMMM yyyy zzzz").parse(string);
            return date;
        }catch (ParseException e){
            System.out.println("Ошибка чтения даты");
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public int compareTo(Ticket o) {
        if(this.price>o.price) return 1;
        if(this.price<o.price)return -1;
        return 0;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString(){
        return "\n" + "ID: " + id
                + "\n" + "name: " + name
                + "\n" + "coordinatesX: " + coordinates.getX()
                + "\n" + "coordinatesY: " + coordinates.getY()
                + "\n" + "creationDate: " + creationDate
                + "\n" + "price: " + price
                + "\n" + "comment: " + comment
                + "\n" + "type: " + type
                + "\n" + "event Type: " + event.getEventType()
                + "\n" + "event Name: " + event.getName()
                + "\n" + "event ID : " + event.getId()
                + "\n" + "hashCode: " + hashCode()
                + "\n" + "LocalDate: "  + LocalDate.now()
                + "\n" + "Refundable: "  + getRefundable()
                + "\n" + "---------------------------------------";

    }

    public String print_Comment(){
        return "\n" +"comment: " + comment;
    }
    public String print_Price(){
        return "\n" +"price: " + price;
    }

    @Override
    public int hashCode(){
        final int prime=11;
        int result=1;
        result=prime*result+Math.round(price);
        result=prime*result+Math.round(coordinates.getX());
        return  result;
    }


    public static Ticket TicketBuilder(){
        Ticket element  = new Ticket();
        Event event = new Event();

        Scanner scan = new Scanner(System.in);
        System.out.println("Введите имя");
        String name = scan.nextLine();
        while (true) {
            if (name.isEmpty()) {
                System.out.println("Пустое имя.Попробуйте еще раз");
                name = scan.nextLine();
                element.name=name;
            } else break;
        }
        System.out.println("Введите координаты");
        System.out.println("Сначала вводится координата X,а после Y. Вводите координаты через space");
        String str_coords=scan.nextLine();
        float x ;
        double y ;
        while (true){
            try{
                String[] array=str_coords.split(" ",2);

                x=(float) Float.parseFloat(array[0]);
                y=(double) Double.parseDouble(array[1]);
                break;


            }catch (Exception e){
                System.out.println("Некоректно былла введена координата(ы)");
                str_coords=scan.nextLine();

            }

        }

        element.coordinates= new Coordinates(x,y);
        Coordinates coordinates=new Coordinates(x,y);

        System.out.println("Введите цену за билет");
        String cool_price=scan.nextLine();
        Float price;
        while (true){
            try {
                if(cool_price.isEmpty()){
                    System.out.println("Развбе билет может не иметь цену? Попробуйте еще раз");
                    cool_price=scan.nextLine();

                }
                price=Float.parseFloat(cool_price);
                element.price=price;
                break;
            }catch (Exception e){
                System.out.println("Не шалите.Введите цену еще раз");
                cool_price=scan.nextLine();
            }
        }

        System.out.println("Введите  комментарии или пожелания");
        String sub_comment=scan.nextLine();
        String comment;
        while (true){
            if(sub_comment.isEmpty()){
                System.out.println("Милсдарь,давай попробуем еще раз.Введи комментарий!");
                sub_comment=scan.nextLine();
                element.comment=sub_comment;
            }else break;
        }

        System.out.println("А как на счет возврата? ");
        System.out.println("Введи одно из двух: Да или Нет");
        String ref=scan.nextLine();
        Boolean refundable = null;
        while (true){
            if(ref.equalsIgnoreCase("Да")){
                refundable=true;
                element.refundable=refundable;
                break;
            }else if(ref.equalsIgnoreCase("Нет")){
                refundable=false;
                element.refundable=refundable;
                break;
            }else {

                System.out.println("Утвердите возвращаемость еще раз");
                ref=scan.nextLine();
                continue;
            }

        }


        System.out.println("Давайте поговорим о мироприятиях");
        System.out.println("Есть слудующие виды : BASEBALL, THEATRE_PERFOMANCE, EXPOSITION");
        String sub_event_Type=scan.nextLine();
        EventType eventType;
        while (true){
            if(sub_event_Type.equalsIgnoreCase(EventType.EXPOSITION.toString())){
                eventType=EventType.EXPOSITION;
                event.eventType=eventType;
                break;
            }else  if (sub_event_Type.equalsIgnoreCase(EventType.BASEBALL.toString())){
                eventType=EventType.BASEBALL;
                event.eventType=eventType;
                break;
            }else if (sub_event_Type.equalsIgnoreCase(EventType.THEATRE_PERFORMANCE.toString())){
                eventType=EventType.THEATRE_PERFORMANCE;
                event.eventType=eventType;
                break;
            }else{
                System.out.println("Некоректный вид мироприятия");
                sub_event_Type=scan.nextLine();
                continue;
            }
        }
        System.out.println("Введите имя ивента");
        String sub_event_name=scan.nextLine();
        String event_name;
        while (true){
            if(sub_event_name.isEmpty()){
                System.out.println("Давайте попробуем еще раз.Введите имя event`a");
                sub_event_name=scan.nextLine();
                event.name=sub_event_name;
            }else break;
        }

        System.out.println("А теперь о цене - какой тип билете желаем приобрести?");
        System.out.println("Имеются : VIP,USUAL,BUDGETARY, CHEAP" );
        String ticket_sub_type=scan.nextLine();
        TicketType ticketType;
        while (true){
            if(ticket_sub_type.equalsIgnoreCase(TicketType.BUDGETARY.toString())){
                ticketType=TicketType.BUDGETARY;
                element.type=ticketType;
                break;
            }else if(ticket_sub_type.equalsIgnoreCase(TicketType.VIP.toString())){
                ticketType=TicketType.VIP;
                element.type=ticketType;
                break;
            }else if(ticket_sub_type.equalsIgnoreCase(TicketType.USUAL.toString())){
                ticketType=TicketType.USUAL;
                element.type=ticketType;
                break;
            }else if (ticket_sub_type.equalsIgnoreCase(TicketType.CHEAP.toString())){
                ticketType=TicketType.CHEAP;
                element.type=ticketType;
                break;
            }else {
                System.out.println("Хммм...а таких билетов у нас нет .Давайте попробуем еще раз");
                ticket_sub_type=scan.nextLine();
                continue;
            }
        }


        Date creationDate= new Date();
        LocalDate date=LocalDate.now();
        event.date=date;
        element.creationDate=creationDate;

        Event event1 =new Event(sub_event_name,eventType,date);
        User user = Client.getUser();
        Ticket ticket =new Ticket(name,coordinates,price,sub_comment,refundable,ticketType,event1,creationDate, user);

        return  ticket;
    }

    public String DateToString(){
        DateFormat dateFormat=new SimpleDateFormat("dd MMMM yyyy zzzz");
        String strDate=dateFormat.format(getCreationDate());
        return strDate;
    }

    public  static String TicketToString(Ticket ticket,boolean god){
        StringBuilder sb = new StringBuilder();
        if(god){
            sb.append("ID: ");
        }
        sb.append(ticket.id);
        sb.append(";");

        if (god){
            sb.append("\nname: ");
        }
        sb.append(ticket.name);
        sb.append(";");
        if (god){
            sb.append("\ncoordinatesX: ");
        }
        sb.append(ticket.coordinates.getX());
        sb.append(";");

        if (god){
            sb.append("\ncoordinatesY: ");
        }
        sb.append(ticket.coordinates.getY());
        sb.append(";");
        if (god){
            sb.append("\ncreationDate: ");
        }
        sb.append(ticket.DateToString());
        sb.append(";");

        if(god){
            sb.append("\nprice: ");
        }
        sb.append(ticket.price.toString());
        sb.append(";");

        if(god){
            sb.append("\ncomment: ");
        }
        sb.append(ticket.comment);
        sb.append(";");

        if(god){
            sb.append("\ntype: ");
        }
        if(ticket.type==TicketType.VIP){
            sb.append("VIP");
        }else if(ticket.type==TicketType.CHEAP){
            sb.append("CHEAP");
        }else if(ticket.type==TicketType.BUDGETARY){
            sb.append("BUDGETARY");
        }else if(ticket.type==TicketType.USUAL){
            sb.append("USUAL");
        }else {
            sb.append("null");
        }
        sb.append(";");

        if(god){
            sb.append("\nevent Type: ");
        }
        if(ticket.event.eventType==EventType.EXPOSITION){
            sb.append("EXPOSITION");
        }else if(ticket.event.eventType==EventType.BASEBALL){
            sb.append("BASEBALL");
        }else if(ticket.event.eventType==EventType.THEATRE_PERFORMANCE){
            sb.append("THEATRE_PERFORMANCE");
        }else {
            sb.append("null");
        }
        sb.append(";");

        if(god){
            sb.append("\nevent Name: ");
        }
        sb.append(ticket.event.name);
        sb.append(";");

        if(god){
            sb.append("\nevent ID: ");
        }
        sb.append(ticket.event.id.toString());
        sb.append(";");

        if(god){
            sb.append("\nRefundable: ");
        }
        sb.append(ticket.refundable.toString());
        sb.append(";");
        if(god){
            sb.append("\nUsername: ");
        }
        sb.append(ticket.getUser().getName());
        sb.append(";");


        return sb.toString();



    }




    }//тупо метод add


