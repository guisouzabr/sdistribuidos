namespace java grafodb

exception VerticeNotFound
{

}

struct Vertice
{
	1:i32 nome,
	2:i32 cor,
	3:double peso,
	4:string desc
}
struct Aresta
{
	1:i32 v1,
	2:i32 v2,
	3:double peso,
	4:i16 flag,
	5:string desc
}
struct Grafo
{
	1:list<Vertice> v,
	2:list<Aresta> a
}

service Operations
{
	bool criaVertice (1:i32 nome,2:i32 cor, 3:double peso, 4:string desc),
	bool criaAresta(1:i32 v1,2:i32 v2, 3:double peso, 4:i16 flag, 5:string desc),
	bool removeVertice(1:i32 nome),
	bool removeAresta(1:i32 v1,2:i32 v2),
	bool atuVertice(1:Vertice v,2:i32 nome),
	bool atuAresta(1:Aresta a,2:i32 v1,3:i32 v2),

	string exibirGrafo (),
	string  listarVertices(),
	string  listarArestas(),

    string listarAvertice(1:i32 nome),
	string  listarVizinhos(1:i32 nome),

	Vertice getVertice(1:i32 nome),
	Aresta getAresta(1:i32 v1,2:i32 v2)
 }