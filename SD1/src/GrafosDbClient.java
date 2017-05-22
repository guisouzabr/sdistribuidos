/**
 * Created by Guilherme on 5/21/17.
 */

import grafodb.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import java.util.Scanner;
public class GrafosDbClient
{
    public static void main(String [] args)
    {
        try {
            TTransport transport = new TSocket("localhost",9000);
            transport.open();


            TProtocol protocolo = new TBinaryProtocol(transport);

            grafodb.Operations.Client client = new grafodb.Operations.Client ( protocolo ) ;

            int op = 0;
            Scanner leitura = new Scanner(System.in);
            while(op != 13)
            {
                op = 0;
                System.out.println("Bem-Vindo ao cliente do Grafos Server");
                System.out.println("O que você deseja fazer?");
                System.out.println("1 - Inserir vértice");
                System.out.println("2 - Inserir Aresta");
                System.out.println("3 - Deletar vértice");
                System.out.println("4 - Deletar Aresta");
                System.out.println("5 - Atualizar vértice");
                System.out.println("6 - Atualizar Aresta");
                System.out.println("7 - Exibir Grafo");
                System.out.println("8 - Exibir Vértices");
                System.out.println("9 - Exibir Arestas");
                System.out.println("10 - Exibir Arestas de um vértice");
                System.out.println("11 - Exibir vértices de uma aresta");
                System.out.println("12 - Exibir Vizinhança");
                System.out.println("13 - Encerrar");
                op = leitura.nextInt();
                switch(op){
                    case 1:
                        Vertice novo = new Vertice();
                        System.out.println("Nome do vértice: ");
                        novo.nome = leitura.nextInt();
                        System.out.println("Peso: ");
                        novo.peso = leitura.nextDouble();
                        System.out.println("Descrição: ");
                        leitura.nextLine();
                        novo.desc = leitura.nextLine();
                        novo.cor = 1;
                        client.criaVertice(novo.nome,novo.cor,novo.peso,novo.desc);
                        System.out.println("Tecle enter");
                        leitura.nextLine();
                        break;
                    case 2:
                        Aresta anovo = new Aresta();
                        System.out.println("Primeiro Vértice: ");
                        anovo.v1 = leitura.nextInt();
                        System.out.println("Segundo Vértice: ");
                        anovo.v2 = leitura.nextInt();
                        System.out.println("Peso: ");
                        anovo.peso = leitura.nextDouble();
                        System.out.println("Descrição: ");
                        leitura.nextLine();
                        anovo.desc = leitura.nextLine();
                        System.out.println("1 - Direcionado(Do primeiro para o segundo), 2 - Bidirecionado");
                        anovo.flag = leitura.nextInt();
                        client.criaAresta(anovo.v1,anovo.v2,anovo.peso,anovo.flag,anovo.desc);
                        System.out.println("Tecle enter");
                        leitura.nextLine();
                        break;
                    case 3:
                        int del;
                        System.out.println(client.listarVertices());
                        System.out.println("Digite o nome do vertice que deseja deletar: ");
                        del = leitura.nextInt();
                        client.removeVertice(del);
                        break;
                    case 4:
                        int del2;
                        int del1;
                        System.out.println(client.listarArestas());
                        System.out.println("Digite os vertices da aresta que deseja deletar: ");
                        del1 = leitura.nextInt();
                        del2 = leitura.nextInt();
                        client.removeAresta(del1,del2);
                        break;
                    case 5:
                        System.out.println(client.listarVertices());
                        Vertice novou = new Vertice();
                        System.out.println("Digite o nome do vértice que será atualizado: ");
                        novou.nome = leitura.nextInt();
                        System.out.println("Peso: ");
                        novou.peso = leitura.nextDouble();
                        System.out.println("Descrição: ");
                        leitura.nextLine();
                        novou.desc = leitura.nextLine();
                        novou.cor = 1;
                        client.atuVertice(novou,novou.nome);
                        System.out.println("Tecle enter");
                        leitura.nextLine();
                        break;
                    case 6:
                        Aresta anovou = new Aresta();
                        System.out.println("Primeiro Vértice da aresta que será modificada: ");
                        anovou.v1 = leitura.nextInt();
                        System.out.println("Segundo Vértice da aresta que será modificada:: ");
                        anovou.v2 = leitura.nextInt();
                        System.out.println("Peso: ");
                        anovou.peso = leitura.nextDouble();
                        System.out.println("Descrição: ");
                        leitura.nextLine();
                        anovou.desc = leitura.nextLine();
                        System.out.println("1 - Direcionado(Do primeiro para o segundo), 2 - Bidirecionado");
                        anovou.flag = leitura.nextInt();
                        client.atuAresta(anovou,anovou.v1,anovou.v2);
                        System.out.println("Tecle enter");
                        leitura.nextLine();
                        System.out.println(client.listarArestas());
                        break;
                    case 7:
                        System.out.println(client.exibirGrafo());
                        try {
                            Thread.sleep(5000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        System.out.println(client.listarVertices());
                        try {
                            Thread.sleep(5000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        System.out.println(client.listarArestas());
                        try {
                            Thread.sleep(5000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    case 10:
                        System.out.println("Digite o nome do vértice: ");
                        int nvertice = leitura.nextInt();
                        System.out.println(client.listarAvertice(nvertice));
                        break;
                    case 11:
                        System.out.println("Digite o nome do vértice1: ");
                        int nvertice1 = leitura.nextInt();
                        System.out.println("Digite o nome do vértice2: ");
                        int nvertice2 = leitura.nextInt();
                        System.out.println(client.listarVaresta(nvertice1,nvertice2));
                        break;
                    case 12:
                        System.out.println("Digite o vértice que deseja conferir os vizinhos: ");
                        int vi = leitura.nextInt();
                        System.out.println(client.listarVizinhos(vi));
                        try {
                            Thread.sleep(5000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        break;
                    case 13:
                        System.out.println("Programando encerrado.");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            }
            transport.close();
        }catch (TException x){
            x.printStackTrace();
        }
    }

}
