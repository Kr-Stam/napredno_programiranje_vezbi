//package problems.messagesystem;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//class PartitionAssigner {
//    public static Integer assignPartition (Message message, int partitionsCount) {
//        return (Math.abs(message.key.hashCode())  % partitionsCount) + 1;
//    }
//}
//
//public class MessageBrokersTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        String date = sc.nextLine();
//        LocalDateTime localDateTime =LocalDateTime.parse(date);
//        Integer partitionsLimit = Integer.parseInt(sc.nextLine());
//        MessageBroker broker = new MessageBroker(localDateTime, partitionsLimit);
//        int topicsCount = Integer.parseInt(sc.nextLine());
//
//        //Adding topics
//        for (int i=0;i<topicsCount;i++) {
//            String line = sc.nextLine();
//            String [] parts = line.split(";");
//            String topicName = parts[0];
//            int partitionsCount = Integer.parseInt(parts[1]);
//            broker.addTopic(topicName, partitionsCount);
//        }
//
//        //Reading messages
//        int messagesCount = Integer.parseInt(sc.nextLine());
//
//        System.out.println("===ADDING MESSAGES TO TOPICS===");
//        for (int i=0;i<messagesCount;i++) {
//            String line = sc.nextLine();
//            String [] parts = line.split(";");
//            String topic = parts[0];
//            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
//            String message = parts[2];
//            if (parts.length==4) {
//                String key = parts[3];
//                try {
//                    broker.addMessage(topic, new Message(timestamp,message,key));
//                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            else {
//                Integer partition = Integer.parseInt(parts[3]);
//                String key = parts[4];
//                try {
//                    broker.addMessage(topic, new Message(timestamp,message,partition,key));
//                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//
//        System.out.println("===BROKER STATE AFTER ADDITION OF MESSAGES===");
//        System.out.println(broker);
//
//        System.out.println("===CHANGE OF TOPICS CONFIGURATION===");
//        //topics changes
//        int changesCount = Integer.parseInt(sc.nextLine());
//        for (int i=0;i<changesCount;i++){
//            String line = sc.nextLine();
//            String [] parts = line.split(";");
//            String topicName = parts[0];
//            Integer partitions = Integer.parseInt(parts[1]);
//            try {
//                broker.changeTopicSettings(topicName, partitions);
//            } catch (UnsupportedOperationException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        System.out.println("===ADDING NEW MESSAGES TO TOPICS===");
//        messagesCount = Integer.parseInt(sc.nextLine());
//        for (int i=0;i<messagesCount;i++) {
//            String line = sc.nextLine();
//            String [] parts = line.split(";");
//            String topic = parts[0];
//            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
//            String message = parts[2];
//            if (parts.length==4) {
//                String key = parts[3];
//                try {
//                    broker.addMessage(topic, new Message(timestamp,message,key));
//                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            else {
//                Integer partition = Integer.parseInt(parts[3]);
//                String key = parts[4];
//                try {
//                    broker.addMessage(topic, new Message(timestamp,message,partition,key));
//                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//
//        System.out.println("===BROKER STATE AFTER CONFIGURATION CHANGE===");
//        System.out.println(broker);
//
//
//    }
//}
//
//class Message{
//    LocalDateTime timestamp;
//    String message;
//    Integer partition;
//    public String key;
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("Message{");
//        sb.append("timestamp=").append(timestamp);
//        sb.append(", message='").append(message).append('\'');
//        sb.append('}');
//        return sb.toString();
//    }
//}
//
//class MessageBroker{
//    Map<String, List<Partition>> messages;
//}
//
//class Partition{}
//
//class Topic{
//    String name;
//    List<Message> messages;
//    int partitionCount;
//
//    public Topic(String name, int partitionCount) {
//        this.name = name;
//        this.partitionCount = partitionCount;
//    }
//
//    public void addMessage(Message message){
//        if(message.partition == null)
//            message.partition = PartitionAssigner.assignPartition(message, partitionCount);
//        messages.add(message);
//    }
//}
///*
//    Topic - класа за чување на пораки по теми. За секој Topic се чува името на темата, како и колекција од сите пораки распределени по партиции. Оваа класа треба да ги има следните методи:
//        Topic(String topicName, int partitionsCount) конструктор
//        void addMessage (Message message) throws PartitionDoesNotExistException - метод за додавање на нова порака во оваа тема.
//        Додавањето се прави во соодветна партиција која се чува како информација во пораката message.
//        Доколку таму не е специфицирана, треба да се искористи методот assignPartition од класата
//        PartitionAssigner која што е дадена во почетниот код.
//        Доколку не постои партицијата која е наведена во пораката да се фрли исклучок од тип PartitionDoesNotExistException.
//        void changeNumberOfPartitions (int newPartitionsNumber) throws UnsupportedOperationException - метод за промена на бројот на партиции.
//        Доколку се проба да се намали бројот на партиции да се фрли исклучок како во потписот на функцијата.
//        String toString() - метод којшто ќе дава toString репрезентација на topic-oт така што за него ќе се испечати неговото име i бројот на партиции во формат Topic: %10s Partitions: %5d\n, а потоа во нови редови сите пораки содржините на партициите. Партициите да се сортирани според нивниот реден број.
//    MessageBroker - класа за чување на повеќе теми (објекти од класата Topics). Дополнително во оваа класа се чуваат и стартниот датум, како и максималниот капацитет на секоја партиција за сите теми во овој брокер. Да се имплементираат следните методи:
//        MessageBroker(LocalDateTime minimumDate, Integer capacityPerTopic)- конструктор
//        void addTopic (String topic, int partitionsCount) - метод за додавање на нов topic со одреден број на партиции во него. не смее да се додаде topic со исто име.
//        void addMessage (String topic, Message message) - метод за додавање на порака на определен topic.
//        void changeTopicSettings (String topic, int partitionsCount) - метод за промена на бројот на партиции на определен topic
//        String toString() - метод којшто ќе дава String нотација на брокерот. Најпрво ќе се испечати колку topic-и има, а потоа за сите topic-и ќе се даде нивната toString репрезентација. Topic-ите да се сортирани според нивното име.
//
//Визуелизација на еден topic
// */