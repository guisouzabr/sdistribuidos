/**
 * Created by Guilherme on 5/21/17.
 */

import grafodb.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

public class GrafosDbClient
{
    public static void main(String [] args)
    {
        try {
            TTransport transport = new TSocket("localhost",9000);
            transport.open();


            TProtocol protocolo = new TBinaryProtocol(transport);

            grafodb.Operations.Client client = new grafodb.Operations.Client ( protocolo ) ;
            client.criaVertice(1,1,1.5,"Primeiro vértice");
            client.criaVertice(2,1,1.6,"Segundo vértice");
            client.criaVertice(3,5,1.8,"Terceiro vértice");
            client.criaAresta(1,2,3, (short) 1,"Primeira aresta");
            try {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(client.exibirGrafo());
            transport.close();
        }catch (TException x){
            x.printStackTrace();
        }
    }

}
