/**
 * Created by Guilherme sd on 5/21/17.
 */
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import grafodb.*;
public class GrafosHandler implements grafodb.Operations.Iface
{
    private  Grafo grafos = new Grafo(new ArrayList<Vertice>(),new ArrayList<Aresta>());

    //Persistência em arquivo
    public synchronized void salvarGrafo(String caminho) {
        try
        {
            FileOutputStream saveFile = new FileOutputStream(caminho);
            ObjectOutputStream stream = new ObjectOutputStream(saveFile);
            // salva o objeto
            stream.writeObject((Object)grafos);
            stream.close();
        } catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
    //Recuperar grafo
        public synchronized void recuperaGrafo(String caminho) {
            Object objeto = null;
            try {
                FileInputStream restFile = new FileInputStream(caminho);
                ObjectInputStream stream = new ObjectInputStream(restFile);
                // recupera o objeto
                objeto = stream.readObject();
                if(objeto != null)
                    grafos = (Grafo)objeto;
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    @Override
    public synchronized boolean criaVertice(int nome, int cor, double peso, String desc)
    {
        Vertice novo = new Vertice();
        if(novo == null) //Foi criado?
            return false;
        else {
            if(grafos.getV().isEmpty()) {
                novo.nome = nome;
                novo.cor = cor;
                novo.peso = peso;
                novo.desc = desc;
                grafos.addToV(novo);
                return true;
            }
            else {
                for (Vertice ve : grafos.getV()) {
                    if (ve.nome == nome) //Não aceita vertices com mesmo nome
                        return false;//Exception de Noã aceita vértices iguais
                }
                novo.nome = nome;
                novo.cor = cor;
                novo.peso = peso;
                novo.desc = desc;
                grafos.addToV(novo);
                return true;
            }
        }
    }

    @Override
    public synchronized boolean criaAresta(int v1,int v2, double peso, int flag, String desc)
    {
        int ctrl = 0;
        Aresta novo = new Aresta();
        if (novo != null) {
            for(Vertice v : grafos.getV() ){
                if(v.nome == v1 || v.nome == v2) //Os vértices existem?
                    ctrl++;
            }
            if(ctrl != 2)
                return false; //exception de vértices não existem.
            else{
                if(grafos.getA().isEmpty())
                {
                    novo.v1 = v1;
                    novo.v2 = v2;
                    novo.peso = peso;
                    novo.flag = flag;
                    novo.desc = desc;
                    if(novo.flag == 2){
                        grafos.addToA(new Aresta(novo.v2,novo.v1,novo.peso,novo.flag,novo.desc));
                    }
                    grafos.addToA(novo);
                    return true;
                }
                else {
                    for (Aresta as : grafos.getA()) {
                        if (as.v1 == v1 && as.v2 == v2)
                            return false; //exception de já existe uma aresta ligando esses vértices
                    }
                    novo.v1 = v1;
                    novo.v2 = v2;
                    novo.peso = peso;
                    novo.flag = flag;
                    novo.desc = desc;
                    if(novo.flag == 2){
                        grafos.addToA(new Aresta(novo.v2,novo.v1,novo.peso,novo.flag,novo.desc));
                    }
                    grafos.addToA(novo);
                    return true;
                }
            }
        }
        else
            return false;
    }

    @Override
    public synchronized boolean removeVertice(int nome)
    {
        //Verifica se o vértice existe, remove as arestas ligadas a ele e o remove
        for(Vertice V : grafos.getV())
        {
            if(V.nome == nome){
                //Removendo arestas que estão ligadas nesse vértice
                for(int i=grafos.getA().size()-1; i >= 0;i--) {
                    if(grafos.getA().get(i).v1 == nome || grafos.getA().get(i).v2 == nome)
                    {
                        grafos.a.remove(i);
                        if(grafos.getA().isEmpty()) {
                            break;
                        }
                    }
                }
                //Removendo o vértice
                grafos.getV().remove(V);
                return true; //vértice foi encontrado, removido e suas arestas também.
            }
        }
        return false; //vértice não foi encontrado
    }

    @Override
    public synchronized boolean removeAresta(int v1,int v2)
    {
        for(Aresta as : grafos.getA())
        {
            if(as.v1 == v1 && as.v2 == v2)
            {
                //Aresta encontrada e removida
                grafos.getA().remove(as);
                return true; // tudo ok
            }
        }
        return false; //Aresta não encontrada
    }

    @Override
    public synchronized boolean atuVertice(Vertice v,int nome)
    {
        if(v == null || v.nome != nome)
            return false; //novo vértice nullo ou com nome diferente do vértice buscado, gera excepetion (vértices não podem ter o nome modificado).
        for(Vertice vs : grafos.getV())
        {
            if(vs.nome == nome)
            {
                vs.desc = v.desc;
                vs.cor = v.cor;
                vs.peso = v.peso;
                return true; //modificação ocorreu com sucesso;
            }
        }
        return false; //Vértice não encontrado na lista

    }

    @Override
    public synchronized boolean atuAresta(Aresta a,int v1,int v2)
    {
        if(a == null || a.v1 != v1 || a.v2 != v2)
            return false; //tentou modificar vértices da aresta ou passou uma aresta nova nula.
        for(Aresta as : grafos.getA())
        {
            //Aresta encontrada, atualizar valores permitidos
            if(as.v1 == v1 && as.v2 == v2)
            {
                as.desc = a.desc;
                as.peso = a.peso;
                if(a.flag == 2 && as.flag != 2){
                    Aresta novo = new Aresta(a.v2,a.v1,a.peso,a.flag,a.desc);
                    grafos.addToA(novo);
                }
                as.flag = a.flag;
                return true; //Modificação ocorreu com sucesso;
            }
        }
        return false; // aresta não foi encontrada na lista de arestadas
    }

    //Função para dizer a cor a partir de um número inteiro
    public String getCores(int cor)
    {
        return "azul";
    }

    @Override
    public synchronized String exibirGrafo ()
    {
        String msg = "Seu grafo: \n";
        msg = msg + listarVertices();
        msg = msg +listarArestas();
        return msg;
    }

    @Override
    public synchronized String listarVertices()
    {
        String s = "vértices: \n";
        for(Vertice v: grafos.getV())
        {
            s = s+ "Vértice: "+ v.nome +" "+
                    "Cor: "+getCores(v.cor) +" "+
                    "Peso: "+ v.peso + " "+
                    "Descrição: "+ v.desc+ "\n";
        }
        return s;
    }

    @Override
    public synchronized String listarArestas()
    {
        String s = "Arestas: \n";
        for(Aresta v: grafos.getA())
        {
            s = s+ "Vértice 1: "+ v.v1 +" "+
                    "Vértice 2: "+v.v2 +" "+
                    "Peso: "+ v.peso + " "+
                    "Flag: "+ v.flag + " "+
                    "Descrição: "+ v.desc+ "\n";
        }
        return s;
    }

    @Override
    public synchronized String listarVizinhos(int nome)
    {
        String s = "Os vértices vizinhos de :"+nome+" são: \n";

        for (Aresta as : grafos.getA())
        {
            if(as.v1 == nome)
            {
                Vertice v = getVertice(as.v2);
                s = s+ "Vértice: "+ v.nome +" "+
                        "Cor: "+getCores(v.cor) +" "+
                        "Peso: "+ v.peso + " "+
                        "Descrição: "+ v.desc+ "\n";
            }
            else if(as.v2 == nome)
            {
                Vertice v = getVertice(as.v1);
                s = s+ "Vértice: "+ v.nome +" "+
                        "Cor: "+getCores(v.cor) +" "+
                        "Peso: "+ v.peso + " "+
                        "Descrição: "+ v.desc+ "\n";
            }
        }
        return s;
    }

    @Override
    public synchronized Vertice getVertice(int nome)
    {
        for(Vertice v: grafos.getV())
        {
            if(v.nome == nome) {
                System.out.println( "Vértice: " + v.nome + " " +
                                    "Cor: " + getCores(v.cor) + " " +
                                    "Peso: " + v.peso + " " +
                                    "Descrição: " + v.desc + "\n");
                return v;
            }
        }
        //Cliente: Quais valores estão no vertice y
        return null;
    }

    @Override
    public synchronized Aresta getAresta(int v1,int v2)
    {
        for(Aresta v: grafos.getA())
        {
            if(v.v1 == v1 && v.v2 == v2)
                System.out.println( "Vértice 1: "+ v.v1 +" "+
                                    "Vértice 2: "+v.v2 +" "+
                                    "Peso: "+ v.peso + " "+
                                    "Descrição: "+ v.desc+ "\n");
                return v;
        }
        //Cliente: Quais valores estão na aresta X
        return null;
    }

    @Override
    public synchronized String listarAvertice(int nome)
    {
        String s = "Arestas do vértice :"+nome+"\n";
        for(Aresta as : grafos.getA())
        {
            if(as.v1 == nome || as.v2 == nome)
                s = s+ "Vértice 1: "+ as.v1 +" "+
                        "Vértice 2: "+as.v2 +" "+
                        "Peso: "+ as.peso + " "+
                        "Flag: "+ as.flag + " "+
                        "Descrição: "+ as.desc+ "\n";
        }
        return s;
    }

    public synchronized String listarVaresta(int v1, int v2)
    {
        String s = "Vértices da aresta desejada: \n";
        for(Vertice v: grafos.getV())
        {
            if(v.nome == v1 || v.nome == v2) {
                s = s + "Vértice: " + v.nome + " " +
                        "Cor: " + getCores(v.cor) + " " +
                        "Peso: " + v.peso + " " +
                        "Descrição: " + v.desc + "\n";
            }
        }
        return s;
    }
}
