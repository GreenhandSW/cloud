package io.github.greenhandsw.binlogreader;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.IOException;

public class BinLogReader {
    public static void main(String[] args) {
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "canal", "cloud");

        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            EventData data=event.getData();
            if(data instanceof TableMapEventData tableMapEventData){
                System.out.println("Table:");
                System.out.printf("%s:[%s-%s]\n", tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable());
            }
            if(data instanceof UpdateRowsEventData){
                System.out.println("Update:");
                System.out.printf("%s", data);
            }else if(data instanceof WriteRowsEventData){
                System.out.println("Insert:");
                System.out.printf("%s", data);
            }else if(data instanceof DeleteRowsEventData){
                System.out.println("Delete:");
                System.out.printf("%s", data);
            }
        });
        try {
            client.connect();
            System.out.println(client.getGtidSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//        client.setServerId(0);
//
//        client.registerEventListener(event -> {
//            EventData data=event.getData();
//            if(data instanceof TableMapEventData tableMapEventData){
//                System.out.println("Table:");
//                System.out.printf("%s:[%s-%s]\n", tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable());
//            }
//            if(data instanceof UpdateRowsEventData){
//                System.out.println("Update:");
//                System.out.printf("%s", data);
//            }else if(data instanceof WriteRowsEventData){
//                System.out.println("Insert:");
//                System.out.printf("%s", data);
//            }else if(data instanceof DeleteRowsEventData){
//                System.out.println("Delete:");
//                System.out.printf("%s", data);
//            }
//        });
//        try {
//            client.connect();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//}
