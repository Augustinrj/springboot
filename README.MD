Spring Boot Application

GetProductById
Select by id
http://localhost:8080/Produits/1

GetProduct
listes produits
http://localhost:8080/Produits

PostProduct
ajouter un produit ,avec content-type
Content-Type application/json
http://localhost:8080/Produits
{
	"id": 4,
	"nom": "Poney en bois cracheur de feu",
	"prix": 145
}

Produits
UPDATE
Content-Type application/json
http://localhost:8080/Produits


http://localhost:8080/retrait/C001
http://localhost:8080/retrait

http://localhost:8080/versement