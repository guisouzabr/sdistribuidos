/**
 * Created by Guilherme sd on 5/21/17.
 */
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;

import grafodb.*;
import java.util.HashMap;

public class GrafosDbServer
{
    //versão multi-thread
    public static void main(String [] args){
        try {
            TServerSocket serverTransport = new TServerSocket(9000);
            GrafosHandler handler = new GrafosHandler();
            grafodb.Operations.Processor processor = new grafodb.Operations.Processor(handler);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Servidor conectado com sucesso!");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* //Versão single thread
    public static void main(String [] args)
    {
        try{
            TServerTransport serverTransport = new TServerSocket(9000);
            GrafosHandler handler = new GrafosHandler();
            grafodb.Operations.Processor  processor = new grafodb.Operations.Processor(handler);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            System.out.println("Servidor iniciado!");
            server.serve();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    */
}
